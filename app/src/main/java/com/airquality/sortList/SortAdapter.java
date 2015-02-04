package com.airquality.sortList;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.airquality.R;
import com.airquality.data.CityDataSource;
import com.airquality.data.CityItem;
import com.airquality.data.CityList;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;
	private Context mContext;
    private Boolean cityIndex;
    private int cityPosition;
	
	public SortAdapter(Context mContext, List<SortModel> list, Boolean cityIndex, int cityPosition) {
		this.mContext = mContext;
		this.list = list;
        this.cityIndex = cityIndex;
        this.cityPosition = cityPosition;
	}
	
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
         	view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		int section = getSectionForPosition(position);
		
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
       	}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(this.list.get(position).getName());
        if (cityIndex) {
            CityDataSource dataSource = new CityDataSource(mContext);
            CityList dataList = dataSource.getCityList();
            List<CityItem> list = dataList.getCityList();

            if (list.size()!=0) {
                for (CityItem cityItem : list) {
                    if (cityItem.getCityName().equals(this.list.get(position).getName())) {
                        if (cityPosition == -1) {
                            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_action_action_done);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            viewHolder.tvTitle.setCompoundDrawables(drawable, null, null, null);
                            //viewHolder.tvTitle.setCompoundDrawablePadding(10);
                            break;
                        }

                    }
                }
            }
        }
		
		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
	}

	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}