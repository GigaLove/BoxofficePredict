/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 *
 * @author GigaLiu
 */
public class BoxPredictModel {

    private Instances trainOrigin;
    private Instances trainData;
    private Instances testOrigin;
    private Instances testData;
    private Classifier cls;

    /**
     * version 0.1 将csv文件转换成arff文件
     *
     * @param inputPath
     * @param outputPath
     */
    public void convertCsv2Arff(String inputPath, String outputPath) {
        CSVLoader loader = new CSVLoader();
        ArffSaver saver = new ArffSaver();
        try {
            loader.setSource(new File(inputPath));
            Instances data = loader.getDataSet();
            // 规范化数据集
            int[] toNomIndex = {0, 1, 2, 3, 4, 5, 6, data.numAttributes() - 1};
            data = num2Nominal(data, toNomIndex);
            if (data != null) {
                data.setClassIndex(data.numAttributes() - 1);
                data = normalize(data, 1.0, 0.0);
                if (data != null) {
                    // 存储数据集到arff文件
                    saver.setInstances(data);
                    saver.setFile(new File(outputPath));
                    saver.writeBatch();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * version 0.2 将csv文件转换成arff文件
     *
     * @param inputPath
     * @param outputPath
     */
    public void convertCsv2Arff2(String inputPath, String outputPath) {
        CSVLoader loader = new CSVLoader();
        ArffSaver saver = new ArffSaver();
        try {
            loader.setSource(new File(inputPath));
            Instances data = loader.getDataSet();
            // 规范化数据集，规范的数据维度与version0.1不同
            int[] toNomIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, data.numAttributes() - 1};
            data = num2Nominal(data, toNomIndex);
            if (data != null) {
                data.setClassIndex(data.numAttributes() - 1);
//                data = normalize(data, 1.0, 0.0);
//                if (data != null) {
                // 存储数据集到arff文件
                saver.setInstances(data);
                saver.setFile(new File(outputPath));
                saver.writeBatch();
//                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 数值属性转换为名词属性
     *
     * @param data
     * @param index
     * @return
     */
    private Instances num2Nominal(Instances data, int[] index) {
        NumericToNominal filter = new NumericToNominal();
        try {
            filter.setAttributeIndicesArray(index);
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 数据归一化
     *
     * @param data
     * @param scale
     * @param translation
     * @return
     */
    private Instances normalize(Instances data, double scale, double translation) {
        Normalize filter = new Normalize();
        filter.setScale(scale);
        filter.setTranslation(translation);
        try {
            filter.setInputFormat(data);
            return Filter.useFilter(data, filter);
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Instances loadArff(String inputPath) {
        if (inputPath != null || new File(inputPath).isFile()) {
            ArffLoader loader = new ArffLoader();
            try {
                loader.setSource(new File(inputPath));
                return loader.getDataSet();
            } catch (IOException ex) {
                Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * 加载所有数据，并划分训练和测试集
     *
     * @param inputPath
     */
    public void loadAllData(String inputPath) {
        Instances allData = loadArff(inputPath);
        allData.setClassIndex(allData.numAttributes() - 1);
        splitDataByYear(allData);
//        splitDataByRandom(allData, 5, 1);
    }

    /**
     * 划分训练集和测试集
     *
     * @param allData
     */
    private void splitDataByYear(Instances allData) {
        allData.deleteAttributeAt(9);
        allData.deleteAttributeAt(11);
        allData.deleteAttributeAt(11);
        trainOrigin = new Instances(allData, 0);
        testOrigin = new Instances(allData, 0);

        int[] counts = new int[allData.classAttribute().numValues()];
        for (int i = 0; i < allData.numInstances(); i++) {
            int year = Integer.parseInt(allData.instance(i).stringValue(3));
            int boxoffice = (int) allData.instance(i).classValue();

            if (year == 2016) {
                testOrigin.add(new Instance(allData.instance(i)));
            } else {
                if (counts[boxoffice] < 300) {
                    trainOrigin.add(new Instance(allData.instance(i)));
                    counts[boxoffice]++;
                }
            }
        }

        trainData = new Instances(trainOrigin);
        trainData.deleteAttributeAt(0);
        testData = new Instances(testOrigin);
        testData.deleteAttributeAt(0);
    }

    /**
     * 基于NUM_FOLD生成数据集和验证集
     *
     * @param allData
     */
    private void splitDataByRandom(Instances allData, int numFolds, int numFold) {
        // remove marketCount, tralerView
        allData.deleteAttributeAt(9);
        allData.deleteAttributeAt(11);
        allData.deleteAttributeAt(11);

        allData.randomize(new Random(1));

        trainOrigin = allData.trainCV(numFolds, numFold);
        trainData = new Instances(trainOrigin);
        trainData.deleteAttributeAt(0);

        testOrigin = allData.testCV(numFolds, numFold);
        testData = new Instances(testOrigin);
        testData.deleteAttributeAt(0);
    }

    public void loadTrainData(String inputPath) {
        this.trainOrigin = loadArff(inputPath);
        if (trainData != null) {
            trainData = new Instances(trainOrigin);
            trainData.deleteAttributeAt(0);
            trainData.setClassIndex(trainData.numAttributes() - 1);
        }
    }

    public void loadTestData(String inputPath) {
        this.testOrigin = loadArff(inputPath);
        if (testOrigin != null) {
            testData = new Instances(testOrigin);
            testData.deleteAttributeAt(0);
            testData.setClassIndex(testData.numAttributes() - 1);
        }
    }

    public void addAttribute() {
        Add filter = new Add();
        filter.setAttributeIndex("last");
        filter.setAttributeName("series");
        try {
            filter.setInputFormat(trainData);
            trainData = Filter.useFilter(trainData, filter);
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void trainModelByJ48() {
        J48 treeCls = new J48();
        trainData.randomize(new Random(1));
        try {
            treeCls.setConfidenceFactor(0.1f);
            treeCls.buildClassifier(trainData);
            this.cls = treeCls;
            this.crossValidate();
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void trainModelByNaiveBayes() {
        NaiveBayes bayesCls = new NaiveBayes();
        trainData.randomize(new Random(1));
        try {
            bayesCls.buildClassifier(trainData);
            this.cls = bayesCls;
            this.crossValidate();
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void trainModelBySMO() {
        SMO smoCls = new SMO();
        trainData.randomize(new Random(1));
        try {
            smoCls.buildClassifier(trainData);
            this.cls = smoCls;
            this.crossValidate();
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void j48ParaSelect() {
        CVParameterSelection selection = new CVParameterSelection();
        selection.setClassifier(cls);
        try {
            selection.addCVParameter("C 0.1 0.5 5.0");
            selection.buildClassifier(trainData);
            System.out.println(Arrays.toString(selection.getBestClassifierOptions()));
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crossValidate() {
        Evaluation eval;
        try {
            eval = new Evaluation(trainData);
            eval.crossValidateModel(cls, trainData, 10, new Random(1));
            System.out.println(eval.toSummaryString());//输出总结信息
            System.out.println(eval.toClassDetailsString());//输出分类详细信息
            System.out.println(eval.toMatrixString());//输出分类的混淆矩阵
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 基于测试数据进行模型评估
     */
    public void evaluateByTestData() {
        Evaluation eval;
        try {
            eval = new Evaluation(trainData);
            eval.evaluateModel(cls, testData);
            System.out.println(eval.toSummaryString());//输出总结信息
            System.out.println(eval.toClassDetailsString());//输出分类详细信息
            System.out.println(eval.toMatrixString());//输出分类的混淆矩阵
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 序列化分类器
     *
     * @param outputPath
     */
    public void serializeCls(String outputPath) {
        ObjectOutputStream oo;
        try {
            oo = new ObjectOutputStream(new FileOutputStream(new File(outputPath)));
            oo.writeObject(cls);
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.INFO, "Classifier serialize success");
            oo.close();
        } catch (IOException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 序列化分类器
     *
     * @param outputPath
     */
    public void serializeDataSchema(String outputPath) {
        ObjectOutputStream oo;
        try {
            oo = new ObjectOutputStream(new FileOutputStream(new File(outputPath)));
            oo.writeObject(new Instances(trainData, 0));
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.INFO, "Data schema serialize success");
            oo.close();
        } catch (IOException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 基于序列化文件读取分类器
     *
     * @param modelPath
     */
    public void readModel(String modelPath) {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(BoxPredictModel.class.getClassLoader().getResourceAsStream(modelPath));
            this.cls = (Classifier) ois.readObject();
            ois.close();
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.INFO, "Classifier deserialize success");
        } catch (IOException | ClassNotFoundException ex) {
            this.cls = null;
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 模型测试
     *
     * @param predictData
     * @return
     */
    public int predict(Instance predictData) {
        try {
            if (predictData != null && cls != null) {
                return (int) cls.classifyInstance(predictData);
            } else {
                return -1;
            }
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
    }

    /**
     * 输出错误信息
     */
    public void outputWrongInfo() {
        if (testData != null && cls != null) {
            try {
                Instance predictData;
                int count = 0;
                for (int i = 0; i < testData.numInstances(); i++) {
                    predictData = testData.instance(i);
                    int predictVal = (int) cls.classifyInstance(predictData);
                    int classVal = (int) predictData.classValue();
                    if (predictVal != classVal) {
                        count++;
                        System.out.println(String.format("Predict Instances: %s, predict value: %s, class value: %s",
                                testOrigin.instance(i).toString(), predictVal, classVal));
                    }
                }
                System.out.println(String.format("Predict instances %s, wrong count %s",
                        testData.numInstances(), count));
            } catch (Exception ex) {
                Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void run(String inputPath, String outputPath) {
        this.loadAllData(inputPath);
//        this.trainModelByJ48();
//        this.j48ParaSelect();
//        this.trainModelByNaiveBayes();
        this.trainModelBySMO();
//        this.outputWrongInfo();
//        this.serializeCls(outputPath);
    }

    public static void main(String[] args) {
        BoxPredictModel mModel = new BoxPredictModel();
//        mModel.convertCsv2Arff("data/train_data_4_2016.csv",
//                "data/train_4.arff");
//        mModel.convertCsv2Arff("data/test_data_4.csv",
//                "data/test_4.arff");
//        mModel.loadTrainData("data/train_4.arff");
//        mModel.trainModelByJ48();
//        mModel.loadTestData("data/test_4.arff");
//        mModel.evaluateByTestData();

//        mModel.loadAllData("data/train_4.arff");
//        mModel.trainModelByJ48();
//        mModel.j48ParaSelect();
//        mModel.evaluateByTestData();
//        mModel.convertCsv2Arff2("E:/Workspaces/NetBeansProject/Film/data/en_filter_20160509_04.csv",
//                "E:/Workspaces/NetBeansProject/Film/data/en_filter_train5_20160509_04.arff");
        mModel.run("E:/Workspaces/NetBeansProject/Film/data/en_filter_train5_20160509_04.arff",
                "E:/Workspaces/NetBeansProject/Film/data/smo_cls.model");
        mModel.serializeDataSchema("E:/Workspaces/NetBeansProject/Film/data/data.model");
    }
}
