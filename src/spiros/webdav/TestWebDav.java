package spiros.webdav;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.jackrabbit.webdav.property.DefaultDavProperty;
import org.apache.jackrabbit.webdav.property.PropEntry;

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

            // Read all properties of a resource
            DavMethod pFind = new PropFindMethod("http://localhost:8008/HttpClient.java",
                    DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_INFINITY);
            client.executeMethod(pFind);

            MultiStatus multiStatus = pFind.getResponseBodyAsMultiStatus();

            // Not quite nice, but for a example ok
            DavPropertySet props = multiStatus.getResponses()[0].getProperties(200);

            Collection propertyColl = props.getContent();
            propertyColl.iterator();
            for (Iterator iterator = propertyColl.iterator(); iterator.hasNext();)
            {
                DefaultDavProperty tmpProp = (DefaultDavProperty) iterator.next();
                System.out.println(tmpProp.getName() + "  " + tmpProp.getValue());
            }

            // Getting a list of subresources of a resource
            pFind = new PropFindMethod("http://localhost:8008/", DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
            client.executeMethod(pFind);

            multiStatus = pFind.getResponseBodyAsMultiStatus();
            MultiStatusResponse[] responses = multiStatus.getResponses();
            MultiStatusResponse currResponse;
            ArrayList files = new ArrayList();
            System.out.println("Folders and files in http://localhost:8008/ :");
            for (int i = 0; i < responses.length; i++)
            {
                currResponse = responses[i];

                System.out.println(currResponse.getHref());

                // if (!(currResponse.getHref().equals(path) ||
                // currResponse.getHref().equals(path + "/"))) {
                // System.out.println(currResponse.getHref());
                // }
            }

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
