package app.android.mingjiang.com.qrcode.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mingjiang.android.base.event.ComEvent;

import java.util.Date;

import app.android.mingjiang.com.qrcode.R;
import app.android.mingjiang.com.qrcode.adapter.ItemAdapter;
import app.android.mingjiang.com.qrcode.entity.Item;
import app.android.mingjiang.com.qrcode.service.ComService;
import app.android.mingjiang.com.qrcode.utils.FinishCode2QRUtil;
import de.greenrobot.event.EventBus;

/**
 * Created by wangzs on 2015/12/22.
 */
public class QRCodeActivity extends Activity {
    private ListView listView;
    ItemAdapter itemAdapter;
    public static Item nowItem = null;
    Button stopPrint;
    EditText byteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
//      开启服务
        Log.e("QRCodeActivity", "startService");
        startService(new Intent(this, ComService.class));

        listView = (ListView) findViewById(R.id.item_list);
        itemAdapter = new ItemAdapter(QRCodeActivity.this, R.layout.listview_item);
        listView.setAdapter(itemAdapter);
        EventBus.getDefault().register(this);


        Button print = (Button) findViewById(R.id.print);
        stopPrint = (Button) findViewById(R.id.stop_print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowItem != null)
                    EventBus.getDefault().post(new ComEvent(nowItem.getQRcode(), ComEvent.ACTION_PRINT));
            }
        });
        stopPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComService.PRINT = !ComService.PRINT;
                if (ComService.PRINT) {
                    stopPrint.setText("禁止打印");
                } else {
                    stopPrint.setText("允许打印");
                }
            }
        });
    }

    public void onEventMainThread(ComEvent event) {
        if (event.getActionType() == ComEvent.ACTION_GET_CODE) {
            String code = event.getMessage();
            nowItem = new Item(code, FinishCode2QRUtil.toQECodeAuto31(code), getString(R.string.printed), new Date());
            Log.e("_GET_CODE - nowItem", nowItem.toString());
            addItem(nowItem);

        } else if (event.getActionType() == ComEvent.ACTION_WAIT) {
            Log.e("ACTION_WAIT - nowItem", nowItem.toString());
            Toast.makeText(this, getString(R.string.wait), Toast.LENGTH_LONG).show();

        }

    }

    public void addItem(Item item) {
        itemAdapter.insert(item, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this, ComService.class));
    }
}
