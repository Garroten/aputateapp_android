package com.apuntate.app.helper.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apuntate.app.R;

import java.util.ArrayList;

/**
 * Created by rafaelgarrote on 25/06/13.
 */
public class EventDailyListAdaptor extends BaseAdapter {
    private static final int HEADER_TYPE = 0;
    private static final int ITEM_TYPE = 1;
    private static final int ADS_TYPE = 2;

    private static final String HEADER_CLASS = "EventListHeader";
    private static final String ITEM_CLASS   = "EventListItem";

    private ArrayList items;
    private int R_layout_event_idView;
    private int R_layout_header_idView;
    private int R_layout_ads_idView;
    private Context context;
    private int adsCount;

    public EventDailyListAdaptor(ArrayList items, int r_layout_event_idView, int r_layout_header_idView, int r_layout_ads_idView, Context context) {
        this.items = items;
        R_layout_event_idView = r_layout_event_idView;
        R_layout_header_idView = r_layout_header_idView;
        R_layout_ads_idView = r_layout_ads_idView;
        this.context = context;
        this.adsCount = 0;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean isEnabled(int position) {
        String classname = items.get(position).getClass().getSimpleName();
        if(classname.equals(HEADER_CLASS)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String classname = items.get(i).getClass().getSimpleName();
        if((classname.equals(ITEM_CLASS) && i == 5) || (classname.equals(ITEM_CLASS) && (i % 10 == 0) && (i != 0))) {
            view = vi.inflate(R_layout_ads_idView, null);
            onItem(items.get(i), view, ITEM_TYPE);
        } else {
            if(classname.equals(ITEM_CLASS)) {
                view = vi.inflate(R_layout_event_idView, null);
                onItem(items.get(i), view, ITEM_TYPE);
            } else if(classname.equals(HEADER_CLASS)) {
                view = vi.inflate(R_layout_header_idView, null);
                onItem(items.get(i), view, HEADER_TYPE);
            }
        }
        return view;
    }

    public void onItem(Object item, View view, int type) {
        if(type == ITEM_TYPE) {
            ImageView imagenEvento = (ImageView) view.findViewById(R.id.imageView_imagen);
            if(((EventListItem) item).getImage() == null) {
                imagenEvento.setImageResource(((EventListItem) item).getImageId());
            } else {
                imagenEvento.setImageBitmap(((EventListItem) item).getImage());
            }
            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
            texto_superior_entrada.setText(((EventListItem) item).getTitle());
            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
            texto_inferior_entrada.setText(((EventListItem) item).getDescription());
        } else if(type == HEADER_TYPE) {
            TextView date = (TextView) view.findViewById(R.id.list_header_title);
            date.setText(((EventListHeader) item).getDate());
        }
    }

    public void addItems(ArrayList items){
        this.items.addAll(items);
    }
}
