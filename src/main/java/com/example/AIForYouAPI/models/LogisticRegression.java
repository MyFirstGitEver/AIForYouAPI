package com.example.AIForYouAPI.models;

import com.example.AIForYouAPI.tools.Pair;
import com.example.AIForYouAPI.tools.Vector;

public class LogisticRegression extends Regression {

    public LogisticRegression(PredictFunction predictor, Pair<Vector, Float>[] dataset) {
        super(predictor, dataset);
    }

    @Override
    public float errorOnData(Pair<Vector, Float>[] set) {
        float total = 0;

        // -(yi * log(sigmoid) + (1 - yi)*log(1 - sigmoid))

        for (Pair<Vector, Float> point : set) {
            float predictPercent = predictor.predict(point.first, w, b);

            total -= (point.second * Math.log(Math.abs(predictPercent + 0.0001)))
                    + (1 - point.second) * Math.log(Math.abs(1 - predictPercent + 0.0001));
        }

        return total / dataset.length;
    }

    @Override
    public float cost() {
        return errorOnData(dataset);
    }

    @Override
    protected float predict(Vector x) {
        return predictor.predict(x, w, b);
    }

    public boolean isPositive(Vector x) {
        return predict(x) >= 0.5f;
    }
}
