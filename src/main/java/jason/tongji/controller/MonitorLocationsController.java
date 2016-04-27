package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.City;
import jason.tongji.model.MonitorLocation;
import jason.tongji.tool.EchartsDataScript;
import jason.tongji.tool.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jason on 2016/3/27.
 */
public class MonitorLocationsController extends Controller {

    public void index() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "AQI";
        processCommon(locationName,pollution);
        setAttr("pollution","AQI");
        render("/page/monitor/monitorlocation.html");
    }

    private void processCommon(String locationName,String pollution) {
        //String timePoint = getTime(Calendar.getInstance());
        String timePoint = GlobalConfig.timePoint;
        MonitorLocation monitorLocation = MonitorLocation.dao.getMonitorLocation(locationName,timePoint).get(0);
        String cityName = City.dao.getCityName(monitorLocation.getInt("city_id"));
        List<MonitorLocation> monitorLocations = MonitorLocation.dao.getMonitorLocations(cityName, timePoint);
        List<MonitorLocation> lastDayMonitorData = getLast24HourMonitorData(locationName, timePoint, pollution);
        List<MonitorLocation> lastWeekMonitorData = getLastWeekMonitorData(locationName, timePoint, pollution);
        List<MonitorLocation> lastMonthMonitorData = getLastMonthMonitorData(locationName, timePoint, pollution);
        String color = Helper.getColorByQuality(monitorLocation.getStr("quality"));
        initEchartsDataScript(lastDayMonitorData,color,pollution,"pg_content_24h","location-last24-pollution.js");

        initEchartsDataScript(lastWeekMonitorData,color,pollution,"pg_content_7d","location-last7-pollution.js");

        initEchartsDataScript(lastMonthMonitorData,color,pollution,"pg_content_30d","location-last30-pollution.js");

        String tips = Helper.getTips(monitorLocation.getStr("quality"));
        setAttr("tips",tips);
        setAttr("cityName",cityName);
        setAttr("monitorlocation",monitorLocation);
        setAttr("monitorLocations",monitorLocations);
        setAttr("timePoint", timePoint.replace("T", " ").replace("Z", " "));
    }

    private List<MonitorLocation> getLastWeekMonitorData(String locationName, String timePoint, String pollution) {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        timePoint = getTime(calendar);
        return MonitorLocation.dao.getRangeMonitorData(locationName, timePoint, pollution);
    }

    private List<MonitorLocation> getLast24HourMonitorData(String locationName, String timePoint, String pollution) {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        timePoint = getTime(calendar);
        return MonitorLocation.dao.getRangeMonitorData(locationName, timePoint, pollution);
    }

    private List<MonitorLocation> getLastMonthMonitorData(String locationName, String timePoint, String pollution) {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        calendar.add(Calendar.MONTH,-1);
        timePoint = getTime(calendar);
        return MonitorLocation.dao.getRangeMonitorData(locationName,timePoint,pollution);
    }

    private void initEchartsDataScript(List<MonitorLocation> locationData,String color,
                                       String pollution,String id,String fileName) {
        EchartsDataScript echartsDataScript = new EchartsDataScript();
        echartsDataScript.setId(id);
        ArrayList<String> colors = new ArrayList<String>();
        colors.add(color);
        echartsDataScript.setColors(colors);
        ArrayList<String> legends = new ArrayList<String>();
        legends.add(pollution);
        echartsDataScript.setLegends(legends);
        ArrayList<String> xAxis_data = new ArrayList<String>();
        ArrayList<String> seriesData = new ArrayList<String>();
        for (MonitorLocation locationDatum:locationData) {
            String timePoint = locationDatum.getStr("timePoint");
            xAxis_data.add(timePoint.substring(8, 13).replace("T", "Day") + "H");
            seriesData.add(String.valueOf(locationDatum.getDouble(pollution.toLowerCase())));
        }
        echartsDataScript.setxAxis_data(xAxis_data);
        ArrayList<String> seriesNames = new ArrayList<String>();
        seriesNames.add(pollution);
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

    private String getTime(Calendar calendar) {
        int minute = calendar.get(Calendar.MINUTE);
        if (minute>10) {
            calendar.add(calendar.HOUR,-1);
        } else {
            calendar.add(calendar.HOUR,-2);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
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

    private Calendar getCalendar(String timePoint) {
        Calendar calendar = Calendar.getInstance();
        timePoint = timePoint.replace("Z","");
        String[] times = timePoint.split("T");
        String[] day = times[0].split("-");
        String[] second = times[1].split(":");
        calendar.set(Calendar.YEAR,Integer.valueOf(day[0]));
        calendar.set(Calendar.MONTH,Integer.valueOf(day[1]));
        calendar.set(Calendar.DAY_OF_MONTH,Integer.valueOf(day[2]));
        calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(second[0]));
        calendar.set(Calendar.MINUTE,Integer.valueOf(second[1]));
        calendar.set(Calendar.SECOND,Integer.valueOf(second[2]));

        return calendar;
    }

    public void AQI() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "AQI";
        processCommon(locationName,pollution);
        setAttr("pollution","AQI");
        render("/page/monitor/monitorlocation.html");
    }

    public void PM25() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "PM25";
        processCommon(locationName,pollution);
        setAttr("pollution","PM25");
        render("/page/monitor/monitorlocation.html");
    }

    public void PM10() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "PM10";
        processCommon(locationName,pollution);
        setAttr("pollution","PM10");
        render("/page/monitor/monitorlocation.html");
    }

    public void SO2() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "SO2";
        processCommon(locationName,pollution);
        setAttr("pollution","SO2");
        render("/page/monitor/monitorlocation.html");
    }

    public void NO2() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "NO2";
        processCommon(locationName,pollution);
        setAttr("pollution","NO2");
        render("/page/monitor/monitorlocation.html");
    }

    public void CO() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "CO";
        processCommon(locationName,pollution);
        setAttr("pollution","CO");
        render("/page/monitor/monitorlocation.html");
    }

    public void O3() {
        String locationName = null;
        String para = getPara(0);
        if (para == null) {
            locationName = "普陀";
        } else {
            try {
                locationName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String pollution = "O3";
        processCommon(locationName,pollution);
        setAttr("pollution","O3");
        render("/page/monitor/monitorlocation.html");
    }


}
