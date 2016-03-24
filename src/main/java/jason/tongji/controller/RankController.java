package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.AirData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jason on 2016/3/23.
 */
public class RankController extends Controller {

    public void index() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_RANK);
        String timePoint = getTime();
        ArrayList<AirData> airData = AirData.dao.getAirData(timePoint);
        System.out.println(airData.size());
        setAttr("airData", airData);
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));
        render("/page/rank/rank.html");
    }

    private String getTime() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        if (minute>10) {
            calendar.add(calendar.HOUR,-1);
        } else {
            calendar.add(calendar.HOUR,-2);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
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
