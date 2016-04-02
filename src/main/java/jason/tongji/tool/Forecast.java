package jason.tongji.tool;

import sun.security.jca.GetInstance;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
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
            cfs = (Classifier)Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
            cfs.buildClassifier(ins);
            Instance testInst;
            Evaluation testingEvaluation = new Evaluation(ins);
            int length = ins.numInstances();
            for (int i =0; i < length; i++) {
                testInst = ins.instance(i);
                double value = cfs.classifyInstance(testInst);
                //System.out.println(value);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
