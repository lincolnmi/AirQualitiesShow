package jason.tongji.controller;

import jason.tongji.config.GlobalConfig;
import com.jfinal.core.Controller;

public class AboutController extends Controller {
	public void index(){
		setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_ABOUT);
		render("/page/about/about_mcc.html");
	}

	public void visionandmission(){
		setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_ABOUT);
		render("/page/about/mission.html");
	}

    public void member(){
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_ABOUT);
        render("/page/about/member.html");
    }

	public void contact(){
		setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_ABOUT);
		render("/page/about/contract.html");
	}

	public void activities(){
		setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_ABOUT);
		render("/page/about/activities.html");
	}

    public void potential(){
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_ABOUT);
        render("/page/about/potential.html");
    }

}
