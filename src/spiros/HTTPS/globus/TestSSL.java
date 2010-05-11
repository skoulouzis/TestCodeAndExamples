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

//            doit2();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void doit2() throws GSSException, UnknownHostException, IOException
    {
        ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager.getInstance();
        /*
         * Create a GSS Credential grid-proxy-init is required 1. Load COG
         * propertirs 2. Read proxy file 3. Obtain a GSSManager instance 4.
         * Create a GSSCredential w/ default values
         */
        CoGProperties cog = CoGProperties.getDefault();

        byte proxyBytes[] = readBinFile(cog.getProxyFile());

        GSSCredential credential = manager.createCredential(proxyBytes, ExtendedGSSCredential.IMPEXP_OPAQUE,
                GSSCredential.DEFAULT_LIFETIME, null, GSSCredential.INITIATE_AND_ACCEPT);

        // GSSName serverName =
        // manager.createName("O=dutchgrid/O=hosts/OU=nikhef.nl/CN=grasveld.nikhef.nl",
        // null);
        // GSSName serverName =
        // manager.createName("host/grasveld.nikhef.nl",null);
        // GSSContext context = manager.createContext(null, null, credential,
        // GSSContext.DEFAULT_LIFETIME);
        // GSSContext context = manager.createContext(credential);
        // context.requestCredDeleg(false);
        // context.requestMutualAuth(true);

        TrustedCertificates trustedCerts = TrustedCertificates.load("/etc/grid-security/certificates");

        ExtendedGSSContext context = (ExtendedGSSContext) manager.createContext(credential);

        context.setOption(GSSConstants.TRUSTED_CERTIFICATES, trustedCerts);

        GssSocketFactory factory = GssSocketFactory.getDefault();

        Socket socket = SocketFactory.getDefault().createSocket("grasveld.nikhef.nl", 9000);
        GssSocket gsiSocket = (GssSocket) factory.createSocket(socket, null, 0, context);
        gsiSocket.setWrapMode(GssSocket.SSL_MODE);

        // gsiSocket.setUseClientMode(true);

        gsiSocket.startHandshake();

        if (context.getMutualAuthState())
        {
            System.err.println("TCP @ " + gsiSocket.getLocalPort() + ": Mutual authentication took place!");
            System.err.println("Client is " + context.getSrcName());
            System.err.println("Server is " + context.getTargName());
        }

    }

    public ExtendedGSSContext getGsiServerContext(String cert, String key) throws Exception
    {
        GlobusCredential serverCred = null;
        TrustedCertificates trustedCerts = null;
        ExtendedGSSContext context = null;
        if (cert == null)
        {
            cert = "/etc/grid-security/hostcert.pem";
        }
        if (key == null)
        {
            key = "/etc/grid-security/hostkey.pem";
        }
        if (cert.length() == 0)
        {
            cert = "/etc/grid-security/hostcert.pem";
        }
        if (key.length() == 0)
        {
            key = "/etc/grid-security/hostkey.pem";
        }

        serverCred = new GlobusCredential(cert, key);
        serverCred.verify();

        GSSCredential cred = new GlobusGSSCredentialImpl(serverCred, GSSCredential.INITIATE_AND_ACCEPT);
        trustedCerts = TrustedCertificates.load("/etc/grid-security/certificates");
        GSSManager manager = ExtendedGSSManager.getInstance();
        context = (ExtendedGSSContext) manager.createContext(cred);
        context.setOption(GSSConstants.GSS_MODE, GSIConstants.MODE_SSL);
        context.setOption(GSSConstants.TRUSTED_CERTIFICATES, trustedCerts);
        return (context);

    }

    private static byte[] readBinFile(String path)
    {
        if (path == null)
        {
            CoGProperties cog = CoGProperties.getDefault();
            path = cog.getProxyFile();
        }
        byte data[] = null;
        try
        {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            data = new byte[(int) raf.length()];
            raf.readFully(data);
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return data;
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
