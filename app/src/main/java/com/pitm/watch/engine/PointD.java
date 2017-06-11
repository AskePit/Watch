package com.pitm.watch.engine;

import android.graphics.Point;

/**
 * Created by Aske on 09.04.2017.
 */

public class PointD {
    public double x;
    public double y;

    public double x() {
        return x;
    }
    public double y() {
        return y;
    }

    public float xf() { return (float)x; }
    public float yf() {
        return (float)y;
    }

    public PointD(double _x, double _y) {
        set(_x, _y);
    }
    public PointD(PointD another) {
        set(another);
    }
    public PointD(Point another) {
        set(another);
    }

    public void set(double _x, double _y) {
        x = _x;
        y = _y;
    }

    public void set(PointD another) {
        x = another.x;
        y = another.y;
    }

    public void set(Point another) {
        x = another.x;
        y = another.y;
    }

    public void aadd(PointD another) {
        x += another.x;
        y += another.y;
    }

    public void aadd(Point another) {
        x += another.x;
        y += another.y;
    }

    public void asub(PointD another) {
        x -= another.x;
        y -= another.y;
    }

    public void asub(Point another) {
        x -= another.x;
        y -= another.y;
    }

    public void amul(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public PointD add(PointD another) {
        return new PointD(this.x + another.x, this.y + another.y);
    }
    public PointD add(Point another) {
        return new PointD(this.x + another.x, this.y + another.y);
    }
    public PointD sub(PointD another) {
        return new PointD(this.x - another.x, this.y - another.y);
    }
    public PointD sub(Point another)  {
        return new PointD(this.x - another.x, this.y - another.y);
    }
    public PointD mul(double scalar)     {
        return new PointD(this.x*scalar, this.y*scalar);
    }
}
