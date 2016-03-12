/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.model;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 *
 * @author GigaLiu
 */
public class BoxPredictModel {

    private Instances movieData;
    private Instances trainData;
    private Classifier cls;

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
                    movieData = data;
                    trainData = new Instances(data);
                    trainData.deleteAttributeAt(0);
                    saver.setInstances(data);
                    saver.setFile(new File(outputPath));
                    saver.writeBatch();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public void loadArff2Instance(String inputPath) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setSource(new File(inputPath));
            movieData = loader.getDataSet();
            trainData = new Instances(movieData);
            trainData.deleteAttributeAt(0);
            trainData.setClassIndex(trainData.numAttributes() - 1);
            System.out.println(trainData);
        } catch (IOException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void addAttribute() {
//        Add filter = new Add();
//        filter.setAttributeIndex("last");
//        filter.setAttributeName("series");
//        try {
//            filter.setInputFormat(trainData);
//            trainData = Filter.useFilter(trainData, filter);
//        } catch (Exception ex) {
//            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void trainModel() {
        cls = new J48();
        trainData.randomize(new Random(1));
        try {
            cls.buildClassifier(trainData);
            Evaluation eval = new Evaluation(trainData);
            eval.crossValidateModel(cls, trainData, 10, new Random(1));
            System.out.println(eval.toSummaryString());//输出总结信息
            System.out.println(eval.toClassDetailsString());//输出分类详细信息
            System.out.println(eval.toMatrixString());//输出分类的混淆矩阵
        } catch (Exception ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        BoxPredictModel mModel = new BoxPredictModel();
//        mModel.convertCsv2Arff("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_ins.csv");
        mModel.convertCsv2Arff("data/train_data_4.csv",
                "data/train_4.arff");
//        mModel.loadArff2Instance("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_data.arff");
        mModel.trainModel();
    }
}
