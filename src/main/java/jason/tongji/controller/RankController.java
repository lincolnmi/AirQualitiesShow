package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.AirData;
import jason.tongji.model.CityProvince;

import java.util.*;

/**
 * Created by Jason on 2016/3/23.
 */
public class RankController extends Controller {

    public void index() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_RANK);
        //String timePoint = getCurrentTime();
        String timePoint = GlobalConfig.timePoint;
        ArrayList<AirData> airData = AirData.dao.getAirData(timePoint);
        HashMap<String,String> cityProvinces = CityProvince.getCityProvinces();
        Iterator<AirData> it = airData.iterator();
        while(it.hasNext()){
            AirData air = it.next();
            if(!cityProvinces.containsKey(air.get("area"))){
                it.remove();
            }
        }
        setAttr("rankHeader", "today");
        setAttr("airData", airData);
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));
        setAttr("cityProvinces", cityProvinces);
        render("/page/rank/rank.html");
    }

    public void lastDay() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_RANK);
        String timePoint = getLastDayTime();
        ArrayList<AirData> airData = AirData.dao.getAirData(timePoint);
        HashMap<String,String> cityProvinces = CityProvince.getCityProvinces();
        Iterator<AirData> it = airData.iterator();
        while(it.hasNext()){
            AirData air = it.next();
            if(!cityProvinces.containsKey(air.get("area"))){
                it.remove();
            }
        }
        setAttr("rankHeader", "lastDay");
        setAttr("airData", airData);
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));
        setAttr("cityProvinces", cityProvinces);
        render("/page/rank/rank.html");
    }

    public void lastWeek() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_RANK);
        String timePoint = getLastWeekTime();
        ArrayList<AirData> airData = AirData.dao.getLastWeekAirData(timePoint);
        HashMap<String,String> cityProvinces = CityProvince.getCityProvinces();
        Iterator<AirData> it = airData.iterator();
        while(it.hasNext()){
            AirData air = it.next();
            if(!cityProvinces.containsKey(air.get("area"))){
                it.remove();
            }
        }
        setAttr("rankHeader", "lastWeek");
        setAttr("airData", airData);
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));
        setAttr("cityProvinces", cityProvinces);
        render("/page/rank/rank.html");
    }

    public void lastMonth() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_RANK);
        String timePoint = getLastMonthTime();
        ArrayList<AirData> airData = AirData.dao.getLastWeekAirData(timePoint);
        HashMap<String,String> cityProvinces = CityProvince.getCityProvinces();
        Iterator<AirData> it = airData.iterator();
        while(it.hasNext()){
            AirData air = it.next();
            if(!cityProvinces.containsKey(air.get("area"))){
                it.remove();
            }
        }
        setAttr("rankHeader", "lastMonth");
        setAttr("airData", airData);
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));
        setAttr("cityProvinces", cityProvinces);
        render("/page/rank/rank.html");
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

    private String getCurrentTime() {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        return getTime(calendar);
    }

    private String getLastDayTime() {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        calendar.add(calendar.DAY_OF_MONTH,-1);
        return getTime(calendar);
    }

    private String getLastWeekTime() {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        calendar.add(calendar.DAY_OF_MONTH,-7);
        return getTime(calendar);
    }

    private String getLastMonthTime() {
        Calendar calendar = getCalendar(GlobalConfig.timePoint);
        calendar.add(calendar.MONTH,-1);
        return getTime(calendar);
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

}
