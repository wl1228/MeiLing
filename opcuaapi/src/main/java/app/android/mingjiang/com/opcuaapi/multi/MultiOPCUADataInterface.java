package app.android.mingjiang.com.opcuaapi.multi;

import com.prosysopc.ua.client.MonitoredDataItem;

import org.opcfoundation.ua.builtintypes.DataValue;

/**
 * 获取刷新数据接口。
 * Created by wangzs on 2015/12/15.
 */
public interface MultiOPCUADataInterface {

    public void dealDataValue(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1);

    public String getConfig_str_value();

    public int getConfig_namespaceIndex();

    public int getConfig_int_value();

    public String getName();
}
