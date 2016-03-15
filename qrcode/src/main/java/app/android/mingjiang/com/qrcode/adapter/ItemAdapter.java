package app.android.mingjiang.com.qrcode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import app.android.mingjiang.com.qrcode.R;
import app.android.mingjiang.com.qrcode.entity.Item;

/**
 * Created by SunYi on 2015/12/14/0014.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    public static final int MAX_ITEM = 20;
    private int resourceId;
    SimpleDateFormat setText = new SimpleDateFormat();
    private Context context;
    public ItemAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (getCount() > MAX_ITEM){
            remove(getItem(getCount()-1));
        }
        View view;
        if (convertView == null){
            view=LayoutInflater.from(getContext()).inflate(resourceId, null);
        }
        else {
            view = convertView;
        }
        TextView finishCode = (TextView) view.findViewById(R.id.finish_code);
        TextView QRcode = (TextView) view.findViewById(R.id.QR_code);
        TextView status = (TextView) view.findViewById(R.id.status);
        TextView time = (TextView) view.findViewById(R.id.time);
        if (position == 0){
            finishCode.setBackgroundResource(R.drawable.item_line_new);
            QRcode.setBackgroundResource(R.drawable.item_line_new);
            status.setBackgroundResource(R.drawable.item_line_new);
            time.setBackgroundResource(R.drawable.item_line_new);
        }
        else if (position%2 != 0){
            finishCode.setBackgroundResource(R.drawable.item_line1);
            QRcode.setBackgroundResource(R.drawable.item_line1);
            status.setBackgroundResource(R.drawable.item_line1);
            time.setBackgroundResource(R.drawable.item_line1);
        }
        else {
            finishCode.setBackgroundResource(R.drawable.item_line2);
            QRcode.setBackgroundResource(R.drawable.item_line2);
            status.setBackgroundResource(R.drawable.item_line2);
            time.setBackgroundResource(R.drawable.item_line2);
        }
        finishCode.setText(item.getFinsishCode());
        QRcode.setText(item.getQRcode());
        status.setText(item.getStatus());
        time.setText( setText.format(item.getTime()));
        return view;
    }
}
