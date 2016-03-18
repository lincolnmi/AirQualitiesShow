package jason.tongji.config;

import com.jfinal.config.Routes;
import jason.tongji.controller.*;

public class FrontgroundRouter extends Routes {
	public void config(){
		add("/", CommonController.class);
        add("/news", NewsController.class);
        add("/daqiang", DaqiangController.class);
        add("/publication", PublicationController.class);
        add("/notice", NoticeController.class);
        add("/about", AboutController.class);
        add("/download", DownloadController.class);
	}
}
