package spiros.HTTPS.globus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.globus.tomcat.coyote.net.HTTPSConnector;

public class TestSSL
{

    private static URLConnection conn;

    public static void main(String[] args)
    {

        try
        {
             doit1();
             
             
             doit2();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

  
    private static void doit2()
    {
        HTTPSConnector c = new HTTPSConnector();
        
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
            conn = new MYGSIHttpURLConnection(new URL("https://grasveld.nikhef.nl:9000/"));

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
