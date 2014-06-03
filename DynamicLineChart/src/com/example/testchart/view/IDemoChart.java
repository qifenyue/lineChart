package com.example.testchart.view;
import org.achartengine.GraphicalView;
import android.content.Context;
import android.content.Intent;
import android.view.View;
/**
 * Defines the demo charts.
 */
public interface IDemoChart {
  //TODO step1:ͼ�������� ������  ����ִ�����ͼƬ����
  /** A constant for the name field in a list activity. */
  String NAME = "name";
  /** A constant for the description field in a list activity. */
  String DESC = "desc";
  String getName();
  String getDesc();
  Intent execute(Context context);
  GraphicalView executeForView(Context context);
}
