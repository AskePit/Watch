package com.pitm.watch.fabrics;

import android.graphics.Bitmap;

import com.pitm.watch.R;
import com.pitm.watch.engine.Face;
import com.pitm.watch.engine.Hand;
import com.pitm.watch.engine.PointD;
import com.pitm.watch.engine.Scale;
import com.pitm.watch.engine.Watch;

import java.util.Calendar;

/**
 * Created by Aske on 09.04.2017.
 */

public class Z_7086_1 {
    public static Watch get() {
        Hand hourHand = new Hand(Bitmap.createBitmap(10, 160, Bitmap.Config.RGB_565), 0, 0);
        Hand minHand = new Hand(Bitmap.createBitmap(10, 260, Bitmap.Config.RGB_565), 0, 0);
        Hand secHand = new Hand(Bitmap.createBitmap(3, 320, Bitmap.Config.RGB_565), 50, 0);

        Hand l_hourHand = new Hand(Bitmap.createBitmap(5, 40, Bitmap.Config.RGB_565), 0, 0);
        Hand l_minHand = new Hand(Bitmap.createBitmap(5, 70, Bitmap.Config.RGB_565), 0, 0);

        Hand r_hourHand = new Hand(Bitmap.createBitmap(5, 40, Bitmap.Config.RGB_565), 0, 0);
        Hand r_minHand = new Hand(Bitmap.createBitmap(5, 70, Bitmap.Config.RGB_565), 0, 0);

        Hand b_hourHand = new Hand(Bitmap.createBitmap(5, 40, Bitmap.Config.RGB_565), 0, 0);
        Hand b_minHand = new Hand(Bitmap.createBitmap(5, 70, Bitmap.Config.RGB_565), 0, 0);

        final PointD center = new PointD(600, 621);
        final PointD bottomCenter = new PointD(596, 739);
        final PointD leftCenter = new PointD(482, 595);
        final PointD rightCenter = new PointD(710, 597);

        final int radius = 297;

        Face f1 = new Face(center, radius, Face.Type.H12);
        f1.attachHand(hourHand, center, Scale.hoursScale(), Calendar.HOUR);
        f1.attachHand(minHand, center, Scale.minsecScale(), Calendar.MINUTE);
        f1.attachHand(secHand, center, Scale.minsecScale(), Calendar.SECOND);

        f1.attachHand(l_hourHand, leftCenter, Scale.hoursScale(), Calendar.HOUR);
        f1.attachHand(l_minHand, leftCenter, Scale.minsecScale(), Calendar.MINUTE);

        f1.attachHand(r_hourHand, rightCenter, Scale.hoursScale(), Calendar.HOUR);
        f1.attachHand(r_minHand, rightCenter, Scale.minsecScale(), Calendar.MINUTE);

        f1.attachHand(b_hourHand, bottomCenter, Scale.hoursScale(), Calendar.HOUR);
        f1.attachHand(b_minHand, bottomCenter, Scale.minsecScale(), Calendar.MINUTE);

        Watch w = new Watch();
        w.setBitmapId(R.drawable.z_7086_1_z_7086_1);
        w.addFace(f1);

        return w;
    }
}
