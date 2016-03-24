package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/23.
 */
public class AirData extends Model<AirData> {
    public static AirData dao = new AirData();

    public ArrayList<AirData> getAirData(String timePoint) {
        String sql = "select * from airdata where timePoint = '2016-03-12T18:00:00Z'";
        System.out.println(sql);
        return (ArrayList<AirData>) dao.find(sql);
    }

}
