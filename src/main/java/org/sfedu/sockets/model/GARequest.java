package org.sfedu.sockets.model;

import java.io.Serializable;

/**
 * Created by Zaporozhets on 07.11.2019.
 */
public class GARequest implements Serializable {
    private static final long serialVersionUID = -3200814841854592256L;
    private int iterations;
    private int populationSize;
    private String targetWord;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }
}
