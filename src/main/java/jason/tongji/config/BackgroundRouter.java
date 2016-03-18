package jason.tongji.config;

import jason.tongji.controller.*;
import com.jfinal.config.Routes;

public class BackgroundRouter extends Routes {
	public void config(){
		add("/private",PrivateCommonController.class);
		add("/private/publication",PublicationManageController.class);
		add("/private/post",PostManageController.class);
		add("/private/user",UserManageController.class);
		add("/private/pic",PictureManagerController.class);
	}
}
