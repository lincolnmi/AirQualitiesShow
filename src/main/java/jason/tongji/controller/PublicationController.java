package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.Files;
import jason.tongji.model.Publications;
import jason.tongji.tool.UITools;

public class PublicationController extends Controller {
	public void index() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
		setAttr("materials", Publications.dao.getAllPublications());
		render("/page/publication/publication.html");
	}

	public void content() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
		int id = Integer.parseInt(getPara(0));
		setAttr("publication", Publications.dao.findById(id));
		setAttr("publication_files", Files.dao.getFilesByPublicattionId(id));
        Publications.dao.addViewCount(id);
		render("/page/publication/publication-content.html");
	}

    public void byYear() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
        int id = Integer.parseInt(getPara(0));
        setAttr("publication", Publications.dao.findById(id));
        setAttr("publication_files", Files.dao.getFilesByPublicattionId(id));
        Publications.dao.addViewCount(id);
        render("/page/publication/publication-content.html");
    }

}
