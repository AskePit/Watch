package com.pitm.watch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CanvasView m_canvasView;
    boolean m_draw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_canvasView = new CanvasView(this);
        setContentView(m_canvasView);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(m_draw) {
                                    m_canvasView.invalidate();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        m_draw = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        m_draw = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        m_draw = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        m_draw = false;
    }
}
