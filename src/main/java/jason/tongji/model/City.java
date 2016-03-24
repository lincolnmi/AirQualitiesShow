package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Jason on 2016/3/24.
 */
public class City extends Model<City> {
    public static City dao = new City();

    public int getCityId(String cityName) {
        String sql = "select * from city where city_name= " + cityName;
        City city = (City) dao.find(sql);
        return city.getInt("city_id");
    }
}
