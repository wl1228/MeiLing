package app.android.mingjiang.com.opcuaapi.multi;

import android.util.Log;

import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.MonitoredDataItemListener;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;

/**
 * 处理数据监控。
 * Created by wangzs on 2015/12/19.
 */
public class MultiOPCUAMonitoredDataItem {

    public MultiOPCUADataInterface dataInterface = null;
    public MonitoredDataItem dataItem = null;

    private int config_namespaceIndex = 0;
    private String config_str_value = "";
    private int  config_int_value = 0;

    public MultiOPCUAMonitoredDataItem(MultiOPCUADataInterface dataInterface){
        this.dataInterface = dataInterface;
        this.config_namespaceIndex =dataInterface.getConfig_namespaceIndex();
        this.config_int_value = dataInterface.getConfig_int_value();
        this.config_str_value = dataInterface.getConfig_str_value();
    }

    public MonitoredDataItem addMonitoredDataItem(){
        // 设置监听的节点。可以监听具体的某个值，可以监听多个节点
        MonitoredDataItem item = new MonitoredDataItem(
                new NodeId(this.config_namespaceIndex,this.config_str_value));
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
                dataInterface.dealDataValue(monitoredDataItem, dataValue, dataValue1);
            }
        });
        return item;
    }

}
