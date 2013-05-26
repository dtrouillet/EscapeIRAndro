package fr.esipe.game.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class MapListView extends ListView {

	private int endTime=3;
	private int startTime=0;
	private TextView tv=null;

	public MapListView(Context context) {
		super(context);
		// 
	}
	public MapListView(Context context, AttributeSet attrs) {
		super(context,attrs);
		// 
	}
	public MapListView(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		// 
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		Log.d("Scroll", "update");
		updateTime();
		updateTextView();
		super.onScrollChanged(l, t, oldl, oldt);
	}
	public void setTextView(TextView tv){
		this.tv=tv;
	}
	public int getStartTime(){
		return startTime;
	}
	public int getEndTime(){
		return endTime;
	}
	public String getMapTime(){
		int s=getStartTime();
		int e=getEndTime();
		return new String(s/60+":"+s%60+"-"+e/60+":"+e%60);
	}
	private void updateTime(){
		startTime=(getCount()-1-getLastVisiblePosition())*Constant.SEC_P_MAP;
		endTime=(getCount()-getFirstVisiblePosition())*Constant.SEC_P_MAP;
	}
	private void updateTextView(){
		if(tv!=null){
			tv.setText(getMapTime());
		}
	}

}
