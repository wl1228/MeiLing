package app.android.mingjiang.com.mpchartapi;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 备注：折线图。
 * Created by SunYi on 2015/12/21/0021.
 */
public class BrokeLineChartUtil {
    public static final int CIRCLE_COLOR = Color.RED;
    public static final int BACKGROUND_COLOR = Color.GRAY;
    public static final int VALUE_TEXT_COLOR = Color.BLUE;
    public static final int LINE_COLOR = Color.BLACK;
    public static final int HIGH_LINE_COLOR = Color.RED;
    public static final int FILL_COLOR = Color.YELLOW;
    public static final boolean IS_FILL = false;
    public static final boolean CAN_OPERATION = false;
    public static final int MAX_COUNT = 20;

    private LineChart mChart;
    private String content;
    private String description;

    /**
     * 构造函数
     *
     * @param mChart 图表
     */
    public BrokeLineChartUtil(LineChart mChart) {
        this.mChart = mChart;
        initMChart();
    }

    public BrokeLineChartUtil(LineChart mChart,String description,String content, List<String> xValues, List<Float>... dataList) {
        this.mChart = mChart;
        initMChart(xValues, dataList);
    }

    /**
     * 初始化图表
     */
    private void initMChart() {
        initMChart(new ArrayList<String>(), new ArrayList<Float>());
    }

    /**
     * 初始化图表
     *
     * @param xValues  x轴
     * @param dataList y轴数据
     */
    private void initMChart(List<String> xValues, List<Float>... dataList) {
        // no description text
        //描述性语言
        mChart.setDescription(this.description);
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        //这只一个背景颜色
        // set an alternative background color
        mChart.setBackgroundColor(BACKGROUND_COLOR);
        //初始化操作相关
        initOperation();

        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setDrawGridBackground(true);
        //初始化数据
        setData(mChart, xValues, dataList);
        //初始化XY轴
        initXY();

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);
//        //X轴动画形式显示图标
//        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        //图表可用
        mChart.invalidate();
        mChart.setDoubleTapToZoomEnabled(false);
    }

    /**
     * 初始化操作相关属性
     */
    private void initOperation() {
        // enable touch gestures
        //能否触摸
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        //能否缩放或拖拽
        mChart.setDragEnabled(CAN_OPERATION);
        mChart.setScaleEnabled(CAN_OPERATION);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        //能否分别缩放XY轴
        mChart.setPinchZoom(CAN_OPERATION);
        //最大缩放值
        mChart.getViewPortHandler().setMaximumScaleY(2f);
        mChart.getViewPortHandler().setMaximumScaleX(2f);
    }

    /**
     * 初始化XY轴相关属性
     */
    private void initXY() {
        //X轴设置
        XAxis xAxis = mChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        //左侧Y轴设置
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        //设置最大值最小值

//        leftAxis.setAxisMaxValue(MaxValue);
//        leftAxis.setAxisMinValue(MinValue);
//        //是否从零开始
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        //线是不是要画在数据（显示的文字）后面
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setEnabled(true);
        //不要右侧
        mChart.getAxisRight().setEnabled(false);

    }

    /**
     * 为图标添加数据
     *
     * @param xValues
     * @param dataLists
     */
    public void setData(LineChart mChart, List<String> xValues, List<Float>... dataLists) {

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineData lineData;
        for (List<Float> data : dataLists) {
            if (data.size() != 0) {
                LineDataSet set1 = createSet(data);
                dataSets.add(set1); // add the dataSets
            }
        }
        // create a data object with the dataSets
        if (dataSets.size() == 0) {
            lineData = new LineData();
        } else {
            lineData = new LineData(xValues, dataSets);
        }
        // set data
        mChart.setData(lineData);
        mChart.invalidate();
    }

    /**
     * 创建一个LineDataSet
     *
     * @param data 传入的数据数组
     * @return 单回一个LineDataSet
     */
    private LineDataSet createSet(List<Float> data) {
        LineDataSet set;
        if (data == null || data.size() == 0) {
            set = toLineDataSet(null);
        } else {
            set = toLineDataSet(data);
        }
        if (IS_FILL) {
            set.setFillAlpha(65);
            set.setFillColor(FILL_COLOR);
        }
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        //画虚线
        // set the line to be drawn like this "- - - - - -"
        set.enableDashedLine(10f, 5f, 0f);
        set.enableDashedHighlightLine(10f, 5f, 0f);
        //数据文字颜色
        set.setValueTextColor(VALUE_TEXT_COLOR);
        //数据线的颜色
        set.setColor(LINE_COLOR);
        //数据点颜色
        set.setCircleColor(CIRCLE_COLOR);
        //数据点大小
        set.setCircleSize(3f);
        //实心圆
        set.setDrawCircleHole(true);
        //数据文字大小
        set.setValueTextSize(9f);

        set.setDrawCubic(true);
        return set;
    }

    /**
     * 把数组转化为可用的图标数据
     *
     * @param dataList 数据数组
     * @return 返回的LineData
     */
    private LineDataSet toLineDataSet(List<Float> dataList) {
        if (dataList == null) {
            return new LineDataSet(null, this.content);

        }
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            yVals.add(new Entry(dataList.get(i), i));
        }
        // create a dataset and give it a type
        return new LineDataSet(yVals, this.content);
    }

    /**
     * 添加一个高亮警戒线
     *
     * @param value 高亮线的位置
     * @param name  高亮线的名字
     */
    public void addXLimitLine(float value, String name) {
        addXLimitLine(value, name, HIGH_LINE_COLOR);
    }

    /**
     * 添加一个X轴高亮警戒线
     *
     * @param value 高亮线的位置
     * @param name  高亮线的名字
     * @param color 线的颜色
     */
    public void addXLimitLine(float value, String name, int color) {
        LimitLine ll1 = new LimitLine(value, name);
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setLineColor(color);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.addLimitLine(ll1);
        mChart.notifyDataSetChanged();
    }


    /**
     * 为第一条数据线添加一个新的值
     * 不想添加X轴请输入null
     *
     * @param yValue y轴的值
     * @param xValue x轴的值
     */
    public void addEntry(String xValue, Float yValue) {
        addEntry(xValue, yValue, 0);
    }

    /**
     * 为一条数据线添加一个新的值
     * 不想添加X轴请输入null
     *
     * @param yValue       y轴的值
     * @param xValue       x轴的值
     * @param dataSetIndex 为哪一条数据添加
     */
    public void addEntry(String xValue, Float yValue, int dataSetIndex) {
        LineData data = mChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(dataSetIndex);
            if (set == null) {
                set = createSet(null);
                data.addDataSet(set);
            }
            if (data.getYValCount() > MAX_COUNT) {
                data.removeEntry(set.getEntryForXIndex(0), dataSetIndex);
                //把每组数据后移
                for (int i = 1; i < MAX_COUNT; i++) {
                    set.getEntryForXIndex(i).setXIndex(i - 1);
                }
                data.removeXValue(0);
            }
            //添加一个新的X轴值
            if (xValue != null && data.getXValCount() == data.getYValCount()) {
                data.addXValue(xValue);
            }
            //添加数据值
            data.addEntry(new Entry(yValue, set.getEntryCount()), dataSetIndex);

            // let the chart know it's data has changed
            //通知图标修改了
            mChart.notifyDataSetChanged();

            // 转移到最前面
            mChart.moveViewToX(data.getXValCount() - 1);
        }
        mChart.invalidate();
    }


}
