package app.android.mingjiang.com.opcuaapi.single;

/**
 * 备注：单连接配置信息。
 * 作者：wangzs on 2016/1/4 17:52
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class SingleOPCUAConfig {

    //设置NodeID(namespaceIndex,value)//namespaceIndex:命名空间位置，value:节点名称
    public static final int NODE_ID_NAMESPACE_INDEX = 6;
    public static final String NODE_ID_SVALUE = "DataItem_0000";
    public static final int NODE_ID_IVALUE = 6;

    public static final String URL = "opc.tcp://192.168.1.11:53530/OPCUA/SimulationServer";

    public static final String applicationName = "";

    //设置连接超时
    public static final int TIME_OUT = 60000;

}
