package jason.tongji.controller;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.*;
import jason.tongji.tool.EchartsDataScript;
import jason.tongji.tool.Forecast;
import jason.tongji.tool.ForecastData;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CommonController extends Controller {
    private EchartsDataScript echartsDataScript = new EchartsDataScript();

	public void index(){
		setAttr(GlobalConfig.NAV_KEY, GlobalConfig.NAV_INDEX);

        String cityName = null;
        String para = getPara(0);
        if (para == null) {
            cityName = "上海";
        } else {
            try {
                cityName = URLDecoder.decode(getPara(0), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        int size = 24;
        String path = ComparisonController.class.getClassLoader().getResource("/").getPath();
        path = path.replace("/WEB-INF/classes/","/resource/static/data/"+cityName+".txt");

        boolean flag = needRepredict(path);
        ArrayList<String> predictData = new ArrayList<String>();
        if (flag) {
            predictData = Forecast.getPredictData(cityName, size);
            writeToFile(predictData,path);
        } else {
            predictData = readFromFile(path);
        }
        ArrayList<String> xAxis_data = getxAxisData(Calendar.getInstance(),size);
        initEchartsDataScript(xAxis_data,predictData,"#d4d4d4","forecast_aqi","forecast-aqi.js");

        String timePoint = getTime(Calendar.getInstance());
        List<MonitorLocation> monitorLocations = MonitorLocation.dao.getMonitorLocations(cityName, timePoint);
        HashMap<String, ArrayList<String>> provinceCities = CityProvince.getProvinceCities();
        HashMap<String,ArrayList<String>> cityPrefixes = City.getCityPrefixes();
        AirData airData = AirData.dao.getAirDataByCity(cityName,timePoint);
        ArrayList<AirData> airDatas = AirData.dao.getAirData(timePoint);
        int rank = getRank(airDatas,airData);
        int count = airDatas.size();
        int percent = (int) ((count-rank)*1.0/count*100);
        CityAir cityAir = new CityAir(airData.getStr("quality"));
        setAttr("currentCity", cityName);
        setAttr("provinceCities", provinceCities);
        setAttr("cityPrefixes", cityPrefixes);
        setAttr("monitorLocations", monitorLocations);
        setAttr("airData", airData);
        setAttr("percent", percent);
        setAttr("rank", rank);
        setAttr("cityAir", cityAir);
        setAttr("timePoint", timePoint.replace("T"," ").replace("Z"," "));
        render("/page/index.html");
	}

    private ArrayList<String> readFromFile(String path) {
        ArrayList<String > result = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str = null;
            while((str=br.readLine())!=null) {
                result.add(str);
            }
            br.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeToFile(ArrayList<String> predictData, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (String str:predictData) {
                bw.write(str+"\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean needRepredict(String path) {
        File file = new File(path);
        long currentTime = System.currentTimeMillis()/1000;
        long createTime = file.lastModified()/1000;
        if (currentTime-createTime>=3600) {
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<String> getxAxisData(Calendar calendar, int size) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i=1;i<=size;i++) {
            calendar.add(calendar.HOUR,1);
            String timePoint = getTime(calendar);
            timePoint = timePoint.substring(8, 13).replace("T", "Day")+"H";
            result.add(timePoint);
        }
        return result;
    }

    private int getRank(ArrayList<AirData> airDatas, AirData airData) {
        int size = airDatas.size();
        for (int i=0;i<size;i++) {
            if (airDatas.get(i).get("area").equals(airData.get("area"))) {
                return i+1;
            }
        }
        return 1;
    }

    private String getTime(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String timePoint = year + "-";
        if (month<10) {
            timePoint += "0";
        }
        timePoint += month + "-";
        if (day<10) {
            timePoint += "0";
        }
        timePoint += day + "T";
        if (hour<10) {
            timePoint += "0";
        }
        timePoint += hour + ":00:00Z";
        return timePoint;
    }

    private void initEchartsDataScript(ArrayList<String> xAxis_data,ArrayList<String> seriesData,String color,String id,String fileName) {
        echartsDataScript.setId(id);
        ArrayList<String> colors = new ArrayList<String>();
        colors.add(color);
        echartsDataScript.setColors(colors);
        ArrayList<String> legends = new ArrayList<String>();
        legends.add("AQI");
        echartsDataScript.setLegends(legends);

        echartsDataScript.setxAxis_data(xAxis_data);
        ArrayList<String> seriesNames = new ArrayList<String>();
        seriesNames.add("AQI");
        echartsDataScript.setSeries_names(seriesNames);
        ArrayList<String> seriesTypes = new ArrayList<String>();
        seriesTypes.add("line");
        echartsDataScript.setSeries_types(seriesTypes);
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        data.add(seriesData);
        echartsDataScript.setSeries_data(data);
        String path = this.getClass().getClassLoader().getResource("/").getPath();
        System.out.println(path);
        path = path.replace("/WEB-INF/classes/","/resource/static/js/"+fileName);
        echartsDataScript.writeJS(path);
    }

}
