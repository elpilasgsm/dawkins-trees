package org.sfedu.sockets.model;

import java.io.Serializable;

/**
 * Created by Zaporozhets on 08.11.2019.
 */
public class TreeGene implements Serializable {
    private static final long serialVersionUID = 3786809516666598218L;
    private Double value;
    private final GeneTypes type;

    public TreeGene(Double value, GeneTypes type) {
        this.value = value;
        this.type = type;
    }

    public Double increment() {
        value = Double.sum(value, type.getIncrement());
        if (value > type.getMax()) {
            value = type.getMax();
        }
        return value;
    }

    public Double decrement() {
        value = Double.sum(value, -1 * type.getIncrement());
        if (value < type.getMin()) {
            value = type.getMin();
        }
        return value;
    }

    public Double getValue() {
        return value;
    }

    public GeneTypes getType() {
        return type;
    }

}
