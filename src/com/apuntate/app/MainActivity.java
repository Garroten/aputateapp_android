package com.apuntate.app;

import android.app.ProgressDialog;
import android.view.Menu;

import com.apuntate.app.helper.events.EventListItem;
import com.apuntate.app.thread.EventsThread;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		loadEvents(false);
	}

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadEvents(true);
                return true;
            //case R.id.menu_events:
            //    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadEvents(boolean showDialog) {
        ProgressDialog pDialog = null;
        if(showDialog) {
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Cargando eventos...");
            pDialog.setCancelable(true);
            pDialog.show();
        }
        EventsThread eventsTrhread = new EventsThread(new ArrayList<EventListItem>(), 1, pDialog);
        eventsTrhread.execute(this);
    }
}
