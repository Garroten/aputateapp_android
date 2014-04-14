package com.apuntate.app.helper.events;

import android.graphics.Bitmap;

/**
 * Created by rafaelgarrote on 28/05/13.
 */
public class EventListItem {
    private int id;
    private int imageId;
    private String title;
    private String description;
    private Bitmap image;

    public EventListItem(int id, int imageId, String title, String description, Bitmap image) {
        this.id = id;
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }
}
