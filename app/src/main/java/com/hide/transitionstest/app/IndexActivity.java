package com.hide.transitionstest.app;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * * * * * * * * * * * * * * * * * * * * * * *
 * Created by zhaoyiding
 * Date: 15/10/11
 * * * * * * * * * * * * * * * * * * * * * * *
 **/
public class IndexActivity extends ListActivity {

    public static int sStatusBarHeight;
    private String[] indexs = new String[]{"Simple Transition", "DragTransition"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, indexs));

        getListView().post(new Runnable() {
            @Override
            public void run() {

                Rect statusBarRect = new Rect();
                getListView().getWindowVisibleDisplayFrame(statusBarRect);
                sStatusBarHeight = statusBarRect.top;
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, DragTransitionActivity.class));
                break;
        }
    }
}
