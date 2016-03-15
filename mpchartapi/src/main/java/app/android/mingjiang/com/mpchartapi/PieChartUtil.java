package app.android.mingjiang.com.mpchartapi;

import android.graphics.Color;
import android.text.SpannableString;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：饼状图处理工具。
 * 作者：wangzs on 2015/12/25 09:27
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class PieChartUtil {

    public static void createPieChart(PieChart pieChart,String centerContext,List<Float>yValues,List<String> xValues){
        initPicChartConfig(pieChart, centerContext);
        updateValues(pieChart,yValues, xValues);
    }

    private static void initPicChartConfig(PieChart pieChart,String centerContext){
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);//拖动减速摩擦系数

        pieChart.setCenterText(setCenterContext(centerContext));//设置中间显示文字

        pieChart.setDrawHoleEnabled(true);//设置为true,饼状图中心为空
        pieChart.setHoleColorTransparent(true);//设置为true，饼状图中心透明

        pieChart.setTransparentCircleColor(Color.WHITE);//设置中心位置颜色
        pieChart.setTransparentCircleAlpha(110);//设置透明度：0为完全透明，255为不透明

        pieChart.setHoleRadius(50f);//设置中心位置占总显示的百分比
        pieChart.setTransparentCircleRadius(60f);//设置透明圈所占百分比（以中心位置为心）

        pieChart.setDrawCenterText(true);//设置是否显示中心位置文字

        pieChart.setRotationAngle(0);//设置角度偏移度
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);//设置是否可以旋转
        pieChart.setHighlightPerTapEnabled(true);//设置是否可以点击凸显

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        Legend l = pieChart.getLegend();//设置标签及位置（右上角）
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.setXEntrySpace(7f);
        //l.setYEntrySpace(0f);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);
        l.setYOffset(0f);

        //setData(3, 100);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);//设置动画
    }
    /**
     * 设置中心文字显示文字及其风格设置。
     */
    private static SpannableString setCenterContext(String centerContext){
        SpannableString s = new SpannableString(centerContext);
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, centerContext.length(), 0);
        // s.setSpan(new StyleSpan(Typeface.NORMAL), centerContext.length(), s.length() - 15, 0);
        //s.setSpan(new ForegroundColorSpan(Color.GRAY), centerContext.length(), s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), centerContext.length(), s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - centerContext.length(), s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    /**
     * 更新数据。
     * @param yValues
     * @param xValues
     */
    public static void updateValues(PieChart pieChart,List<Float> yValues,List<String> xValues){

        //设置数据值
        ArrayList<Entry> yValsEntry = new ArrayList<>();
        for(int i=0;i<yValues.size();i++){
            yValsEntry.add(new Entry(yValues.get(i),i));
        }
        PieDataSet dataSet = new PieDataSet(yValsEntry, "结果数据");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(20f);
        //设置数据颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        //设置PieData
        PieData data = new PieData(xValues, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }
}
