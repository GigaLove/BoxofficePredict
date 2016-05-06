/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
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
        allData.deleteAttributeAt(0);
        allData.setClassIndex(allData.numAttributes() - 1);
        spiltData(allData);
    }

    private void spiltData(Instances allData) {
        trainData = new Instances(allData, 0);
        testData = new Instances(allData, 0);

        for (int i = 0; i < allData.numInstances(); i++) {
            int year = Integer.parseInt(allData.instance(i).stringValue(2));
            if (year == 2016) {
                testData.add(new Instance(allData.instance(i)));
            } else {
                trainData.add(new Instance(allData.instance(i)));
            }
        }
    }

    public void loadTrainData(String inputPath) {
        this.trainData = loadArff(inputPath);
        if (trainData != null) {
            trainData.deleteAttributeAt(0);
            trainData.setClassIndex(trainData.numAttributes() - 1);
        }
    }

    public void loadTestData(String inputPath) {
        this.testOrigin = loadArff(inputPath);
        if (testOrigin != null) {
            testData = new Instances(testOrigin);
            testData.deleteAttributeAt(0);
            testData.setClassIndex(trainData.numAttributes() - 1);
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
//            treeCls.setOptions(new String[]{"C", "0.1"});
            treeCls.setConfidenceFactor(0.1f);
            treeCls.buildClassifier(trainData);
            Evaluation eval = new Evaluation(trainData);
            eval.crossValidateModel(treeCls, trainData, 10, new Random(1));
            System.out.println(eval.toSummaryString());//输出总结信息
            System.out.println(eval.toClassDetailsString());//输出分类详细信息
            System.out.println(eval.toMatrixString());//输出分类的混淆矩阵
            this.cls = treeCls;
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
        mModel.convertCsv2Arff2("E:/Workspaces/NetBeansProject/Film/data/en_filter_20160506.csv",
                "E:/Workspaces/NetBeansProject/Film/data/en_filter_train6_20160506.arff");
    }
}
