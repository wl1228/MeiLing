package app.android.mingjiang.com.mpchartapi;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 备注：水平柱状图。
 * 作者：wangzs on 2015/12/25 15:47
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class HistogramUtil {

    public static void createBarChart(BarChart histogramChart,String yName,List<String> xValues,List<Float> yValues){
        initBarChartConfig(histogramChart);
        initAxisConfig(histogramChart);
        updateData(histogramChart, xValues, yName, yValues);
    }


    /**
     * 初始化Chart配置信息。
     * @param histogramChart
     */
    private static void initBarChartConfig(BarChart histogramChart){

        histogramChart.setDrawBarShadow(false); //是否显示背景
        histogramChart.setDrawValueAboveBar(true);//是否在Bar基础上添加值
        histogramChart.setDescription("");//设置描述信息
        histogramChart.setMaxVisibleValueCount(60);//设置最大可视数目
        histogramChart.setPinchZoom(false);//设置缩放
        histogramChart.setDrawGridBackground(false);//是否设置Grid背景
        histogramChart.setNoDataText("");
        histogramChart.animateY(2500);
        histogramChart.setDoubleTapToZoomEnabled(false);
        //设置图例
        Legend l = histogramChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    /**
     * 初始化坐标轴配置信息。
     * @param histogramChart
     */
    private static void initAxisConfig(BarChart histogramChart){
        XAxis xl = histogramChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = histogramChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
//        yl.setInverted(true);

        YAxis yr = histogramChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
//        yr.setInverted(true);
    }

    /**
     * 多数据更新。
     * @param barChart
     * @param yValues
     * @param xValues
     */
    public static void updateMultiData(BarChart barChart,List<String> xValues,List<String> yNames,List<Float>... yValues){

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        int j = 0;
        for(List<Float> yValue : yValues){

            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            for (int i = 0; i < yValue.size(); i++) {
                yVals1.add(new BarEntry(yValue.get(i), i));
            }
            BarDataSet set1 = new BarDataSet(yVals1, yNames.get(j));
            set1.setBarSpacePercent(35f);
            dataSets.add(set1);
            set1.setColor(ColorTemplate.VORDIPLOM_COLORS[j]);
            j++;
        }
        //设置数据颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();


        BarData data = new BarData(xValues, dataSets);
        data.setValueTextSize(10f);
        barChart.setData(data);
        barChart.invalidate();
    }

    /**
     * 更新数据。
     * @param barChart
     * @param xValues
     * @param yName
     * @param yValues
     */
    public static void updateData(BarChart barChart,List<String>xValues,String yName,List<Float> yValues){
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < yValues.size(); i++) {
            yVals1.add(new BarEntry(yValues.get(i), i));
        }
        BarDataSet set1 = new BarDataSet(yVals1, yName);
        set1.setBarSpacePercent(35f);
        dataSets.add(set1);
        BarData data = new BarData(xValues, dataSets);
        data.setValueTextSize(10f);
        data.notifyDataChanged();
        barChart.notifyDataSetChanged();
        barChart.setData(data);
        barChart.invalidate();
    }
}
