package com.kbq.qrcodedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Button btnScanQRCode,btnGenerateQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnScanQRCode = (Button) findViewById(R.id.btn_scan_qrcode);
        btnGenerateQRCode = (Button) findViewById(R.id.btn_generate_qrcode);
        btnScanQRCode.setOnClickListener(this);
        btnGenerateQRCode.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_scan_qrcode:
                startActivity(new Intent(this,ScanQRCodeActivity.class));
                break;
            case R.id.btn_generate_qrcode:
                startActivity(new Intent(this,GenerateQRCodeActivity.class));
                break;
        }
    }
}
