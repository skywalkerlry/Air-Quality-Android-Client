/**
 * Copyright 2014  XCL-Charts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version v0.1
 */

package com.airquality.dialChart;

import java.util.ArrayList;
import java.util.List;

import org.xclcharts.chart.DialChart;
import org.xclcharts.common.MathHelper;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotAttrInfo;
import org.xclcharts.renderer.plot.Pointer;
import org.xclcharts.view.GraphicalView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @ClassName DialChart例子
 * @Description  仪表盘例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class DialChart03View extends GraphicalView {

	private String TAG = "DialChart03View";	
	
	private DialChart chart = new DialChart();
	private float mPercentage = (float)0.0;
    private int color;
	
	public DialChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public DialChart03View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public DialChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 
	 private void initView()
	 {
		chartRender();
	 }
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	        chart.setChartRange(w ,h ); 
	    }  		
						
		public void chartRender()
		{
			try {								
							
				//设置标题背景
				//chart.setApplyBackgroundColor(true);
				//chart.setBackgroundColor( (int)Color.rgb(28, 129, 243) );
				//绘制边框
				//chart.showRoundBorder();
						
				//设置当前百分比
				chart.getPointer().setPercentage(mPercentage);
				
				//设置指针长度
				chart.getPointer().setLength(0.75f);
				
				//增加轴
				addAxis("ffffffff");
				/////////////////////////////////////////////////////////////
				//增加指针
				addPointer();
				//设置附加信息
				addAttrInfo(0,"");
				/////////////////////////////////////////////////////////////
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.toString());
			}
			
		}
		
		public void addAxis(String color)
		{
			
			List<Float> ringPercentage = new ArrayList<Float>();			
			float rper = MathHelper.getInstance().div(1, 6); //相当于40%	//270, 4
        //    Log.i("rper", Float.toString(rper));
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			ringPercentage.add(rper);
			ringPercentage.add(rper);
            ringPercentage.add(rper);
            ringPercentage.add(rper);
			
			List<Integer> rcolor  = new ArrayList<Integer>();			
			rcolor.add((int)Color.rgb(254, 254, 254));
			rcolor.add((int)Color.rgb(204, 204, 204));
			rcolor.add((int)Color.rgb(154, 154, 154));
			rcolor.add((int)Color.rgb(104, 104, 104));
            rcolor.add((int)Color.rgb(54, 54, 54));
			chart.addStrokeRingAxis(0.95f,0.8f, ringPercentage, rcolor);
			
			
			List<String> rlabels  = new ArrayList<String>();
			rlabels.add("0");
			rlabels.add("50");
			rlabels.add("100");
			rlabels.add("150");
			rlabels.add("200");
			rlabels.add("250");
			rlabels.add("300");
			chart.addInnerTicksAxis(0.8f, rlabels);

            String cs = color.substring(2,color.length());
            int rColor = Integer.valueOf(cs.substring(0,2),16);
            int gColor = Integer.valueOf(cs.substring(2,4),16);
            int bColor = Integer.valueOf(cs.substring(4,6),16);
			
						
			chart.getPlotAxis().get(0).getFillAxisPaint().setColor((int)Color.rgb(rColor, gColor, bColor));
			chart.getPlotAxis().get(1).getFillAxisPaint().setColor((int)Color.rgb(0, 0, 0));
			chart.getPlotAxis().get(1).getTickLabelPaint().setColor(Color.BLACK);
			chart.getPlotAxis().get(1).getTickMarksPaint().setColor(Color.BLACK);
			chart.getPlotAxis().get(1).hideAxisLine();
			chart.getPlotAxis().get(1).setDetailModeSteps(3);
			
			chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
			chart.getPointer().getPointerPaint().setColor((int)Color.rgb(0, 0, 0) );
			chart.getPointer().getPointerPaint().setStrokeWidth(3);			
			chart.getPointer().getPointerPaint().setStyle(Style.STROKE);			
			chart.getPointer().hideBaseCircle();
			
		}
		
		//增加指针
		public void addPointer()
		{					
			chart.addPointer();			
			List<Pointer> mp = chart.getPlotPointer();	
			mp.get(0).setPercentage( mPercentage);
			//设置指针长度
			mp.get(0).setLength(0.75f);	
			mp.get(0).getPointerPaint().setColor(Color.WHITE);
			mp.get(0).setPointerStyle(XEnum.PointerStyle.TRIANGLE);			
			mp.get(0).hideBaseCircle();
			
		}
		
		
		private void addAttrInfo(int aqi, String airLevel)
		{
			/////////////////////////////////////////////////////////////
			PlotAttrInfo plotAttrInfo = chart.getPlotAttrInfo();
			//设置附加信息
			Paint paintTB = new Paint();
			paintTB.setColor(Color.BLACK);
			paintTB.setTextAlign(Align.CENTER);
			paintTB.setTextSize(50);
			paintTB.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.TOP, Integer.toString(aqi), 0.3f, paintTB);
			
			Paint paintBT = new Paint();
			paintBT.setColor(Color.BLACK);
			paintBT.setTextAlign(Align.CENTER);
			paintBT.setTextSize(35);
			paintBT.setFakeBoldText(true);
			paintBT.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, 
					airLevel, 0.45f, paintBT);
			
			/*Paint paintBT2 = new Paint();
			paintBT2.setColor(Color.BLACK);
			paintBT2.setTextAlign(Align.CENTER);
			paintBT2.setTextSize(30);
			paintBT2.setFakeBoldText(true);
			paintBT2.setAntiAlias(true);	
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, "MB/S", 0.55f, paintBT2);*/
		}
		
		public void setCurrentStatus(float percentage, String airLevel, String color)
		{								
			mPercentage =  percentage;
			chart.clearAll();
			
			//设置当前百分比
			chart.getPointer().setPercentage(mPercentage);
			addAxis(color);
			//增加指针
			addPointer();
			addAttrInfo((int)(percentage*300), airLevel);
		}


		@Override
		public void render(Canvas canvas) {
			// TODO Auto-generated method stub
			 try{
		            chart.render(canvas);
		        } catch (Exception e){
		        	Log.e(TAG, e.toString());
		        }
		}

}
