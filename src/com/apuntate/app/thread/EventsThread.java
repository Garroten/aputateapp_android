package com.apuntate.app.thread;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.*;

import com.apuntate.app.EventActivity;
import com.apuntate.app.helper.events.EventDailyListAdaptor;
import com.apuntate.app.helper.events.EventListAdaptor;
import com.apuntate.app.helper.events.EventListItem;

import org.apache.http.conn.ConnectTimeoutException;
import org.xmlpull.v1.XmlPullParserException;

import com.apuntate.app.MainActivity;
import com.apuntate.app.R;
import com.apuntate.app.beans.Event;
import com.apuntate.app.listener.EndlessScrollListener;
import com.apuntate.app.service.EventService;

import android.os.AsyncTask;

public class EventsThread extends AsyncTask<MainActivity, Integer, ArrayList<Event>> {
	private EventService eventService;
	private MainActivity activity;
    private ArrayList<EventListItem> items;
    private int currentPage;
    private ProgressDialog pDialog;

    public EventsThread(ArrayList<EventListItem> items, int currentPage, ProgressDialog pDialog) {
        this.items = items;
        this.currentPage = currentPage;
        this.pDialog = pDialog;
    }

    @Override
	protected ArrayList<Event> doInBackground(MainActivity... params) {
		this.activity = params[0];
		ArrayList<Event> events;
		eventService = new EventService();
		try {
			events = eventService.getEvents(currentPage);
			return events;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ConnectTimeoutException e) {
            Toast.makeText(this.activity, "Network timeout reached!", Toast.LENGTH_SHORT).show();
            return null;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(ArrayList<Event> result) {
		super.onPostExecute(result);
        if(this.pDialog != null) {
            this.pDialog.dismiss();
        }
		if(result != null && result.size() > 0) {
			//String toastText = result.size() + " eventos encontrados";
			//Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show();

            try {
                ListView eventsListView = (ListView) activity.findViewById(R.id.ListView_events);
                TextView textoLoading = (TextView) activity.findViewById(R.id.fullscreen_content);
                textoLoading.setText("");
                if(currentPage == 1) {
                    items.addAll(eventService.getItems(result));
                    //eventsListView.setAdapter(new EventListAdaptor(items, R.layout.event_list_element, activity));
                    eventsListView.setAdapter(new EventDailyListAdaptor(items, R.layout.event_list_element, R.layout.event_list_header, R.layout.event_list_ad, activity));
                    eventsListView.setOnScrollListener(new EndlessScrollListener(activity, items));
                    eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> pariente,View view,int posicion,long id) {
                            EventListItem elegido =(EventListItem) pariente.getItemAtPosition(posicion);
                            Intent intent = new Intent(activity, EventActivity.class);
                            intent.putExtra("id",elegido.getId());
                            activity.startActivity(intent);
                        }
                    });
                } else {
                    //EventListAdaptor adaptor = (EventListAdaptor) eventsListView.getAdapter();
                    EventDailyListAdaptor adaptor = (EventDailyListAdaptor) eventsListView.getAdapter();
                    adaptor.addItems(eventService.getItems(result));
                    adaptor.notifyDataSetChanged();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
		} else {
            if(isOnline()) {
                TextView textoLoading = (TextView) activity.findViewById(R.id.fullscreen_content);
                textoLoading.setText("No se encontraron eventos");
            } else {
                TextView textoLoading = (TextView) activity.findViewById(R.id.fullscreen_content);
                textoLoading.setText("Sin conexión");
            }
        }
	}

    @Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
