package jason.tongji.config;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import jason.tongji.interceptor.GlobalSetup;
import jason.tongji.model.AirData;
import jason.tongji.model.City;
import jason.tongji.model.MonitorLocation;

public class JfinalConfiguration extends JFinalConfig {
	
	private static int postsPageSize;
    private static int publicationPageSize;
	private static String baseURL;
	/*
	 * Set up basic infomation
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		JfinalConfiguration.setPostsPageSize(getPropertyToInt("postsPageSize", 10));
		JfinalConfiguration.setBaseURL(getProperty("baseURL"));
		me.setDevMode(true);
	}

	/*
	 * Set up router
	 */
	public void configRoute(Routes me) {
		me.add(new FrontgroundRouter());
		me.add(new BackgroundRouter());
	}

	/*
	 * Config plugins
	 */
	public void configPlugin(Plugins me) {
		// Add database plugin
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
				getProperty("user"), getProperty("password").trim());
        System.out.println(getProperty("jdbcUrl"));
		me.add(c3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
        arp.addMapping("airdata", AirData.class);
        arp.addMapping("cities", City.class);
        arp.addMapping("monitorlocation", MonitorLocation.class);
		// add spring framework
	}

	public void configInterceptor(Interceptors me) {
		me.add(new GlobalSetup());
	}

	public void configHandler(Handlers me) {

	}

	/**
	 * test interface
	 */
	public static void main(String[] args) {
		JFinal.start("web", 8080, "/", 5);
	}

	public static int getPostsPageSize() {
		return postsPageSize;
	}

    public static int getPublicationPageSize() {
        return publicationPageSize;
    }

    public static void setPublicationPageSize(int publicationPageSize) {
        JfinalConfiguration.publicationPageSize = publicationPageSize;
    }

    public static void setPostsPageSize(int postsPageSize) {
		JfinalConfiguration.postsPageSize = postsPageSize;
	}

	public static String getBaseURL() {
		return baseURL;
	}

	public static void setBaseURL(String baseURL) {
		JfinalConfiguration.baseURL = baseURL;
	}
}
