package com.example.governmentjobsalert.async;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.governmentjobsalert.Listener.UpdateUIListener;
import com.example.governmentjobsalert.bean.Rowitem;

public class XMLParser extends AsyncTask<URL, Void, Void>{


	

private Context myContext;
private UpdateUIListener updateView;
private ArrayList<Rowitem> joblist;
private Rowitem jobdetail;


public XMLParser(Context mcontext,UpdateUIListener updateView)
{
	this.updateView=updateView;
	myContext=mcontext;
}

	//	@Override
//	public void onClick(View v) {
//
//		try {
//			url[0] = new URL("http://governmentjobsindia.net/feed/");
//			url[1] = new URL(
//					"http://jobsearch.naukri.com/mynaukri/mn_newsmartsearch.php?xz=1_0_15&xo=2727&xl=rss&rss=r&type=all&qf=catid_28");
//			url[2] = new URL(
//					"http://www.worldfreshers.in/external.php?type=RSS2&forumids=4");
//			url[3] = new URL(
//					"http://feeds.feedburner.com/Mysarkarinaukricom-MySarkariNaukri?format=xml");
//		} catch (MalformedURLException e) {
//
//			e.printStackTrace();
//		}
//
//		for (int i = 0; i <= 3; i++) {
//			new fetcher().execute(url[i]);
//		}
//	}
//
	public InputStream getInputStream(URL url) {
		try {
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			return null;
		}
	}

//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		Uri uri = Uri.parse((String) links.get(arg2));
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);
//		Toast.makeText(getBaseContext(), "Directing to the Website",
//				Toast.LENGTH_SHORT).show();
//	}
//


		@Override
		protected Void doInBackground(URL... params) {
			
			try {
				
				URL url= params[0];
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser xpp = factory.newPullParser();

				// We will get the XML from an input stream
				xpp.setInput(getInputStream(url), "UTF_8");

				boolean insideItem = false;
				
				// Returns the type of current event: START_TAG, END_TAG, etc..
				
				int eventType = xpp.getEventType();
				joblist=new ArrayList<Rowitem>();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					jobdetail=new Rowitem();
					if (eventType == XmlPullParser.START_TAG) {

						if (xpp.getName().equalsIgnoreCase("item")) 
						{
							insideItem = true;
						} 
						else if (xpp.getName().equalsIgnoreCase("title")) 
						{
							if (insideItem)
							{
								jobdetail.setTitle(xpp.nextText());
							
								
							}
							
							else if (xpp.getName().equalsIgnoreCase("description")) 
							{
								if (insideItem)
									//map.put("description", xpp.nextText()); // extract the link
																// of article
									jobdetail.setDesc(xpp.nextText());
							}
								
								
						}
						else if (xpp.getName().equalsIgnoreCase("link")) 
						{
							if (insideItem)
								jobdetail.setLink(xpp.nextText()); // extract the link
															// of article
						}
						
					} 
					else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) 
					{
						insideItem = false;
					}
					
				joblist.add(jobdetail);
					eventType = xpp.next(); // move to next element
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.v("chk",""+joblist);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (joblist!=null)
			{
			updateView.uiUpdated(joblist);
			}
		}
             
		

	
}
