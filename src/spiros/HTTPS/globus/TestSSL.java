package spiros.HTTPS.globus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.globus.common.CoGProperties;
import org.globus.gsi.GSIConstants;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.TrustedCertificates;
import org.globus.gsi.gssapi.GSSConstants;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.gssapi.net.GssSocket;
import org.globus.gsi.gssapi.net.GssSocketFactory;
import org.globus.net.GSIHttpURLConnection;
import org.gridforum.jgss.ExtendedGSSContext;
import org.gridforum.jgss.ExtendedGSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;

public class TestSSL
{

    private static URLConnection conn;

    public static void main(String[] args)
    {

        try
        {
             doit1();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void doit1()
    {
        try
        {
            // MYGSIHttpURLConnection conn = new MYGSIHttpURLConnection(new
            // URL("https://wms.grid.sara.nl:9000/"));
            // MYGSIHttpURLConnection conn = new MYGSIHttpURLConnection(new
            // URL("https://elab.science.uva.nl:8443/"));

            // conn = new org.apache.commons.httpclient.util.HttpURLConnection(
            // new org.apache.commons.httpclient.methods.GetMethod(), new URL(
            // "http://en.wikipedia.org/"));
//            conn = new MYGSIHttpURLConnection(new URL("https://grasveld.nikhef.nl:9000/"));
            conn = new GSIHttpURLConnection(new URL("https://grasveld.nikhef.nl:9000/"));
            conn.setRequestProperty("gssMode","ssl");
            
            
            
            conn.connect();

            System.err.println("ContentType: " + conn.getContentType());

            System.err.println("ContentEncoding: " + conn.getContentEncoding());

            System.err.println("ContentLength: " + conn.getContentLength());

            // conn.getContent();

            // conn.disconnect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();

        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
