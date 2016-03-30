package xyz.youngbin.timegradient.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by youngbin on 16. 3. 30.
 */
public class BitMapUtil {
    public static int getAverageColor(Drawable image){

        //Get Bitmap Width and Height
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        int BitmapWidth = b.getWidth();
        int BitmapHeight = b.getHeight();
        int pixel;
        int pixelSumRed = 0;
        int pixelSumBlue = 0;
        int pixelSumGreen = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 70; j < 100; j++){
                //Get Pixels from bitmap
                pixel = b.getPixel((int) Math.round((i * BitmapWidth) / 100),
                        (int) Math.round((j * BitmapHeight) / 100));

                //Get RGB code
                pixelSumRed += Color.red(pixel);
                pixelSumGreen += Color.green(pixel);
                pixelSumBlue += Color.blue(pixel);
            }
        }
        int averagePixelRed = pixelSumRed / 3000;
        int averagePixelBlue = pixelSumBlue / 3000;
        int averagePixelGreen = pixelSumGreen / 3000;

        int AverageColorCodeRGB = Color.rgb(averagePixelRed + 3, averagePixelGreen + 3, averagePixelBlue + 3);

        return AverageColorCodeRGB;
    }
}
