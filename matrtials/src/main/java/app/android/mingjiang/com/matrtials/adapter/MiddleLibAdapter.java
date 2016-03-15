package app.android.mingjiang.com.matrtials.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.entity.MidMaterialValue;

/**
 * 备注：中间库数据显示。
 * 作者：wangzs on 2016/2/19 17:31
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MiddleLibAdapter extends BaseAdapter {

    private Context context = null;
    private List<MidMaterialValue> midMaterialValueList = new ArrayList<MidMaterialValue>();

    public MiddleLibAdapter(Context context,List<MidMaterialValue> midMaterialValueList){
        this.context = context;
        this.midMaterialValueList = midMaterialValueList;
    }

    @Override
    public int getCount() {
        return midMaterialValueList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.midMaterialValueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MidMaterialValue midMaterialValue = midMaterialValueList.get(position);
        ViewHolder viewHolder=null;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_middle_item, null);
            viewHolder = new ViewHolder();
            viewHolder.material_id = (TextView)convertView.findViewById(R.id.material_id);
            viewHolder.material_name = (TextView)convertView.findViewById(R.id.material_name);
            viewHolder.seat_id = (TextView)convertView.findViewById(R.id.seat_id);
            viewHolder.safety = (TextView)convertView.findViewById(R.id.safety);
            viewHolder.safety_stock = (TextView)convertView.findViewById(R.id.safety_stock);
            viewHolder.number = (TextView)convertView.findViewById(R.id.number);
            viewHolder.shortage = (TextView)convertView.findViewById(R.id.shortage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.material_id.setText(midMaterialValue.material_id);
        viewHolder.material_name.setText(midMaterialValue.material_name);
        viewHolder.seat_id.setText(midMaterialValue.seat_id);
        viewHolder.safety.setText(midMaterialValue.safety?"是":"否");
        viewHolder.safety_stock.setText(midMaterialValue.safety_stock);
        viewHolder.number.setText(midMaterialValue.number);
        viewHolder.shortage.setText(midMaterialValue.shortage);

        return convertView;
    }

     class ViewHolder{
        TextView material_id,material_name,seat_id,safety,safety_stock,number,shortage;
    }
}
