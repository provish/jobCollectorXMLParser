package com.example.governmentjobsalert.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.governmentjobsalert.R;
import com.example.governmentjobsalert.bean.Rowitem;
 
public class JobsListAdapter extends BaseAdapter {
 
    Context myContext;
	private ArrayList<Rowitem> data;
	private View rowView;
 
   
 
    public JobsListAdapter(ArrayList<Rowitem> joblist, Context context) {
		this.data=joblist;
		myContext=context;
	}

	/*private view holder class*/
    private class ViewHolder {
        
        TextView txtTitle;
        TextView txtDesc;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
    	 rowView = convertView;
    	 ViewHolder holder= null;
    	 
    	 
        LayoutInflater mInflater = (LayoutInflater) myContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (rowView == null) 
        {
            rowView = mInflater.inflate(R.layout.list_of_jobs_row, null);
            holder= new ViewHolder();
           
            holder.txtDesc = (TextView) rowView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) rowView.findViewById(R.id.title);
            
            rowView.setTag(holder);
       
         }
    	 else 
    	  holder= (ViewHolder) rowView.getTag();
    	
    	 holder.txtTitle.setText(data.get(position).getTitle());
 		 holder.txtDesc.setText(data.get(position).getDesc());
 
        return rowView;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.v("size",""+data.size());
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}