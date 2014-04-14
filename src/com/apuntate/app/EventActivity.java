package com.apuntate.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;

import com.apuntate.app.beans.Event;
import com.apuntate.app.thread.EventThread;

/**
 * Created by rafaelgarrote on 30/05/13.
 */
public class EventActivity extends Activity {
    private EventThread eventThread;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Bundle extra = this.getIntent().getExtras();
        Integer eventId = (Integer) extra.get("id");

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando evento...");
        pDialog.setCancelable(true);
        pDialog.show();

        eventThread = new EventThread(eventId, pDialog);
        eventThread.execute(this);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_event_share:
                return share();
            case R.id.menu_event_agenda:
                return addEventToCalendar();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean share() {
        Event event = eventThread.getEvetn();
        if (event != null) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, event.getTitle());
            sharingIntent.putExtra(Intent.EXTRA_TEXT, event.getDescription());
            startActivity(Intent.createChooser(sharingIntent, "Comparte en"));
            return true;
        } else {
            return false;
        }
    }

    private boolean addEventToCalendar() {
        Event event = eventThread.getEvetn();
        if(event != null) {
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.getBegin().getTime());
            intent.putExtra(CalendarContract.Events.ALL_DAY, false);
            intent.putExtra(CalendarContract.Events.RRULE , "FREQ=DAILY");
            intent.putExtra(CalendarContract.Events.TITLE, event.getTitle());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, event.getDescription());
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION,event.getAddress());

            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
}
