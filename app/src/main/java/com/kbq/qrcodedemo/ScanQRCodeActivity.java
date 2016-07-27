package com.kbq.qrcodedemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.chzz.qrcode.core.QRCodeView;
import org.chzz.qrcode.zxing.QRCodeDecoder;

import java.io.File;

/**
 * Created by KBQ on 16/7/27.
 */
public class ScanQRCodeActivity extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener{
    private static final String TAG = "ScanQRCodeActivity";
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private QRCodeView qrCodeView;
    private Button btnStartSpot,btnStopSpot,btnShowRect,btnHiddenRect
            ,btnStartSpotShowrect,btnStopSpotHiddenrect
    ,btnStartPreview,btnStopPreview,btnScanBarcode,btnScanQrcode
    ,btnOpenFlashlight,btnCloseFlashlight,btnChooseQrcdeFromGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanqrcode);

        //扫码预览框
        qrCodeView = (QRCodeView) findViewById(R.id.zxingview);
        qrCodeView.setDelegate(this);
        //按钮们
        btnStartSpot = (Button) findViewById(R.id.btn_start_spot);
        btnStopSpot = (Button) findViewById(R.id.btn_stop_spot);
        btnShowRect = (Button) findViewById(R.id.btn_show_rect);
        btnHiddenRect = (Button) findViewById(R.id.btn_hidden_rect);
        btnStartSpotShowrect = (Button) findViewById(R.id.btn_start_spot_showrect);
        btnStopSpotHiddenrect = (Button) findViewById(R.id.btn_stop_spot_hiddenrect);
        btnStartPreview = (Button) findViewById(R.id.btn_start_preview);
        btnStopPreview = (Button) findViewById(R.id.btn_stop_preview);
        btnScanBarcode = (Button) findViewById(R.id.btn_scan_barcode);
        btnScanQrcode = (Button) findViewById(R.id.btn_scan_qrcode);
        btnOpenFlashlight = (Button) findViewById(R.id.btn_open_flashlight);
        btnCloseFlashlight = (Button) findViewById(R.id.btn_close_flashlight);
        btnChooseQrcdeFromGallery = (Button) findViewById(R.id.btn_choose_qrcde_from_gallery);

        btnStartSpot.setOnClickListener(this);
        btnStopSpot.setOnClickListener(this);
        btnShowRect.setOnClickListener(this);
        btnHiddenRect.setOnClickListener(this);
        btnStartSpotShowrect.setOnClickListener(this);
        btnStopSpotHiddenrect.setOnClickListener(this);
        btnStartPreview.setOnClickListener(this);
        btnStopPreview.setOnClickListener(this);
        btnScanBarcode.setOnClickListener(this);
        btnScanQrcode.setOnClickListener(this);
        btnOpenFlashlight.setOnClickListener(this);
        btnCloseFlashlight.setOnClickListener(this);
        btnChooseQrcdeFromGallery.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        qrCodeView.startCamera();
    }

    @Override
    protected void onStop() {
        qrCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        qrCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:"+result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();//识别成功振动提醒
        qrCodeView.startSpot();//重新开始识别
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机失败");

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_spot:
                qrCodeView.startSpot();
                break;
            case R.id.btn_stop_spot:
                qrCodeView.stopSpot();
                break;
            case R.id.btn_show_rect:
                qrCodeView.showScanRect();
                break;
            case R.id.btn_hidden_rect:
                qrCodeView.hiddenScanRect();
                break;
            case R.id.btn_start_spot_showrect:
                qrCodeView.startSpotAndShowRect();
                break;
            case R.id.btn_stop_spot_hiddenrect:
                qrCodeView.stopSpotAndHiddenRect();
                break;
            case R.id.btn_start_preview:
                qrCodeView.startCamera();
                break;
            case R.id.btn_stop_preview:
                qrCodeView.stopCamera();
                break;
            case R.id.btn_scan_barcode:
                qrCodeView.changeToScanBarcodeStyle();
                break;
            case R.id.btn_scan_qrcode:
                qrCodeView.changeToScanQRCodeStyle();
                break;
            case R.id.btn_open_flashlight:
                qrCodeView.openFlashlight();
                break;
            case R.id.btn_close_flashlight:
                qrCodeView.closeFlashlight();
                break;
            case R.id.btn_choose_qrcde_from_gallery:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
        }
    }

    /**
     * 调用手机振动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 从相册选择照片识别时，选取图片返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        qrCodeView.showScanRect();

        if (requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            String picturePath;
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                picturePath = c.getString(columnIndex);
                c.close();
            } catch (Exception e) {
                picturePath = data.getData().getPath();
            }

            if (new File(picturePath).exists()) {
                QRCodeDecoder.decodeQRCode(BitmapFactory.decodeFile(picturePath), new QRCodeDecoder.Delegate() {
                    @Override
                    public void onDecodeQRCodeSuccess(String result) {
                        Toast.makeText(ScanQRCodeActivity.this, result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDecodeQRCodeFailure() {
                        Toast.makeText(ScanQRCodeActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
