package app.android.mingjiang.com.opcuaapi.multi;

import android.util.Log;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.PkiFileBasedCertificateValidator;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.SessionActivationException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.UserIdentity;
import com.prosysopc.ua.client.UaClient;

import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.transport.security.SecurityMode;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wangzs on 2015/12/15.
 */
public class MultiOPCUAClient {

    private List<MultiOPCUASubscription> opcuaSubscriptionList = new ArrayList<MultiOPCUASubscription>();
    private String serviceUrl = "";
    private UaClient uaClient = null;

    public MultiOPCUAClient(List<MultiOPCUASubscription> opcuaSubscriptions, String url){
        this.opcuaSubscriptionList = opcuaSubscriptions;
        this.serviceUrl = url;
    }


    /**
     * 创建连接Client。
     * @return
     * @throws URISyntaxException
     * @throws SessionActivationException
     */
    public UaClient createClient() throws URISyntaxException,
            SessionActivationException {
        // 1.创建UaClient对象
        uaClient = new UaClient(serviceUrl);

        //2.创建和设置证书验证
         PkiFileBasedCertificateValidator validator = new PkiFileBasedCertificateValidator("/sdcard/PKI/CA");
        uaClient.setCertificateValidator(validator);

        //3.设置应用描述
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setApplicationName(new LocalizedText(MultiOPCUAClientConfig.APP_NAME, Locale.ENGLISH));
        appDescription.setApplicationUri("urn:localhost:UA:"+ MultiOPCUAClientConfig.APP_NAME);
        appDescription.setProductUri("urn:prosysopc.com:UA:"+ MultiOPCUAClientConfig.APP_NAME);
        appDescription.setApplicationType(ApplicationType.Client);

        //4.创建和设置应用程序标识
        ApplicationIdentity identity =  new ApplicationIdentity();
        identity.setApplicationDescription(appDescription);
        identity.setOrganisation("Prosys");
        uaClient.setApplicationIdentity(identity);

        //5.创建订阅并添加到client中去
        for(MultiOPCUASubscription opcuaSubscription : this.opcuaSubscriptionList){
            try {
                uaClient.addSubscription(opcuaSubscription.getSubcription());
            } catch (ServiceException e) {
                e.printStackTrace();
            } catch (StatusException e) {
                e.printStackTrace();
            }
        }
        //6.设置Local、TimeOut、SecurityMode、UserIdentity
        uaClient.setLocale(Locale.CHINA);
        uaClient.setTimeout(MultiOPCUAClientConfig.TIME_OUT);
        uaClient.setSecurityMode(SecurityMode.NONE);
        uaClient.setUserIdentity(new UserIdentity());
        return uaClient;
    }

    /**
     * 根据url连接服务器，并且获取其UaClient对象。
     * @return
     */
    public void connectServer(){
        try {
            uaClient.connect();
        } catch (Exception e) {
          Log.d("connect opcua error:",e.getMessage().toString());
            connectServer();
        }
    }

    public boolean isConnect(){
        if(uaClient != null){
           return  uaClient.isConnected();
        } else{
            try {
                return createClient().isConnected();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return false;
            } catch (SessionActivationException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public UaClient getUaClient(){
        return uaClient;
    }
}
