package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;

/**
 * Created by Jason on 2016/3/19.
 */
public class TeachingController extends Controller {

    public void index() {
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_TEACHING);
        render("/page/teaching/teaching.html");
    }

}
