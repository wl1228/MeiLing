package com.mingjiang.android.instruction.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.util.ComServiceUtils;
import com.mingjiang.android.base.util.WheelProgressApi;
import com.mingjiang.android.instruction.entity.FridgePostBean;
import com.mingjiang.android.instruction.utils.BitmapUtils;
import com.mingjiang.android.instruction.InstructionApp;
import com.mingjiang.android.instruction.R;
import com.mingjiang.android.instruction.adapter.OperStepAdapter;
import com.mingjiang.android.instruction.client.HttpClientUtils;
import com.mingjiang.android.instruction.entity.FridgeParamsBean;
import com.mingjiang.android.instruction.entity.FridgeResponse;
import com.mingjiang.android.instruction.entity.OperStep;
import com.mingjiang.android.instruction.entity.PostOperInstruction;
import com.mingjiang.android.instruction.entity.ShowStep;
import com.mingjiang.android.instruction.json.JsonUtil;
import com.mingjiang.android.instruction.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.client.Response;
import rx.functions.Action1;

/**
 * 岗位指导书界面。
 * Created by wangzs on 2015/12/9.
 */
public class PostOperActivity extends Activity{

    public static final int IMAGE_WIDTH = 360;
    public static final int IMAGE_HEIGHT = 360;
    private String postCode = "";
    private String sessionId = "";

    //试用工厂
    TextView applyFactoryView;

    //岗位名称
    TextView postNameView;

    //岗位编号
    TextView postCodeView;

    //生产线
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

    private String basePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/meiling";//本地保存数据
    private File baseDir = null;
    private File postOperJsonDir = null;

    //数据加载进度条
    //private ProgressDialog mProgressDialog = null;

    //用于显示操作步骤数据结构
    private List<ShowStep> showStepList = new ArrayList<ShowStep>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_oper_instruction);
        initView();
        EventBus.getDefault().register(this);
        //开启服务，读取冰箱二维码
        ComServiceUtils.startService(this, ComEvent.ACTION_INSTRUCTION_FRIDGE_SCAN);
        showProgress();
        getDataFromServer();
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
        /*mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.tip_title));
        mProgressDialog.setMessage(getResources().getString(R.string.load_data_tip));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();*/
        WheelProgressApi.showProgress(this,"",false);
    }

    /**
     * 获取服务端数据。
     */
    private void getDataFromServer()
    {
        //获取岗位编码
        postCode =  getIntent().getStringExtra(Constants.SCAN_POST_CODE);
        sessionId = getIntent().getStringExtra(Constants.SESSION_ID);

        //校验本地数据是否存在
        validLocalData();

        //网络校验
        boolean isConnect = true;// NetCheckUtils.isNetworkAvaiable(this);
        if(isConnect){
            //下载服务端json数据
            downloadServerData();
            //downloadServerGson();
        }else{
            //mProgressDialog.dismiss();
            WheelProgressApi.closeProgress(this);
            Toast.makeText(this,"网络连接有误！",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 校验要下载的数据是否在本地存在。
     */
    private void validLocalData(){
        baseDir = new File(basePath);
        boolean flag = false;
        if(!baseDir.exists()){
           flag = baseDir.mkdirs();
            int a = 0;
        }
        postOperJsonDir = new File(baseDir,postCode);//岗位编号文件夹
        if(!postOperJsonDir.exists())
        {
            postOperJsonDir.mkdirs();
        }
        File postOperJsonFile = new File(postOperJsonDir,postCode+".json");//编号json文件
        if(postOperJsonFile.exists())//存在，则将数据加载到内存中去
        {
            InstructionApp.localPostOperInstruction = JsonUtil.readPostOperInstructionJson(postOperJsonFile.getAbsolutePath());
        }
    }

    private void downloadServerGson(){

        new Thread(){
            @Override
            public void run() {
                String postOperInstructionStr = HttpClientUtils.getGsonMsg(postCode, sessionId);
                PostOperInstruction postOperInstruction= new Gson().fromJson(postOperInstructionStr, new TypeToken<PostOperInstruction>() {
                     }.getType());
                InstructionApp.currentPostOperInstruction = postOperInstruction;
                //保存本地数据
                savePostOperInstructionToLocal(postOperInstruction);
                //获取岗位指导书信息
                EventBus.getDefault().post(new ComEvent(postOperInstruction, ComEvent.ACTION_LOAD_SERVER));
            }
        }.start();
    }

    /**
     * 下载服务端端数据。
     */
    private void downloadServerData()
    {
        InstructionApp.getApp().getNetService(this).getOperInstructionByCode(postCode,sessionId).subscribe(new Action1<PostOperInstruction>() {
            @Override
            public void call(PostOperInstruction postOperInstruction) {
                InstructionApp.currentPostOperInstruction = postOperInstruction;
                //保存本地数据
                savePostOperInstructionToLocal(postOperInstruction);
                //获取岗位指导书信息
                EventBus.getDefault().post(new ComEvent(postOperInstruction, ComEvent.ACTION_LOAD_SERVER));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //加载数据失败
                String sendMsg = "岗位编号为：" + postCode + "数据加载失败:请重新登录！";
                Log.d(sendMsg, throwable.getMessage());
                EventBus.getDefault().post(new ComEvent(sendMsg, ComEvent.ACTION_LOAD_SERVER_ERROR));
            }
        });
    }

    /**
     *  多线程下载图片
     */
    private void downloadPhotoByThread(){
        InstructionApp.downloadCount = 0;
        //本地都存在，不用下载
        if(InstructionApp.needDownloadList.size() == 0){
            EventBus.getDefault().post(new ComEvent("", ComEvent.ACTION_LOAD_IMAGE));
            return;
        }
        //需要下载替换
        for(int i=0;i< InstructionApp.needDownloadList.size();i++){
            final OperStep operStep =  InstructionApp.needDownloadList.get(i);
            InstructionApp.getApp().getNetService(this).getPicMessageById(operStep.pic_path.id).subscribe(new Action1<Response>() {
                @Override
                public void call(Response response) {
                    try {
                        InputStream stream = response.getBody().in();
                        byte[] bytes = FileUtils.getBytesFromStream(stream);
                        //强图片信息转码保存本地，并修改文件保存路径
                        String filePath =postOperJsonDir.getAbsolutePath() + "/"+ operStep.name+".jpg";
                        FileUtils.writeFileByByte(bytes,filePath);
                        operStep.pic_path.file_path = filePath;
                        //修改当前PostOperInstruction内部值
                        changeCurrentPostOperInstruction(operStep);
                        EventBus.getDefault().post(new ComEvent("", ComEvent.ACTION_LOAD_IMAGE));
                        InstructionApp.downloadCount ++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                    String sendMsg = operStep.name+"---图片下载失败！";
                    Log.d(sendMsg, throwable.getMessage());
                    EventBus.getDefault().post(new ComEvent(sendMsg, ComEvent.ACTION_LOAD_IMAGE_ERROR));
                }
            });
        }
    }
    /**
     * 将从服务端数据保存到本地。
     * @param postOperInstruction
     */
    private void savePostOperInstructionToLocal(final PostOperInstruction postOperInstruction)
    {
        new Thread(){
            @Override
            public void run() {
                File postOperJsonFile = new File(postOperJsonDir,postCode+".json");
                if(!postOperJsonFile.exists()){//如果存在，则先不保存；不存在，则进行保存
                    JsonUtil.writeJosnPostOperInstructionJson(postOperInstruction,postOperJsonFile.getAbsolutePath());
                }
            }
        }.start();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //停止服务
        ComServiceUtils.stopService(this);
        for(ShowStep showStep : showStepList){
            if(!showStep.bitmap.isRecycled() && showStep.bitmap != null){
                showStep.bitmap.recycle();
                showStep.bitmap = null;
            }
        }
    }

    public void onEventMainThread(ComEvent event) {

        if(ComEvent.ACTION_INSTRUCTION_FRIDGE_SCAN == event.getActionType()){
            String fridgeCode = event.getMessage();
            if(postCode.equals("")){
                postCode = getIntent().getStringExtra(Constants.SCAN_POST_CODE);
                sessionId = getIntent().getStringExtra(Constants.SESSION_ID);
            }
            sendFridgePostBean(fridgeCode,postCode,sessionId);
        }
        //加载数据成功
        if(ComEvent.ACTION_LOAD_SERVER == event.getActionType()){
            comapreDataToDownload();//数据比较，确定要下载的图片。
            downloadPhotoByThread();//多线程下载数据
        }

        //加载数据失败
        if(ComEvent.ACTION_LOAD_SERVER_ERROR == event.getActionType()){
            String message = event.getMessage();
            //mProgressDialog.dismiss();
            WheelProgressApi.closeProgress(this);
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }

        //图片下载失败
        if(ComEvent.ACTION_LOAD_IMAGE_ERROR == event.getActionType()){
            String message = event.getMessage();
            //mProgressDialog.dismiss();
            WheelProgressApi.closeProgress(this);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        //下载一次图片，执行一次，都下载完毕后，执行页面展现
        if(ComEvent.ACTION_LOAD_IMAGE == event.getActionType()) {
            if(InstructionApp.downloadCount == InstructionApp.needDownloadList.size()) {
                //执行json文件保存
                savePostOperInstructionToLocal(InstructionApp.currentPostOperInstruction);
                //执行页面展现
                dealShowView(InstructionApp.currentPostOperInstruction);
            }
        }
        if(ComEvent.ACTION_LOAD_WHELL_HIDE == event.getActionType()){
            PostOperInstruction instruction = (PostOperInstruction)event.getObjectMsg();
            dealDataToView(instruction);
            //取消进度框
            //mProgressDialog.cancel();
            //mProgressDialog.dismiss();
            //mProgressDialog.hide();
            //mProgressDialog = null;
            WheelProgressApi.closeProgress(this);
        }
    }

    /**
     * 数据比较，确定要下载的图片。
     */
    private void comapreDataToDownload(){
        InstructionApp.needDownloadList.clear();
        if(InstructionApp.localPostOperInstruction == null){//本地不存在，则全部下载
            InstructionApp.needDownloadList = InstructionApp.currentPostOperInstruction.steps;
        }
        else{//本地存在，则下载更新过的图片
            for(OperStep currentOperStep : InstructionApp.currentPostOperInstruction.steps){
                boolean find = false;
                for(OperStep localOperStep : InstructionApp.localPostOperInstruction.steps){
                    if(currentOperStep.pic_path.id.equals(localOperStep.pic_path.id) && !currentOperStep.pic_path.__last_update.equals(localOperStep.pic_path.__last_update)){
                        find = true;
                        break;
                    }
                }
                if(find){
                    InstructionApp.needDownloadList.add(currentOperStep);
                }
            }
        }
    }

    private void dealShowView(final PostOperInstruction postOperInstruction){

        new Thread(){
            @Override
            public void run() {
                super.run();
                dealOperStep(postOperInstruction.steps);
                EventBus.getDefault().post(new ComEvent(postOperInstruction,ComEvent.ACTION_LOAD_WHELL_HIDE));
            }
        }.start();
    }

    private void dealDataToView(PostOperInstruction postOperInstruction) {

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
        operStepAdapter = new OperStepAdapter(this,showStepList);
        gridStep.setAdapter(operStepAdapter);
        operStepAdapter.notifyDataSetChanged();

    }

    //操作步骤
    private void dealOperStep(List<OperStep> operStepList){
        //读取Bitmap数据
        showStepList.clear();
        for(OperStep operStep : operStepList){
            Bitmap bitmap = null;
            if(operStep.pic_path.file_path == null || "".equals(operStep.pic_path.file_path)) {
                String path = this.postOperJsonDir.getAbsolutePath() + "/" + operStep.name + ".jpg";
                bitmap = BitmapFactory.decodeByteArray(BitmapUtils.decodeBitmap(path), 0, BitmapUtils.decodeBitmap(path).length);
                //bitmap = BitmapFactory.decodeFile(this.postOperJsonDir.getAbsolutePath() + "/" + operStep.name + ".jpg");
            }
            else{
                String path = operStep.pic_path.file_path;
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



    /**
     *修改当前PostOperInstruction内部值
     */
    private void changeCurrentPostOperInstruction(OperStep operStep){
        for(OperStep curOperStep : InstructionApp.currentPostOperInstruction.steps){
            if(operStep.id.equals(curOperStep.id)){
                curOperStep.pic_path.file_path = operStep.pic_path.file_path;
                break;
            }
        }
    }

    /**
     * 上传冰箱与岗位代码信息
     * @param fridgeCode
     * @param postCode
     */
    private void sendFridgePostBean(final String fridgeCode, final String postCode,final String sessionId) {

        FridgePostBean fridgePostBean = new FridgePostBean(fridgeCode, postCode);
        FridgeParamsBean fridgeParamsBean = new FridgeParamsBean();

        fridgeParamsBean.setParams(fridgePostBean);

        InstructionApp.getApp().getNetService(this).sendFridgePostBean(fridgeParamsBean,sessionId).subscribe(new Action1<FridgeResponse>() {
            @Override
            public void call(FridgeResponse fridgeResponse) {
                //加载数据成功
                String sendMsg = "岗位编号为：" + postCode + "-冰箱编号为：" + fridgeCode + "-上传成功！";
                Log.d(sendMsg, "Yeah!!!!!");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //加载数据失败
                String sendMsg = "岗位编号为：" + postCode + "-冰箱编号为：" + fridgeCode + "-上传失败！";
                Log.d(sendMsg, throwable.getMessage());
            }
        });
    }
    //将数组转换为列表
    private List<String> changeArrayToList(String[] array)
    {
        List<String> retList = new ArrayList<String>();
        for(String col : array)
        {
            retList.add(col);
        }
        return retList;
    }

    @Override
    public void onBackPressed() {
        //将返回功能过滤掉
        super.onBackPressed();
        /*long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {//如果两次按键时间间隔大于800毫秒，则不退出
            Toast.makeText(this, "再按一次退出程序...",
                    Toast.LENGTH_SHORT).show();
            firstTime = secondTime;//更新firstTime
            return ;
        }else{
            super.onBackPressed();
        }**/
    }
}
