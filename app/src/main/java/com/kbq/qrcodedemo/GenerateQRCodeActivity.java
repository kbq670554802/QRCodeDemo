package com.kbq.qrcodedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.chzz.qrcode.core.CHZZQRCodeUtil;
import org.chzz.qrcode.zxing.QRCodeDecoder;
import org.chzz.qrcode.zxing.QRCodeEncoder;

/**
 * Created by KBQ on 16/7/27.
 * 生成二维码 及识别二维码界面(解析带中文的二维码、带logo的中文二维码、还有识别ISBN都是失败)
 */
public class GenerateQRCodeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "GenerateQRCodeActivity";
    private ImageView ivChinese,ivEnglish,ivChineseLogo,ivEnglishLogo;
    private Button btnChinese,btnEnglish,btnChineseLogo,btnEnglishLogo,btnDecodeIsbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        initView();
        createQRCode();

    }

    private void initView() {
        ivChinese = (ImageView) findViewById(R.id.iv_chinese);
        ivEnglish = (ImageView) findViewById(R.id.iv_english);
        ivChineseLogo = (ImageView) findViewById(R.id.iv_chinese_logo);
        ivEnglishLogo = (ImageView) findViewById(R.id.iv_english_logo);

        btnChinese = (Button) findViewById(R.id.btn_chinese);
        btnEnglish = (Button) findViewById(R.id.btn_english);
        btnChineseLogo = (Button) findViewById(R.id.btn_chinese_logo);
        btnEnglishLogo = (Button) findViewById(R.id.btn_english_logo);
        btnDecodeIsbn = (Button) findViewById(R.id.btn_decode_isbn);
        btnChinese.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);
        btnChineseLogo.setOnClickListener(this);
        btnEnglishLogo.setOnClickListener(this);
        btnDecodeIsbn.setOnClickListener(this);
    }

    private void createQRCode() {
        createChineseQRCode();
        createEnglishQRCode();
        createChineseQRCodeWithLogo();
        createEnglishQRCodeWithLogo();
    }
    @Override
    public void onClick(View view) {
        Bitmap bitmap;
        switch (view.getId()){
            case R.id.btn_chinese:
                ivChinese.setDrawingCacheEnabled(true);
                 bitmap = ivChinese.getDrawingCache();
                decode(bitmap, "解析中文二维码失败");
                break;
            case R.id.btn_english:
                ivEnglish.setDrawingCacheEnabled(true);
                 bitmap = ivEnglish.getDrawingCache();
                decode(bitmap, "解析英文二维码失败");
                break;
            case R.id.btn_chinese_logo:
                ivChineseLogo.setDrawingCacheEnabled(true);
                 bitmap = ivChineseLogo.getDrawingCache();
                decode(bitmap, "解析带logo的中文二维码失败");
                break;
            case R.id.btn_english_logo:
                ivEnglishLogo.setDrawingCacheEnabled(true);
                 bitmap = ivEnglishLogo.getDrawingCache();
                decode(bitmap, "解析带logo的英文二维码失败");
                break;
            case R.id.btn_decode_isbn:
                 bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test_isbn);
                decode(bitmap, "解析ISBN失败");
                break;
        }

    }
    private void createChineseQRCode() {
        QRCodeEncoder.encodeQRCode("二维码", CHZZQRCodeUtil.dp2px(GenerateQRCodeActivity.this, 200), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivChinese.setImageBitmap(bitmap);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateQRCodeActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createEnglishQRCode() {
        QRCodeEncoder.encodeQRCode("chzz", CHZZQRCodeUtil.dp2px(GenerateQRCodeActivity.this, 150), Color.parseColor("#ff0000"), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivEnglish.setImageBitmap(bitmap);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateQRCodeActivity.this, "生成英文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createChineseQRCodeWithLogo() {
        QRCodeEncoder.encodeQRCode("二维码", CHZZQRCodeUtil.dp2px(GenerateQRCodeActivity.this, 150), Color.parseColor("#795dbf"), BitmapFactory.decodeResource(GenerateQRCodeActivity.this.getResources(), R.mipmap.logo), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivChineseLogo.setImageBitmap(bitmap);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateQRCodeActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createEnglishQRCodeWithLogo() {
        QRCodeEncoder.encodeQRCode("chzz", CHZZQRCodeUtil.dp2px(GenerateQRCodeActivity.this, 150), Color.parseColor("#0000ff"), BitmapFactory.decodeResource(GenerateQRCodeActivity.this.getResources(), R.mipmap.logo), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivEnglishLogo.setImageBitmap(bitmap);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateQRCodeActivity.this, "生成带logo的英文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void decode(Bitmap bitmap, final String errorTip) {
        QRCodeDecoder.decodeQRCode(bitmap, new QRCodeDecoder.Delegate() {
            @Override
            public void onDecodeQRCodeSuccess(String result) {
                Toast.makeText(GenerateQRCodeActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDecodeQRCodeFailure() {
                Toast.makeText(GenerateQRCodeActivity.this, errorTip, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
