package com.example.zakaria.zvimobile.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zakaria.zvimobile.R;

import java.util.ArrayList;

public class showDatastoreInfos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datastore_infos);

        Bundle extras = getIntent().getExtras();

        // Intent intent = getIntent();
        ArrayList<String> value = new ArrayList<>() ;
        value = extras.getStringArrayList("datastoreData");

        TextView datastoreName,datastoreType,datastoreCapacity,datastoreFreeSpace,datastoreUsedSpace,datastoreUuid,
                datastoreLocation,datastoreTag,datastoreNbrVms,percentTextView;

        ProgressBar storageProgressBar;

        int storageProgressStatus;

        storageProgressBar = (ProgressBar) findViewById(R.id.storageProgressBar);


        storageProgressStatus = (int)((Float.parseFloat(value.get(4)))*100 / Float.parseFloat(value.get(2)));

        storageProgressBar.getBackground().setColorFilter(0xfff, PorterDuff.Mode.SRC_IN);

        percentTextView = (TextView) findViewById(R.id.storagePercentTextView);
        percentTextView.setText(storageProgressStatus+"%");

        storageProgressBar.setProgress(storageProgressStatus);



        datastoreName = findViewById(R.id.datastoreTextView);
        datastoreType = findViewById(R.id.typeValueTextView);
        datastoreCapacity = findViewById(R.id.capacityTextView);
        datastoreFreeSpace = findViewById(R.id.libreSpaceTextView);
        datastoreUsedSpace = findViewById(R.id.usedSpaceTextView);
        datastoreLocation = findViewById(R.id.locationValueTextView);
        datastoreUuid = findViewById(R.id.uuidValueTextView);
        datastoreTag = findViewById(R.id.tagValueTextView);
        datastoreNbrVms = findViewById(R.id.vMsNbrValueTextView);



        datastoreName.setText(value.get(0));
        datastoreType.setText(value.get(1));
        datastoreCapacity.setText("Capacity : "+value.get(2)+" GB");

        datastoreFreeSpace.setText("Free : " +value.get(3)+" GB");
        datastoreUsedSpace.setText("Used : " +value.get(4)+" GB");
        datastoreLocation.setText(value.get(5));
        datastoreUuid.setText(value.get(6));

        datastoreTag.setText(value.get(7));
        datastoreNbrVms.setText(value.get(8));




    }
}
