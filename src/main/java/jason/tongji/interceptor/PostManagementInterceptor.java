package jason.tongji.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class PostManagementInterceptor implements Interceptor {
	public void intercept(ActionInvocation ai){
		ai.invoke();
		
	}
}
