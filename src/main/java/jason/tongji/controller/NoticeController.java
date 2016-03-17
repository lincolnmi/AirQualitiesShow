package jason.tongji.controller;

import jason.tongji.config.GlobalConfig;
import jason.tongji.model.Files;
import jason.tongji.model.Posts;
import jason.tongji.tool.DataHanlder;
import jason.tongji.tool.UITools;
import com.jfinal.core.Controller;

public class NoticeController extends Controller {
	public void index() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_NEWS);
		int pageIndex = UITools.getPageIndex(getPara(0));
		int pageScale = UITools.getPageSize(Posts.dao.countNotice());
		boolean isLastPage = false;
		boolean isFirstPage = false;
		if(pageScale == 0){
			pageScale = 1;
		}
		if (pageIndex >= pageScale) {
			pageIndex = pageScale;
		} else if (pageIndex < 0) {
			pageIndex = 1;
		}
		if (pageIndex == pageScale) {
			isLastPage = true;
		}
		if (pageIndex == 1) {
			isFirstPage = true;
		}
		setAttr("pageIndex", pageIndex);
		setAttr("pages", DataHanlder.range(pageScale));
		setAttr("isLastPage", isLastPage);
		setAttr("isFirstPage", isFirstPage);
		setAttr("notices", Posts.dao.getPostsByPage(pageIndex, "NOTICE"));
		render("/page/news/notice-index.html");
	}

	public void content() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_NEWS);
		int id = getParaToInt(0);
		setAttr("post", Posts.dao.findById(id));
		setAttr("files", Files.dao.getFilesByPostId(id));
		Posts.dao.addViewCount(id);
		render("/page/news/notice-content.html");
	}
}
