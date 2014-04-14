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
 * Created by rafaelgarrote on 28/05/13.
 */
public class EventListAdaptor extends BaseAdapter {
    private ArrayList<?> items;
    private int R_layout_IdView;
    private Context context;

    public EventListAdaptor(ArrayList<?> items, int r_layout_IdView, Context context) {
        this.items = items;
        R_layout_IdView = r_layout_IdView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onItem(items.get(i), view);
        return view;
    }

    public void addItems(ArrayList<EventListItem> items){
        items.addAll(items);
    }

    /** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
     * @param item El item que será la asociado a la view. El item es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */

    public void onItem(Object item, View view) {
        //LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearLayout_item);
        //layout.setBackgroundResource(((EventListItem) item).getImageId());
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
    }
}
