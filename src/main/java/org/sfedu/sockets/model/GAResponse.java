package org.sfedu.sockets.model;

import java.io.Serializable;

/**
 * Created by Zaporozhets on 07.11.2019.
 */
public class GAResponse implements Serializable {
    private static final long serialVersionUID = -4294106524130069518L;
    private Solution current;
    private int iterationNumber;
    private int totalIterations;
    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Solution getCurrent() {
        return current;
    }

    public void setCurrent(Solution current) {
        this.current = current;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public int getTotalIterations() {
        return totalIterations;
    }

    public void setTotalIterations(int totalIterations) {
        this.totalIterations = totalIterations;
    }
}
