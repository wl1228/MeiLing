package app.android.mingjiang.com.matrtials.fragment;

import android.content.Context;
import android.content.Intent;
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
import app.android.mingjiang.com.matrtials.adapter.AroundLibAdapter;
import app.android.mingjiang.com.matrtials.entity.AroundMaterialQuery;
import app.android.mingjiang.com.matrtials.entity.AroundMaterialResult;
import app.android.mingjiang.com.matrtials.entity.AroundMaterialValue;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：线边库信息。
 * 作者：wangzs on 2016/2/19 17:33
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AroundLibFragment extends Fragment {

    private Context context = null;
    private View view = null;
    private AroundLibAdapter adapter = null;

    private ListView materialList = null;
    private BootstrapEditText materialIdEdit = null;
    private BootstrapEditText materialNameEdit = null;
    private BootstrapEditText areaIdEdit = null;
    private Spinner pageSizeSpinner = null;
    private BootstrapEditText pageNumberEdit = null;
    private BootstrapButton queryBtn = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_around, null);
        context=getActivity();
        //EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().unregister(this);
    }

    private void initView(){
        materialList = (ListView)view.findViewById(R.id.around_material_list);
        materialIdEdit = (BootstrapEditText)view.findViewById(R.id.material_id);
        materialNameEdit = (BootstrapEditText)view.findViewById(R.id.material_name);
        areaIdEdit = (BootstrapEditText)view.findViewById(R.id.area_id);
        pageSizeSpinner = (Spinner)view.findViewById(R.id.page_size);
        pageNumberEdit = (BootstrapEditText)view.findViewById(R.id.page_number);
        queryBtn = (BootstrapButton)view.findViewById(R.id.material_query);
        BootstrapButtonApi.initConfig(queryBtn,2);
        BootstrapEditTextApi.initConfig(materialIdEdit,1.5f);
        BootstrapEditTextApi.initConfig(materialNameEdit,1.5f);
        BootstrapEditTextApi.initConfig(areaIdEdit,1.5f);
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
        AroundMaterialQuery aroundMaterialQuery = new AroundMaterialQuery();
        aroundMaterialQuery.material_id = materialIdEdit.getText().toString();
        aroundMaterialQuery.material_name = materialNameEdit.getText().toString();
        aroundMaterialQuery.area_id = areaIdEdit.getText().toString();
        aroundMaterialQuery.page_size = pageSizeSpinner.getSelectedItem().toString();
        aroundMaterialQuery.page_number = pageNumberEdit.getText().toString();

        dealQueary(aroundMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(AroundMaterialQuery aroundMaterialQuery){
        Map<String,String> retMap = aroundMaterialQuery.setData();
        MaterialApp.getApp().getNetService(context).queryAroundMaterial(retMap).subscribe(new Action1<AroundMaterialResult>() {
            @Override
            public void call(AroundMaterialResult aroundMaterialResult) {
                EventBus.getDefault().post(new ComEvent(aroundMaterialResult, ComEvent.ACTION_AROUND_LIBARY_QUERY));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(AroundMaterialResult aroundMaterialResult){
        List<AroundMaterialValue> list =  aroundMaterialResult.result;
        adapter = new AroundLibAdapter(context,list);
        materialList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onEventMainThread(ComEvent event) {
        if(ComEvent.ACTION_AROUND_LIBARY_QUERY == event.getActionType()){
            AroundMaterialResult aroundMaterialResult = (AroundMaterialResult)event.getObjectMsg();
            dealQueryDataShow(aroundMaterialResult);
            int number = Integer.valueOf(pageNumberEdit.getText().toString());
            pageNumberEdit.setText(number + 1);
        }
    }
}
