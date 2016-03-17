package jason.tongji.controller;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.Posts;

public class CommonController extends Controller {
	public void index(){
		//get things to render in index page
		Page<Posts> news = Posts.dao.paginate(1, 3, "select *","from posts where type = ? order by create_time desc","NEWS");
		Page<Posts> notices = Posts.dao.paginate(1, 3, "select *","from posts where type = ? order by create_time desc","NOTICE");
		setAttr("news", news);
		setAttr("notices", notices);
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_INDEX);
		render("/page/index.html");
	}
}
