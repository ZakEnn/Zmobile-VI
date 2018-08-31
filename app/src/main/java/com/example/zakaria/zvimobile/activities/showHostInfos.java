package com.example.zakaria.zvimobile.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zakaria.zvimobile.R;

import java.util.ArrayList;
import java.util.Locale;

public class showHostInfos extends AppCompatActivity {

    ArrayList<String> value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_host_infos);

        Bundle extras = getIntent().getExtras();

        value = new ArrayList<>() ;
        value = extras.getStringArrayList("hostData");

        TextView libreSpaceCpu,usedCpu,capacityCpu,libreSpaceMemory,fullName,
                usedMemory,capacityMemory,libreSpaceStorage,usedStorage,capacityStorage,date,
                cpuPercent,memoryPercent,storagePercent,manifacturer,model,cpu,memory,nbrVms,
                nbrDatastores,hostName,ipAdresses,dnsServer,defaultGateway,nbrPortGroups,nbrVswitches;


      ProgressBar hostStorageProgressBar,hostCpuProgressBar,hostMemoryProgressBar;

       int storageProgressStatus,memoryProgressStatus,cpuProgressStatus;

       hostStorageProgressBar = (ProgressBar) findViewById(R.id.hostStorageProgressBar);
       hostCpuProgressBar = (ProgressBar) findViewById(R.id.hostCpuProgressBar);
       hostMemoryProgressBar = (ProgressBar) findViewById(R.id.hostMemoryProgressBar);

       storageProgressStatus = (int)((Float.parseFloat(value.get(25))- Float.parseFloat(value.get(26)))*100 / Float.parseFloat(value.get(25)));
       memoryProgressStatus = (int)((Float.parseFloat(value.get(3)))*100 / Float.parseFloat(value.get(2)));
       cpuProgressStatus = (int)((Float.parseFloat(value.get(1)))*100 / Float.parseFloat(value.get(0)));

       hostStorageProgressBar.getBackground().setColorFilter(0xfff, PorterDuff.Mode.SRC_IN);

        cpuPercent = (TextView) findViewById(R.id.cpuPercentTextView);
        cpuPercent.setText(cpuProgressStatus+"%");
        memoryPercent = (TextView) findViewById(R.id.memoryPercentTextView);
        memoryPercent.setText(memoryProgressStatus+"%");
        storagePercent = (TextView) findViewById(R.id.storagePercentTextView);
        storagePercent.setText(storageProgressStatus+"%");

        hostStorageProgressBar.setProgress(storageProgressStatus);
        hostCpuProgressBar.setProgress(cpuProgressStatus);
        hostMemoryProgressBar.setProgress(memoryProgressStatus);

        fullName = findViewById(R.id.fullNameValueTextView);
        libreSpaceCpu = findViewById(R.id.libreSpaceCTextView);
        usedCpu = findViewById(R.id.usedCTextView);
        capacityCpu = findViewById(R.id.capacityCTextView);
        libreSpaceMemory = findViewById(R.id.libreSpaceMTextView);
        usedMemory = findViewById(R.id.usedMTextView);
        capacityMemory = findViewById(R.id.capacityMTextView);
        libreSpaceStorage = findViewById(R.id.libreSpaceTextView);
        usedStorage = findViewById(R.id.usedSpaceTextView);
        capacityStorage = findViewById(R.id.capacitySTextView);
        manifacturer = findViewById(R.id.manifacturerValueTextView);
        nbrVms = findViewById(R.id.vmsValueTextView);
        model = findViewById(R.id.modelValueTextView);
        cpu = findViewById(R.id.cpuValueTextView);
        memory = findViewById(R.id.memoryValueTextView);
        nbrDatastores = findViewById(R.id.nbrDatastoresValueTextView);
        date = findViewById(R.id.dateValueTextView);
        hostName = findViewById(R.id.hostNameValueTextView);
        dnsServer = findViewById(R.id.dnsServerValueTextView);
        defaultGateway = findViewById(R.id.gatewayValueTextView);
        nbrPortGroups= findViewById(R.id.portGroupsValueTextView);
        nbrVswitches = findViewById(R.id.virtualSwitchesValueTextView);
        ipAdresses = findViewById(R.id.ipAddrsValueTextView);

        libreSpaceCpu.setText("Free : "+Long.toString(Long.parseLong(value.get(0))- Long.parseLong(value.get(1)))+ "Mhz");
        usedCpu.setText("Used : "+value.get(1) + "Mhz");
        capacityCpu.setText("Capacity :"+ value.get(0) + "Mhz");

        libreSpaceMemory.setText("Free : "+String.format(Locale.US, "%.2f",(Float.parseFloat(value.get(2))- Float.parseFloat(value.get(3)))) + "GB");
        usedMemory.setText("Used : "+value.get(3)+ "GB");
        capacityMemory.setText("Capacity : "+value.get(2)+ "GB");

        usedStorage.setText("Used : "+String.format(Locale.US, "%.2f",(Float.parseFloat(value.get(25))- Float.parseFloat(value.get(26))) )+ "GB");
        libreSpaceStorage.setText("Free : "+value.get(26)+ "GB");
        capacityStorage.setText("Capacity : "+value.get(25)+ "GB");

        manifacturer.setText(value.get(4));
        model.setText(value.get(5));
        cpu.setText(value.get(6));
        memory.setText(value.get(7) + " GB");
        nbrDatastores.setText(value.get(8) + " : " + value.get(23));
        nbrVms.setText(value.get(9) + " : " + value.get(22));

        date.setText(value.get(10));
        hostName.setText(value.get(11));
        ipAdresses.setText(value.get(12) + ": " + value.get(14)  +"\n"+ value.get(12)+ ": " +value.get(15));
        dnsServer.setText(value.get(16));
        defaultGateway.setText(value.get(17));
        nbrPortGroups.setText(value.get(18) + " : " + value.get(20));
        nbrVswitches.setText(value.get(19) + " : " + value.get(21));
        fullName.setText(value.get(24));

    }
}
