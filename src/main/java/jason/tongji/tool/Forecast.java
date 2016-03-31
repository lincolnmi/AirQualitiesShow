package jason.tongji.tool;

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

    public String getFutureData() {
        Instances ins = null;
        Classifier cfs = null;
        try{
            File file= new File("E:\\data\\weather.arff");
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

                cfs.classifyInstance(testInst);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
