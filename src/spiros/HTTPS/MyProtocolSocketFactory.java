package spiros.HTTPS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class MyProtocolSocketFactory implements ProtocolSocketFactory
{

    private SSLContext sslcontext;

    private void debug(String msg)
    {
        System.err.println(this.getClass().getName() + ": " + msg);
    }

    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
            final HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException
    {
        debug("Host: " + host + " port:" + port + " localAddress: " + localAddress + " localPort: " + localPort
                + " params: " + params);

        SSLContext context = getSSLContext();

        SocketFactory socketfactory = context.getSocketFactory();

        return socketfactory.createSocket(host, port, localAddress, localPort);
    }

    private SSLContext getSSLContext()
    {
        if (this.sslcontext == null)
        {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }

    private SSLContext createSSLContext()
    {
        try
        {

            String pKeyPassword = "pwd";

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            File pKeyFile = new File("/home/skoulouz/.globus/usercert.p12");
            InputStream keyInput = new FileInputStream(pKeyFile);
            keyStore.load(keyInput, pKeyPassword.toCharArray());
            keyInput.close();

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());

            KeyManager[] managers = keyManagerFactory.getKeyManagers();
            
            TrustManager[] trustmanagers = createTrustManagers(keyStore);

            sslcontext = SSLContext.getInstance("SSL");
            
            sslcontext.init(managers, trustmanagers, new SecureRandom());

            return sslcontext;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sslcontext;
    }

    private TrustManager[] createTrustManagers(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException
    {
        TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmfactory.init(keyStore);

        TrustManager[] trustmanagers = tmfactory.getTrustManagers();
        for (int i = 0; i < trustmanagers.length; i++)
        {
//            debug("trustmanagers: " + trustmanagers[i].getClass().getName());
        }
        return trustmanagers;
    }

    public Socket createSocket(String s, int i) throws IOException, UnknownHostException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Socket createSocket(String s, int i, InetAddress inetaddress, int j) throws IOException,
            UnknownHostException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
