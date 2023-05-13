package com.example.AIForYouAPI.models;

import com.example.AIForYouAPI.tools.Pair;
import com.example.AIForYouAPI.tools.Vector;

public class LinearRegression extends Regression {

    public LinearRegression(PredictFunction predictor, Pair<Vector, Float>[] dataset) {
        super(predictor, dataset);
    }

    // R-squared error
    @Override
    public float cost() {
        return errorOnData(dataset);
    }

    @Override
    public float predict(Vector x) {
        if (x.size() != w.size()) {
            return Float.NaN;
        }

        return predictor.predict(x, w, b);
    }

    @Override
    public float errorOnData(Pair<Vector, Float>[] set) {
        int n = dataset.length;
        float total = 0;

        for (Pair<Vector, Float> p : set) {
            float term = (p.second - predictor.predict(p.first, w, b));
            total += term * term;
        }

        return total / (2 * n);
    }
}
