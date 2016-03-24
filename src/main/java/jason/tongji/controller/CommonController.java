package jason.tongji.controller;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import jason.tongji.config.GlobalConfig;

public class CommonController extends Controller {
	public void index(){
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_INDEX);
		render("/page/index.html");
	}
}
