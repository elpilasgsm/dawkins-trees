package org.sfedu.sockets.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Zaporozhets on 07.11.2019.
 */
public class Solution implements Serializable, Comparable<Solution> {
    private static final long serialVersionUID = -6745231044394067513L;
    private char[] data;
    private double fitness;

    public Solution() {

    }

    private void getFitness(Solution solution) {
        double match = 0;
        for (int i = 0; i < solution.getData().length; i++) {
            if (solution.getData()[i] == this.data[i]) {
                match += 1.0;
            }
        }
        fitness = match / (double) solution.getData().length;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Solution(String input, Solution target) {
        if (input != null && input.length() > 0) {
            data = input.toUpperCase().toCharArray();
            if (target != null)
                getFitness(target);
        } else {
            data = null;
        }
    }

    public Solution(char[] input, Solution target) {
        this.data = input;
        getFitness(target);
    }

    public char[] getData() {
        return data;
    }

    public void setData(char[] data) {
        this.data = data;
    }

    @Override
    public int compareTo(Solution o) {
        return o == null ? -1 : Double.compare(o.getFitness(), this.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        if (Double.compare(solution.fitness, fitness) != 0) return false;
        return Arrays.equals(data, solution.data);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Arrays.hashCode(data);
        temp = Double.doubleToLongBits(fitness);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
