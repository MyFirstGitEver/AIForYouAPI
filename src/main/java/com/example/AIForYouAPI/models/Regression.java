package com.example.AIForYouAPI.models;

import com.example.AIForYouAPI.tools.Pair;
import com.example.AIForYouAPI.tools.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Regression{
    protected final PredictFunction predictor;
    protected final Vector w;
    protected float b;
    protected final Pair<Vector, Float>[] dataset;
    Regression(PredictFunction predictor, Pair<Vector, Float>[] dataset) {
        this.predictor = predictor;
        this.w = new Vector(dataset[0].first.size());
        this.dataset = dataset;
    }

    abstract public float errorOnData(Pair<Vector, Float>[] set);

    abstract public float cost();
    abstract protected float predict(Vector x);

    public void train(float learningRate, int iter) {
        float cost;
        int iteration = 0;

        while((cost = Math.abs(cost())) > 0.001 && iteration <= iter){
            if(iteration != 0 && iteration % 30 == 0) {
                System.out.println(iteration + " iterations have passed. Cost: " + cost);
            }

            Vector v = predictor.derivativeByW(w, b, dataset).scaleBy(learningRate);
            b -= learningRate * predictor.derivativeByB(w, b, dataset);
            w.subtract(v);

            iteration++;
        }
    }

    private void saveParams() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("w.param"));
        for(int i=0;i<w.size();i++){
            writer.write(Float.toString((float) w.x(i)));
            writer.newLine();
        }

        writer.close();

        writer = new BufferedWriter(new FileWriter("b.param"));
        for(int i=0;i<b;i++){
            writer.write(Float.toString(b));
            writer.newLine();
        }

        writer.close();
    }

    public Vector getW() {
        return w;
    }

    public float getB() {
        return b;
    }
}

