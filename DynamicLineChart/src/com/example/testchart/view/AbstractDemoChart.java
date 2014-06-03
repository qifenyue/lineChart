package com.example.testchart.view;

import java.util.Date;
import java.util.List;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

//TODO step2��װ�˹���DataSet��renderer�ķ���,Ŀ�Ŀ��ٹ������ǵ�dataset��renderer

//TODO step3��������*Chat��β����󶼼̳���AbstractDemoChart�����ʵ���˽ӿ�IDemoChart.
//���ǿ����ص㿴һ��execute(context)����,����֮������������й���Intent�Ĳ����ͬС��,
//��һ������dataset,�ڶ�������renderer,����������ChartFactory.get***Intent()������ChartFactory.get***View()����,
public abstract class AbstractDemoChart implements IDemoChart {

  /**
   * Builds an XY multiple dataset using the provided values.
   * 
   * @param titles the series titles
   * @param xValues the values for the X axis
   * @param yValues the values for the Y axis
   * @return the XY multiple dataset
   */
  protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    addXYSeries(dataset, titles, xValues, yValues, 0);
    return dataset;
  }
/**
 * ��DataSet���������. 
 * @param dataset
 * @param titles
 * @param xValues
 * @param yValues
 * @param scale
 */
  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
      List<double[]> yValues, int scale) {
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      XYSeries series = new XYSeries(titles[i], scale);// //����ע����TimeSeries����.
      double[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
  }

  /**
   * ����XYMultipleSeriesRenderer. 
   * Builds an XY multiple series renderer.
   * 
   * @param colors the series rendering colors ÿ�����е���ɫ
   * @param styles the series point styles ÿ�����е������(����������,Բ��,����,����ȶ���)
   * @return the XY multiple series renderers
   */
  protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    setRenderer(renderer, colors, styles);
    return renderer;
  }
/**
 *
 * @param renderer
 * @param colors
 * @param styles
 */
  protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
    // ����ͼ����������
    renderer.setAxisTitleTextSize(16);//������������ֵĴ�С 
    renderer.setChartTitleTextSize(20);//��������ͼ��������ֵĴ�С
    renderer.setLabelsTextSize(15);//������̶����ֵĴ�С
    renderer.setLegendTextSize(15);//����ͼ�����ִ�С
    renderer.setPointSize(5f);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)  
    renderer.setMargins(new int[] { 20, 30, 15, 20 });//����ͼ�����߿�(��/��/��/��) 
    
    //���´�������ÿ�������е���ɫ.
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = new XYSeriesRenderer();
      r.setColor(colors[i]);
      r.setPointStyle(styles[i]);
      renderer.addSeriesRenderer(r);
    }
  }

  /**
   * Sets a few of the series renderer settings.����renderer��һЩ����. 
   * 
   * @param renderer the renderer to set the properties toҪ���õ�renderer
   * @param title the chart title ͼ�����
   * @param xTitle the title for the X axis X�����
   * @param yTitle the title for the Y axis y�����
   * @param xMin the minimum value on the X axis X����Сֵ
   * @param xMax the maximum value on the X axis  X�����ֵ
   * @param yMin the minimum value on the Y axis Y����Сֵ
   * @param yMax the maximum value on the Y axis Y�����ֵ
   * @param axesColor the axes color X����ɫ
   * @param labelsColor the labels color y����ɫ
   */
  public void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
      int labelsColor) {
    renderer.setChartTitle(title);
    renderer.setXTitle(xTitle);
    renderer.setYTitle(yTitle);
    renderer.setXAxisMin(xMin);
    renderer.setXAxisMax(xMax);
    renderer.setYAxisMin(yMin);
    renderer.setYAxisMax(yMax);
    renderer.setAxesColor(axesColor);
    renderer.setLabelsColor(labelsColor);
  }

  /**
   * Builds an XY multiple time dataset using the provided values.
   * ������ʱ���йص�XYMultipleSeriesDataset,���������buildDataset�ڲ�������������ҪList<Date[]>������.
   * @param titles the series titles
   * @param xValues the values for the X axis
   * @param yValues the values for the Y axis
   * @return the XY multiple time dataset
   */
  protected XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      TimeSeries series = new TimeSeries(titles[i]);//����ʱ������TimeSeries,
      Date[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
    return dataset;
  }

  /**
   * Builds a category series using the provided values.
   * ��������CategorySeries,���������ɱ�ͼ,ע����buildMultipleCategoryDataset(����Բ��ͼ)������. 
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (double value : values) {
      series.add("Project " + ++k, value);
    }

    return series;
  }

  /**
   * Builds a multiple category series using the provided values.
   * ����MultipleCategorySeries,�����ڹ���Բ��ͼ(ÿ������һ������)
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  protected MultipleCategorySeries buildMultipleCategoryDataset(String title,
      List<String[]> titles, List<double[]> values) {
    MultipleCategorySeries series = new MultipleCategorySeries(title);
    int k = 0;
    for (double[] value : values) {
      series.add(2007 + k + "", titles.get(k), value);
      k++;
    }
    return series;
  }

  /**
   * ����DefaultRenderer. 
   *  
   * Builds a category renderer to use the provided colors.
   * 
   * @param colors the colors ÿ�����е���ɫ
   * @return the category renderer
   */
  protected DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[] { 20, 30, 15, 0 });
    for (int color : colors) {
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(color);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }

  /**
   * Builds a bar multiple series dataset using the provided values.
   * ����XYMultipleSeriesDataset,��������״ͼ. 
   * @param titles the series titles ÿ���������е�ͼ��
   * @param values the values ���ӵĸ߶�ֵ
   * @return the XY multiple bar dataset
   */
  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      CategorySeries series = new CategorySeries(titles[i]);
      double[] v = values.get(i);
      int seriesLength = v.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(v[k]);
      }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }

  /**
   * Builds a bar multiple series renderer to use the provided colors.
   * 
   * @param colors the series renderers colors
   * @return the bar multiple series renderer
   */
  protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = new XYSeriesRenderer();
      r.setColor(colors[i]);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }
}