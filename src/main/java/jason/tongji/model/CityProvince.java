package jason.tongji.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jason on 2016/3/24.
 */
public class CityProvince {
    private static HashMap<String,String> cityResults;
    private static HashMap<String,ArrayList<String>> provinceResults;

    /*
    key-city,value-province
     */
    public static HashMap<String,String> getCityProvinces() {
        if (cityResults==null) {
            String sql = "select * from cities c, province p where c.province_id=p.province_id";
            List<Record> records = Db.find(sql);
            cityResults = new HashMap<String, String>();
            for (Record record:records) {
                String city = record.get("city_name");
                String province = record.get("province_name");
                cityResults.put(city,province);
            }
        }
        return cityResults;
    }

    /*
    key-province,value-List<city>
     */
    public static HashMap<String,ArrayList<String>> getProvinceCities() {
        if (provinceResults==null) {
            String sql = "select * from cities c, province p where c.province_id=p.province_id";
            List<Record> records = Db.find(sql);
            provinceResults = new HashMap<String, ArrayList<String>>();
            for (Record record:records) {
                String city = record.get("city_name");
                String province = record.get("province_name");
                ArrayList<String> cities = null;
                if (provinceResults.containsKey(province)) {
                    cities = provinceResults.get(province);
                } else {
                    cities = new ArrayList<String>();
                }
                cities.add(city);
                provinceResults.put(province, cities);
            }
        }
        return provinceResults;
    }


}
