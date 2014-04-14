package com.apuntate.app.thread;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apuntate.app.EventActivity;
import com.apuntate.app.R;
import com.apuntate.app.beans.Event;
import com.apuntate.app.service.EventService;
import com.apuntate.app.util.DateUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rafaelgarrote on 30/05/13.
 */
public class EventThread extends AsyncTask<EventActivity, Integer, Event> {
    private EventService eventService;
    private EventActivity activity;
    private int eventId;
    private Event event;
    private GoogleMap map;
    static final int ZOOM = 16;
    private ProgressDialog pDialog;

    public EventThread(int eventId, ProgressDialog pDialog) {
        this.eventId = eventId;
        this.pDialog = pDialog;
    }

    @Override
    protected Event doInBackground(EventActivity... params) {
        this.activity = params[0];
        eventService = new EventService();
        try {
            event = eventService.getEvent(eventId);
            return event;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
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
    protected void onPostExecute(Event result) {
        super.onPostExecute(result);
        this.pDialog.dismiss();
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutEventHeader);
        if(result.getImage() != null) {
            BitmapDrawable background = new BitmapDrawable(result.getImage());
            linearLayout.setBackgroundDrawable(background);
        } else {
            linearLayout.setBackgroundResource(R.drawable.event);
        }

        TextView eventTitle = (TextView) activity.findViewById(R.id.evenTitle);
        eventTitle.setText(result.getTitle());
        TextView eventDate = (TextView) activity.findViewById(R.id.evenDate);
        eventDate.setText(DateUtil.dateToString(result.getBegin(), "EEEE, d MMMM yyyy"));
        TextView eventPlace = (TextView) activity.findViewById(R.id.eventPlace);
        eventPlace.setText(result.getPlaceName());
        TextView eventAddress = (TextView) activity.findViewById(R.id.eventAdress);
        eventAddress.setText(result.getAddress2());
        TextView eventDescription = (TextView) activity.findViewById(R.id.eventDescription);
        eventDescription.setText(result.getDescription());
        map = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker kiel;
        if(result.getPlaceName().equals("")) {
            kiel = map.addMarker(new MarkerOptions()
                    .position(new LatLng(result.getLat(), result.getLon()))
                    .title(result.getTitle())
                    .snippet(result.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        } else {
            kiel = map.addMarker(new MarkerOptions()
                    .position(new LatLng(result.getLat(), result.getLon()))
                    .title(result.getPlaceName())
                    .snippet(result.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        }

        // Move the camera instantly to pont with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(result.getLat(), result.getLon()), 12));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(ZOOM), 2000, null);
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

    public Event getEvetn() {
        return event;
    }

    private Bitmap downloadFile(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap loadedImage = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            //imageView.setImageBitmap(loadedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }
}
