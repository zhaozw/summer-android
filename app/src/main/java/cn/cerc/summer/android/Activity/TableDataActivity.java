package cn.cerc.summer.android.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.huagu.ehealth.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.summer.android.Entity.Table_item;


/**
 * Created by Administrator on 2017/4/17.
 */
public class TableDataActivity extends Activity implements View.OnClickListener {

    private JSONObject json;
    private LineChart mLineChart;
    private List<Table_item>  list;
    private TextView title,title_right;
    private ImageView img_back;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_data);

        title =(TextView)findViewById(R.id.title_name);
        img_back =(ImageView)findViewById(R.id.back);
        title_right =(TextView)findViewById(R.id.title_right);

        title.setText("图表");
        title_right.setText("帮助");
        img_back.setOnClickListener(this);
        title_right.setOnClickListener(this);

        try {
            json =new JSONObject(getIntent().getStringExtra("data"));
            list = JSON.parseArray(json.getString("data"),Table_item.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        drawTheChartByMPAndroid();

    }

    private void drawTheChartByMPAndroid() {
        mLineChart = (LineChart) findViewById(R.id.spread_line_chart);
        LineData lineData = getLineData();
        showChart(mLineChart, lineData, Color.parseColor("#ffffff"));
    }

    private LineData getLineData() {
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        LineDataSet lineDataSet =null;
        LineDataSet lineDataSet2=null;
        LineDataSet lineDataSet3=null;
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            if(list.get(0).getXueyaceshi_()!=null){
                xValues.add(list.get(i).getCheckDate_()+"\n"+list.get(i).getXueyaceshi_());
            }else {
                xValues.add(list.get(i).getCheckDate_()+"\n"+list.get(i).getXuetangceshi());
            }

        }

        // y轴的数据
        if(list.get(0).getHeight_()!=null){ //身高/体重
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getHeight_());
                yValues.add(new Entry(value, i));
            }
            ArrayList<Entry> yValues2 = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getWeight_());
                yValues2.add(new Entry(value, i));
            }
            lineDataSet = new LineDataSet(yValues, "身高");
            lineDataSet2 = new LineDataSet(yValues2, "体重");
        }else if(list.get(0).getBlodSug_()!=null){
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getBlodSug_());
                yValues.add(new Entry(value, i));
            }
            lineDataSet = new LineDataSet(yValues, "血糖");
        }else if(list.get(0).getSportTypes_()!=null){
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getSportTime_());
                yValues.add(new Entry(value, i));
            }
            lineDataSet = new LineDataSet(yValues, "运动时长");
        }else if(list.get(0).getXinlv_() !=null){
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getShousuo_());
                yValues.add(new Entry(value, i));
            }
            lineDataSet = new LineDataSet(yValues, "收缩压");
            ArrayList<Entry> yValues2 = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getShuzhan_());
                yValues2.add(new Entry(value, i));
            }
            lineDataSet2 = new LineDataSet(yValues2, "舒张压");
            ArrayList<Entry> yValues3 = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                float value =Float.parseFloat(list.get(i).getXinlv_());
                yValues3.add(new Entry(value, i));
            }
            lineDataSet3 = new LineDataSet(yValues3, "心率");
        }





        if(lineDataSet!=null){
            //用y轴的集合来设置参数
            lineDataSet.setLineWidth(1.75f); // 线宽
            lineDataSet.setCircleSize(3f);// 显示的圆形大小
            lineDataSet.setColor(Color.parseColor("#DD2BB2"));// 显示颜色
            lineDataSet.setCircleColor(Color.parseColor("#DD2BB2"));// 圆形的颜色
            lineDataSet.setHighLightColor(Color.parseColor("#DD2BB2")); // 高亮的线的颜色
            lineDataSet.setValueTextColor(Color.parseColor("#DD2BB2")); //数值显示的颜色
            lineDataSet.setValueTextSize(8f);     //数值显示的大小
            lineDataSets.add(lineDataSet); // 添加数据集合
        }

        if(lineDataSet2!=null){
            //用y轴的集合来设置参数
            lineDataSet2.setLineWidth(1.75f); // 线宽
            lineDataSet2.setCircleSize(3f);// 显示的圆形大小
            lineDataSet2.setColor(Color.parseColor("#FF9600"));// 显示颜色
            lineDataSet2.setCircleColor(Color.parseColor("#FF9600"));// 圆形的颜色
            lineDataSet2.setHighLightColor(Color.parseColor("#FF9600")); // 高亮的线的颜色
            lineDataSet2.setValueTextColor(Color.parseColor("#FF9600")); //数值显示的颜色
            lineDataSet2.setValueTextSize(8f);     //数值显示的大小
            lineDataSets.add(lineDataSet2); // 添加数据集合
        }
        if(lineDataSet3!=null){
            lineDataSet3.setLineWidth(1.75f); // 线宽
            lineDataSet3.setCircleSize(3f);// 显示的圆形大小
            lineDataSet3.setColor(Color.parseColor("#48b2bd"));// 显示颜色
            lineDataSet3.setCircleColor(Color.parseColor("#48b2bd"));// 圆形的颜色
            lineDataSet3.setHighLightColor(Color.parseColor("#48b2bd")); // 高亮的线的颜色
            lineDataSet3.setValueTextColor(Color.parseColor("#48b2bd")); //数值显示的颜色
            lineDataSet3.setValueTextSize(8f);     //数值显示的大小
            lineDataSets.add(lineDataSet3); // 添加数据集合
        }
        //创建lineData
        LineData lineData = new LineData(xValues, lineDataSets);
        return lineData;
    }

    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(true); //在折线图上添加边框
        lineChart.setDescription(""); //数据描述
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        lineChart.setDrawGridBackground(false); //表格颜色
        lineChart.setGridBackgroundColor(Color.GRAY); //表格的颜色，设置一个透明度

        lineChart.setTouchEnabled(true); //可点击
        lineChart.setDragEnabled(true);  //可拖拽
        lineChart.setScaleEnabled(false);  //可缩放
        lineChart.setPinchZoom(false);
        lineChart.setBackgroundColor(color); //设置背景颜色

        lineChart.setData(lineData);  //填充数据

        Legend mLegend = lineChart.getLegend(); //设置标示，就是那个一组y的value的

        mLegend.setForm(Legend.LegendForm.CIRCLE); //样式
        mLegend.setFormSize(6f); //字体
        mLegend.setTextColor(Color.GRAY); //颜色

        lineChart.setVisibleXRange(4.5f);   //x轴可显示的坐标范围
        XAxis xAxis = lineChart.getXAxis();  //x轴的标示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x轴位置
        xAxis.setTextColor(Color.GRAY);    //字体的颜色
        xAxis.setTextSize(10f); //字体大小
        xAxis.setGridColor(Color.GRAY);//网格线颜色
        xAxis.setDrawGridLines(true); //不显示网格线

        YAxis axisLeft = lineChart.getAxisLeft(); //y轴左边标示
        YAxis axisRight = lineChart.getAxisRight(); //y轴右边标示
        axisLeft.setTextColor(Color.GRAY); //字体颜色
        axisLeft.setTextSize(10f); //字体大小
        axisLeft.setAxisMaxValue(200f); //最大值
        axisLeft.setLabelCount(5); //显示格数
        axisLeft.setGridColor(Color.GRAY); //网格线颜色

        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);

        lineChart.animateX(2000);  //立即执行动画
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.back :
            finish();
        break;
        case R.id.title_right :
            setResult(1);
            finish();
        break;
}
    }
}
