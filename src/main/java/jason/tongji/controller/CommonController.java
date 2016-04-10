package jason.tongji.controller;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CommonController extends Controller {
	public void index(){
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_INDEX);

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

        String timePoint = getTime(Calendar.getInstance());
        List<MonitorLocation> monitorLocations = MonitorLocation.dao.getMonitorLocations(cityName, timePoint);
        HashMap<String, ArrayList<String>> provinceCities = CityProvince.getProvinceCities();
        HashMap<String,ArrayList<String>> cityPrefixes = City.getCityPrefixes();
        AirData airData = AirData.dao.getAirDataByCity(cityName,timePoint);
        ArrayList<AirData> airDatas = AirData.dao.getAirData(timePoint);
        int rank = getRank(airDatas,airData);
        int size = airDatas.size();
        int percent = (int) ((size-rank)*1.0/size*100);
        CityAir cityAir = new CityAir(airData.getStr("quality"));
        setAttr("currentCity", cityName);
        setAttr("provinceCities", provinceCities);
        setAttr("cityPrefixes", cityPrefixes);
        setAttr("monitorLocations", monitorLocations);
        setAttr("airData", airData);
        setAttr("percent", percent);
        setAttr("rank", rank);
        setAttr("cityAir", cityAir);
        setAttr("timePoint", timePoint.replace("T"," ").replace("Z"," "));
        render("/page/index.html");
	}

    private int getRank(ArrayList<AirData> airDatas, AirData airData) {
        int size = airDatas.size();
        for (int i=0;i<size;i++) {
            if (airDatas.get(i).get("area").equals(airData.get("area"))) {
                return i+1;
            }
        }
        return 1;
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
