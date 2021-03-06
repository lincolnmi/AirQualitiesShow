package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;
import jason.tongji.config.GlobalConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/24.
 */
public class MonitorLocation extends Model<MonitorLocation> {
    public static MonitorLocation dao = new MonitorLocation();

    public ArrayList<MonitorLocation> getMonitorLocations(String cityName,String timePoint) {
        timePoint = "2016-04-13T12:00:00Z";
        int city_id = City.dao.getCityId(cityName);
        String sql = "select * from monitorlocation where timePoint = '"+timePoint+"'" + " AND city_id = " + city_id;
        System.out.println(sql);
        return (ArrayList<MonitorLocation>) dao.find(sql);
    }

    public List<MonitorLocation> getMonitorLocation(String locationName, String timePoint) {
        timePoint = "2016-04-13T12:00:00Z";
        String sql = "select * from monitorlocation where timePoint = '"+timePoint+"'"
                + " AND monitorName = '" + locationName + "'";
        System.out.println(sql);
        return dao.find(sql);
    }


    public List<MonitorLocation> getRangeMonitorData(String locationName, String timePoint,String pollution) {
        //timePoint = "2016-03-29T12:00:00Z";
        String sql = "select * from monitorlocation where timePoint >= '"+timePoint+"' and timePoint <= '"
                + GlobalConfig.timePoint + "' AND monitorName = '" + locationName + "'";
        System.out.println(sql);
        return dao.find(sql);
    }

}
