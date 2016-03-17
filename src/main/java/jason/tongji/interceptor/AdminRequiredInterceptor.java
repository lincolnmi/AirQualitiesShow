package jason.tongji.interceptor;

import jason.tongji.model.Users;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class AdminRequiredInterceptor implements Interceptor {
	public void intercept(ActionInvocation ai) {
		ai.invoke();
		Controller controller = ai.getController();
		if (controller.getSessionAttr("loginUser") != null) {
			Users user = (Users) controller.getSessionAttr("loginUser");
			if (!user.isAdmin()) {
				controller.renderText("You are not authorized to do this action!");
			}
		} else {
			controller.redirect("/private");
		}
	}
}
