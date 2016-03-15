package app.android.mingjiang.com.logshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用于显示程序崩溃出错信息。
 */
public class ShowLogActivity extends Activity {

    TextView textView = null;
    Button restartBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
        textView = (TextView)findViewById(R.id.showError);
        restartBtn = (Button)findViewById(R.id.restartApp);

        Intent intent = getIntent();
        String errorMsg = intent.getStringExtra("error");
        final String appPacakge = intent.getStringExtra("package");
        textView.setText(errorMsg);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOtherActivity(ShowLogActivity.this,appPacakge);
            }
        });
    }

    //将原出错APP重新启动一下。
    private void startOtherActivity(Context context,String pacakge) {
        Intent intent = context.getPackageManager().
                getLaunchIntentForPackage(pacakge);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(),
                    "您没有安装显示出错信息应用，请本地查看出错信息,联系开发人员!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
