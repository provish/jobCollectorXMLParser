package com.example.governmentjobsalert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.governmentjobsalert.Listener.UpdateUIListener;
import com.example.governmentjobsalert.adapters.JobsListAdapter;
import com.example.governmentjobsalert.async.XMLParser;
import com.example.governmentjobsalert.bean.Rowitem;

public class MainActivity extends Activity implements OnItemClickListener,
		UpdateUIListener {

	ListView jobsListView;
	private JobsListAdapter adapter;
	private URL url;
	private XMLParser myAsyncTask;
	private ArrayList<Rowitem> joblist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		jobsListView = (ListView) findViewById(R.id.JobsListView);
		jobsListView.setOnItemClickListener(this);
		try {
			url = new URL("http://governmentjobsindia.net/feed/");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myAsyncTask = new XMLParser(MainActivity.this, this);

		myAsyncTask.execute(url);

	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
//		Uri uri = Uri.parse(joblist.get(position).get("link"));
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);
//		Toast.makeText(getBaseContext(), "Directing to the Website",
//				Toast.LENGTH_SHORT).show();
		//Log.v("check", joblist.get(0).get("link"));
		// startActivity(new Intent(MainActivity.this, JobDetails.class));
	}

	@Override
	public void uiUpdated(ArrayList<Rowitem> joblist) 
	{
		this.joblist = joblist;
		adapter = new JobsListAdapter( joblist,MainActivity.this);
		jobsListView.setAdapter(adapter);

	}

}
