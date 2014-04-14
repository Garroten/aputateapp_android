package com.apuntate.app.xml.parser;

import android.util.Xml;

import com.apuntate.app.beans.Event;
import com.apuntate.app.util.DateUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by rafaelgarrote on 01/06/13.
 */
public class EventXmlParser {
    private static final String EVENT_TAG  		= "event";
    private static final String ID_TAG 		    = "id";
    private static final String TITLE_TAG  		= "title";
    private static final String DESCRIPTION_TAG = "description";
    private static final String KIND_TAG 		= "kind";
    private static final String GEOCODE_TAG 	= "geocode";
    private static final String STREET_TAG      = "street";
    private static final String NUMERO_TAG      = "number";
    private static final String CITY_TAG        = "city";
    private static final String PROVINCIA_TAG   = "provincia";
    private static final String COUNTRY_TAG     = "country";
    private static final String WHEN_TAG        = "when";
    private static final String ENDS_TAG        = "ends";
    private static final String END_TAG         = "end";
    private static final String PLACE_NAME_TAG  = "place_name";
    private static final String URL_IMAGE_TAG   = "url_image";

    private static final String ns = null;
    private XmlPullParser parser;

    public EventXmlParser(InputStream in) throws XmlPullParserException, IOException {
        this.parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
    }

    public Event getEvent() throws XmlPullParserException, IOException {
        Event event = null;

        int id = 0;
        String title = null;
        String description = null;
        String kind = null;
        String geocode = null;
        String street = null;
        String numero = null;
        String city = null;
        String provincia = null;
        String country = null;
        Date when = null;
        Date ends = null;
        boolean end = false;
        String placeName = null;
        String imageURL = null;

        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.EVENT_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(EventXmlParser.ID_TAG)) {
                id = readId(parser);
            } else if (name.equals(EventXmlParser.TITLE_TAG)) {
                title = readTitle(parser);
            } else if (name.equals(EventXmlParser.DESCRIPTION_TAG)) {
                description = readDescription(parser);
            } else if (name.equals(EventXmlParser.KIND_TAG)) {
                kind = readKind(parser);
            } else if (name.equals(EventXmlParser.GEOCODE_TAG)) {
                geocode = readGeocode(parser);
            } else if (name.equals(EventXmlParser.STREET_TAG)) {
                street = readStreet(parser);
            } else if (name.equals(EventXmlParser.NUMERO_TAG)) {
                numero = readNumero(parser);
            } else if (name.equals(EventXmlParser.CITY_TAG)) {
                city = readCity(parser);
            } else if (name.equals(EventXmlParser.PROVINCIA_TAG)) {
                provincia = readProvincia(parser);
            } else if (name.equals(EventXmlParser.COUNTRY_TAG)) {
                country = readCountry(parser);
            } else if (name.equals(EventXmlParser.WHEN_TAG)) {
                when = readBegin(parser);
            } else if (name.equals(EventXmlParser.ENDS_TAG)) {
                ends = readEnds(parser);
            } else if (name.equals(EventXmlParser.END_TAG)) {
                end = readEnd(parser);
            } else if (name.equals(EventXmlParser.PLACE_NAME_TAG)) {
                placeName = readPaceName(parser);
            } else if (name.equals(EventXmlParser.URL_IMAGE_TAG)) {
                imageURL = readImageURL(parser);
            } else {
                skip(parser);
            }
        }
        return new Event(id, title, description, kind, geocode, when, ends, end, street, numero,
                city, provincia, country, placeName, imageURL);
    }

    private int readId(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.ID_TAG);
        String id = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.ID_TAG);
        return Integer.parseInt(id);
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.TITLE_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.TITLE_TAG);
        return title;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.DESCRIPTION_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.DESCRIPTION_TAG);
        return title;
    }

    private String readKind(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.KIND_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.KIND_TAG);
        return title;
    }

    private String readGeocode(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.GEOCODE_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.GEOCODE_TAG);
        return title;
    }

    private String readStreet(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.STREET_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.STREET_TAG);
        return title;
    }

    private String readNumero(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.NUMERO_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.NUMERO_TAG);
        return title;
    }

    private String readCity(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.CITY_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.CITY_TAG);
        return title;
    }

    private String readProvincia(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.PROVINCIA_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.PROVINCIA_TAG);
        return title;
    }

    private String readCountry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.COUNTRY_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.COUNTRY_TAG);
        return title;
    }

    private String readPaceName(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.PLACE_NAME_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.PLACE_NAME_TAG);
        return title;
    }

    private Date readBegin(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.WHEN_TAG);
        String title = readText(parser).substring(0,19);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.WHEN_TAG);
        try {
            return DateUtil.stringToDate(title, "yyyy-MM-dd'T'HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Date readEnds(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.ENDS_TAG);
        String title = readText(parser).substring(0,19);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.ENDS_TAG);
        try {
            return DateUtil.stringToDate(title, "yyyy-MM-dd'T'HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean readEnd(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.END_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.END_TAG);
        if(title.equals("on")) {
            return true;
        } else {
            return false;
        }
    }

    private String readImageURL(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EventXmlParser.URL_IMAGE_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, EventXmlParser.URL_IMAGE_TAG);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
