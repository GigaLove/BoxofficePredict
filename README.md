# 影视票房预测

> 基于影视作品的基本信息，结合影人的主创指数计算，实现影视作品的票房分类预测。

[TOC]

## 项目说明
1. FilmService：核心影视票房预测逻辑实现以及基于jersey框架的web service接口暴露。
2. FilmStorage：基于JPA及EJB Entity Bean实现的数据持久层，映射数据库表单
---
## 进展情况

### 2016-05-06
1. 基于时间权重以及角色权重来计算主创指数，每部影片的主创团队针对主创指数计算算数平均数，不区分导演与演员，效果不佳，下一步的考虑：
	* 考虑引入历史作品来计算主创指数
	* 影视作品主创指数计算时，区分导演和演员
2. 引入2008~2015年的作品进行主创指数计算，对于每部影片会除以当年最高票房作品；对于年代权限，则考虑引入S型曲线，进行权重衰减，开展情况如下：
	* 老牌影星的影响力有所提升，但还是不够准确，所以进一步考虑增大时间跨度
	* 数据集的均衡性不好，低票房影片太多，可能会导致分类效果有偏差
3. 目前整体呈现的状态时对于低票房影片预测效果较好，可能的因素也在于目前主要考虑了主创，影响影视票房的其他很多因素未考虑进来
4. 考虑属性：
	* Naive Bayes效果最佳：
```
=== Stratified cross-validation ===
=== Summary ===

Correctly Classified Instances         861               64.8343 %
Incorrectly Classified Instances       467               35.1657 %
Kappa statistic                          0.3546
Mean absolute error                      0.14
Root mean squared error                  0.2887
Relative absolute error                 68.3245 %
Root relative squared error             90.2743 %
Total Number of Instances             1328

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.94      0.443      0.74      0.94      0.828      0.848    0
                 0.211     0.091      0.351     0.211     0.264      0.662    1
                 0         0.007      0         0         0          0.719    2
                 0.457     0.072      0.468     0.457     0.462      0.859    3
                 0.31      0.013      0.433     0.31      0.361      0.943    4
                 0.333     0.006      0.385     0.333     0.357      0.962    5
Weighted Avg.    0.648     0.281      0.566     0.648     0.597      0.809

=== Confusion Matrix ===

   a   b   c   d   e   f   <-- classified as
 716  33   3   7   1   2 |   a = 0
 169  53   2  26   1   0 |   b = 1
  48  24   0  22   2   0 |   c = 2
  32  39   4  74   9   4 |   d = 3
   1   2   0  24  13   2 |   e = 4
   1   0   0   5   4   5 |   f = 5
```

### 2016-05-08
1. 人工修正影视信息中是否是系列电影、IP电影字段，引入更多有效的数据维度
2. 尝试基于现有的预告片播放量信息，进行影视信息预测

### 2016-05-09
1. 基于数据库中的预告片信息，抽取每部预告片综合后的平均播放量以及最大播放量作为数据特征引入，由于具备预告片的影片数据只有600部左右，此外，预告片播放量也需要考虑时间因素，初步整合下来效果一般。
2. 考虑属性：`type country releaseYear period is3D isIMAX isIp isSeries directorIndex actorIndex avgTrailerView maxTrailerView`
	* Naive Bayes目前效果最佳，再查准率和查全率上会优于SMO和J48：
```
=== Stratified cross-validation ===
=== Summary ===

Correctly Classified Instances         281               54.4574 %
Incorrectly Classified Instances       235               45.5426 %
Kappa statistic                          0.292 
Mean absolute error                      0.1589
Root mean squared error                  0.3437
Relative absolute error                 68.7493 %
Root relative squared error            101.2222 %
Total Number of Instances              516

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.9       0.383      0.688     0.9       0.78       0.839    0
                 0.238     0.151      0.287     0.238     0.26       0.652    1
                 0.152     0.068      0.179     0.152     0.165      0.705    2
                 0.081     0.02       0.4       0.081     0.135      0.788    3
                 0.483     0.047      0.378     0.483     0.424      0.904    4
                 0.333     0.014      0.364     0.333     0.348      0.96     5
Weighted Avg.    0.545     0.228      0.495     0.545     0.497      0.788

=== Confusion Matrix ===

   a   b   c   d   e   f   <-- classified as
 225  19   4   1   1   0 |   a = 0
  69  25   8   3   0   0 |   b = 1
  20  15   7   3   1   0 |   c = 2
  13  26  10   6  17   2 |   d = 3
   0   2   7   1  14   5 |   e = 4
   0   0   3   1   4   4 |   f = 5
```
3. 由于预告片的噪声较大，因此，移除预告片信息，考虑数据维度，`type country releaseYear period is3D isIMAX isIp isSeries directorIndex actorIndex`
	* 目前J48决策时效果最佳
```
=== Stratified cross-validation ===
=== Summary ===

Correctly Classified Instances         296               57.3643 %
Incorrectly Classified Instances       220               42.6357 %
Kappa statistic                          0.3405
Mean absolute error                      0.1767
Relative absolute error                 76.425  %
Root relative squared error             97.2014 %
Total Number of Instances              516

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.892     0.372      0.693     0.892     0.78       0.801    0
                 0.19      0.102      0.323     0.19      0.24       0.613    1
                 0.261     0.045      0.364     0.261     0.304      0.645    2
                 0.365     0.081      0.429     0.365     0.394      0.699    3
                 0.379     0.039      0.367     0.379     0.373      0.677    4
                 0.25      0.006      0.5       0.25      0.333      0.752    5
Weighted Avg.    0.574     0.219      0.527     0.574     0.539      0.726

=== Confusion Matrix ===

   a   b   c   d   e   f   <-- classified as
 223  16   6   2   2   1 |   a = 0
  63  20  10  10   2   0 |   b = 1
  15  12  12   7   0   0 |   c = 2
  19  12   4  27  11   1 |   d = 3
   2   2   0  13  11   1 |   e = 4
   0   0   1   4   4   3 |   f = 5
```

4. 对影视预告片以10为底取log，进行模型预测，数据属性同2
	* Naive Bayes效果最佳：
```
=== Stratified cross-validation ===
=== Summary ===

Correctly Classified Instances         295               57.1705 %
Incorrectly Classified Instances       221               42.8295 %
Kappa statistic                          0.3609
Mean absolute error                      0.1582
Root mean squared error                  0.3151
Relative absolute error                 68.4528 %
Root relative squared error             92.789  %
Total Number of Instances              51

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.86      0.271      0.749     0.86      0.801      0.87     0
                 0.305     0.097      0.444     0.305     0.362      0.7      1
                 0.239     0.074      0.239     0.239     0.239      0.783    2
                 0.338     0.113      0.333     0.338     0.336      0.799    3
                 0.241     0.037      0.28      0.241     0.259      0.94     4
                 0.417     0.012      0.455     0.417     0.435      0.962    5
Weighted Avg.    0.572     0.176      0.549     0.572     0.556      0.824

=== Confusion Matrix ===

   a   b   c   d   e   f   <-- classified as
 215  20   6   9   0   0 |   a = 0
  53  32  10  10   0   0 |   b = 1
  10  12  11  12   1   0 |   c = 2
   9   8  17  25  13   2 |   d = 3
   0   0   1  17   7   4 |   e = 4
   0   0   1   2   4   5 |   f = 5
```
5. 调整票房分类标准，修改为5类票房预测：<100w 100w 1000w 10,000w, 100,000w
	* 决策树效果如下：
```
Correctly Classified Instances         603               56.7797 %
Incorrectly Classified Instances       459               43.2203 %
Kappa statistic                          0.4111
Mean absolute error                      0.2122
Root mean squared error                  0.3425
Relative absolute error                 71.538  %
Root relative squared error             88.9515 %
Total Number of Instances             1062

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.594     0.103      0.68      0.594     0.634      0.846    0
                 0.671     0.289      0.509     0.671     0.579      0.749    1
                 0.441     0.128      0.552     0.441     0.49       0.722    2
                 0.573     0.074      0.573     0.573     0.573      0.862    3
                 0         0          0         0         0          0.778    4
Weighted Avg.    0.568     0.161      0.57      0.568     0.563      0.785

=== Confusion Matrix ===

   a   b   c   d   e   <-- classified as
 170 101   8   7   0 |   a = 0
  52 220  49   7   0 |   b = 1
  22  90 123  44   0 |   c = 2
   6  20  41  90   0 |   d = 3
   0   1   2   9   0 |   e = 4
```

### 2016-05-10
1. 基于豆瓣的即将上映影片信息补充本地数据库:
  * 本地最大video id: 643854
  * 本地最大star id: 2254074
  * 本地最大video_star_rel: 30705
2. 将模型和训练数据schema进行序列化，反序列化加载到web项目中，并暴露成web service

### 2016-05-11
1. 考虑将现有的web service接口与前端进行对接
2. 对不同分类器预测的接口进行投票，最终输出

### 2016-05-12
1. 完成影人主创排名的界面展现，影人排名可根据不同身份进行排名。

### 2016-05-16
1. 完成缺失影人数据爬虫，重新更新主创指数，导入未上映影片数据
2. 将影评情感分析与关键词提取，集成到项目中。
