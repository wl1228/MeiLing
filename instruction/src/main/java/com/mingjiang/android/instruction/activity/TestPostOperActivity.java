package com.mingjiang.android.instruction.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.mingjiang.android.instruction.utils.BitmapUtils;
import com.mingjiang.android.instruction.InstructionApp;
import com.mingjiang.android.instruction.R;
import com.mingjiang.android.instruction.adapter.OperStepAdapter;
import com.mingjiang.android.instruction.entity.OperStep;
import com.mingjiang.android.instruction.entity.PostOperInstruction;
import com.mingjiang.android.instruction.entity.ShowStep;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wangzs on 2015/12/25 12:38
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class TestPostOperActivity extends Activity {

    public static final int IMAGE_WIDTH = 360;
    public static final int IMAGE_HEIGHT = 360;
    private String postCode = "";
    private String sessionId = "";

    TextView applyFactoryView;

    TextView postNameView;

    TextView postCodeView;

    TextView productLineView;

    //岗位指导书名称
    TextView directorNameView;

    //上岗安全须知及岗前岗后注意事项
    TextView productionNotesView;
    TextView productionNotesNameView;

    //防护要求
    TextView protectionRequirementView;
    TextView protectionRequirementNameView;

    //工艺要点
    TextView pointNameView;
    TextView pointView;

    //工具/工装
    TextView toolsEquipmentView;
    TextView toolsEquipmentNameView;

    //部件清单
    TextView matarialListNameView;
    TextView matarialListView;

    //操作步骤
    GridView gridStep;
    private OperStepAdapter operStepAdapter;

    private String basePath = Environment.getExternalStorageDirectory() + "/meiling/meiling/gwXZ00010";//本地保存数据
    private File baseDir = null;
    private File postOperJsonDir = null;

    //数据加载进度条
    private ProgressDialog mProgressDialog = null;

    //用于显示操作步骤数据结构
    private List<ShowStep> showStepList = new ArrayList<ShowStep>();

    //退出时间记录
    private long firstTime = 0;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //取消进度框
            mProgressDialog.cancel();
            mProgressDialog.dismiss();
            mProgressDialog.hide();
            mProgressDialog = null;

            operStepAdapter = new OperStepAdapter(TestPostOperActivity.this,showStepList);
            gridStep.setAdapter(operStepAdapter);
            operStepAdapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_oper_instruction);
        initView();
        showProgress();
        dealService();

    }

    private void dealService(){
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                InstructionApp.currentPostOperInstruction = readLocalFile();
                showView();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
    private PostOperInstruction readLocalFile(){
        String jsonPath = Environment.getExternalStorageDirectory() + "/meiling/gwXZ00010/gwXZ00010.json";
        PostOperInstruction operInstruction = null;
        String fileMsg = readFile(jsonPath);
        try {
            JSONObject jsonObject = new JSONObject(fileMsg);
            operInstruction = new PostOperInstruction(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return operInstruction;
    }

    //读文件
    public static String readFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
    }
    private void showView(){
        dealDataToView();
    }
    private void dealDataToView() {
        PostOperInstruction postOperInstruction = InstructionApp.currentPostOperInstruction;
        //岗位指导书名称
        String directorName = postOperInstruction.name;
        if("false".equals(directorName) || "".equals(directorName)){
            directorNameView.setVisibility(View.GONE);
        }
        else{
            directorNameView.setText(directorName);
        }

        //岗位名称
        String postName = postOperInstruction.operation_id;
        if("false".equals(postName) || "".equals(postName)){
            postNameView.setVisibility(View.GONE);
        }
        else{
            postNameView.setText(postName);//岗位名称
        }

        //岗位编号
        postCodeView.setText(postCode);

        //适用工厂
        String applyFactory = postOperInstruction.apply_factory;
        if("false".equals(applyFactory) || "".equals(applyFactory)){
            applyFactoryView.setVisibility(View.GONE);
        }
        else{
            applyFactoryView.setText(applyFactory);
        }

        //生产线
        String productLine = postOperInstruction.production_line;
        if("false".equals(productLine) || "".equals(productLine)){
            productLineView.setVisibility(View.GONE);
        }
        else {
            productLineView.setText(productLine);
        }

        //上岗安全须知及班后注意事项
        String productNote = postOperInstruction.production_notes;
        if("false".equals(productNote) || "".equals(productNote)){
            productionNotesView.setVisibility(View.GONE);
            productionNotesNameView.setVisibility(View.GONE);
        }
        else{
            productionNotesView.setText(productNote);
        }

        //防护要求
        String protectRequirement = postOperInstruction.protection_requirement;
        if("false".equals(protectRequirement) || "".equals(protectRequirement)){
            protectionRequirementView.setVisibility(View.GONE);
            protectionRequirementNameView.setVisibility(View.GONE);
        }
        else{
            protectionRequirementView.setText(protectRequirement);
        }

        //工艺要点
        String point = postOperInstruction.point;
        if("false".equals(point) || "".equals(point)){
            pointView.setVisibility(View.GONE);
            pointNameView.setVisibility(View.GONE);
        }
        else{
            pointView.setText(point);
        }

        //工具/工装
        String toolEquipment = postOperInstruction.tools_equipment;
        if("false".equals(toolEquipment) || "".equals(toolEquipment)){
            toolsEquipmentView.setVisibility(View.GONE);
            toolsEquipmentNameView.setVisibility(View.GONE);
        }
        else{
            toolsEquipmentView.setText(toolEquipment);
        }

        //部件清单
        String matrialList = postOperInstruction.matarial_list;
        if("".equals(matrialList) || "false".equals(matrialList)){
            matarialListView.setVisibility(View.GONE);
            matarialListNameView.setVisibility(View.GONE);
        }
        else{
            matarialListView.setText(postOperInstruction.matarial_list);
        }

        //操作步骤
        dealOperStep(postOperInstruction.steps);
    }

    //操作步骤
    private void dealOperStep(List<OperStep> operStepList){
        //读取Bitmap数据
        showStepList.clear();
        for(OperStep operStep : operStepList){
            Bitmap bitmap = null;
            if(operStep.pic_path.file_path == null || "".equals(operStep.pic_path.file_path)) {
                String path = Environment.getExternalStorageDirectory() + "/meiling/gwXZ00010/" + operStep.name + ".jpg";
                bitmap = BitmapFactory.decodeByteArray(BitmapUtils.decodeBitmap(path), 0, BitmapUtils.decodeBitmap(path).length);
                //bitmap = BitmapFactory.decodeFile(this.postOperJsonDir.getAbsolutePath() + "/" + operStep.name + ".jpg");
            }
            else{
                String path = Environment.getExternalStorageDirectory() + "/meiling/gwXZ00010/" + operStep.name + ".jpg";
                bitmap = BitmapFactory.decodeByteArray(BitmapUtils.decodeBitmap(path), 0, BitmapUtils.decodeBitmap(path).length);
                //bitmap =  BitmapFactory.decodeFile(operStep.pic_path.file_path);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
            ShowStep showStep = new ShowStep();
            showStep.bitmap = bitmap;
            showStep.name = operStep.name;
            showStep.description = operStep.description;
            showStepList.add(showStep);
        }


    }

    private void initView(){
        applyFactoryView = (TextView)findViewById(R.id.apply_factory);
        postNameView = (TextView)findViewById(R.id.postName);
        postCodeView = (TextView)findViewById(R.id.postCode);
        productLineView = (TextView)findViewById(R.id.productLine);
        directorNameView = (TextView)findViewById(R.id.directorNameView);
        productionNotesView = (TextView)findViewById(R.id.productionNotesView);
        productionNotesNameView = (TextView)findViewById(R.id.productionNotesNameView);
        protectionRequirementView = (TextView)findViewById(R.id.protectionRequirementView);
        protectionRequirementNameView = (TextView)findViewById(R.id.protectionRequirementNameView);
        pointNameView = (TextView)findViewById(R.id.pointNameView);
        pointView = (TextView)findViewById(R.id.pointView);
        toolsEquipmentView = (TextView)findViewById(R.id.toolsEquipmentView);
        toolsEquipmentNameView = (TextView)findViewById(R.id.toolsEquipmentNameView);
        matarialListNameView = (TextView)findViewById(R.id.matarialListNameView);
        matarialListView = (TextView)findViewById(R.id.matarialListView);
        gridStep = (GridView)findViewById(R.id.gridStep);
    }

    /**
     * 显示进度框。
     */
    private void showProgress()
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.tip_title));
        mProgressDialog.setMessage(getResources().getString(R.string.load_data_tip));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
