package com.example.testchart;

import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
/**
 * ��̬����ͼ
 * @author shourong
 *
 */
public class MainActivity2 extends Activity {
	private Timer timer = new Timer();
	private TimerTask task;
	private Handler handler;
	private String title = "Signal Strength";
	private XYSeries series;
	private XYMultipleSeriesDataset mDataset;
	private GraphicalView chart;
	private XYMultipleSeriesRenderer renderer;
	private Context context;
	private int addX = -1, addY;

	int[] xv = new int[100];
	int[] yv = new int[100];

	RelativeLayout layout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		layout = (RelativeLayout) findViewById(R.id.base_rl);

		context = getApplicationContext();

		// ������main�����ϵĲ��֣�������ͼ���������������
		// ������������������ϵ����е㣬��һ����ļ��ϣ�������Щ�㻭������
		series = new XYSeries(title);

		// ����һ�����ݼ���ʵ����������ݼ�������������ͼ��
		mDataset = new XYMultipleSeriesDataset();

		// ���㼯��ӵ�������ݼ���
		mDataset.addSeries(series);

		// ���¶������ߵ���ʽ�����Եȵȵ����ã�renderer�൱��һ��������ͼ������Ⱦ�ľ��
		int color = Color.GREEN;
		PointStyle style = PointStyle.CIRCLE;
		renderer = buildRenderer(color, style, true);

		// ���ú�ͼ�����ʽ
		setChartSettings(renderer, "X", "Y", 0, 100, 0, 90, Color.WHITE,
				Color.WHITE);

		// ����ͼ��
		chart = ChartFactory.getLineChartView(context, mDataset, renderer);

		// ��ͼ����ӵ�������ȥ
		layout.addView(chart, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		// �����Handlerʵ������������Timerʵ������ɶ�ʱ����ͼ��Ĺ���
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// ˢ��ͼ��
				updateChart();
				super.handleMessage(msg);
			}
		};

		task = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};

		timer.schedule(task, 200, 200);

	}

	@Override
	public void onDestroy() {
		// ����������ʱ�ص�Timer
		timer.cancel();
		super.onDestroy();
	}

	protected XYMultipleSeriesRenderer buildRenderer(int color,
			PointStyle style, boolean fill) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// ����ͼ�������߱������ʽ��������ɫ����Ĵ�С�Լ��ߵĴ�ϸ��
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(color);
		r.setPointStyle(style);
		r.setFillPoints(fill);
		r.setLineWidth(3);
		renderer.addSeriesRenderer(r);

		return renderer;
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String xTitle, String yTitle, double xMin, double xMax,
			double yMin, double yMax, int axesColor, int labelsColor) {
		// �йض�ͼ�����Ⱦ�ɲο�api�ĵ�
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.GREEN);
		renderer.setXLabels(20);
		renderer.setYLabels(10);
		renderer.setXTitle("Time");
		renderer.setYTitle("����");
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setPointSize((float) 2);
		renderer.setShowLegend(false);
	}

	private void updateChart() {

		// ���ú���һ����Ҫ���ӵĽڵ�
		addX = 0;
		addY = (int) (Math.random() * 90);
		// �Ƴ����ݼ��оɵĵ㼯
		mDataset.removeSeries(series);
		// �жϵ�ǰ�㼯�е����ж��ٵ㣬��Ϊ��Ļ�ܹ�ֻ������100�������Ե���������100ʱ��������Զ��100
		int length = series.getItemCount();
		if (length > 100) {
			length = 100;
		}
		// ���ɵĵ㼯��x��y����ֵȡ��������backup�У����ҽ�x��ֵ��1�������������ƽ�Ƶ�Ч��
		for (int i = 0; i < length; i++) {
			xv[i] = (int) series.getX(i) + 1;
			yv[i] = (int) series.getY(i);
		}
		// �㼯����գ�Ϊ�������µĵ㼯��׼��
		series.clear();

		// ���²����ĵ����ȼ��뵽�㼯�У�Ȼ����ѭ�����н�����任���һϵ�е㶼���¼��뵽�㼯��
		// �����������һ�°�˳��ߵ�������ʲôЧ������������ѭ���壬������²����ĵ�
		series.add(addX, addY);
		for (int k = 0; k < length; k++) {
			series.add(xv[k], yv[k]);
		}
		// �����ݼ�������µĵ㼯
		mDataset.addSeries(series);

		// ��ͼ���£�û����һ�������߲�����ֶ�̬
		// ����ڷ�UI���߳��У���Ҫ����postInvalidate()������ο�api
		chart.invalidate();
	}
}
