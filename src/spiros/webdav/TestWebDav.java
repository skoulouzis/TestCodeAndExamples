package spiros.webdav;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.Status;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyIterator;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertyNameIterator;
import org.apache.jackrabbit.webdav.property.DavPropertyNameSet;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.xerces.dom.DeferredElementNSImpl;

public class TestWebDav
{
    public static void main(String args[])
    {

        HostConfiguration hostConfig = new HostConfiguration();

        hostConfig.setHost("localhost", 8008);

        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        int maxHostConnections = 30;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);
        HttpClient client = new HttpClient(connectionManager);
        Credentials creds = new UsernamePasswordCredentials("userId", "pw");
        client.getState().setCredentials(AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);

        // source, dest, overwrite
        DavMethod copy = new CopyMethod("http://localhost:8008/vlet_devplan3.pdf",
                "http://localhost:8008/vlet_devplan3.pdf.copy", true);

        GetMethod get = new GetMethod("http://localhost:8008/");

        try
        {

            // Read all properties of a resource
//            DavMethod pFind = new PropFindMethod("http://localhost:8008/file2",
//                    DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_INFINITY);
//            client.executeMethod(pFind);

//            MultiStatus multiStatus = pFind.getResponseBodyAsMultiStatus();

//            // Not quite nice, but for a example ok
//            DavPropertySet props = multiStatus.getResponses()[0].getProperties(200);
//
//            Collection propertyColl = props.getContent();
//            propertyColl.iterator();
//            for (Iterator iterator = propertyColl.iterator(); iterator.hasNext();)
//            {
//                DefaultDavProperty tmpProp = (DefaultDavProperty) iterator.next();
//                System.out.println(tmpProp.getName() + "  " + tmpProp.getValue());
//                
//            }

                        
            // Getting a list of subresources of a resource
            PropFindMethod method = new PropFindMethod("http://localhost:8008/", DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_0);
            
            
            client.executeMethod(method);
            
            MultiStatus multiStatus = method.getResponseBodyAsMultiStatus();
            
            MultiStatusResponse[] responses = multiStatus.getResponses();
            MultiStatusResponse currResponse;
            
            System.out.println("Folders and files in http://localhost:8008/ :");
            for (int i = 0; i < responses.length; i++)
            {
                currResponse = responses[i];

                System.out.println("getHref: "+currResponse.getHref());
                System.out.println("getResponseDescription: "+currResponse.getResponseDescription());
                
                Status[] status = currResponse.getStatus();
                for(int j=0;j<status.length;j++){
                    System.out.println("    status: "+status[j].getStatusCode());
                                       
                    
                    DavPropertySet props = currResponse.getProperties(status[j].getStatusCode());
                    DavPropertyIterator propIt = props.iterator();
                    
                    while(propIt.hasNext()){
                        DavProperty<?> prop = propIt.nextProperty();
                        DavPropertyName name = prop.getName();
                        Object value = prop.getValue();
                        if(value instanceof org.apache.xerces.dom.DeferredElementNSImpl){
                            org.apache.xerces.dom.DeferredElementNSImpl deferredElementNSImpl = (DeferredElementNSImpl) value;  
                            
                            System.out.println("getBaseURI: "+deferredElementNSImpl.getBaseURI());
                            System.out.println("getLength: "+deferredElementNSImpl.getLength());
                            System.out.println("getLocalName: "+deferredElementNSImpl.getLocalName());
                            System.out.println("getNamespaceURI: "+deferredElementNSImpl.getNamespaceURI());
                            System.out.println("getNodeIndex: "+deferredElementNSImpl.getNodeIndex());
                            System.out.println("getNodeName: "+deferredElementNSImpl.getNodeName());
                            System.out.println("getNodeType: "+deferredElementNSImpl.getNodeType());
                            System.out.println("getNodeValue: "+deferredElementNSImpl.getNodeValue());
                            System.out.println("getPrefix: "+deferredElementNSImpl.getPrefix());
                            System.out.println("getTagName: "+deferredElementNSImpl.getTagName());
                            System.out.println("getTextContent: "+deferredElementNSImpl.getTextContent());
                            System.out.println("getOwnerDocument().getLocalName(): "+deferredElementNSImpl.getOwnerDocument().getLocalName());
                            
                            System.out.println("deferredElementNSImpl    "+name.getName()+" : "+deferredElementNSImpl.getNodeName());
                            
                            
                        }else{
                            System.out.println("    "+name.getName()+" : "+value);   
                        }
                        if(value !=null)
                        System.out.println("        propNamegetValuegetClass().getName: "+value.getClass().getName());
                    }
                }

            }

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
