package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;

/**
 * Created by Jason on 2016/3/18.
 */
public class DaqiangController extends Controller {

    public void biography() {
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_DAQIANG);
        render("/page/daqiang/biography.html");
    }

    public void research() {
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_DAQIANG);
        render("/page/daqiang/research.html");
    }

}
