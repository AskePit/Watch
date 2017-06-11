package com.pitm.watch.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.pitm.watch.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Aske on 08.04.2017.
 */

public class Watch {

    public Watch() {}

    public void addFace(Face face) {
        m_faces.add(face);
    }
    public ArrayList<Face> faces() { return m_faces; }
    public void setContext(Context context) { m_context = context; }
    public void setBitmapId(int bitmapId) { m_bitmapId = bitmapId; }

    private void calcBitmapSettings(Canvas canvas) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.z_7086_1_z_7086_1, options);

        int deviceWidth = canvas.getWidth();
        int deviceHeight = canvas.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        Log.d("Watch deviceWidth", Integer.toString(deviceWidth));
        Log.d("Watch deviceHeight", Integer.toString(deviceHeight));
        Log.d("Watch bitmapWidth", Integer.toString(bitmapWidth));
        Log.d("Watch bitmapHeight", Integer.toString(bitmapHeight));

        double wRatio = 1.0;
        double hRatio = 1.0;

        if(bitmapWidth > deviceWidth) {
            wRatio = deviceWidth/(double)bitmapWidth;
        }

        if(bitmapHeight > deviceHeight) {
            hRatio = deviceHeight/(double)bitmapHeight;
        }

        m_ratio = java.lang.Math.min(wRatio, hRatio);
        m_scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int)java.lang.Math.round(bitmapWidth*m_ratio), (int)java.lang.Math.round(bitmapHeight*m_ratio), true);

        int xOffset = (deviceWidth - m_scaledBitmap.getWidth()) / 2;
        int yOffset = (deviceHeight - m_scaledBitmap.getHeight()) / 2;
        m_offset = new Point(xOffset, yOffset);
    }

    public void draw(Canvas canvas) {
        calcBitmapSettings(canvas);
        canvas.drawBitmap(m_scaledBitmap, m_offset.x, m_offset.y, null);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

        Calendar now = new GregorianCalendar();

        for (Face face : m_faces) {
            for (Hand hand : face.hands()) {
                double length = hand.length()*m_ratio;
                double center = hand.center()*m_ratio;
                PointD centerPos = hand.pos().mul(m_ratio).add(m_offset);

                PointD startPos = new PointD(centerPos);
                startPos.y += center;

                PointD endPos = new PointD(centerPos);
                endPos.y -= length-center;

                double val = 0.0;
                switch (hand.role()) {
                    case Calendar.HOUR:        val = Math.hours(now); break;
                    case Calendar.MINUTE:      val = Math.minutes(now); break;
                    case Calendar.SECOND:      val = Math.seconds(now); break;
                    case Calendar.MILLISECOND: val = Math.milliseconds(now); break;
                }
                double angle = Math.valToRads(val, hand.scale());
                startPos = Math.translatePoint(startPos, centerPos, angle);
                endPos = Math.translatePoint(endPos, centerPos, angle);

                paint.setStrokeWidth(hand.bitmap().getWidth());
                canvas.drawLine(startPos.xf(), startPos.yf(), endPos.xf(), endPos.yf(), paint);
            }
        }


    }

    private ArrayList<Face> m_faces; {
        m_faces = new ArrayList<Face>();
    }

    private Context m_context;
    private int m_bitmapId;
    private Bitmap m_scaledBitmap;
    private double m_ratio;
    private Point m_offset;
}
