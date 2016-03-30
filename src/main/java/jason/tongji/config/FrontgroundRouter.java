package jason.tongji.config;

import com.jfinal.config.Routes;
import jason.tongji.controller.*;
import jason.tongji.model.MonitorLocation;

public class FrontgroundRouter extends Routes {
	public void config(){
		add("/", CommonController.class);
        add("/rank", RankController.class);
        add("/city", CityController.class);
        add("/monitorLocation", MonitorLocationController.class);
        add("/home", HomeController.class);
        add("/comparison", ComparisonController.class);
	}
}
