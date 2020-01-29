package com.samsung.myitschool.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{


    private float x,y,radius;
    private boolean flag;
    private MyThread m1;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m1 = new MyThread();
        m1.setRunning(true);
        m1.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        m1.setRunning(false);
        Log.e("RRRRRRR","surfaceDestroyed()");
        while(retry) {
            Log.e("RRRRRRR","retry...");
            try {
                m1.join();
                retry = false;
                Log.e("RRRRRRR","finished!!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        this.x = event.getX();
        this.y = event.getY();
        this.radius = 0;
        flag = true;
        return true;
    }


    class MyThread extends Thread {
            private boolean running;
            @Override
            public void run() {
                Paint p = new Paint();
                p.setColor(Color.YELLOW);
                while(running) {
                    Canvas canvas = getHolder().lockCanvas();
                    if(canvas != null) {
                        canvas.drawColor(Color.BLUE);
                        canvas.drawCircle(x,y,radius,p);
                        radius += flag ? 5 : 0;
                        getHolder().unlockCanvasAndPost(canvas);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}