package app.android.mingjiang.com.opcuaapi;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import app.android.mingjiang.com.opcuaapi.multi.MultiOPCUADataInterface;

/**
 * Created by wangzs on 2015/12/19.
 */
public class OPCUAApp extends Application{

    private static OPCUAApp instance = null;

    private OPCUAApp(){}

    public static OPCUAApp getInstance() {
        if(instance == null){
            instance = new OPCUAApp();
        }
        return instance;
    }

    List<MultiOPCUADataInterface> opcuaDataInterfaceList = new ArrayList<MultiOPCUADataInterface>();
}
