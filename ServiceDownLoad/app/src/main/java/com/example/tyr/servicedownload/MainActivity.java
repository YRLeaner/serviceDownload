package com.example.tyr.servicedownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tyr.servicedownload.Serivice.DownloadService;

public class MainActivity extends AppCompatActivity {

    private TextView mTvFileName = null;
    private ProgressBar mPbProgress ;
    private Button mStop;
    private Button mStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvFileName = (TextView)findViewById(R.id.tvFileName);
        mPbProgress = (ProgressBar)findViewById(R.id.nbProgress);
        mStart = (Button)findViewById(R.id.btnStart);
        mStop = (Button)findViewById(R.id.btnStop);

        mPbProgress.setMax(100);

        final Fileinfo fileinfo = new Fileinfo(0,"http://dldir1.qq.com/weixin/android/weixin6316android780.apk","mukewang.apk",0,0);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileinfo",fileinfo);
                startService(intent);
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra("fileinfo", fileinfo);
                startService(intent);
            }
        });

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATA);
        registerReceiver(mReceiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATA.equals(intent.getAction())){
                int finished = intent.getIntExtra("finished",0);
                mPbProgress.setProgress(finished);
            }
        }
    };
}
