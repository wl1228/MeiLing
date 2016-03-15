package app.android.mingjiang.com.opcuaapi.single;

import android.util.Log;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.PkiFileBasedCertificateValidator;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.SessionActivationException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.UserIdentity;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;

import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.transport.security.SecurityMode;

import java.net.URISyntaxException;
import java.util.Locale;

/**
 * 备注：获取并连接UaClient对象。
 * 作者：wangzs on 2016/1/4 17:52
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class SingleOPCUAClient {

    private SingleOPCUAClient(){
    }

    /**
     * 获取UaClient对象。
     * @param serverUri
     * @return
     */
    public static UaClient createClient(String serverUri){
        return createClient(serverUri,SingleOPCUAConfig.TIME_OUT);
    }
    /**
     * 创建连接Client。
     * @param serverUri
     * @return
     * @throws URISyntaxException
     * @throws SessionActivationException
     */
    public static UaClient createClient(String serverUri,int timeout) {
        // 1.创建UaClient对象
        UaClient myClient = null;
        try {
            myClient = new UaClient(serverUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //2.创建和设置证书验证
         PkiFileBasedCertificateValidator validator = new PkiFileBasedCertificateValidator("/sdcard/PKI/CA");
          myClient.setCertificateValidator(validator);

        //3.设置应用描述
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setApplicationName(new LocalizedText(SingleOPCUAConfig.applicationName, Locale.ENGLISH));
        appDescription.setApplicationUri("urn:localhost:UA:"+ SingleOPCUAConfig.applicationName);
        appDescription.setProductUri("urn:prosysopc.com:UA:"+ SingleOPCUAConfig.applicationName);
        appDescription.setApplicationType(ApplicationType.Client);

        //4.创建和设置应用程序标识
        ApplicationIdentity identity =  new ApplicationIdentity();
        identity.setApplicationDescription(appDescription);
        identity.setOrganisation("Prosys");
        myClient.setApplicationIdentity(identity);

        //5.创建订阅并添加到client中去
        Subscription subscription = new Subscription();
        try {
            //添加dataItem。
           /* for(int i=0;i<10;i++){
                MonitoredDataItem item = addMonitoredDataItem(i,""+i);
                subscription.addItem(item);
            }*/
            myClient.addSubscription(subscription);
        } catch (ServiceException | StatusException e) {
            e.printStackTrace();
        }
        //6.设置Local、TimeOut、SecurityMode、UserIdentity
        myClient.setLocale(Locale.CHINA);
        myClient.setTimeout(timeout);
        myClient.setSecurityMode(SecurityMode.NONE);
        try {
            myClient.setUserIdentity(new UserIdentity());
        } catch (SessionActivationException e) {
            e.printStackTrace();
        }
        return myClient;
    }

    /**
     * 根据url连接服务器，并且获取其UaClient对象。
     * @return
     */
    public static boolean connectServer(UaClient myClient){
        try {
            myClient.connect();
            return myClient.isConnected();
        } catch (Exception e) {
            connectServer(myClient);
          Log.d("connect opcua error:",e.getMessage().toString());
           return false;
        }
    }

    /**
     * 断开UaClient的连接。
     * @param uaClient
     */
    public static void disconnectClient(UaClient uaClient){
        uaClient.disconnect();
    }
}
