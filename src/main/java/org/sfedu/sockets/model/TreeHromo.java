package org.sfedu.sockets.model;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zaporozhets on 08.11.2019.
 */
public class TreeHromo implements Serializable {

    private final static Random RND = new Random();

    private static final long serialVersionUID = -6958865279291538510L;
    private final TreeGene length;
    private final TreeGene minLength;
    private final TreeGene lengthFactor;
    private final TreeGene widthFactor;
    private final TreeGene angleFactor;
    private final TreeGene width;
    private final TreeGene maxLevel;

    public TreeHromo(double length, double minLength, double lengthFactor, double widthFactor, double width, double angleFactor, Double maxLevel) {
        this.length = new TreeGene(length, GeneTypes.LENGTH);
        this.minLength = new TreeGene(minLength, GeneTypes.MIN_LENGTH);
        this.lengthFactor = new TreeGene(lengthFactor, GeneTypes.LENGTH_FACTOR);
        this.widthFactor = new TreeGene(widthFactor, GeneTypes.WIDTH_FACTOR);
        this.angleFactor = new TreeGene(angleFactor, GeneTypes.ANGLE_FACTOR);
        this.width = new TreeGene(width, GeneTypes.WIDTH);
        this.maxLevel = new TreeGene(maxLevel, GeneTypes.MAX_LEVEL);
    }

    public TreeHromo() {
        this.length = getRandom(GeneTypes.LENGTH);
        this.minLength = getRandom(GeneTypes.MIN_LENGTH);
        this.lengthFactor = getRandom(GeneTypes.LENGTH_FACTOR);
        this.widthFactor = getRandom(GeneTypes.WIDTH_FACTOR);
        this.angleFactor = getRandom(GeneTypes.ANGLE_FACTOR);
        this.width = getRandom(GeneTypes.WIDTH);
        this.maxLevel = getRandom(GeneTypes.MAX_LEVEL);
    }

    private static TreeGene getRandom(GeneTypes type) {
        return new TreeGene((RND.nextDouble() * (type.getMax() - type.getMin())) + type.getMin(), type);
    }

    public TreeGene getLength() {
        return length;
    }

    public TreeGene getMinLength() {
        return minLength;
    }

    public TreeGene getLengthFactor() {
        return lengthFactor;
    }

    public TreeGene getWidthFactor() {
        return widthFactor;
    }

    public TreeGene getAngleFactor() {
        return angleFactor;
    }

    public TreeGene getMaxLevel() {
        return maxLevel;
    }

    public TreeGene getWidth() {
        return width;
    }

    private TreeHromo getParent(TreeHromo treeHromo) {
        return Math.random() > .5 ? treeHromo : this;
    }

    public TreeHromo crossover(TreeHromo treeHromo) {
        return new TreeHromo(
                getParent(treeHromo).getLength().getValue(),
                getParent(treeHromo).getMinLength().getValue(),
                getParent(treeHromo).getLengthFactor().getValue(),
                getParent(treeHromo).getWidthFactor().getValue(),
                getParent(treeHromo).getWidth().getValue(),
                getParent(treeHromo).getAngleFactor().getValue(),
                getParent(treeHromo).getMaxLevel().getValue());
    }
}
