package com.pitm.watch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.pitm.watch.engine.Watch;
import com.pitm.watch.fabrics.Z_7086_1;


public class CanvasView extends View {
    public CanvasView(Context context) {
        super(context);
    }

    Watch watch;
    {
        watch = Z_7086_1.get();
        watch.setContext(this.getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        watch.draw(canvas);
    }
}
