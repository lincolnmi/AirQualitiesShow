package jason.tongji.tool;

import jason.tongji.model.AirData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jason on 2016/4/2.
 */
public class ForecastData {
    private static final int size = 5;
    private static int maxValue = 0;

    private static HashMap<String,ArrayList<AirData>> processData() {
        HashMap<String,ArrayList<AirData>> cityAirData = new HashMap<String, ArrayList<AirData>>();
        ArrayList<AirData> airDatas = AirData.dao.getAllAirData();
        for (AirData airData:airDatas) {
            String city = airData.getStr("area");
            ArrayList<AirData> aqi = null;
            if (cityAirData.containsKey(city)) {
                aqi = cityAirData.get(city);
            } else {
                aqi = new ArrayList<AirData>();
            }
            aqi.add(airData);
            cityAirData.put(city,aqi);
        }
        return cityAirData;
    }

    public static void writeTrainData(String fileName) {
        HashMap<String,ArrayList<AirData>> cityAirData = processData();
        Iterator<Map.Entry<String, ArrayList<AirData>>> it = cityAirData.entrySet().iterator();
        ArrayList<String> dataSets = new ArrayList<String>();
        while (it.hasNext()) {
            ArrayList<AirData> aqi = it.next().getValue();
            dataSets.addAll(getDataSet(aqi));
        }
        writeArffData(dataSets,fileName);
    }

    private static ArrayList<String> getDataSet(ArrayList<AirData> airDatas) {
        ArrayList<String> dataSets = new ArrayList<String>();
        int length = airDatas.size(),i = 0;
        for (i=0;i<length-1;i++) {
            StringBuilder dataSet = new StringBuilder();

            AirData airData = airDatas.get(i);
            int aqi = (int) airDatas.get(i+1).getDouble("aqi").doubleValue();
            int co = (int) airData.getDouble("co").doubleValue();
            int pm25 = (int) airData.getDouble("pm25").doubleValue();
            int pm10 = (int) airData.getDouble("pm10").doubleValue();
            int so2 = (int) airData.getDouble("so2").doubleValue();
            int no2 = (int) airData.getDouble("no2").doubleValue();

            if (aqi>maxValue) {
                maxValue = aqi;
            }
            dataSet.append(co + " " + pm25 + " " + pm10 + " " + so2 +" "+no2+" ");
            dataSet.append(aqi);
            dataSets.add(dataSet.toString());
        }
        return dataSets;
    }

    private static String getHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("@relation labeled_data\n");
        for (int i=1;i<=size;i++) {
            sb.append("@attribute value" + i + " numeric\n");
        }
        sb.append("@attribute label {");
        for (int i=0;i<maxValue;i++) {
            sb.append(i+",");
        }
        sb.append(maxValue+"}\n");
        sb.append("@data\n");

        return sb.toString();
    }

    public static void writeArffData(ArrayList<String> dataSets,String fileName) {
        StringBuilder sb  = new StringBuilder();
        String header = getHeader();
        sb.append(header);
        for (String dataSet:dataSets) {
            sb.append(dataSet);
            sb.append("\n");
        }
        String path = ForecastData.class.getClassLoader().getResource("/").getPath();
        path = path.replace("/WEB-INF/classes/","/resource/static/data/"+fileName);
        System.out.println(path);
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
