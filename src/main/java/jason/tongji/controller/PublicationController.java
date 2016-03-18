package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;

/**
 * Created by Jason on 2016/3/18.
 */
public class PublicationController extends Controller {

    public void index() {

    }

    public void selectedPapers() {
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_PUBLICATION);
        render("/page/publication/selectedPapers.html");
    }

    public void byYear() {
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_PUBLICATION);
        render("/page/publication/byYear.html");
    }

    public void allPapers() {
        setAttr(GlobalConfig.NAV_KEY,GlobalConfig.NAV_PUBLICATION);
        redirect("http://dblp.uni-trier.de/pers/hd/z/Zhang:Daqiang");
    }

}
