package com.example.testchart;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.testchart.view.PmChartView;
/**
 * 折线图
 * @author shourong
 *
 */
public class MainActivity extends Activity {
	RelativeLayout mRl_base;
	PmChartView pmChartView;
	GraphicalView graphicalView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRl_base = (RelativeLayout) findViewById(R.id.base_rl);

		String[] titles = new String[] { "PM2.5" };// 显示名称
		List<double[]> x = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			x.add(new double[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
					14, 15, 17, 18, 19, 20, 21, 22, 23 });
		}
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4,
				26.1, 23.6, 20.3, 17.2, 13.9, 2.3, 12.5, 23.8, 8.8, 20.4, 15.4,
				26.4, 16.1, 23.6, 20.3, 27.2, 13.9 });

		int[] colors = new int[] { Color.YELLOW };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };

		pmChartView = new PmChartView("PM2.5", "显示一天24小时的PM2.5变化情况");
		try {
			pmChartView.initDataset(titles, x, values);
			XYMultipleSeriesRenderer renderers = pmChartView.initRenderer(
					colors, styles);

			pmChartView.setChartSettings(renderers, "PM2.5监测", "Hour", "value",
					0, 24, 0, 40, Color.LTGRAY, Color.LTGRAY);
			renderers.setShowGrid(true);
			renderers.setXLabels(24);
			renderers.setYLabels(10);
			renderers.setXLabelsAlign(Align.RIGHT);
			renderers.setYLabelsAlign(Align.RIGHT);
			renderers.setPointSize(10);
//			如果我想让y轴不动该怎么办呀？	
//			renderer.setZoomEnabled(true, false);
//			一个设置拖动时Y轴不动，一个设置放大缩小时Y轴不放大缩小
			renderers.setPanLimits(new double[] { 0, 24, 0, 40 });
			renderers.setZoomLimits(new double[] { 0, 24, 0, 40 });
			renderers.setClickEnabled(true);
			renderers.setSelectableBuffer(30);
			int length = renderers.getSeriesRendererCount();  
		    for (int i = 0; i < length; i++) {  
		    	
		      ((XYSeriesRenderer) renderers.getSeriesRendererAt(i)).setFillPoints(true);
		    }  
			graphicalView = pmChartView.executeForView(this);

			graphicalView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// handle the click event on the chart
					SeriesSelection seriesSelection = graphicalView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(MainActivity.this, "没有点击",
								Toast.LENGTH_SHORT).show();
					} else {
						// display information of the clicked point
						Toast.makeText(
								MainActivity.this,
								"series index "
										+ seriesSelection.getSeriesIndex()
										+ " point index "
										+ seriesSelection.getPointIndex()
										+ "  X=" + seriesSelection.getXValue()
										+ ", Y=" + seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {

			e.printStackTrace();
		}

		ViewGroup.LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mRl_base.addView(graphicalView, lp);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
