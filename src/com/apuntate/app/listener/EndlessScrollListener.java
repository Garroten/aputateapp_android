package com.apuntate.app.listener;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

import com.apuntate.app.MainActivity;
import com.apuntate.app.helper.events.EventListItem;
import com.apuntate.app.thread.EventsThread;

import java.util.ArrayList;

/**
 * Created by rafaelgarrote on 29/05/13.
 */
public class EndlessScrollListener implements OnScrollListener {

    private int visibleThreshold = 1;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private MainActivity activity;
    private ArrayList items;

    public EndlessScrollListener(MainActivity activity, ArrayList items) {
        this.activity = activity;
        this.items = items;
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            String toastText = (currentPage + 1) + "loading...";
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show();
            EventsThread eventsTrhread = new EventsThread(items, currentPage + 1, null);
            eventsTrhread.execute(activity);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}