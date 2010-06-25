package spiros.webdav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestWebDav
{
    public static void main(String args[])
    {
        try
        {
            // tetstGetOutputStreamApache();

//             tetstGetOutputStreamWebDav4j();

//             testList();

//             testLock();

//            testWrite();
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void testWrite() throws IOException
    {

        // Construct data
        String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
        data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");

        URL url = new URL("http://localhost:8008/");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write("POST "+"/file1.txt"+" HTTP/1.0\r\n"); 
        wr.write("Content-Length: "+data.length()+"\r\n"); 
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n"); 
        wr.write("\r\n"); 

        
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = rd.readLine()) != null)
        {
            System.out.println("Line: " + line);
        }
        wr.close();
        rd.close();

    }

    private static void testLock() throws org.apache.jackrabbit.webdav.DavException, IOException
    {
        org.apache.commons.httpclient.HostConfiguration hostConfig = new org.apache.commons.httpclient.HostConfiguration();

        hostConfig.setHost("localhost", 8008);

        org.apache.commons.httpclient.MultiThreadedHttpConnectionManager connectionManager = new org.apache.commons.httpclient.MultiThreadedHttpConnectionManager();
        org.apache.commons.httpclient.params.HttpConnectionManagerParams params = new org.apache.commons.httpclient.params.HttpConnectionManagerParams();
        int maxHostConnections = 30;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient(
                connectionManager);
        org.apache.commons.httpclient.Credentials creds = new org.apache.commons.httpclient.UsernamePasswordCredentials(
                "userId", "pw");
        client.getState().setCredentials(org.apache.commons.httpclient.auth.AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);

        org.apache.jackrabbit.webdav.lock.Scope scope = org.apache.jackrabbit.webdav.lock.Scope.EXCLUSIVE;

        org.apache.jackrabbit.webdav.lock.Type type = org.apache.jackrabbit.webdav.lock.Type.WRITE;

        String owner = "userId";
        long timeout = 5000;
        boolean isDeep = false;
        org.apache.jackrabbit.webdav.lock.LockInfo lockInfo = new org.apache.jackrabbit.webdav.lock.LockInfo(scope,
                type, owner, timeout, isDeep);
        org.apache.jackrabbit.webdav.client.methods.LockMethod lock = new org.apache.jackrabbit.webdav.client.methods.LockMethod(
                "http://localhost:8008/file1.txt", lockInfo);

        int code = client.executeMethod(lock);

        
        String lockTocken = lock.getLockToken();

        System.out.println("lockTocken: " + lockTocken);

        System.out.println("Status code and line: " + code + " " + lock.getStatusText());

//        org.apache.jackrabbit.webdav.lock.LockDiscovery lockDiscovery = lock.getResponseAsLockDiscovery();
//        org.apache.jackrabbit.webdav.property.DavPropertyName name = lockDiscovery.getName();
//        List<ActiveLock> value = lockDiscovery.getValue();
//
//        System.out.println("DavPropertyName: " + name.getName());
//
//        for (int i = 0; i < value.size(); i++)
//        {
//            ActiveLock activeLock = value.get(i);
//            System.out.println("getLockroot: " + activeLock.getLockroot());
//            System.out.println("getOwner: " + activeLock.getOwner());
//            System.out.println("getTimeout: " + activeLock.getTimeout());
//            System.out.println("getToken: " + activeLock.getToken());
//            System.out.println("getScope: " + activeLock.getScope());
//            System.out.println("getType: " + activeLock.getType());
//        }
    }

    private static void tetstGetOutputStreamWebDav4j()
    {
        java.io.OutputStream fileWriter = null;
        try
        {
            // get a VirtualFileSystem Manager (pached)
            org.apache.commons.vfs.FileSystemManager fsManager = org.apache.commons.vfs.VFS.getManager();
            // retrieve the webdav resource
            org.apache.commons.vfs.provider.webdav.WebdavFileObject resource = (org.apache.commons.vfs.provider.webdav.WebdavFileObject) fsManager
                    .resolveFile("webdav://localhost:8008/file2.txt");
            // retrieve an output stream from that resource
            fileWriter = resource.getOutputStream();
            // use that output stream
            // ...

            fileWriter.write("aaaaaaaaaaaaaaaa".getBytes());

            fileWriter.flush();

            fileWriter.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void testList()
    {
        org.apache.commons.httpclient.HostConfiguration hostConfig = new org.apache.commons.httpclient.HostConfiguration();

        hostConfig.setHost("localhost", 8008);

        org.apache.commons.httpclient.MultiThreadedHttpConnectionManager connectionManager = new org.apache.commons.httpclient.MultiThreadedHttpConnectionManager();
        org.apache.commons.httpclient.params.HttpConnectionManagerParams params = new org.apache.commons.httpclient.params.HttpConnectionManagerParams();
        int maxHostConnections = 30;
        params.setMaxConnectionsPerHost(hostConfig, maxHostConnections);
        connectionManager.setParams(params);
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient(
                connectionManager);
        org.apache.commons.httpclient.Credentials creds = new org.apache.commons.httpclient.UsernamePasswordCredentials(
                "userId", "pw");
        client.getState().setCredentials(org.apache.commons.httpclient.auth.AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);

        // source, dest, overwrite
        org.apache.jackrabbit.webdav.client.methods.CopyMethod copy = new org.apache.jackrabbit.webdav.client.methods.CopyMethod(
                "http://localhost:8008/vlet_devplan3.pdf", "http://localhost:8008/vlet_devplan3.pdf.copy", true);

        org.apache.commons.httpclient.methods.GetMethod get = new org.apache.commons.httpclient.methods.GetMethod(
                "http://localhost:8008/");

        try
        {

            // Read all properties of a resource
            // DavMethod pFind = new
            // PropFindMethod("http://localhost:8008/file2",
            // DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_INFINITY);
            // client.executeMethod(pFind);

            // MultiStatus multiStatus = pFind.getResponseBodyAsMultiStatus();

            // // Not quite nice, but for a example ok
            // DavPropertySet props =
            // multiStatus.getResponses()[0].getProperties(200);
            //
            // Collection propertyColl = props.getContent();
            // propertyColl.iterator();
            // for (Iterator iterator = propertyColl.iterator();
            // iterator.hasNext();)
            // {
            // DefaultDavProperty tmpProp = (DefaultDavProperty)
            // iterator.next();
            // System.out.println(tmpProp.getName() + "  " +
            // tmpProp.getValue());
            //                
            // }

            // Getting a list of subresources of a resource
            org.apache.jackrabbit.webdav.client.methods.PropFindMethod method = new org.apache.jackrabbit.webdav.client.methods.PropFindMethod(
                    "http://localhost:8008/", org.apache.jackrabbit.webdav.DavConstants.PROPFIND_ALL_PROP,
                    org.apache.jackrabbit.webdav.DavConstants.DEPTH_0);

            client.executeMethod(method);

            org.apache.jackrabbit.webdav.MultiStatus multiStatus = method.getResponseBodyAsMultiStatus();

            org.apache.jackrabbit.webdav.MultiStatusResponse[] responses = multiStatus.getResponses();
            org.apache.jackrabbit.webdav.MultiStatusResponse currResponse;

            System.out.println("Folders and files in http://localhost:8008/ :");
            for (int i = 0; i < responses.length; i++)
            {
                currResponse = responses[i];

                System.out.println("getHref: " + currResponse.getHref());
                System.out.println("getResponseDescription: " + currResponse.getResponseDescription());

                org.apache.jackrabbit.webdav.Status[] status = currResponse.getStatus();
                for (int j = 0; j < status.length; j++)
                {
                    System.out.println("    status: " + status[j].getStatusCode());

                    org.apache.jackrabbit.webdav.property.DavPropertySet props = currResponse.getProperties(status[j]
                            .getStatusCode());
                    org.apache.jackrabbit.webdav.property.DavPropertyIterator propIt = props.iterator();

                    while (propIt.hasNext())
                    {
                        org.apache.jackrabbit.webdav.property.DavProperty<?> prop = propIt.nextProperty();
                        org.apache.jackrabbit.webdav.property.DavPropertyName name = prop.getName();
                        Object value = prop.getValue();
                        if (value instanceof org.apache.xerces.dom.DeferredElementNSImpl)
                        {
                            org.apache.xerces.dom.DeferredElementNSImpl deferredElementNSImpl = (org.apache.xerces.dom.DeferredElementNSImpl) value;

                            System.out.println("getBaseURI: " + deferredElementNSImpl.getBaseURI());
                            System.out.println("getLength: " + deferredElementNSImpl.getLength());
                            System.out.println("getLocalName: " + deferredElementNSImpl.getLocalName());
                            System.out.println("getNamespaceURI: " + deferredElementNSImpl.getNamespaceURI());
                            System.out.println("getNodeIndex: " + deferredElementNSImpl.getNodeIndex());
                            System.out.println("getNodeName: " + deferredElementNSImpl.getNodeName());
                            System.out.println("getNodeType: " + deferredElementNSImpl.getNodeType());
                            System.out.println("getNodeValue: " + deferredElementNSImpl.getNodeValue());
                            System.out.println("getPrefix: " + deferredElementNSImpl.getPrefix());
                            System.out.println("getTagName: " + deferredElementNSImpl.getTagName());
                            System.out.println("getTextContent: " + deferredElementNSImpl.getTextContent());
                            System.out.println("getOwnerDocument().getLocalName(): "
                                    + deferredElementNSImpl.getOwnerDocument().getLocalName());

                            System.out.println("deferredElementNSImpl    " + name.getName() + " : "
                                    + deferredElementNSImpl.getNodeName());

                        }
                        else
                        {
                            System.out.println("    " + name.getName() + " : " + value);
                        }
                        if (value != null)
                            System.out.println("        propNamegetValuegetClass().getName: "
                                    + value.getClass().getName());
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

    private static void tetstGetOutputStreamApache()
    {

        java.io.OutputStream fileWriter = null;
        try
        {
            // get a VirtualFileSystem Manager
            org.apache.commons.vfs.FileSystemManager fsManager = org.apache.commons.vfs.VFS.getManager();

            // retrieve the webdav resource
            org.apache.commons.vfs.provider.http.HttpFileObject resource = (org.apache.commons.vfs.provider.http.HttpFileObject) fsManager
                    .resolveFile("http://localhost:8008/file1.html");

            // org.apache.commons.vfs.provider.webdav.WebdavFileObject d=
            // org.apache.commons.vfs.provider.webdav.WebdavFileObject

            fileWriter = resource.getOutputStream();

            // use that output stream

            // fileWriter.write("AAAAAAAAAAAAAA".getBytes());

            // fileWriter.flush();

            // fileWriter.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
