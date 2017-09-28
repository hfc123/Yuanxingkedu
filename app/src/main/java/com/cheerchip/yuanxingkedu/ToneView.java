package com.cheerchip.yuanxingkedu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by noname on 2017/8/23.
 */

public class ToneView extends View {

    private float cx;
    private int cy;
    private Bitmap tonedefault;
    private Bitmap tone_yellow;
    private Paint paint;
    private Bitmap tone_knob;
    private Bitmap tone_knobdisc;
    private float degree;
    private float degrees;
    private float markPointX;
    private float markPointY;
    private int paddingwidth;
    private int paddingheight;
    private int viwwidth;
    private int viewheight;
    private int paddingheight2;

    public ToneView(Context context) {
        super(context);
      //  init();
    }

    public ToneView(Context context, AttributeSet attrs) {
        super(context, attrs);
       // init();
    }

    public ToneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
     //   init();
    }

    public ToneView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init() {
        //cx，cy是相对于屏幕的距离
        Log.e( "init: ",this.getX()+","+this.getY() );
        cx =this.getWidth()/2+this.getX();
        int[] location = new  int[2] ;
        getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        System.out.println("view--->x坐标:"+location [0]+"view--->y坐标:"+location [1]);
        cy =this.getHeight()/2+location [1];
        //两个圆图
        tonedefault = BitmapFactory.decodeResource(getResources(), R.mipmap.tone_knob_d);
        tone_yellow = BitmapFactory.decodeResource(getResources(), R.mipmap.tone_knob_2);
        //中间的图
        tone_knob = BitmapFactory.decodeResource(getResources(), R.mipmap.tone_knob);
        //外圆盘
        tone_knobdisc = BitmapFactory.decodeResource(getResources(), R.mipmap.tone_knob_disc);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viwwidth = MeasureSpec.getSize(widthMeasureSpec);
        viewheight = MeasureSpec.getSize(heightMeasureSpec);
        if(viwwidth >0){
            init();
            //改变大小时对图片大小进行缩放
            tonedefault= Graphics.scale(tonedefault, viwwidth -180, viewheight -180);
            tone_yellow= Graphics.scale(tone_yellow, viwwidth -180, viewheight -180);
            tone_knobdisc= Graphics.scale(tone_knobdisc, viwwidth, viewheight);
            tone_knob= Graphics.scale(tone_knob, viwwidth /2, viewheight /2);
            paddingwidth =(getWidth()-tone_yellow.getWidth())/2;
            paddingheight =(getHeight()-tone_yellow.getHeight())/2;
            //计算黄色圆心需要用
            paddingheight2 = viewheight /2-paddingheight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //取消硬件加速
       // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //画中间圆盘
       // canvas.drawBitmap(tone_knobdisc,0,0,paint);
        if (viwwidth>0) {
            canvas.drawBitmap(tonedefault, paddingwidth, paddingheight, paint);
            Bitmap bitmapsrcin = srcin(tone_yellow, 120, (int) (degrees + 151));
            canvas.drawBitmap(bitmapsrcin, paddingwidth, paddingheight, paint);
            Bitmap bitmapsrcout = srcout(tonedefault, 123, (int) (degrees + 146));
            //画按钮
            canvas.save();
            Log.e("onDraw: ", degrees + "");
            canvas.rotate(degrees, getWidth() / 2, getHeight() / 2);
            if (getWidth() != 0) {
                Log.e("onDraw: ", viwwidth + "");
                canvas.drawBitmap(tone_knob, viwwidth / 4, viewheight / 4, null);
            }
            canvas.restore();
        }
    }

    /**
     * 同srcin改变一下模式
     * @param tonedefault
     * @param i
     * @param i1
     * @return
     */
    private Bitmap srcout(Bitmap tonedefault, int i, int i1) {
        Bitmap bitmap3 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap3);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas2.drawARGB(0, 0, 0, 0);
        canvas2.drawArc(-120,-120,tone_yellow.getWidth(),tone_yellow.getHeight(),i,i1,true,paint);
        //更换模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas2.drawBitmap(tonedefault,0,0,paint);
        paint.setXfermode(null);
        return bitmap3;
    }

    //去共同的部分，和扇形的交集

    /**
     *
     * @param bitmapup 黄色圆
     * @param angle1 起始角度
     * @param angle2 扇形扫过的角度
     * @return
     */
    private Bitmap srcin(Bitmap bitmapup, int angle1, int angle2){
        //画布背景
        Bitmap bitmap = Bitmap.createBitmap(tone_yellow.getWidth(), tone_yellow.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmap);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas1.drawARGB(0, 0, 0, 0);
        //画扇形
        Log.e("srcin: ",angle2+"" );
        canvas1.drawArc(0,0,tone_yellow.getWidth(),paddingheight2*2,angle1,angle2,true,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas1.drawBitmap(bitmapup,0,0,paint);
        paint.setXfermode(null);
        //返回图片
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (true) {
            float x = event.getRawX();
            float y = event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    getdegree(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    getdegree(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    getdegree(x, y);
                    if (onDegreeChangeLisener!=null){
                        //Log.e( "onTouchEvent: ",degree+"" );
                        onDegreeChangeLisener.onDegreeChangeLisener(degrees);
                    }
                    break;
            }

            invalidate();
        }
        return true;
        //     return super.onTouchEvent(event);
    }



    /**
     * float x = event.getRawX();
     * float y = event.getRawY();
     * @param x
     * @param y
     */
    public void getdegree(float x, float y){
        degrees = (float) ((float) ((Math.toDegrees(Math.atan2(
                x - cx, cy-y)) + 360.0)) % 360.0);
        if (degrees>=146 && degrees<=217){
            degrees =-146;
        }
        if (degrees>217){
            degrees-=360;
        }
       // degrees
    }
   private OnDegreeChangeLisener onDegreeChangeLisener;

    public OnDegreeChangeLisener getOnDegreeChangeLisener() {
        return onDegreeChangeLisener;
    }

    public void setOnDegreeChangeLisener(OnDegreeChangeLisener onDegreeChangeLisener) {
        this.onDegreeChangeLisener = onDegreeChangeLisener;
    }

   public interface OnDegreeChangeLisener{
       void onDegreeChangeLisener(float degree);
    }
}
