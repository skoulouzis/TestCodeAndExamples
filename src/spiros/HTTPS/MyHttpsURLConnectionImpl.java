package spiros.HTTPS;

import java.io.IOException;
import java.net.URL;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class MyHttpsURLConnectionImpl extends HttpsURLConnectionImpl
{

    protected MyHttpsURLConnectionImpl(URL url) throws IOException
    {
        super(url);
        debug("Connecnting to: "+url);
    }

    private void debug(String msg)
    {
       System.err.println(this.getClass().getName()+": "+msg);
        
    }

}
