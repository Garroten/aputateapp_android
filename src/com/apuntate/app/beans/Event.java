package com.apuntate.app.beans;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.StringTokenizer;

public class Event {
    private int id;
	private String title;
	private String description;
	private String kind;
	private String geocode;
    private Date begin;
    private Date ends;
    private boolean end;
    private String street;
    private String numero;
    private String city;
    private String provincia;
    private String country;
    private String placeName;
    private String imageURL;
    private Bitmap image;

    public Event(int id, String title, String description, String kind, String geocode,
                 Date begin, Date ends, boolean end, String street, String numero, String city,
                 String provincia, String country, String placeName, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.kind = kind;
        this.geocode = geocode;
        this.begin = begin;
        this.ends = ends;
        this.end = end;
        this.street = street;
        this.numero = numero;
        this.city = city;
        this.provincia = provincia;
        this.country = country;
        this.placeName = placeName;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getGeocode() {
		return geocode;
	}
	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getLat() {
        StringTokenizer st = new StringTokenizer(geocode);
        return Float.parseFloat(st.nextToken(","));
    }

    public float getLon() {
        StringTokenizer st = new StringTokenizer(geocode);
        st.nextToken(",");
        return Float.parseFloat(st.nextToken(","));
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getAddress() {
        String address = getStreet() + " " + getNumero() + "\n" + getCity();
        if(getCity().equalsIgnoreCase(getProvincia())) {
            address = address + "\n" + getCountry();
        } else {
            address = address + "\n" + getProvincia() + "\n" + getCountry();
        }
        return  address;
    }

    public String getAddress2() {
        String address = getStreet() + " " + getNumero() + ", " + getCity();
        if(getCity().equalsIgnoreCase(getProvincia())) {
            address = address + ", " + getCountry();
        } else {
            address = address + ", " + getProvincia() + "\n" + getCountry();
        }
        return  address;
    }
}
