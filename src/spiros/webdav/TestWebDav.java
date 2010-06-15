package spiros.webdav;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;

public class TestWebDav
{
    public static void main(String args[]){
        
        
        HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost("www.somehost.com"); 
        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        int maxHostConnections = 20;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);    
        HttpClient client = new HttpClient(connectionManager);
        Credentials creds = new UsernamePasswordCredentials("userId", "pw");
        client.getState().setCredentials(AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);
        
        
      //source, dest, overwrite
        DavMethod copy=new CopyMethod("http://www.somehost.com/duff/test3.txt", "http://www.somehost.com/duff/test4.txt", true);
        
        
        try
        {
            client.executeMethod(copy);
        }
        catch (HttpException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println(copy.getStatusCode() + " "+ copy.getStatusText());
        
    }
}
