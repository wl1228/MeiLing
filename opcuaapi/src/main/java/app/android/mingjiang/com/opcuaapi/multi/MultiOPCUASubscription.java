package app.android.mingjiang.com.opcuaapi.multi;

import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取OPCUA订阅。
 * Created by wangzs on 2015/12/19.
 */
public class MultiOPCUASubscription {

    private List<MultiOPCUAMonitoredDataItem> opcuaMonitoredDataItemList = new ArrayList<MultiOPCUAMonitoredDataItem>();
    private Subscription subcription = new Subscription();

    public MultiOPCUASubscription(List<MultiOPCUAMonitoredDataItem> opcuaMonitoredDataItems){
        this.opcuaMonitoredDataItemList = opcuaMonitoredDataItems;
        this.addMonitoredDataItem();
    }

    //给订阅添加监控。
    private void addMonitoredDataItem(){
        for(MultiOPCUAMonitoredDataItem opcuaMonitoredDataItem :this.opcuaMonitoredDataItemList){
            try {
                this.subcription.addItem(opcuaMonitoredDataItem.addMonitoredDataItem());
            } catch (ServiceException e) {
                e.printStackTrace();
            } catch (StatusException e) {
                e.printStackTrace();
            }
        }
    }

    public Subscription getSubcription() {
        return subcription;
    }

    public List<MultiOPCUAMonitoredDataItem> getOpcuaMonitoredDataItemList() {
        return opcuaMonitoredDataItemList;
    }
}
