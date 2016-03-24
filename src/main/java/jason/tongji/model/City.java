package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/24.
 */
public class City extends Model<City> {
    public static final City dao = new City();

    public int getCityId(String cityName) {
        String sql = "select * from cities where city_name= '" + cityName +"'";
        System.out.println(sql);
        ArrayList<City> city = (ArrayList<City>) City.dao.find(sql);
        return city.get(0).getInt("city_id");
    }
}
