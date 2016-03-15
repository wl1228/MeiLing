package app.android.mingjiang.com.opcuaapi.single;

import android.util.Log;

import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;

/**
 * MonitoredDataItem处理工具:用添加MonitorDataItem对象及数据处理。
 * Created by wangzs on 2015/12/15.
 */
public class SingleModItemUtils {
    public SingleDataInterface dataInterface = null;
    public MonitoredDataItem dataItem = null;

    public SingleModItemUtils(SingleDataInterface dataInterface){
        this.dataInterface = dataInterface;
    }

    public MonitoredDataItem addMonitoredDataItem(int namespaceIndex,String value){
        //        设置监听的节点。可以监听具体的某个值，可以监听多个节点
        MonitoredDataItem item = new MonitoredDataItem(
                new NodeId(namespaceIndex,value));
        // 添加监听事件，服务器端数据改变是本地对应的动作设置
        item.setDataChangeListener(new MonitoredDataItemListener() {
            /**
             * @param monitoredDataItem
             * monitoredDataItem存储着节点的相关信息
             * @param dataValue
             * dataValue 上一次的value
             * @param dataValue1
             * 当前的value
             */
            @Override
            public void onDataChange(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1) {
                Log.i("--old--DataValue----", dataValue.getValue() + "");
                Log.i("--new--DataValue1----", dataValue1.getValue() + "");
               // Log.d("--data--item--",monitoredDataItem.getIndexRange().toString());
//               通知UI更新,在界面获取数据
                dataInterface.dealDataValue(monitoredDataItem,dataValue,dataValue1);
            }
        });
        return item;
    }


}
