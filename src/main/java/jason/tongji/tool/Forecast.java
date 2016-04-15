package jason.tongji.tool;

import jason.tongji.model.AirData;
import sun.security.jca.GetInstance;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jason on 2016/3/27.
 */
public class Forecast {

    private static Classifier buildTrain() {
        String path = ForecastData.class.getClassLoader().getResource("/").getPath();
        path = path.replace("/WEB-INF/classes/","/resource/static/data/train.arff");
        File file= new File(path);
        if (!file.exists()) {
            ForecastData.writeTrainData("train.arff");
        }
        ArffLoader loader = new ArffLoader();
        Classifier cfs = null;
        Instances ins = null;
        try {
            loader.setFile(file);
            ins = loader.getDataSet();
            ins.setClassIndex(ins.numAttributes()-1);
            cfs = new J48();
            cfs.buildClassifier(ins);
            return cfs;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*

    public static String getFutureData() {


        String path = ForecastData.class.getClassLoader().getResource("/").getPath();
                path = path.replace("/WEB-INF/classes/","/resource/static/data/train.arff");
                ForecastData.writeTrainData("train.arff");
                File file= new File(path);
                ArffLoader loader = new ArffLoader();
                Classifier cfs = null;
                Instances ins = null;
                try {
                    loader.setFile(file);
                    ins = loader.getDataSet();
                    cfs = new J48();
                    cfs.buildClassifier(ins);
                    return cfs;


            ins.setClassIndex(ins.numAttributes()-1);

            Instance testInst;
            int length = ins.numInstances();
            int count = 0;
            for (int i =0; i < length; i++) {
                testInst = ins.instance(i);
                double realNum = testInst.classValue();
                double value = cfs.classifyInstance(testInst);
                double ratio = Math.abs(value - realNum)*1.0/realNum * 100;
                if (ratio<=10) {
                    count++;
                }
            }
            System.out.println(count*1.0/length*100+"%");
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }*/

    public static ArrayList<Integer> getPredictData(String area,int size) {
        Classifier cfs = buildTrain();
        List<AirData> airDatas = AirData.dao.getLastNumAirDataByCity(area,24);

        ArrayList<String> AQIs = new ArrayList<String>();
        for (AirData airData:airDatas) {
            int aqi = (int) airData.getDouble("aqi").doubleValue();
            AQIs.add(aqi+"");
        }

        ArrayList<String> testData = new ArrayList<String>();
        testData.add(getStr(AQIs));
        //write test data
        ForecastData.writeArffData(testData, "test.arff");
        ArrayList<Integer> result = new ArrayList<Integer>();
        String path = ForecastData.class.getClassLoader().getResource("/").getPath();
        path = path.replace("/WEB-INF/classes/","/resource/static/data/test.arff");
        File file= new File(path);

        ArffLoader loader = new ArffLoader();
        Instances ins = null;
        try {
            for (int i=0;i<size;i++) {
                loader.setFile(file);
                ins = loader.getDataSet();
                ins.setClassIndex(ins.numAttributes()-1);
                int predict = getPredict(cfs,ins.instance(i));
                result.add(predict);
                AQIs.remove(0);
                AQIs.add(predict+"");
                testData.add(getStr(AQIs));
                ForecastData.writeArffData(testData,"test.arff");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int getPredict(Classifier cfs,Instance instance) {
        try {
            return (int) cfs.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String getStr(ArrayList<String> data) {
        StringBuilder sb = new StringBuilder();
        for (String str:data) {
            sb.append(str+" ");
        }
        sb.append("0");
        return sb.toString();
    }

}
