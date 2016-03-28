package xyz.youngbin.c3w;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.view.SurfaceHolder;

public class C3WService extends android.service.wallpaper.WallpaperService {
    public C3WService() {
    }

       @Override
    public Engine onCreateEngine() {
        return new C3WEngine();
    }

    private class C3WEngine extends Engine {

        // VARS |->
        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                drawClock();
            }
        };
        private int width;
        int height;
        private boolean visible = true;
        // ->| VARS

        public C3WEngine(){
            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        //Draw Wallpaper
        private void drawClock() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {

                    //Build Gradient
                    SweepGradient gradient = Util.buildGradient(canvas.getWidth(), canvas.getHeight());
                    Matrix gradientMatrix = new Matrix();
//                    gradientMatrix.preRotate(Util.getCurrentTimeInPercentage());
                    gradientMatrix.preRotate(Util.getCurrentTimeInPercentage(),
                            canvas.getWidth()/2, canvas.getHeight()/2);
                    gradient.setLocalMatrix(gradientMatrix);

                    //Paint Object that will used for filling rectangle with gradient
                    Paint Pnt= new Paint();
                    Pnt.setAntiAlias(true);
                    Pnt.setShader(gradient);

                    //Draw a rectangle that fits screen
//                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), Pnt);

                    int radius;
                    if(canvas.getWidth()>canvas.getHeight()){
                        radius = canvas.getWidth();
                    }else{
                        radius = canvas.getHeight();
                    }
                    canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2,radius, Pnt);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 5000);
            }
        }
    }
}
