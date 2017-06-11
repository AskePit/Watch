package com.pitm.watch.engine;

/**
 * Created by Aske on 09.04.2017.
 */

public class Scale {
    public enum Linearity {
        Linear,
        Logarithmic,
    }

    public int minVal = 0;
    public int maxVal = 12;

    public int minDegree = 0;
    public int maxDegree = 360;

    public Scale() {}

    public Scale(int _minVal, int _maxVal) {
        minVal = _minVal;
        maxVal = _maxVal;
    }

    public Scale(int _minVal, int _maxVal, int _minDegree, int _maxDegree) {
        minVal = _minVal;
        maxVal = _maxVal;
        minDegree = _minDegree;
        maxDegree = _maxDegree;
    }

    public Scale(int _minVal, int _maxVal, int _minDegree, int _maxDegree, Linearity l) {
        minVal = _minVal;
        maxVal = _maxVal;
        minDegree = _minDegree;
        maxDegree = _maxDegree;
        linearity = l;
    }

    public static Scale hoursScale() {
        return new Scale();
    }

    public static Scale minsecScale() {
        return new Scale(0, 60);
    }

    public double factor() {
        double degreeDiff = maxDegree-minDegree;
        double valDiff = maxVal-minVal;
        return degreeDiff/valDiff;
    }

    public Linearity linearity = Linearity.Linear;
}
