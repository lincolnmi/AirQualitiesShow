package jason.tongji.tool;

import sun.security.jca.GetInstance;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/27.
 */
public class Forecast {

    public static String getFutureData() {
        Instances ins = null;
        Classifier cfs = null;
        try{
            String path = ForecastData.class.getClassLoader().getResource("/").getPath();
            path = path.replace("/WEB-INF/classes/","/resource/static/data/train.arff");
            File file= new File(path);
            ArffLoader loader = new ArffLoader();
            loader.setFile(file);
            ins = loader.getDataSet();
            ins.setClassIndex(ins.numAttributes()-1);

            cfs = new J48();
            cfs.buildClassifier(ins);
            Instance testInst;
            int length = ins.numInstances();
            int count = 0;
            for (int i =0; i < length; i++) {
                testInst = ins.instance(i);
                double realNum = testInst.classValue();
                double value = cfs.classifyInstance(testInst);
                double ratio = Math.abs(value - realNum)*1.0/realNum * 100;
                if (ratio<=1) {
                    count++;
                }
            }
            System.out.println(count*1.0/length*100+"%");
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
