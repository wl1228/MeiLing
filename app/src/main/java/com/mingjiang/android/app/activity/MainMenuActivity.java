package com.mingjiang.android.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mingjiang.android.app.R;
import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.util.CustomerDialogAPi;
import com.mingjiang.android.equipmonitor.activity.EquipMonitorMainActivity;
import com.mingjiang.kouzeping.spectaculars.SpectacularsActivity;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;
import com.mingjiang.android.scan.activity.PostScanActivity;

import org.mj.com.app.activity.MainActivity;

import app.android.mingjiang.com.herilyalertdialog.HerilyAlertDialog;
import app.android.mingjiang.com.matrtials.activity.MaterialLoginActivity;
import app.android.mingjiang.com.qrcode.activity.QRCodeActivity;
import meiling.mingjiang.line_storage.LineStorageActivity;

/**
 * 目前还不确定业务是不是都要进行岗位扫描和人员扫描，服务端业务对各个功能点出来都一样，所以暂时不统一处理。
 * 如果将来确定后再统一处理。
 */
public class MainMenuActivity extends Activity implements CircleLayout.OnItemSelectedListener,
        CircleLayout.OnItemClickListener, CircleLayout.OnRotationFinishedListener, CircleLayout.OnCenterClickListener {
    TextView selectedTextView;
    CircleLayout circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initView();

        circleMenu.setOnItemSelectedListener(this);
        circleMenu.setOnItemClickListener(this);
        circleMenu.setOnRotationFinishedListener(this);
        circleMenu.setOnCenterClickListener(this);

        selectedTextView.setText(((CircleImageView) circleMenu
                .getSelectedItem()).getName());


    }

    private void initView(){
        selectedTextView = (TextView)findViewById(R.id.selected_textView);
        circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);
    }

    /**
     * 点击事件。
     * @param view
     * @param name
     */
    @Override
    public void onItemClick(View view, String name) {
        selectedTextView.setText(name);
        //执行页面跳转
        getForwardActivityName(name);
    }

    /**
     * 点击中间部位事件：用于设置URL。
     */
    @Override
    public void onCenterClick() {
        HerilyAlertDialog.Builder builder = CustomerDialogAPi.createAlertDlgBuilder(this);
        builder.setTitle("URL配置界面");
        builder.setCancelable(true);
        final EditText edittext_Msg = new EditText(this);
        edittext_Msg.setGravity(Gravity.TOP);
        edittext_Msg.setLines(1);
        String baseUrl = Config.getBaseUrl(this);
        edittext_Msg.setText(baseUrl);
        edittext_Msg.setTextColor(this.getResources().getColor(com.mingjiang.android.base.R.color.alertex_dlg_edit_text_color));
        edittext_Msg.setBackgroundDrawable(this.getResources().getDrawable(com.mingjiang.android.base.R.drawable.herily_alertex_dlg_textinput_drawable));
        builder.setView(edittext_Msg);
        builder.setPositiveButton("确定", new ConfigListener(edittext_Msg,true));
        builder.setNegativeButton("取消", new ConfigListener(edittext_Msg,false));
        builder.show();
    }

    /**
     * 设置URL事件处理。
     */
    class ConfigListener implements DialogInterface.OnClickListener{

        private EditText editText = null;
        private boolean isConfig = false;

        ConfigListener(EditText editText,boolean isConfig){
            this.editText = editText;
            this.isConfig = isConfig;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(isConfig){
                String baseUrl = editText.getText().toString();
                Config.setBaseUrl(MainMenuActivity.this,baseUrl);
            }
            dialog.cancel();
        }
    }

    /**
     *选择事件。
     * @param view
     * @param name
     */
    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

    }

    /**
     *旋转操作。
     * @param view
     * @param name
     */
    @Override
    public void onRotationFinished(View view, String name) {
        selectedTextView.setText(name);
    }

    //执行页面跳转
    public void getForwardActivityName(String name){
        Intent intent = null;
        if(getString(R.string.oper_instruction).equals(name)) {                             //岗位指导书
            intent = new Intent(MainMenuActivity.this,PostScanActivity.class);
            intent.putExtra(Constants.FUNCTION_TYPE_NAME, Constants.OPER_INSTRUCTION);
        } else if(getString(R.string.print_qr_code).equals(name)){                          //打印二维码
            intent = new Intent(MainMenuActivity.this,QRCodeActivity.class);
            //Toast.makeText(this,"打印二维码开发中。。。",Toast.LENGTH_SHORT).show();
        } else if(getString(R.string.quality_inspection).equals(name)){                     //质量检测
            intent = new Intent(MainMenuActivity.this,MainActivity.class);
//            Toast.makeText(this,"质量检测开发中。。。",Toast.LENGTH_SHORT).show();
        } else if(getString(R.string.equipment_monitor).equals(name)){                      //设备监控
            intent = new Intent(MainMenuActivity.this,EquipMonitorMainActivity.class);
//            Toast.makeText(this,"设备监控开发中。。。",Toast.LENGTH_SHORT).show();
        } else if(getString(R.string.product_monitor).equals(name)){                        //生产监控
            intent = new Intent(MainMenuActivity.this,SpectacularsActivity.class);
//            Toast.makeText(this,"生产监控开发中。。。",Toast.LENGTH_SHORT).show();
        } else if(getString(R.string.line_storage).equals(name)){                   //点检记录
            intent = new Intent(MainMenuActivity.this,LineStorageActivity.class);
            Toast.makeText(this,"线边仓库开发中。。。",Toast.LENGTH_SHORT).show();
        }else if(getString(R.string.fixe_scan_code_gun_proof).equals(name)){                //固定扫码枪防错
            Toast.makeText(this,"固定扫描枪防错开发中。。。",Toast.LENGTH_LONG).show();
        } else if(getString(R.string.materials_management).equals(name)){                   //物料管理
            intent = new Intent(MainMenuActivity.this,MaterialLoginActivity.class);
            //Toast.makeText(this,"物料管理开发中。。。",Toast.LENGTH_LONG).show();
        }else if(getString(R.string.onoff_line).equals(name)){                              //上下线
            intent = new Intent(MainMenuActivity.this,PostScanActivity.class);
            intent.putExtra(Constants.FUNCTION_TYPE_NAME, Constants.ON_OFF_LINE);
        }
        if(intent != null){
            this.startActivity(intent);
        }
    }
}