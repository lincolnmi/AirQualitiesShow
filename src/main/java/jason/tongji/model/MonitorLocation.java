package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/24.
 */
public class MonitorLocation extends Model<MonitorLocation> {
    public static MonitorLocation dao = new MonitorLocation();

    public ArrayList<MonitorLocation> getMonitorLocations(String cityName,String timePoint) {
        timePoint = "2016-03-23T18:00:00Z";
        int city_id = City.dao.getCityId(cityName);
        String sql = "select * from monitorlocation where timePoint = '"+timePoint+"'" + " AND city_id = " + city_id;
        System.out.println(sql);
        return (ArrayList<MonitorLocation>) dao.find(sql);
    }

}
