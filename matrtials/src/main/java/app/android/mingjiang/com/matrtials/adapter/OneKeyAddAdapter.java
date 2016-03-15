package app.android.mingjiang.com.matrtials.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.android.mingjiang.com.matrtials.MaterialApp;
import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.entity.MaterialOneKeyAddResult;
import app.android.mingjiang.com.matrtials.fragment.AddLibraryFragment;

/**
 * 备注：
 * 作者：wangzs on 2016/2/29 18:48
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class OneKeyAddAdapter extends BaseAdapter {

    private List<MaterialOneKeyAddResult> list = new ArrayList<MaterialOneKeyAddResult>();
    private Context context = null;
    private AddLibraryFragment addLibraryFragment = null;
    public OneKeyAddAdapter(List<MaterialOneKeyAddResult> list,Context context,AddLibraryFragment addLibraryFragment){

        this.list = list;
        this.context = context;
        this.addLibraryFragment = addLibraryFragment;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MaterialOneKeyAddResult materialOneKeyAddResult = this.list.get(position);
        ViewHolder viewHolder=null;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_onekeyadd_item, null);
            viewHolder = new ViewHolder();
            viewHolder.material_id = (TextView)convertView.findViewById(R.id.material_code);
            viewHolder.material_name = (TextView)convertView.findViewById(R.id.material_name);
            viewHolder.material_number = (TextView)convertView.findViewById(R.id.material_number);
            viewHolder.seat_id = (TextView)convertView.findViewById(R.id.seat_id);
            viewHolder.oper = (Button)convertView.findViewById(R.id.submit_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.material_id.setText(materialOneKeyAddResult.code);
        viewHolder.material_name.setText(materialOneKeyAddResult.name);
        viewHolder.material_number.setText(materialOneKeyAddResult.number);
        viewHolder.seat_id.setText(materialOneKeyAddResult.seat_id);
        viewHolder.oper.setOnClickListener(new SubmitListener(materialOneKeyAddResult));
        return convertView;
    }

    class ViewHolder{
        TextView material_id,material_name,material_number,seat_id;
        Button oper;
    }

    class SubmitListener implements View.OnClickListener
    {
        private MaterialOneKeyAddResult materialOneKeyAddResult;

        public SubmitListener(MaterialOneKeyAddResult materialOneKeyAddResult){
            this.materialOneKeyAddResult = materialOneKeyAddResult;
        }

        @Override
        public void onClick(View v) {
            addLibraryFragment.dealSubmit(materialOneKeyAddResult);
        }
    }


}
