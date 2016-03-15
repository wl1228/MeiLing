package app.android.mingjiang.com.qrcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import app.android.mingjiang.com.qrcode.utils.QRUtil;
import app.android.mingjiang.com.qrcode.R;

public class TestQRActivity extends Activity {

    private Button button;
    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_qr);

        button = (Button) findViewById(R.id.create_img_button);
        imageView = (ImageView) findViewById(R.id.image_view);
        editText = (EditText) findViewById(R.id.url);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                imageView.setImageBitmap(QRUtil.createQRImage(url));
            }
        });
    }
}
