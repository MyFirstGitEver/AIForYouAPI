package com.example.AIForYouAPI.models;

import com.example.AIForYouAPI.tools.Pair;
import com.example.AIForYouAPI.tools.Vector;

abstract class PredictFunction {
    abstract float predict(Vector x, Vector w, float b);
    abstract  Vector derivativeByW(Vector w, float b, Pair<Vector, Float>[] dataset);

    public float derivativeByB(Vector w, float b, Pair<Vector, Float>[] dataset) {
        float total = 0;
        int datasetLength = dataset.length;

        for (Pair<Vector, Float> vectorFloatPair : dataset) {
            total += (predict(vectorFloatPair.first, w, b) - vectorFloatPair.second);
        }

        return total / datasetLength;
    }
}

public class PolynomialPredictor extends PredictFunction{
    @Override
    public float predict(Vector x, Vector w, float b) {
        return (float) (x.dot(w) + b);
    }

    @Override
    public Vector derivativeByW(Vector w, float b, Pair<Vector, Float>[] dataset) {
        Vector derivative = new Vector(w.size());

        int datasetLength = dataset.length;
        int features = w.size();

        for(int i=0;i<features;i++){
            for (Pair<Vector, Float> vectorFloatPair : dataset) {
                float curr = (float) derivative.x(i);

                curr += vectorFloatPair.first.x(i) *
                        (predict(vectorFloatPair.first, w, b) - vectorFloatPair.second);
                derivative.setX(i, curr);
            }

            derivative.setX(i, derivative.x(i) / datasetLength);
        }

        return derivative;
    }
}
