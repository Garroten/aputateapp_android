package com.apuntate.app.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import com.apuntate.app.R;
import com.apuntate.app.helper.events.EventListHeader;
import com.apuntate.app.helper.events.EventListItem;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;

import com.apuntate.app.constants.Constatns;
import com.apuntate.app.beans.Event;
import com.apuntate.app.util.DateUtil;
import com.apuntate.app.xml.parser.EventXmlParser;
import com.apuntate.app.xml.parser.EventsXmlParser;

public class EventService {
	
	public ArrayList<Event> getEvents(int currentPage) throws XmlPullParserException, IOException {
		EventsXmlParser eventParser;
        HttpGet get;
		HttpClient httpClient = new DefaultHttpClient();
        if(currentPage == 1) {
		    get = new HttpGet(Constatns.apuntate + "/main/anteriores");
        } else {
            get = new HttpGet(Constatns.apuntate + "/main/anteriores?page="+currentPage);
        }
		get.setHeader("content-type", "application/xml");

		HttpResponse resp = httpClient.execute(get);
		String respStr = EntityUtils.toString(resp.getEntity());
		InputStream in = new ByteArrayInputStream(respStr.getBytes());
		eventParser = new EventsXmlParser(in);
        ArrayList<Event> events = eventParser.getEvents();
        for(Event e:events) {
            if(e.getImageURL() != null && !e.getImageURL().equals("")) {
                Bitmap image = downloadFile(Constatns.apuntate + e.getImageURL());
                e.setImage(image);
            }
        }
		return events;
	}

    public Event getEvent(int id) throws XmlPullParserException, IOException {
        EventXmlParser evnetParser;
        HttpGet get;
        HttpClient httpClient = new DefaultHttpClient();
        get = new HttpGet(Constatns.apuntate +"/events/" + id);
        get.setHeader("content-type", "application/xml");

        HttpResponse resp = httpClient.execute(get);
        String respStr = EntityUtils.toString(resp.getEntity());
        InputStream in = new ByteArrayInputStream(respStr.getBytes());
        evnetParser = new EventXmlParser(in);
        Event event = evnetParser.getEvent();
        if(event.getImageURL() != null && !event.getImageURL().equals("")) {
            Bitmap image = downloadFile(Constatns.apuntate + event.getImageURL());
            event.setImage(image);
        }
        return event;
    }

    public ArrayList getItems(ArrayList<Event> events) throws IOException, XmlPullParserException {
        ArrayList items = new ArrayList();
        LinkedHashMap<String, ArrayList> clusterizedEvents = clusterizeEvents(events);
        Set<String> dates = clusterizedEvents.keySet();
        for(String date:dates) {
            items.add(new EventListHeader(date));
            ArrayList<Event> events1 = clusterizedEvents.get(date);
            for(Event e:events1) {
                EventListItem item = new EventListItem(e.getId(), R.drawable.fotografia, e.getTitle(),e.getPlaceName() + ", " + e.getCity(), e.getImage());
                items.add(item);
            }
        }
        return items;
    }

    private Bitmap downloadFile(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap loadedImage = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }

    private LinkedHashMap<String, ArrayList> clusterizeEvents(ArrayList<Event> events) {
        LinkedHashMap<String, ArrayList> clustericedEvents = new LinkedHashMap<String, ArrayList>();
        ArrayList items = new ArrayList();
        String lastDate = DateUtil.dateToString(events.get(0).getBegin(), "EEEE, d MMMM yyyy");
        for(int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            String date = DateUtil.dateToString(e.getBegin(), "EEEE, d MMMM yyyy");
            if(date.equals(lastDate)) {
                items.add(e);
                if(i == (events.size()-1)) {
                    clustericedEvents.put(lastDate, items);
                }
            } else {
                clustericedEvents.put(lastDate, items);
                lastDate = date;
                items = new ArrayList();
                items.add(e);
                if(i == (events.size()-1)) {
                    clustericedEvents.put(lastDate, items);
                }
            }
        }
        return clustericedEvents;
    }
}
