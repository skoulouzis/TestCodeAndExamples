package spiros.HTTPS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import com.sun.net.ssl.internal.ssl.SSLContextImpl;

public class TestSSL
{

    private static final String HOST_NAME = "wms.grid.sara.nl";

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        TestSSL test = new TestSSL();

        try
        {
            // test.doIt();

//            test.doIt2();

             test.rawSocket();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void rawSocket() throws UnknownHostException, IOException
    {
        
        
        SSLContextImpl context = new SSLContextImpl();
        
        
        SocketFactory factory = SSLSocketFactory.getDefault();
        
        // Get Socket from factory
        Socket socket = factory.createSocket(HOST_NAME, 9000);
        

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.write("GET / HTTP/1.0\n\n");

        out.flush();

        String line;

        StringBuffer sb = new StringBuffer();

        while ((line = in.readLine()) != null)
        {

            sb.append(line);

        }

        out.close();

        in.close();

        System.out.println(sb.toString());

    }

    private void doIt2() throws HttpException, IOException
    {
        String scheme = "https";
        String keystorePassword = null;
        String truststoreUrl = "/home/skoulouz/.globus/usercert.p12";
        String keystoreUrl = ("/home/skoulouz/.vletrc/cacerts");
        String truststorePassword = null;
        ProtocolSocketFactory factory = new AuthSSLProtocolSocketFactory(keystoreUrl, keystorePassword, truststoreUrl,
                truststorePassword);
        int defaultPort = 9000;
        Protocol authhttps = new Protocol(scheme, factory, defaultPort);
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost(HOST_NAME, 9000, authhttps);
        // use relative url only
        GetMethod httpget = new GetMethod("/");
        client.executeMethod(httpget);
    }

    private void doIt() throws HttpException, IOException
    {
        String scheme = "https";
        ProtocolSocketFactory factory = new MyProtocolSocketFactory();
        int defaultPort = 9000;
        Protocol authhttps = new Protocol(scheme, factory, defaultPort);
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost("wms.grid.sara.nl", 9000, authhttps);
        // use relative url only
        GetMethod httpget = new GetMethod("/");
        client.executeMethod(httpget);
    }

    private void debug(String msg)
    {
        System.err.println(this.getClass().getName() + ": " + msg);
    }

}
