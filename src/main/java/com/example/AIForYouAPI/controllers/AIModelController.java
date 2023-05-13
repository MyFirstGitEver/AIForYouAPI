package com.example.AIForYouAPI.controllers;

import com.example.AIForYouAPI.dtos.RegressionParams;
import com.example.AIForYouAPI.entities.AIModelEntity;
import com.example.AIForYouAPI.entities.ModelUsageEntity;
import com.example.AIForYouAPI.entities.UserEntity;
import com.example.AIForYouAPI.models.*;
import com.example.AIForYouAPI.repositories.AIModelRepo;
import com.example.AIForYouAPI.repositories.AIReceiptsRepo;
import com.example.AIForYouAPI.repositories.ModelUsageRepo;
import com.example.AIForYouAPI.repositories.UserRepo;
import com.example.AIForYouAPI.tools.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ai/")
public class AIModelController {
    interface GettingTrainResultListener {
        ResponseEntity<String> getResponse(Pair<Vector, Float>[] train, Pair<Vector, Float>[] test, int iteration);
    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    AIReceiptsRepo aiReceiptsRepo;

    @Autowired
    AIModelRepo aiModelRepo;

    @Autowired
    ModelUsageRepo modelUsageRepo;

    @GetMapping("/lr/{requester}/{col}")
    public ResponseEntity<String> trainUsingLR(
            @PathVariable("requester") String requester,
            @PathVariable("col") int col,
            @RequestParam("features") List<String> features,
            @RequestParam("excelUrl") String url) {

        return train(requester, url, "Linear regression", col, 0.8f, features, (train, test, iteration) -> {
            featureScaling(train, test);
            LinearRegression regression = new LinearRegression(new PolynomialPredictor(), train);
            regression.train(0.001f, iteration);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                RegressionParams params = new RegressionParams(regression.getW(), regression.getB(),
                        regression.errorOnData(test));

                return ResponseEntity.ok(ow.writeValueAsString(params));
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/lor/{requester}/{col}")
    public ResponseEntity<String> trainUsingLoR(
            @PathVariable("requester") String requester,
            @PathVariable("col") int col,
            @RequestParam("features") List<String> features,
            @RequestParam("excelUrl") String url) {

        return train(requester, url, "Logistic regression", col, 0.8f, features, (train, test, iteration) -> {
            boolean binary = true;

            for(Pair<Vector, Float> point : train) {
                if(point.second > 1) {
                    binary = false;
                    break;
                }
            }

            if(!binary) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
            }

            featureScaling(train, test);
            LogisticRegression regression = new LogisticRegression(new PolynomialPredictor(), train);
            regression.train(0.0001f, iteration);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                RegressionParams params = new RegressionParams(regression.getW(), regression.getB(),
                        regression.errorOnData(test));

                return ResponseEntity.ok(ow.writeValueAsString(params));
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/clustering/{requester}/{cluster}")
    public ResponseEntity<String> cluster(@PathVariable("requester") String requester,
                                               @RequestParam("excelUrl") String url,
                                               @PathVariable("cluster") int cluster,
                                               @RequestParam("features") List<String> features) {
        return train(requester, url, "Clustering", Integer.MAX_VALUE, 1.0f, features, (train, test, iteration) -> {
            Vector[] data = new Vector[train.length];

            for(int i=0;i<data.length;i++) {
                data[i] = train[i].first;
            }

            featureScaling(train, test);
            KMeansClustering clustering = new KMeansClustering(5, cluster, iteration, data);
            clustering.train();

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                int[] indexes = new int[train.length];

                for(int i=0;i<train.length;i++) {
                    indexes[i] = clustering.clusterNumber(train[i].first);
                }

                return ResponseEntity.ok(ow.writeValueAsString(indexes));
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }
    private ResponseEntity<String> train(String requester,
                                              String url,
                                              String modelName,
                                              int col,
                                              float percent,
                                              List<String> features,
                                              GettingTrainResultListener gettingTrainResultListener) {
        UserEntity user = userRepo.findByTenDn(requester);
        AIModelEntity model = aiModelRepo.findByTenModel(modelName);

        if(user == null || model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        int idNguoiMua = userRepo.findByTenDn(requester).getId();
        int idModel = aiModelRepo.findByTenModel(modelName).getId();

        boolean ownedThisModel = aiReceiptsRepo.findByIdNguoiMuaAndIdModel(idNguoiMua, idModel) != null;

        if(!ownedThisModel) {
            long count = modelUsageRepo.countByUserId(idModel);

            if(count == 5) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            }

            modelUsageRepo.save(new ModelUsageEntity(idNguoiMua, idModel, new Date()));
        }

        try {
            ExcelReader reader = new ExcelReader(url);
            Pair<Vector, Float>[] dataset = reader.createLabeledDataset(col, 0, features);

            Pair<Vector, Float>[] trainData = Arrays.copyOfRange(dataset, 0, (int) (dataset.length * percent));
            Pair<Vector, Float>[] testData = Arrays.copyOfRange(dataset, (int) (dataset.length * percent),
                    dataset.length);

            int iteration;
            if(modelName.equals("Clustering")) {
                iteration = ownedThisModel ? 200 : 100;
            }
            else {
                iteration = ownedThisModel ? 4000 : 2000;
            }

            return gettingTrainResultListener.getResponse(trainData, testData, iteration);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void featureScaling(Pair<Vector, Float>[] trainSet, Pair<Vector, Float>[] testSet) {
        float[] mean = new float[trainSet[0].first.size()];
        float[] std = new float[trainSet[0].first.size()];

        for(int i=0;i<mean.length;i++) {
            for (Pair<Vector, Float> train : trainSet) {
                mean[i] += train.first.x(i);
            }

            mean[i] /= trainSet.length;
        }

        for(int i=0;i<mean.length;i++) {
            for (Pair<Vector, Float> train : trainSet) {
                double term = (train.first.x(i) - mean[i]);

                std[i] += term * term;
            }

            std[i] = (float) Math.sqrt(std[i] / trainSet.length);
        }

        for (Pair<Vector, Float> train : trainSet) {
            for (int i = 0; i < trainSet[0].first.size(); i++) {
                train.first.setX(i, (train.first.x(i) - mean[i]));

                if(std[i] != 0) {
                    train.first.setX(i, train.first.x(i) / std[i]);
                }
            }
        }

        for (Pair<Vector, Float> test : testSet) {
            for (int i = 0; i < testSet[0].first.size(); i++) {
                test.first.setX(i, (test.first.x(i) - mean[i]));

                if(std[i] != 0) {
                    test.first.setX(i, test.first.x(i) / std[i]);
                }
            }
        }
    }
}