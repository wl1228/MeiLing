package app.android.mingjiang.com.opcuaapi.single;

import com.prosysopc.ua.client.MonitoredDataItem;

import org.opcfoundation.ua.builtintypes.DataValue;

/**
 * 获取刷新数据接口。
 * Created by wangzs on 2015/12/15.
 */
public interface SingleDataInterface {

    public void dealDataValue(MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1);
}