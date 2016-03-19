package jason.tongji.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Created by Jason on 2016/3/19.
 */
public class PublicationValidator extends Validator {

    @Override
    protected void validate(Controller controller) {
        controller.setAttr("titleMsg", "");
        controller.setAttr("yearMsg", "");
        if (controller.getRequest().getMethod().equals("POST")) {
            validateRequired("title", "titleMsg", "Title can't be empty");
            validateRequired("year", "yearMsg","Year can't be empty");
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.keepPara();
        controller.render("/backpage/publication/add_publication.html");
    }

}
