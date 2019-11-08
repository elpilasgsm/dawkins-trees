package org.sfedu.sockets.model;

/**
 * Created by Zaporozhets on 08.11.2019.
 */
public enum GeneTypes {

    LENGTH(100.0, 10.0, 1.0),
    MIN_LENGTH(10.0, 0.0, 0.05),
    LENGTH_FACTOR(1.0, 0.0, 0.05),
    WIDTH(23.0, 1.0, 0.5),
    WIDTH_FACTOR(1.0, .01, 0.05),
    ANGLE_FACTOR(Math.PI, -Math.PI, Math.PI / 90.0),
    MAX_LEVEL(15.0, 0.0, 1.0);

    private final double max;
    private final double min;
    private final double increment;

    GeneTypes(double max, double min, double increment) {
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getIncrement() {
        return increment;
    }
}
