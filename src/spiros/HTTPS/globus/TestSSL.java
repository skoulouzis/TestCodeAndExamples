package spiros.HTTPS.globus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.globus.net.GSIHttpURLConnection;
import org.globus.net.protocol.https.Handler;

public class TestSSL
{

    public static void main(String[] args)
    {

        try
        {
//            MYGSIHttpURLConnection conn = new MYGSIHttpURLConnection(new URL("https://wms.grid.sara.nl:9000/"));
//            MYGSIHttpURLConnection conn = new MYGSIHttpURLConnection(new URL("https://elab.science.uva.nl:8443/"));
            
            org.apache.commons.httpclient.util.HttpURLConnection conn =  
                new org.apache.commons.httpclient.util.HttpURLConnection(new org.apache.commons.httpclient.methods.GetMethod(),new URL("https://grasveld.nikhef.nl:9000/"));
//            MYGSIHttpURLConnection conn = new MYGSIHttpURLConnection(new URL("https://grasveld.nikhef.nl:9000/"));
            conn.connect();

            // conn.getContent();

//            conn.disconnect();


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
