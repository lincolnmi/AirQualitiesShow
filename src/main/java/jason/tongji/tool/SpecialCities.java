package jason.tongji.tool;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jason on 2016/3/30.
 */
public class SpecialCities {
    private static HashMap<String,String> cities;
    private static ArrayList<String> beijing;
    private static ArrayList<String> tianjin;
    private static ArrayList<String> chongqing;
    private static ArrayList<String> shanghai;

    private SpecialCities() {

    }

    public static HashMap<String,String> getInstances() {
        if (cities==null) {
            cities = initCities();
        }
        return cities;
    }

    public static ArrayList<String> getBeijing() {
        if (beijing==null) {
            beijing = initBeijing();
        }
        return beijing;
    }

    public static ArrayList<String> getShanghai() {
        if (shanghai==null) {
            shanghai = initShanghai();
        }
        return shanghai;
    }

    public static ArrayList<String> getChongqing() {
        if (chongqing==null) {
            chongqing = initChongqing();
        }
        return chongqing;
    }

    public static ArrayList<String> getTianjin() {
        if (tianjin==null) {
            tianjin = initTianjin();
        }
        return tianjin;
    }

    private static HashMap<String, String> initCities() {
        String path = SpecialCities.class.getClassLoader().getResource("/").getPath();
        cities = new HashMap<String, String>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path+"/specialCities.txt"),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str=br.readLine())!=null) {
                String[] values = str.split(",");
                cities.put(values[0],values[1]);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private static ArrayList<String> initBeijing() {
        beijing = new ArrayList<String>();
        initZhiXiaShi(beijing,"beijing.txt");
        return beijing;
    }

    private static ArrayList<String> initShanghai() {
        shanghai = new ArrayList<String>();
        initZhiXiaShi(shanghai,"shanghai.txt");
        return shanghai;
    }

    private static ArrayList<String> initChongqing() {
        chongqing = new ArrayList<String>();
        initZhiXiaShi(chongqing,"chongqing.txt");
        return chongqing;
    }

    private static ArrayList<String> initTianjin() {
        tianjin = new ArrayList<String>();
        initZhiXiaShi(tianjin,"tianjin.txt");
        return tianjin;
    }

    private static void initZhiXiaShi(ArrayList<String> areas,String fileName) {
        String path = SpecialCities.class.getClassLoader().getResource("/").getPath();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path+"/"+fileName),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str=br.readLine())!=null) {
                areas.add(str);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
