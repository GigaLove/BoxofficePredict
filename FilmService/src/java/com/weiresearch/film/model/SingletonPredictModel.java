/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.model;

import com.weiresearch.film.pojo.EnMoviePojo;
import com.weiresearch.film.pojo.PredictResPojo;
import com.weiresearch.film.service.PropertiesLoader;
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
    private final BoxPredictModel randomForestModel;
    private final BoxPredictModel logisticModel;
    private Instances schemaData;

    private SingletonPredictModel() {
        j48Model = new BoxPredictModel();
        randomForestModel = new BoxPredictModel();
        logisticModel = new BoxPredictModel();

        j48Model.readModel(PropertiesLoader.getInstance().getProp().getProperty("j48.cls.path"));
        randomForestModel.readModel(PropertiesLoader.getInstance().getProp().getProperty("randomforest.cls.path"));
        logisticModel.readModel(PropertiesLoader.getInstance().getProp().getProperty("logistic.cls.path"));
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

    public BoxPredictModel getRandomForestModel() {
        return randomForestModel;
    }

    public BoxPredictModel getLogisticModel() {
        return logisticModel;
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
            ois = new ObjectInputStream(SingletonPredictModel.class.getClassLoader().getResourceAsStream(schemaPath));
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
        attrs[2] = movieInfo.getReleaseYear() - 2011;
        attrs[3] = movieInfo.getPeriod();
        attrs[4] = movieInfo.getIs3D();
        attrs[5] = movieInfo.getIsIMAX();
        attrs[6] = movieInfo.getIsIp();
        attrs[7] = movieInfo.getIsSeries();
        attrs[8] = movieInfo.getVideoChiefIndex()[0];
        attrs[9] = movieInfo.getVideoChiefIndex()[1];
        attrs[10] = 0;
        return new Instance(1, attrs);
    }

    public void predict(EnMoviePojo movieInfo, PredictResPojo predictResPojo) {
        Instance predictData = this.convertInstance(movieInfo);
        Instances predictDatas = new Instances(schemaData);
        predictDatas.add(predictData);
        predictResPojo.getPredictRes().setJ48Res(this.j48Model.predict(predictDatas.lastInstance()));
        predictResPojo.getPredictRes().setRandomForestRes(this.randomForestModel.predict(predictDatas.lastInstance()));
        predictResPojo.getPredictRes().setLogisticRes(this.logisticModel.predict(predictDatas.lastInstance()));
        predictResPojo.getPredictRes().computeMaxRes();
        predictResPojo.setErrorCode(0);
        predictResPojo.setMsg("success");
    }
}
