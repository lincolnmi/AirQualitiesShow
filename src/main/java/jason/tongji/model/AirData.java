package jason.tongji.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/23.
 */
public class AirData extends Model<AirData> {
    public static AirData dao = new AirData();

    public ArrayList<AirData> getAirData(String timePoint) {
        String sql = "select * from airdata where timePoint = '"+timePoint+"'";
        System.out.println(sql);
        return (ArrayList<AirData>) dao.find(sql);
    }

    public AirData getAirDataByCity(String cityName,String timePoint) {
        timePoint = "2016-03-23T19:00:00Z";
        String sql = "select * from airdata where timePoint = '"+timePoint+"' AND area = '" + cityName +"'";
        System.out.println(sql);
        return dao.find(sql).get(0);
    }

    public ArrayList<AirData> getLastWeekAirData(String timePoint) {
        String sql = "select truncate(AVG(aqi),0) as aqi,area,truncate(AVG(co),2) as co,truncate(AVG(no2),0) as no2,"
        + "truncate(AVG(o3),0) as o3,truncate(AVG(pm25),0) as pm25,truncate(AVG(pm10),0) as pm10,truncate(AVG(so2),0) as so2"
        + " from airdata where timePoint >= '" + timePoint + "' GROUP BY area  ORDER BY aqi";
        System.out.println(sql);
        //List<Record> results = Db.find(sql);
        ArrayList<AirData> results = (ArrayList<AirData>) dao.find(sql);
        for (AirData airData : results) {
            if (airData.getDouble("aqi")<50) {
                airData.set("quality","优");
            } else if (airData.getDouble("aqi")<100) {
                airData.set("quality","良");
            } else if (airData.getDouble("aqi")<150) {
                airData.set("quality","轻度污染");
            } else if (airData.getDouble("aqi")<200) {
                airData.set("quality","中度污染");
            } else if (airData.getDouble("aqi")<300) {
                airData.set("quality","重度污染");
            } else {
                airData.set("quality","严重污染");
            }
        }
        return results;
    }
}
