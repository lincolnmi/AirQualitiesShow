package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.Files;
import jason.tongji.model.Posts;
import jason.tongji.model.Publications;
import jason.tongji.tool.DataHanlder;
import jason.tongji.tool.UITools;

public class PublicationController extends Controller {

    public void selectedIndex() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
        int pageIndex = UITools.getPageIndex(getPara(0));
        int pageScale = UITools.getPageSize(Publications.dao.countSelectedPublications());
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
        setAttr("publications", Publications.dao.getSelectedPublicationsByPage(pageIndex));
        render("/page/publication/selected-index.html");
    }

	public void byYearIndex() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
        int pageIndex = UITools.getPageIndex(getPara(0));
        int pageScale = UITools.getPageSize(Publications.dao.countAllPublications());
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
        setAttr("publications", Publications.dao.getPublicationsByPage(pageIndex));
        render("/page/publication/byyear-index.html");
	}

    public void allPapers() {
        redirect("http://dblp.uni-trier.de/pers/hd/z/Zhang:Daqiang");
    }

	public void content() {
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
		int id = Integer.parseInt(getPara(0));
		setAttr("publication", Publications.dao.findById(id));
		setAttr("publication_files", Files.dao.getFilesByPublicattionId(id));
        Publications.dao.addViewCount(id);
		render("/page/publication/publication-content.html");
	}

    public void selected() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
        int pageIndex = UITools.getPageIndex(getPara(0));
        int pageScale = UITools.getPageSize(Publications.dao.countSelectedPublications());
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
        setAttr("publications", Publications.dao.getSelectedPublicationsByPage(pageIndex));
        render("/page/publication/selected-index.html");
    }

    public void byYear() {
        setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_PUBLICATION);
        int pageIndex = UITools.getPageIndex(getPara(0));
        int pageScale = UITools.getPageSize(Publications.dao.countAllPublications());
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
        setAttr("publications", Publications.dao.getPublicationsByPage(pageIndex));
        render("/page/publication/byyear-index.html");
    }

}
