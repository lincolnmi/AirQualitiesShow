package jason.tongji.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.sun.prism.impl.Disposer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016/3/23.
 */
public class Province extends Model<Province> {
    public static Province dao = new Province();

    public ArrayList<Province> getAllProvince() {
        return (ArrayList<Province>) Province.dao.find("select * from province");
    }

    public String getProvinceName(int province_id) {
        return String.valueOf(Province.dao.find("select province_name from province " +
                "where province_id = " + province_id));
    }

}
