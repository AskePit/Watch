package com.pitm.watch.engine;

import java.util.Calendar;

/**
 * Created by Aske on 08.04.2017.
 */

public class Math {

    private static double normalize(double val, int limit) {
        int truncated = (int)val;
        double rest = val%1;
        return truncated%limit + rest;
    }

    public static PointD translatePoint(PointD point, PointD center, double rads)
    {
        point.asub(center);

        double x = point.x;
        double y = point.y;

        double newX = x*java.lang.Math.cos(rads) - y*java.lang.Math.sin(rads);
        double newY = y*java.lang.Math.cos(rads) + x*java.lang.Math.sin(rads);

        point.set(newX, newY);
        point.aadd(center);

        return point;
    }

    // Default Scale methods
    public static double hoursToRads(double hours) {
        hours = normalize(hours, 12);
        return java.lang.Math.toRadians(hours*30.0);
    }

    public static double minsecToRads(double minsec) {
        minsec = normalize(minsec, 60);
        return java.lang.Math.toRadians(minsec*6.0);
    }

    public static double radsToHours(double rads) {
        double degrees = java.lang.Math.toDegrees(rads);
        degrees = normalize(degrees, 360);

        return degrees/30.0;
    }

    public static double radsToMinSec(double rads) {
        double degrees = java.lang.Math.toDegrees(rads);
        degrees = normalize(degrees, 360);

        return degrees/6.0;
    }

    // Extended Scale methods
    public static double valToRads(double val, Scale scale) {
        val = normalize(val, scale.maxVal);
        val = java.lang.Math.max(val, scale.minVal);

        switch (scale.linearity) {
            default:
            case Linear: {
                return java.lang.Math.toRadians(val*scale.factor());
            }
        }
    }

    public static double radsToVal(double rads, Scale scale) {
        double degrees = java.lang.Math.toDegrees(rads);
        degrees = normalize(degrees, scale.maxDegree);
        degrees = java.lang.Math.max(degrees, scale.minDegree);

        return degrees/scale.factor();
    }

    public static double hours(Calendar calendar) {
        int hours = calendar.get(Calendar.HOUR);
        double minutes = minutes(calendar);

        return (double)hours + minutes/60.0;
    }

    public static double minutes(Calendar calendar) {
        int minutes = calendar.get(Calendar.MINUTE);
        double seconds = seconds(calendar);

        return (double)minutes + seconds/60.0;
    }

    public static double seconds(Calendar calendar) {
        /*int seconds = calendar.get(Calendar.SECOND);
        double miliseconds = milliseconds(calendar);

        return (double)seconds + miliseconds/1000.0;*/
        return calendar.get(Calendar.SECOND);
    }

    public static double milliseconds(Calendar calendar) {
        return calendar.get(Calendar.MILLISECOND);
    }
}
