package jason.tongji.config;

import com.jfinal.config.Routes;
import jason.tongji.controller.*;

public class FrontgroundRouter extends Routes {
	public void config(){
		add("/", CommonController.class);
        add("/rank", RankController.class);
	}
}
