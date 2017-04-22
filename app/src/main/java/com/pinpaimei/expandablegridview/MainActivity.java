package com.pinpaimei.expandablegridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pinpaimei.expandgridview.ExpandableGridView;
import com.pinpaimei.expandgridview.OnGridItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableGridView expandableGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableGridView = (ExpandableGridView) findViewById(R.id.egv);
        List<GroupRequest> groupList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            GroupRequest request = new GroupRequest();
            request.groupName = "Title: " + i;
            request.childList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                ChildItem item = new ChildItem();
                item.childName = "Child: " + j;
                request.childList.add(item);
            }
            groupList.add(request);
        }
        SimpleExpandableGridAdapter gridAdapter = new SimpleExpandableGridAdapter(this);
        gridAdapter.setDatas(groupList);
        expandableGridView.setExpandableGridAdapter(gridAdapter);
        expandableGridView.setGroupClickable(false);
        expandableGridView.expandAll(true);
        expandableGridView.setOnGridItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onGridItemClick(View view, int gridGroupPosition, int gridChildPosition) {
                Toast.makeText(MainActivity.this, "Group: " + gridGroupPosition + ",Child: " + gridChildPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
