package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.model.AirData;
import jason.tongji.model.MonitorLocation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jason on 2016/3/24.
 */
public class CityController extends Controller {

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
        setAttr("airData",airData);
        setAttr("cityName",cityName);
        setAttr("timePoint",timePoint.replace("T"," ").replace("Z"," "));
        setAttr("monitorlocations",monitorLocations);
        render("/page/city/city.html");
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
}
