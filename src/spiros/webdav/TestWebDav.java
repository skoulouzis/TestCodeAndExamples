package spiros.webdav;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;

public class TestWebDav
{
    public static void main(String args[])
    {

        HostConfiguration hostConfig = new HostConfiguration();

        hostConfig.setHost("localhost", 8008);

        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        int maxHostConnections = 20;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);
        HttpClient client = new HttpClient(connectionManager);
        Credentials creds = new UsernamePasswordCredentials("userId", "pw");
        client.getState().setCredentials(AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);

        // source, dest, overwrite
        DavMethod copy = new CopyMethod("http://localhost:8008/vlet_devplan3.pdf",
                "http://localhost:8008/vlet_devplan3.pdf.copy", true);

        GetMethod get = new GetMethod("http://localhost:8008/eclipse");

        try
        {
            PropFindMethod propFind = new PropFindMethod("http://localhost:8008/");

            // client.executeMethod(copy);

            int status = client.executeMethod(propFind);

            System.out.println(propFind.getStatusCode() + " " + propFind.getStatusText());

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
