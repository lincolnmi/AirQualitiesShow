package jason.tongji.config;

import com.jfinal.config.Routes;
import jason.tongji.controller.*;

public class FrontgroundRouter extends Routes {
	public void config(){
		add("/", CommonController.class);
        add("/news", NewsController.class);
        add("/notice", NoticeController.class);
        add("/about", AboutController.class);
        add("/material", MaterialController.class);
        add("/download", DownloadController.class);
	}
}
