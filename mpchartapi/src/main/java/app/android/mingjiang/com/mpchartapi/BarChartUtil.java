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
 * 备注：垂直柱状图。
 * 作者：wangzs on 2015/12/25 15:47
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class BarChartUtil {

    public static void createBarChart(BarChart barChart,String yName,List<String> xValues,List<Float> yValues){
        initBarChartConfig(barChart);
        initAxisConfig(barChart);
        updateData(barChart, xValues, yName, yValues);
    }


    /**
     * 初始化Chart配置信息。
     * @param barChart
     */
    private static void initBarChartConfig(BarChart barChart){

        barChart.setDrawBarShadow(false); //是否显示背景
        barChart.setDrawValueAboveBar(true);//是否在Bar基础上添加值
        barChart.setDescription("");//设置描述信息
        barChart.setMaxVisibleValueCount(60);//设置最大可视数目
        barChart.setPinchZoom(false);//设置缩放
        barChart.setDrawGridBackground(false);//是否设置Grid背景
        barChart.setNoDataText("");
        barChart.setDoubleTapToZoomEnabled(false);//双击放大取消
        barChart.setTouchEnabled(true);

        //设置图例
        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    /**
     * 初始化坐标轴配置信息。
     * @param barChart
     */
    private static void initAxisConfig(BarChart barChart){
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
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
        barChart.setData(data);
        barChart.invalidate();
    }
}
