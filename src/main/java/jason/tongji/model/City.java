package jason.tongji.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jason on 2016/3/24.
 */
public class City extends Model<City> {
    public static final City dao = new City();
    private static HashMap<String,ArrayList<String>> cityPrefixes;

    public static ArrayList<String> getAllCities() {
        String sql = "select * from cities";
        List<Record> records = Db.find(sql);
        ArrayList<String> cities = new ArrayList<String>();
        for (Record record:records) {
            cities.add(record.getStr("city_name"));
        }
        return cities;
    }

    public int getCityId(String cityName) {
        String sql = "select * from cities where city_name= '" + cityName +"'";
        System.out.println(sql);
        ArrayList<City> city = (ArrayList<City>) City.dao.find(sql);
        return city.get(0).getInt("city_id");
    }

    public String getCityName(int cityId) {
        String sql = "select * from cities where city_id= " + cityId;
        System.out.println(sql);
        ArrayList<City> city = (ArrayList<City>) City.dao.find(sql);
        return city.get(0).getStr("city_name");
    }

    public static HashMap<String,ArrayList<String>> getCityPrefixes() {
        if (cityPrefixes==null) {
            String sql = "select * from cities";
            List<Record> records = Db.find(sql);
            cityPrefixes = new HashMap<String, ArrayList<String>>();
            for (Record record:records) {
                String city = record.get("city_name");
                String city_prefix = record.get("city_prefix");
                ArrayList<String> cities = null;
                if (cityPrefixes.containsKey(city_prefix)) {
                    cities = cityPrefixes.get(city_prefix);
                } else {
                    cities = new ArrayList<String>();
                }
                cities.add(city);
                cityPrefixes.put(city_prefix,cities);
            }
        }
        return cityPrefixes;
    }

}
