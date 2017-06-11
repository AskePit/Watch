package com.pitm.watch.engine;

import java.util.ArrayList;

/**
 * Created by Aske on 08.04.2017.
 */

public class Face {
    public enum Type {
        H12,
        H24,
    }

    public Face(PointD pos, int radius, Type type) {
        m_pos = pos;
        m_radius = radius;
        m_type = type;
    }

    public void attachHand(Hand hand, PointD pos, Scale scale, int role) {
        hand.attach(this, pos, scale, role);
        m_hands.add(hand);
    }

    public PointD pos() { return m_pos; }
    public int radius() { return m_radius; }
    public Type type() { return m_type; }
    public ArrayList<Hand> hands() { return m_hands; }

    private PointD m_pos;
    private int m_radius;
    private Type m_type;
    private ArrayList<Hand> m_hands; {
        m_hands = new ArrayList<Hand>();
    }
}
