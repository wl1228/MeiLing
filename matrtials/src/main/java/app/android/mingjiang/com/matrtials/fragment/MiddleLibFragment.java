package app.android.mingjiang.com.matrtials.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.BootstrapButtonApi;
import com.mingjiang.android.base.util.BootstrapEditTextApi;

import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;
import app.android.mingjiang.com.matrtials.MaterialApp;
import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.adapter.MiddleLibAdapter;
import app.android.mingjiang.com.matrtials.entity.MidMaterialQuery;
import app.android.mingjiang.com.matrtials.entity.MidMaterialResult;
import app.android.mingjiang.com.matrtials.entity.MidMaterialValue;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：中间库信息。
 * 作者：wangzs on 2016/2/19 17:33
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class MiddleLibFragment extends Fragment {

    private Context context = null;
    private View view = null;

    private ListView materialList = null;
    private BootstrapEditText materialIdEdit = null;
    private BootstrapEditText materialNameEdit = null;
    private BootstrapEditText seatIdEdit = null;
    private BootstrapEditText reservoirEdit = null;
    private Spinner pageSizeSpinner = null;
    private BootstrapEditText pageNumberEdit = null;
    private BootstrapButton queryBtn = null;

    private MiddleLibAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_middle, null);
        context=getActivity();
        //EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    private void initView(){

        materialList = (ListView)view.findViewById(R.id.midel_material_list);
        materialIdEdit = (BootstrapEditText)view.findViewById(R.id.material_id);
        materialNameEdit = (BootstrapEditText)view.findViewById(R.id.material_name);
        seatIdEdit = (BootstrapEditText)view.findViewById(R.id.seat_id);
        reservoirEdit = (BootstrapEditText)view.findViewById(R.id.reservoir_area);
        pageSizeSpinner = (Spinner)view.findViewById(R.id.page_size);
        pageNumberEdit = (BootstrapEditText)view.findViewById(R.id.page_number);
        queryBtn = (BootstrapButton)view.findViewById(R.id.material_query);
        BootstrapButtonApi.initConfig(queryBtn,2);
        BootstrapEditTextApi.initConfig(materialIdEdit,1.5f);
        BootstrapEditTextApi.initConfig(materialNameEdit,1.5f);
        BootstrapEditTextApi.initConfig(seatIdEdit,1.5f);
        BootstrapEditTextApi.initConfig(reservoirEdit,1.5f);
        BootstrapEditTextApi.initConfig(pageNumberEdit,1.5f);
        pageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));    //设置颜色
                tv.setTextSize(getResources().getDimension(R.dimen.item_text));    //设置大小
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        queryBtn.setOnClickListener(new QueryClick());
    }

    //执行物料数据查询操作。
    class QueryClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            dealQueryData();
        }
    }

    //组织查询数据。
    private void dealQueryData(){
        MidMaterialQuery midMaterialQuery = new MidMaterialQuery();
        midMaterialQuery.material_id = materialIdEdit.getText().toString();
        midMaterialQuery.material_name = materialNameEdit.getText().toString();
        midMaterialQuery.seat_id = seatIdEdit.getText().toString();
        midMaterialQuery.reservoir_area = reservoirEdit.getText().toString();
        midMaterialQuery.page_size = pageSizeSpinner.getSelectedItem().toString();
        midMaterialQuery.page_number = pageNumberEdit.getText().toString();
       dealQueary(midMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(MidMaterialQuery midMaterialQuery){
        Map<String,String> retMap = midMaterialQuery.setData();
        MaterialApp.getApp().getNetService(context).queryMidMaterial(retMap).subscribe(new Action1<MidMaterialResult>() {
            @Override
            public void call(MidMaterialResult midMaterialResult) {
                EventBus.getDefault().post(new ComEvent(midMaterialResult, ComEvent.ACTION_MIDDLE_LIBARY_QUERY));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(MidMaterialResult midMaterialResult){
       List<MidMaterialValue> list =  midMaterialResult.result;
        adapter = new MiddleLibAdapter(context,list);
        materialList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onEventMainThread(ComEvent event) {
        if(ComEvent.ACTION_MIDDLE_LIBARY_QUERY == event.getActionType()){
            MidMaterialResult midMaterialResult = (MidMaterialResult)event.getObjectMsg();
            dealQueryDataShow(midMaterialResult);
            int number = Integer.valueOf(pageNumberEdit.getText().toString());
            pageNumberEdit.setText(number + 1);
        }
    }
}
