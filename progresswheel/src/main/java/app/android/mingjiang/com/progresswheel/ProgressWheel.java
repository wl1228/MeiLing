package app.android.mingjiang.com.progresswheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * An indicator of progress, similar to Android's ProgressBar.
 *
 * @author Todd Davies
 *         <p/>
 *         See MIT-LICENSE.txt for licence details
 */
public class ProgressWheel extends View {

    //绘制View用到的各种长、宽带大小
    private int layoutHeight = 0;
    private int layoutWidth = 0;
    private int fullRadius = 100;
    private int circleRadius = 80;
    private int barLength = 60;
    private int barWidth = 20;
    private int rimWidth = 20;
    private int textSize = 20;
    private float contourSize = 0;

    //与页边的间距
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    //View要绘制的颜色
    private int barColor = 0xAA000000;
    private int contourColor = 0xAA000000;
    private int circleColor = 0x00000000;
    private int rimColor = 0xAADDDDDD;
    private int textColor = 0xFF000000;

    //绘制要用的画笔
    private Paint barPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint rimPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint contourPaint = new Paint();

    //绘制要用的矩形
    private RectF innerCircleBounds = new RectF();
    private RectF circleBounds = new RectF();
    private RectF circleOuterContour = new RectF();
    private RectF circleInnerContour = new RectF();

    //动画
    //每次绘制要移动的像素数目
    private float spinSpeed = 2f;
    //绘制过程的时间间隔
    private int delayMillis = 10;
    private float progress = 0;
    boolean isSpinning = false;

    //其他
    private String text = "";
    private String[] splitText = {};

    /**
     * TProgressWheel的构造方法
     *
     * @param context
     * @param attrs
     */
    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.ProgressWheel));
    }

    //----------------------------------
    //初始化一些元素
    //----------------------------------

    /*
     * 调用这个方法时，使View绘制为方形
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 首先我们要调用超类的onMeasure借口
        // 原因是我们自己去实现一个方法获得长度、宽度太麻烦了
        // 使用超类的的方法非常方便而且让复杂的细节可控
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 在这里我们不能使用getWidth()和getHeight()。
        // 因为这两个方法只能在View的布局完成后才能使用，而一个View的绘制过程是先绘制元素，再绘制Layout
        // 所以我们必须使用getMeasuredWidth()和getMeasuredHeight()
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // 最后我们用一些简单的逻辑去计算View的大小并调用setMeasuredDimension()去设置View的大小
        // 在比较View的长宽前我们不考虑间距，但当我们设置View所需要绘制的面积时，我们要考虑它
        // 不考虑间距的View（View内的实际画面）此时就应该是方形的，但是由于间距的存在，最终View所占的面积可能不是方形的
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (heightMode != MeasureSpec.UNSPECIFIED && widthMode != MeasureSpec.UNSPECIFIED) {
            if (widthWithoutPadding > heightWithoutPadding) {
                size = heightWithoutPadding;
            } else {
                size = widthWithoutPadding;
            }
        } else {
            size = Math.max(heightWithoutPadding, widthWithoutPadding);
        }


        // 如果你重写了onMeasure()方法，你必须调用setMeasuredDimension()方法
        // 这是你设置View大小的唯一途径
        // 如果你不调用setMeasuredDimension()方法，父控件会抛出异常，并且程序会崩溃
        // 如果我们使用了超类的onMeasure()方法，我们就不是那么需要setMeasuredDimension()方法
        // 然而，重写onMeasure()方法是为了改变既有的绘制流程，所以我们必须调用setMeasuredDimension()方法以达到我们的目的
        setMeasuredDimension(
                size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom());
    }

    /**
     * 使用onSizeChanged方法代替onAttachedToWindow获得View的面积
     * 因为这个方法会在测量了MATCH_PARENT和WRAP_CONTENT后马上被调用
     * 使用获得的面积设置View
     */
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        layoutWidth = newWidth;
        layoutHeight = newHeight;
        setupBounds();
        setupPaints();
        invalidate();
    }

    /**
     * 设置我们想要绘制的progress wheel的颜色
     */
    private void setupPaints() {
        barPaint.setColor(barColor);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Style.STROKE);
        barPaint.setStrokeWidth(barWidth);

        rimPaint.setColor(rimColor);
        rimPaint.setAntiAlias(true);
        rimPaint.setStyle(Style.STROKE);
        rimPaint.setStrokeWidth(rimWidth);

        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.FILL);

        textPaint.setColor(textColor);
        textPaint.setStyle(Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);

        contourPaint.setColor(contourColor);
        contourPaint.setAntiAlias(true);
        contourPaint.setStyle(Style.STROKE);
        contourPaint.setStrokeWidth(contourSize);
    }

    /**
     * 设置元素的边界
     */
    private void setupBounds() {
        // 为了保持宽度和长度的一致，我们要获得layout_width和layout_height中较小的一个，从而绘制一个圆
        int minValue = Math.min(layoutWidth, layoutHeight);

        // 计算在绘制过程中在x,y方向的偏移量
        int xOffset = layoutWidth - minValue;
        int yOffset = layoutHeight - minValue;

        // 间距加上偏移量
        paddingTop = this.getPaddingTop() + (yOffset / 2);
        paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width = getWidth();
        int height = getHeight();

        innerCircleBounds = new RectF(
                paddingLeft + (1.5f * barWidth),
                paddingTop + (1.5f * barWidth),
                width - paddingRight - (1.5f * barWidth),
                height - paddingBottom - (1.5f * barWidth));
        circleBounds = new RectF(
                paddingLeft + barWidth,
                paddingTop + barWidth,
                width - paddingRight - barWidth,
                height - paddingBottom - barWidth);
        circleInnerContour = new RectF(
                circleBounds.left + (rimWidth / 2.0f) + (contourSize / 2.0f),
                circleBounds.top + (rimWidth / 2.0f) + (contourSize / 2.0f),
                circleBounds.right - (rimWidth / 2.0f) - (contourSize / 2.0f),
                circleBounds.bottom - (rimWidth / 2.0f) - (contourSize / 2.0f));
        circleOuterContour = new RectF(
                circleBounds.left - (rimWidth / 2.0f) - (contourSize / 2.0f),
                circleBounds.top - (rimWidth / 2.0f) - (contourSize / 2.0f),
                circleBounds.right + (rimWidth / 2.0f) + (contourSize / 2.0f),
                circleBounds.bottom + (rimWidth / 2.0f) + (contourSize / 2.0f));

        fullRadius = (width - paddingRight - barWidth) / 2;
        circleRadius = (fullRadius - barWidth) + 1;
    }

    /**
     * 从XML中解析控件的属性
     *
     * @param a the attributes to parse
     */
    private void parseAttributes(TypedArray a) {
        barWidth = (int) a.getDimension(R.styleable.ProgressWheel_pwBarWidth, barWidth);
        rimWidth = (int) a.getDimension(R.styleable.ProgressWheel_pwRimWidth, rimWidth);
        spinSpeed = (int) a.getDimension(R.styleable.ProgressWheel_pwSpinSpeed, spinSpeed);
        barLength = (int) a.getDimension(R.styleable.ProgressWheel_pwBarLength, barLength);

        delayMillis = a.getInteger(R.styleable.ProgressWheel_pwDelayMillis, delayMillis);
        if (delayMillis < 0) { delayMillis = 10; }

        // 如果text是空的，就忽略它
        if (a.hasValue(R.styleable.ProgressWheel_pwText)) {
            setText(a.getString(R.styleable.ProgressWheel_pwText));
        }

        barColor = a.getColor(R.styleable.ProgressWheel_pwBarColor, barColor);
        textColor = a.getColor(R.styleable.ProgressWheel_pwTextColor, textColor);
        rimColor = a.getColor(R.styleable.ProgressWheel_pwRimColor, rimColor);
        circleColor = a.getColor(R.styleable.ProgressWheel_pwCircleColor, circleColor);
        contourColor = a.getColor(R.styleable.ProgressWheel_pwContourColor, contourColor);

        textSize = (int) a.getDimension(R.styleable.ProgressWheel_pwTextSize, textSize);
        contourSize = a.getDimension(R.styleable.ProgressWheel_pwContourSize, contourSize);

        // 使用TypedArray获得控件属性时必须要注意：使用结束后必须回收TypedArray的对象
        a.recycle();
    }

    //----------------------------------
    //动画
    //----------------------------------

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制内圆
        canvas.drawArc(innerCircleBounds, 360, 360, false, circlePaint);
        //绘制边界
        canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
        canvas.drawArc(circleOuterContour, 360, 360, false, contourPaint);
        //canvas.drawArc(circleInnerContour, 360, 360, false, contourPaint);
        //绘制条纹
        if (isSpinning) {
            canvas.drawArc(circleBounds, progress - 90, barLength, false, barPaint);
        } else {
            canvas.drawArc(circleBounds, -90, progress, false, barPaint);
        }
        //绘制我们想要设置的文字 (并让它显示在圆水平和垂直方向的中心处)
        float textHeight = textPaint.descent() - textPaint.ascent();
        float verticalTextOffset = (textHeight / 2) - textPaint.descent();

        for (String line : splitText) {
            float horizontalTextOffset = textPaint.measureText(line) / 2;
            canvas.drawText(
                    line,
                    this.getWidth() / 2 - horizontalTextOffset,
                    this.getHeight() / 2 + verticalTextOffset,
                    textPaint);
        }
        if (isSpinning) {
            scheduleRedraw();
        }
    }

    private void scheduleRedraw() {
    	progress += spinSpeed;
        if (progress > 360) {
            progress = 0;
        }
        postInvalidateDelayed(delayMillis);
    }

    /**
    *    判断wheel是否在旋转
    */
    public boolean isSpinning() {
    	return isSpinning;
    }
    
    /**
     * 重设进度条的值
     */
    public void resetCount() {
        progress = 0;
        setText("0%");
        invalidate();
    }

    /**
     * 停止进度条的旋转
     */
    public void stopSpinning() {
        isSpinning = false;
        progress = 0;
        postInvalidate();
    }


    /**
     * 让进度条开启旋转模式
     */
    public void startSpinning() {
        isSpinning = true;
        postInvalidate();
    }

    /**
     * 让进度条每次增加1（最大值为360）
     */
    public void incrementProgress() {
        incrementProgress(1);
    }

    public void incrementProgress(int amount) {
        isSpinning = false;
        progress += amount;
        if (progress > 360)
            progress %= 360;
        postInvalidate();
    }


    /**
     * 设置进度条为一个确切的数值
     */
    public void setProgress(int i) {
        isSpinning = false;
        progress = i;
        postInvalidate();
    }

    /**
     * 设置progress bar的文字并不需要刷新View
     */
    public void setText(String text) {
        this.text = text;
        splitText = this.text.split("\n");
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getBarLength() {
        return barLength;
    }

    public void setBarLength(int barLength) {
        this.barLength = barLength;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        
        if ( this.barPaint != null ) {
        	this.barPaint.setStrokeWidth( this.barWidth );
        }
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        
        if ( this.textPaint != null ) {
        	this.textPaint.setTextSize( this.textSize );
        }
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
        
        if ( this.barPaint != null ) {
        	this.barPaint.setColor( this.barColor );
        }
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        
        if ( this.circlePaint != null ) {
        	this.circlePaint.setColor( this.circleColor);
        }
    }

    public int getRimColor() {
        return rimColor;
    }

    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;
        
        if ( this.rimPaint != null ) {
        	this.rimPaint.setColor( this.rimColor );
        }
    }

    public Shader getRimShader() {
        return rimPaint.getShader();
    }

    public void setRimShader(Shader shader) {
        this.rimPaint.setShader(shader);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        
        if ( this.textPaint != null ) {
        	this.textPaint.setColor( this.textColor );
        }
    }

    public float getSpinSpeed() {
        return spinSpeed;
    }

    public void setSpinSpeed(float spinSpeed) {
        this.spinSpeed = spinSpeed;
    }

    public int getRimWidth() {
        return rimWidth;
    }

    public void setRimWidth(int rimWidth) {
        this.rimWidth = rimWidth;
        
        if ( this.rimPaint != null ) {
        	this.rimPaint.setStrokeWidth( this.rimWidth );
        }
    }

    public int getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }
    
    public int getContourColor() {
    	return contourColor;
    }
    
    public void setContourColor(int contourColor) {
    	this.contourColor = contourColor;
    	
    	if ( contourPaint != null ) {
    		this.contourPaint.setColor( this.contourColor );
    	}
    }
    
    public float getContourSize() {
    	return this.contourSize;
    }
    
    public void setContourSize(float contourSize) {
    	this.contourSize = contourSize;
    	
    	if ( contourPaint != null ) {
    		this.contourPaint.setStrokeWidth( this.contourSize );
    	}
    }

    public int getProgress() { return (int) progress; }
}
