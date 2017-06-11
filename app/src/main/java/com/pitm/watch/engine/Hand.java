package com.pitm.watch.engine;

import android.graphics.Bitmap;

/**
 * Created by Aske on 08.04.2017.
 */

public class Hand {

    void attach(Face parent, PointD pos, Scale scale, int role) {
        m_parent = parent;
        m_pos = pos;
        m_scale = scale;
        m_role = role;
    }

    public Hand(Bitmap bitmap, int center, int zOrder) {
        m_bitmap = bitmap;
        m_center = center;
        m_length = bitmap.getHeight();
        m_zOrder = zOrder;
    }

    public Face parent() { return m_parent; }
    public PointD pos() { return m_pos; }
    public Scale scale() { return m_scale; }
    public int role() { return m_role; }

    public Bitmap bitmap() { return m_bitmap; }
    public int length() { return m_length; }
    public int center() { return m_center; }
    public int zOrder() { return m_zOrder; }

    public double angle() { return m_angle; }
    public void setAngle(double angle) { m_angle = angle; }

    private Face m_parent;
    private PointD m_pos;
    private Scale m_scale;
    private int m_role;

    private Bitmap m_bitmap;
    private int m_length;
    private int m_center;
    private int m_zOrder;

    private double m_angle;
}
