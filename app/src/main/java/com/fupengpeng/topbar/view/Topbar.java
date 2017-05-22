package com.fupengpeng.topbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fupengpeng.topbar.R;

/**
 * Created by fupengpeng on 2017/5/22 0022.
 *     模板复用和接口回调提高开发效率，降低耦合度
 *     创建更加灵活的模板，增加更多的功能
 *     模板设计不限于UI设计，代码架构设计等也可以
 *     遇到问题可以思考Android系统是怎样的实现方法
 *     学习经典的设计模式
 */

public class Topbar extends RelativeLayout{

    /*
    1.自定义属性的声明和获取
        a、分析需要的自定义属性
        b、在res/values/attrs.xml定义声明
        c、在layout.xml文件中进行使用
        d、在view的构造方法中进行获取
    2.测量onMeasure:测量模式和测量值决定自定义view的大小，
        一般情况下模式和值都进行封装成MeasureSpec，而MeasureSpec是从onMeasureSpec方法中
        父控件传递下来的
        a、确定测量模式
            01.EXACTLY：设置明确的值，例如100dp
            02.AT_MOST：根据自身内容大小展示，但不能超过父控件ViewGroup的值
            03.UNSPECIFIED：完全根据自定义view的大小展示，没有父控件的限制，主要用于listview和Scrollview
        b、MeasureSpec辅助类里面根据测量模式的不同调用方法得到测量值
            一般情况下模式和值都进行封装成MeasureSpec，
            而MeasureSpec是从onMeasureSpec方法中父控件传递下来的
            一般情况下通过MeasureSpec的静态方法getMode方法和getSize方法获取
        c、setMeasuredDimension(width, height);将最终得到的测量值传递进去
        d、自定义内容发生改变时，理论上测量的大小值也会发生改变，此事如何触发
            重新测量呢？对外提供一个方法，在方法中调用requestLayout();方法，此时就会
            重新测量并设置大小
    3.布局onLayout（ViewGroup）（如果只是单纯的自定义view就不用执行此方法，
        如果还自定义ViewGroup的话就需要执行此方法）,此方法只执行一次
        a、决定子view的位置
        b、尽可能将onMeasure方法中的一些操作移至此方法中(耗时等操作)
        c、requestLayout()
    4.绘制onDraw
        a、绘制内容区域
            01.EXACTLY：设置明确的值，例如100dp
            02.AT_MOST：根据自身内容大小展示，但不能超过父控件ViewGroup的值
            03.UNSPECIFIED：完全根据自定义view的大小展示，没有父控件的限制，主要用于listview和Scrollview
        b、invalidate(); postInvalidate();
        c、Canvas.drawxxx
        d、translate、totate、scale、skew、 使用变换的方法
        e、save()、restore()  使用变换的方法后记得保存
    5.onTouchEvent

    6.onInterceptTouchEvent（ViewGroup）
     */

    private Button leftButton,rightButton;
    private TextView tvTitle;

    //自定义属性变量
    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;


    //定义点击事件的接口
    public interface topbarClickListerner{
        public void leftClick();
        public void rightClick();
    }
    private topbarClickListerner listerner;
    //提供一个方法 给调用者，当用户点击button后，
    // 调用setOnTopbarClickListener方法时，创建一个topbarClickListerner对象
    //      topbarClickListerner对象调用topbarClickListerner接口的抽想方法
    //      调用者实现topbarClickListerner对象调用的方法，在方法中执行想要的操作

    public void setOnTopbarClickListener(topbarClickListerner listener){
        this.listerner = listener;
    }


    //把控件放到layout中，
    //定义layout属性
    private LayoutParams leftParams,rightParams,titleParams;

    public Topbar(final Context context, AttributeSet attrs) {
        super(context, attrs);



        /*
        //获得我们所定义的自定义样式属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();
         */



        //获取存储在attrs中的自定义属性集合
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        //将自定义的属性赋值给相应的变量，  给控件属性设置值的时候通过相应的变量
        leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor,0);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftText = ta.getString(R.styleable.Topbar_leftText);

        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor,0);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightText = ta.getString(R.styleable.Topbar_rightText);

        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize,0);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor,0);
        title = ta.getString(R.styleable.Topbar_title);

        ta.recycle();//回收，避免浪费资源和缓存引起的错误


        //定义控件  组合模式，将已有的控件通过组合的方式形成了一个新的控件
        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        //将自定义的属性赋值给对应的控件
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        tvTitle.setTextSize(titleTextSize);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);

        //设置topbar背景色
        setBackgroundColor(0xfff59563);


        //把控件放到layoutViewGroup中，
        //定义宽高属性
        leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //对齐方式
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        //将leftButton以leftParams的方式添加到了ViewGroup中
        addView(leftButton,leftParams);

        //定义宽高属性
        rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //对齐方式
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        //将leftButton以leftParams的方式添加到了ViewGroup中
        addView(rightButton,rightParams);

        //定义宽高属性
        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //对齐方式
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        //将leftButton以leftParams的方式添加到了ViewGroup中
        addView(tvTitle,titleParams);

        //自定义View动态属性部分
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.leftClick();

            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.rightClick();

            }
        });


    }

    /*@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }
            //计算childView layout的左上角坐标  ss  yy
             child.layout(ss,yy,ss+width,yy+width);
        }
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void setLeftIsVisable(boolean flag){
        if (flag){
            leftButton.setVisibility(VISIBLE);
        }else {
            leftButton.setVisibility(GONE);
        }
    }
}
