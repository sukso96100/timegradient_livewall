package xyz.youngbin.timegradient;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.view.SurfaceHolder;

import xyz.youngbin.timegradient.util.GradientUtil;

public class TimeGradientService
        extends android.service.wallpaper.WallpaperService {
    public TimeGradientService() {
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
                    SweepGradient gradient = GradientUtil.buildGradient(
                            canvas.getWidth(), canvas.getHeight(), TimeGradientService.this);
                    Matrix gradientMatrix = new Matrix();
//                    gradientMatrix.preRotate(GradientUtil.getCurrentTimeInPercentage());
                    gradientMatrix.preRotate(GradientUtil.getCurrentTimeInPercentage(),
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
                    Paint Black = new Paint();
                    Black.setColor(Color.BLACK);
                    canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),Black);
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
