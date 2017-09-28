package com.cheerchip.yuanxingkedu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class Graphics {

    public static void drawline(Canvas canvas, float startx, float starty, float endx, float endy, Paint paint){
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(startx,starty,endx,endy,paint);
    }
    public static void drawstring(Canvas canvas, float x, float y, String text, float textsize, Paint paint){
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(textsize);
        canvas.drawText(text,x, y,paint);
    }
    public static  void drawBitmap(Canvas canvas, float x, float y, Bitmap bitmap){
        canvas.drawBitmap(bitmap,x,y,null);
    }
    public static Bitmap scale(Bitmap img, float newWidth, float newHeight)
    {
        if (img == null || img.isRecycled())
        {
            return null;
        }

        float height = img.getHeight();
        float width = img.getWidth();
        if (height == 0 || width == 0 || newWidth == 0 || newHeight == 0)
        {
            return null;
        }
        // 创建缩放图片所需的Matrix
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / width, newHeight / height);
        try
        {
            // 生成对img缩放之后的图片
            return Bitmap.createBitmap(img, 0, 0,
                    (int) width, (int) height, matrix, true);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
