package jason.tongji.controller;

import jason.tongji.config.GlobalConfig;
import com.jfinal.core.Controller;

public class HistoryController extends Controller {
	public void index(){
		setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_HISTORY);
		render("/page/history/history.html");
	}
}
