/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.model;

import com.weiresearch.film.pojo.EnMoviePojo;
import com.weiresearch.film.pojo.PredictResPojo;
import com.weiresearch.film.service.PropertiesLoader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author GigaLiu
 */
public class SingletonPredictModel {
    
    private static SingletonPredictModel model = null;
    
    private final BoxPredictModel j48Model;
    private final BoxPredictModel bayesModel;
    private final BoxPredictModel smoModel;
    private Instances schemaData;
    
    private SingletonPredictModel() {
        j48Model = new BoxPredictModel();
        bayesModel = new BoxPredictModel();
        smoModel = new BoxPredictModel();
        
        j48Model.readModel(PropertiesLoader.getInstance().getProp().getProperty("j48.cls.path"));
        bayesModel.readModel(PropertiesLoader.getInstance().getProp().getProperty("bayes.cls.path"));
        smoModel.readModel(PropertiesLoader.getInstance().getProp().getProperty("smo.cls.path"));
    }
    
    public static final SingletonPredictModel getInstance() {
        if (model == null) {
            synchronized (SingletonPredictModel.class) {
                if (model == null) {
                    model = new SingletonPredictModel();
                    model.readSchema(PropertiesLoader.getInstance().getProp().getProperty("instances.path"));
                }
            }
        }
        return model;
    }
    
    public BoxPredictModel getJ48Model() {
        return j48Model;
    }
    
    public BoxPredictModel getBayesModel() {
        return bayesModel;
    }
    
    public BoxPredictModel getSmoModel() {
        return smoModel;
    }

    /**
     * 基于序列化文件读取schema instances
     *
     * @param schemaPath
     */
    private void readSchema(String schemaPath) {
        ObjectInputStream ois;
        this.schemaData = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(schemaPath));
            this.schemaData = (Instances) ois.readObject();
            ois.close();
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.INFO, "Schema instances deserialize success");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BoxPredictModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Instance convertInstance(EnMoviePojo movieInfo) {
        double[] attrs = new double[schemaData.numAttributes()];
        attrs[0] = movieInfo.getType();
        attrs[1] = movieInfo.getCountry();
        attrs[2] = movieInfo.getPeriod();
        attrs[3] = movieInfo.getIs3D();
        attrs[4] = movieInfo.getIsIMAX();
        attrs[5] = movieInfo.getIsIp();
        attrs[6] = movieInfo.getIsSeries();
        attrs[7] = movieInfo.getVideoChiefIndex()[0];
        attrs[8] = movieInfo.getVideoChiefIndex()[1];
        attrs[9] = 0;
        return new Instance(1, attrs);
    }
    
    public void predict(EnMoviePojo movieInfo, PredictResPojo predictRes) {
        Instance predictData = this.convertInstance(movieInfo);
        Instances predictDatas = new Instances(schemaData);
        predictDatas.add(predictData);
        predictRes.setJ48Res(this.j48Model.predict(predictDatas.lastInstance()));
        predictRes.setBayesRes(this.bayesModel.predict(predictDatas.lastInstance()));
        predictRes.setSmoRes(this.smoModel.predict(predictDatas.lastInstance()));
        predictRes.setErrorCode(0);
        predictRes.setMsg("success");
    }
}
