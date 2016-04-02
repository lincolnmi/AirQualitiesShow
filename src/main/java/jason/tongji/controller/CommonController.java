package jason.tongji.controller;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import jason.tongji.config.GlobalConfig;
import jason.tongji.tool.Forecast;
import jason.tongji.tool.ForecastData;

public class CommonController extends Controller {
	public void index(){
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_INDEX);
        ForecastData.writeTrainData("train.arff");
        Forecast.getFutureData();
        render("/page/index.html");
	}
}
