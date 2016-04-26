package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.model.AirData;
import jason.tongji.model.City;
import jason.tongji.model.CityProvince;
import jason.tongji.model.MonitorLocation;
import jason.tongji.tool.EchartsDataScript;
import jason.tongji.tool.Helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jason on 2016/3/24.
 */
public class CityController extends Controller {
    private EchartsDataScript echartsDataScript = new EchartsDataScript();

    public void index() {
        String cityName = null;
        String para = getPara(0);
        if (para == null) {
            cityName = "上海";
        } else {
            try {
                cityName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cityName);
        Calendar calendar = Calendar.getInstance();
        String timePoint = getTime(calendar);
        List<MonitorLocation> monitorLocations = MonitorLocation.dao.getMonitorLocations(cityName,timePoint);
        AirData airData = AirData.dao.getAirDataByCity(cityName,timePoint);
        String color = Helper.getColorByQuality(airData.getStr("quality"));
        timePoint = getLastDayTime();
        List<AirData> last24AirData = AirData.dao.getLast24HourAirDataByCity(cityName, timePoint);
        initEchartsDataScript(last24AirData,color,"pg_content_24h","graph-last24-aqi.js");

        color = Helper.getColorByQuality(airData.getStr("quality"));
        List<AirData> lastWeekAirData = getLastWeekAirData(cityName, timePoint);
        initEchartsDataScript(lastWeekAirData,color,"pg_content_7d","graph-last7d-aqi.js");

        color = Helper.getColorByQuality(airData.getStr("quality"));
        List<AirData> lastMonthAirData = getLastMonthAirData(cityName, timePoint);
        initEchartsDataScript(lastMonthAirData,color,"pg_content_30d","graph-last30d-aqi.js");

        String tips = Helper.getTips(airData.getStr("quality"));

        HashMap<String, ArrayList<String>> provinceCities = CityProvince.getProvinceCities();
        HashMap<String,ArrayList<String>> cityPrefixes = City.getCityPrefixes();
        setAttr("currentCity", cityName);
        setAttr("provinceCities", provinceCities);
        setAttr("cityPrefixes", cityPrefixes);

        setAttr("airData", airData);
        setAttr("cityName",cityName);
        setAttr("timePoint",timePoint.replace("T"," ").replace("Z"," "));
        setAttr("monitorlocations",monitorLocations);
        setAttr("tips",tips);
        render("/page/city/city.html");
    }

    private List<AirData> getLastMonthAirData(String cityName, String timePoint) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        timePoint = getTime(calendar);
        return AirData.dao.getRangeAirData(cityName,timePoint);
    }

    private void initEchartsDataScript(List<AirData> last24AirData,String color,String id,String fileName) {
        echartsDataScript.setId(id);
        ArrayList<String> colors = new ArrayList<String>();
        colors.add(color);
        echartsDataScript.setColors(colors);
        ArrayList<String> legends = new ArrayList<String>();
        legends.add("AQI");
        echartsDataScript.setLegends(legends);
        ArrayList<String> xAxis_data = new ArrayList<String>();
        ArrayList<String> seriesData = new ArrayList<String>();
        for (AirData airData:last24AirData) {
            String timePoint = airData.getStr("timePoint");
            xAxis_data.add(timePoint.substring(8, 13).replace("T", "Day")+"H");
            seriesData.add(String.valueOf(airData.getDouble("aqi")));
        }
        echartsDataScript.setxAxis_data(xAxis_data);
        ArrayList<String> seriesNames = new ArrayList<String>();
        seriesNames.add("AQI");
        echartsDataScript.setSeries_names(seriesNames);
        ArrayList<String> seriesTypes = new ArrayList<String>();
        seriesTypes.add("line");
        echartsDataScript.setSeries_types(seriesTypes);
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        data.add(seriesData);
        echartsDataScript.setSeries_data(data);
        String path = this.getClass().getClassLoader().getResource("/").getPath();
        System.out.println(path);
        path = path.replace("/WEB-INF/classes/","/resource/static/js/"+fileName);
        echartsDataScript.writeJS(path);
    }

    private List<AirData> getLastWeekAirData(String cityName, String timePoint) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        timePoint = getTime(calendar);
        timePoint = "2016-03-20T10:00:00Z";
        return AirData.dao.getRangeAirData(cityName,timePoint);
    }

    private String getTime(Calendar calendar) {
        int minute = calendar.get(Calendar.MINUTE);
        if (minute>10) {
            calendar.add(calendar.HOUR,-1);
        } else {
            calendar.add(calendar.HOUR,-2);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String timePoint = year + "-";
        if (month<10) {
            timePoint += "0";
        }
        timePoint += month + "-";
        if (day<10) {
            timePoint += "0";
        }
        timePoint += day + "T";
        if (hour<10) {
            timePoint += "0";
        }
        timePoint += hour + ":00:00Z";
        return timePoint;
    }

    private String getLastDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_MONTH,-1);
        return getTime(calendar);
    }


}
