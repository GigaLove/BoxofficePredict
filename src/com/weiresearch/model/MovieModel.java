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
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;

/**
 *
 * @author GigaLiu
 */
public class MovieModel {

    private Instances movieData;
    private Instances trainData;
    private Classifier cls;

    public void loadCsv2Instance(String inputPath) {
        CSVLoader loader = new CSVLoader();
        try {
            loader.setSource(new File(inputPath));
            Instances data = loader.getDataSet();
            System.out.println(data);
        } catch (IOException ex) {
            Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
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
//            Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        MovieModel mModel = new MovieModel();
//        mModel.loadCsv2Instance("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_ins.csv");
        mModel.loadArff2Instance("C:\\Users\\GigaLiu\\Desktop\\影视数据全\\movie_data.arff");
        mModel.trainModel();
    }
}
