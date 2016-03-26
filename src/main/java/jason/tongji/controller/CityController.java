package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.model.AirData;
import jason.tongji.model.MonitorLocation;
import jason.tongji.tool.EchartsDataScript;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
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
        String color = getColorByQuality(airData.getStr("quality"));
        timePoint = getLastDayTime();
        List<AirData> last24AirData = AirData.dao.getLast24HourAirDataByCity(cityName,timePoint);

        initEchartsDataScript(last24AirData,color);
        setAttr("airData",airData);
        setAttr("cityName",cityName);
        setAttr("timePoint",timePoint.replace("T"," ").replace("Z"," "));
        setAttr("monitorlocations",monitorLocations);
        render("/page/city/city.html");
    }

    private void initEchartsDataScript(List<AirData> last24AirData,String color) {
        echartsDataScript.setId("pg_content_24h");
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
        path = path.replace("/WEB-INF/classes/","/resource/static/js/graph-last24-aqi.js");
        echartsDataScript.writeJS(path);
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

    private String getColorByQuality(String quality) {
        System.out.println(quality);
        if (quality.equals("优")) {
            return "#7db364";
        } else if (quality.equals("良")) {
            return "#e3b649";
        } else if (quality.equals("轻度污染")) {
            return "#e38748";
        } else if (quality.equals("中度污染")) {
            return "#e07373";
        } else if (quality.equals("重度污染")) {
            return "#9590c9";
        } else if (quality.equals("严重污染")) {
            return "#9f6aa2";
        } else {
            return "#7db364";
        }
    }
}
