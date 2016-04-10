package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.model.City;

import java.util.ArrayList;

/**
 * Created by Jason on 2016/4/10.
 */
public class CheckCityController extends Controller {

    public void index() {
        String cityname = getPara("cityname");
        ArrayList<String> cities = City.getAllCities();
        if (cities.contains(cityname)) {
            setAttr("err", 0);
            setAttr("cityname", cityname);
        } else {
            setAttr("err", 1);
        }
        renderJson();
    }

}
