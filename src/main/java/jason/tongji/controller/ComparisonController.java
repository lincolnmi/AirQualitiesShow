package jason.tongji.controller;

import com.jfinal.core.Controller;
import jason.tongji.config.GlobalConfig;
import jason.tongji.model.AirData;
import jason.tongji.model.CityProvince;
import jason.tongji.model.MonitorLocation;
import jason.tongji.tool.EchartsDataScript;
import jason.tongji.tool.Helper;
import jason.tongji.tool.SpecialCities;

import java.io.*;
import java.net.CacheRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by Jason on 2016/3/24.
 */
public class ComparisonController extends Controller {

    public void index() {
        Calendar calendar = Calendar.getInstance();
        //String timePoint = getTime(calendar);
        String timePoint = GlobalConfig.timePoint;
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));

        ArrayList<AirData> airDatas = AirData.dao.getAirData(timePoint);
        HashMap<String,String> cities = getCitiesData(airDatas);
        HashMap<String,String> provinces = getProvincesData(cities);
        update(provinces,cities,"/resource/static/js/aqi-map.js");

        //lastDay();
        render("/page/comparison/comparison.html");
    }

    public void lastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String timePoint = getTime(calendar);
        setAttr("timePoint",timePoint.replace("T", " ").replace("Z"," "));

        ArrayList<AirData> airDatas = AirData.dao.getAirData(timePoint);
        HashMap<String,String> cities = getCitiesData(airDatas);
        HashMap<String,String> provinces = getProvincesData(cities);
        update(provinces,cities,"/resource/static/js/aqi-province.js");
    }

    private HashMap<String, String> getProvincesData(HashMap<String,String> cities) {
        HashMap<String,String> provinces = new HashMap<String, String>();
        HashMap<String,ArrayList<String>> provincecities = CityProvince.getProvinceCities();
        for (String key:provincecities.keySet()) {
            double aqi = 0;
            int count = 0;
            for (String value:provincecities.get(key)) {
                String city  = getCityName(value);
                //System.out.println(city);
                if (cities.containsKey(city)) {
                    aqi += Double.valueOf(cities.get(city));
                    count++;
                }
            }
            aqi /= count;
            provinces.put(key,aqi+"");
        }
        System.out.println("provinces");
        return provinces;
    }

    private HashMap<String,String> getCitiesData(ArrayList<AirData> airDatas) {
        HashMap<String,String> cities = new HashMap<String, String>();
        for (AirData airData:airDatas) {
            String city = getCityName(airData.getStr("area"));
            String aqi = airData.getDouble("aqi") + "";
            cities.put(city, aqi);
            if (city.equals("北京市")) {
                for (String area:SpecialCities.getBeijing()) {
                    cities.put(area, aqi);
                }
            }
            if (city.equals("天津市")) {
                for (String area:SpecialCities.getTianjin()) {
                    cities.put(area, aqi);
                }
            }
            if (city.equals("重庆市")) {
                for (String area:SpecialCities.getChongqing()) {
                    cities.put(area, aqi);
                }
            }
            if (city.equals("上海市")) {
                for (String area:SpecialCities.getShanghai()) {
                    cities.put(area, aqi);
                }
            }
        }
        System.out.println("cities");
        return cities;
    }

    private String getCityName(String city) {
        HashMap<String,String> specialCities = SpecialCities.getInstances();
        String result = "";
        if (specialCities.containsKey(city)) {
            result = specialCities.get(city);
        } else if (city.indexOf("地区")!=-1) {
            result = city;
        } else {
            result = city + "市";
        }
        return result;
    }

    private void update(HashMap<String,String> provinces,HashMap<String,String> cities,String fileName) {
        StringBuilder sb = new StringBuilder();
        String path = this.getClass().getClassLoader().getResource("/").getPath();
        System.out.println(path);
        path = path.replace("/WEB-INF/classes/","/resource/static/js/aqi-map.js");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while ((str=br.readLine())!=null) {
                sb.append(str);
                sb.append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pre1 = "data1-start";
        String pos1 = "data1-end";
        int data1_start = sb.indexOf(pre1);
        int data1_end = sb.indexOf(pos1);
        sb.replace(data1_start,data1_end,getData(pre1,pos1,provinces));

        String pre2 = "data2-start";
        String pos2 = "data2-end";
        int data2_start = sb.indexOf(pre2);
        int data2_end = sb.indexOf(pos2);
        sb.replace(data2_start,data2_end,getData(pre2,pos2,cities));

        writeToFile(path,sb.toString());
    }

    private void writeToFile(String path,String data) {
        try {
            OutputStreamWriter isr = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            BufferedWriter bw = new BufferedWriter(isr);
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getData(String pre,String pos,HashMap<String,String> data) {
        StringBuilder provinces_data = new StringBuilder(pre+"\n");
        Iterator<String> iterator = data.keySet().iterator();
        provinces_data.append("\t\t\tdata:[\n");
        while (iterator.hasNext()) {
            StringBuilder province_item = new StringBuilder();
            String key = iterator.next();
            province_item.append("{name: '");
            province_item.append(key);
            province_item.append("',value: ");
            province_item.append(data.get(key));
            province_item.append("}");
            if (iterator.hasNext()) {
                province_item.append(",");
            }
            provinces_data.append("\t\t\t\t"+province_item);
            provinces_data.append("\n");
        }
        provinces_data.append("\t\t\t]\n");
        provinces_data.append("//");
        return provinces_data.toString();
    }

    private String getTime(Calendar calendar) {
        int minute = calendar.get(Calendar.MINUTE);
        if (minute>10) {
            calendar.add(calendar.HOUR,-1);
        } else {
            calendar.add(calendar.HOUR,-2);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
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

}
