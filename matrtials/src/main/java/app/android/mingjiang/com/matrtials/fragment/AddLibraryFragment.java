package app.android.mingjiang.com.matrtials.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.BootstrapEditTextApi;
import com.mingjiang.android.base.util.ComServiceUtils;
import com.mingjiang.android.base.util.SuperToastApi;

import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;
import app.android.mingjiang.com.matrtials.MaterialApp;
import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.adapter.OneKeyAddAdapter;
import app.android.mingjiang.com.matrtials.entity.MaterialOneKeyAddResult;
import app.android.mingjiang.com.matrtials.entity.RetValue;
import app.android.mingjiang.com.matrtials.entity.SessionObj;
import app.android.mingjiang.com.matrtials.utils.NumberUtils;
import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * 备注：一键入库操作界面。
 * 作者：wangzs on 2016/2/23 09:11
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AddLibraryFragment extends Fragment {

    private View view = null;
    private Context context = null;
    private ListView materialList = null;
    private BootstrapEditText outCodeEdit = null;
    private OneKeyAddAdapter addAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_library, null);
        context=getActivity();
        initView();
        startService();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService();
    }

    private void initView(){

        outCodeEdit = (BootstrapEditText)view.findViewById(R.id.out_code);
        materialList = (ListView)view.findViewById(R.id.material_list);
        BootstrapEditTextApi.initConfig(outCodeEdit,1.5f);
    }

    private void startService(){
        ComServiceUtils.startService(context, ComEvent.ACTION_ADDLIB_SCAN_CODE);
    }

    private void stopService(){
        ComServiceUtils.stopService(context);
    }

    public void onEventMainThread(ComEvent event) {

        if(ComEvent.ACTION_ADDLIB_TIP == event.getActionType()){//提示操作
            String message = event.getMessage();
            SuperToastApi.showInitSuperToast(context,message);
        }else if(ComEvent.ACTION_ADDLIB_SCAN_CODE == event.getActionType()){//根据入库单获取物料信息
            String code = event.getMessage();
            outCodeEdit.setText(code);
            queryMaterial(code);
        }else if(ComEvent.ACTION_ADDLIB_GET_MATERIALS == event.getActionType()){//获取物料信息，刷新页面
            List<MaterialOneKeyAddResult> lists = (List<MaterialOneKeyAddResult>)event.getObjectMsg();
            addAdapter = new OneKeyAddAdapter(lists,context,this);
            materialList.setAdapter(addAdapter);
            addAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 执行查询操作。
     * @param code
     */
    private void queryMaterial(String code){
        MaterialApp.getApp().getNetService(context).queryMaterial(code).subscribe(new Action1<List<MaterialOneKeyAddResult>>() {
            @Override
            public void call(List<MaterialOneKeyAddResult> materialOneKeyAddResults) {
                EventBus.getDefault().post(new ComEvent(materialOneKeyAddResults,ComEvent.ACTION_ADDLIB_GET_MATERIALS));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new ComEvent("获取物料信息出错！",ComEvent.ACTION_ADDLIB_TIP));
            }
        });
    }

    /**
     * 提交入库操作。
     * @param materialOneKeyAddResult
     */
    public void dealSubmit(final MaterialOneKeyAddResult materialOneKeyAddResult){
        MaterialApp.getApp().getNetService(context).submitMaterial(materialOneKeyAddResult.code,materialOneKeyAddResult.number).subscribe(new Action1<String>() {
            @Override
            public void call(String result) {
                if(result == null || "".equals(result)){

                    String name = materialOneKeyAddResult.name + ":一键入库操作失败！";
                    EventBus.getDefault().post(new ComEvent(name, ComEvent.ACTION_ADDLIB_TIP));

                }else if(result.equalsIgnoreCase("success") || result.equalsIgnoreCase("default")){
                    String name = materialOneKeyAddResult.name + ":一键入库成功！";
                    EventBus.getDefault().post(new ComEvent(name,ComEvent.ACTION_ADDLIB_TIP));
                }else{
                    String name = materialOneKeyAddResult.name + ":一键入库操作失败！";
                    EventBus.getDefault().post(new ComEvent(name, ComEvent.ACTION_ADDLIB_TIP));
                }


            }
        }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    EventBus.getDefault().post(new ComEvent("系统异常：一键入库操作失败！", ComEvent.ACTION_ADDLIB_TIP));
                }
            });
    }
}
