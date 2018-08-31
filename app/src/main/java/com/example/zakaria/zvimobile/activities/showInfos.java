package com.example.zakaria.zvimobile.activities;

import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zakaria.zvimobile.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class showInfos extends AppCompatActivity {

    TextView cpuTextView,memoryTextView,storageTextView,guestNameTextView,guestOsTextView,guestVersionTextView,
             guestStateTextView,hardDiskbackingTextView,hardDiskCapacityTextView,hardDiskThinTextView,
            hardDiskModeTextView,networkAdapterTextView,networkConnectedTextView,
            networkAdressTypeTextView,networkMacAdressTextView,videoSizeTextView,videoRenderTextView,
            videoDisplaysTextView;

    private ProgressBar cpuProgressBar,memoryProgressBar,storageProgressBar;
    private int  cpuProgressStatus , memoryProgressStatus, storageProgressStatus;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_infos);

        Bundle extras = getIntent().getExtras();

        // Intent intent = getIntent();
        ArrayList<String> value = new ArrayList<>() ;
        value = extras.getStringArrayList("data");

        cpuProgressBar = (ProgressBar) findViewById(R.id.cpuProgressBar);
        memoryProgressBar = (ProgressBar) findViewById(R.id.memoryProgressBar);
        storageProgressBar = (ProgressBar) findViewById(R.id.storageProgressBar);
        textView = (TextView) findViewById(R.id.textView);

        cpuProgressStatus = (int)((Float.parseFloat(value.get(18)))*100 / Float.parseFloat(value.get(0)));
        memoryProgressStatus = (int)((Float.parseFloat(value.get(19)))*100 / Float.parseFloat(value.get(1)));
        storageProgressStatus = (int)((Float.parseFloat(value.get(20)))*100 / Float.parseFloat(value.get(2)));

        cpuProgressBar.setProgress(cpuProgressStatus);
        memoryProgressBar.setProgress(memoryProgressStatus);
        storageProgressBar.setProgress(storageProgressStatus);
        int colorC = 0xFF000FFF;
        int colorM = 0xFF000FFF;
        int colorS = 0xFF000FFF;
        if(cpuProgressStatus > 80){
            colorC = 0xFFF00000;
        }
            else if(cpuProgressStatus > 60){
                colorC = 0xFF900000;
            }
            else {
            if (cpuProgressStatus > 0)
                colorC = 0x00FF0000;
        }
        if(memoryProgressStatus > 80){
            colorM = 0xFFF00000;
        }
        else if(memoryProgressStatus > 60){
            colorM = 0xFF900000;
        }
        else {
            if (memoryProgressStatus > 0)
                colorM = 0x00FF0000;
        }
        if(storageProgressStatus > 80){
            colorS = 0xFFF00000;
        }
        else if(storageProgressStatus > 60){
            colorS = 0xFF900000;
        }
        else {
            if (storageProgressStatus > 0)
                colorS = 0x00FF0000;
        }
        cpuProgressBar.getProgressDrawable().setColorFilter(colorC, PorterDuff.Mode.SRC_IN);
        memoryProgressBar.getProgressDrawable().setColorFilter(colorM, PorterDuff.Mode.SRC_IN);
        storageProgressBar.getProgressDrawable().setColorFilter(colorS, PorterDuff.Mode.SRC_IN);

        cpuTextView = findViewById(R.id.cpuPercentTextView);
        memoryTextView = findViewById(R.id.memoryPercentTextView);
        storageTextView = findViewById(R.id.storagePercentTextView);

        guestNameTextView = findViewById(R.id.guestNameValueTextView);
        guestOsTextView = findViewById(R.id.guestOsValueTextView);
        guestStateTextView = findViewById(R.id.guestStateValueTextView);
        guestVersionTextView = findViewById(R.id.guestVersionValueTextView);

        hardDiskbackingTextView = findViewById(R.id.backingValueTextView);
        hardDiskCapacityTextView = findViewById(R.id.capacityValueTextView);
        hardDiskThinTextView = findViewById(R.id.thinProvisionedValueTextView);
        hardDiskModeTextView = findViewById(R.id.modeValueTextView);

        networkAdapterTextView = findViewById(R.id.adapterTypeValueTextView);
        networkConnectedTextView = findViewById(R.id.connectedValueTextView);
        networkAdressTypeTextView = findViewById(R.id.adressTypeValueTextView);
        networkMacAdressTextView = findViewById(R.id.macAddressValueTextView);

        videoSizeTextView = findViewById(R.id.videoMemoryValueTextView);
        videoDisplaysTextView = findViewById(R.id.nbrDisplaysValueTextView);
        videoRenderTextView = findViewById(R.id.rendererValueTextView);




        cpuTextView.setText(value.get(18)+"/"+value.get(0)+" vGhz");
        memoryTextView.setText(value.get(19)+"/"+value.get(1)+" GB");
        storageTextView.setText(value.get(20)+"/"+value.get(2)+" GB");

        guestNameTextView.setText(value.get(3));
        guestOsTextView.setText(value.get(4));
        guestStateTextView.setText(value.get(5));
        guestVersionTextView.setText(value.get(6));

        hardDiskbackingTextView.setText(value.get(7));
        hardDiskCapacityTextView.setText(value.get(8));
        hardDiskThinTextView.setText(value.get(9));
        hardDiskModeTextView.setText(value.get(10));

        networkAdapterTextView.setText(value.get(11));
        networkConnectedTextView.setText(value.get(12));
        networkAdressTypeTextView.setText(value.get(13));
        networkMacAdressTextView.setText(value.get(14));

        videoSizeTextView.setText(value.get(15));
        videoDisplaysTextView.setText(value.get(16));
        videoRenderTextView.setText(value.get(17));

    }
}
