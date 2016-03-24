package jason.tongji.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import jason.tongji.config.JfinalConfiguration;

public class GlobalSetup implements Interceptor {
	public void intercept(ActionInvocation ai){
		ai.invoke();
		Controller controller = ai.getController();
		controller.setAttr("baseURL", JfinalConfiguration.getBaseURL());
	}
}
