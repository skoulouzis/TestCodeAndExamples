package spiros.HTTPS.globus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

import org.globus.common.ChainedIOException;
import org.globus.gsi.GSIConstants;
import org.globus.gsi.gssapi.GSSConstants;
import org.globus.gsi.gssapi.net.GssSocket;
import org.globus.gsi.gssapi.net.GssSocketFactory;
import org.globus.net.GSIHttpURLConnection;
import org.globus.util.http.HTTPChunkedInputStream;
import org.globus.util.http.HTTPProtocol;
import org.globus.util.http.HTTPResponseParser;
import org.gridforum.jgss.ExtendedGSSContext;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;

public class MYGSIHttpURLConnection extends GSIHttpURLConnection
{

    private InputStream is;

    private Socket socket;

    private OutputStream os;

    private int port;

    private HTTPResponseParser response;

    public MYGSIHttpURLConnection(URL u)
    {
        super(u);
    }

    @Override
    public synchronized InputStream getInputStream() throws IOException
    {
        if (is == null)
        {
            connect();
            if (os == null)
            {
                OutputStream out = socket.getOutputStream();

                String msg = HTTPProtocol.createGETHeader(url.getFile(), url.getHost() + ":" + port,
                        "Java-Globus-GASS-HTTP/1.1.0");

                System.err.println("Message is: " + msg.toString());

                out.write(msg.getBytes());
                out.flush();
            }
            else
            {
                os.flush();
                os.close();
                os = null;
            }
            InputStream in = socket.getInputStream();
            response = new HTTPResponseParser(in);

            System.err.println("response: " + response.getMessage() + " " + response.getStatusCode());

            if (!response.isOK())
                throw new IOException(response.getMessage());
            if (response.isChunked())
                is = new HTTPChunkedInputStream(in);
            else
                is = in;
        }
        return is;
    }

    @Override
    public synchronized void connect() throws IOException
    {
        GSSName expectedName = null;
        try
        {
            expectedName = getExpectedName();
        }
        catch (GSSException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (connected)
            return;
        connected = true;
        port = url.getPort() != -1 ? url.getPort() : 8443;
        GSSManager manager = ExtendedGSSManager.getInstance();

        ExtendedGSSContext context = null;
        try
        {
            context = (ExtendedGSSContext) manager.createContext(expectedName, GSSConstants.MECH_OID, credentials, 0);
            switch (delegationType)
            {
                case 1: // '\001'
                    context.requestCredDeleg(false);
                    break;

                case 2: // '\002'
                    context.requestCredDeleg(true);
                    context.setOption(GSSConstants.DELEGATION_TYPE, GSIConstants.DELEGATION_TYPE_LIMITED);
                    break;

                case 3: // '\003'
                    context.requestCredDeleg(true);
                    context.setOption(GSSConstants.DELEGATION_TYPE, GSIConstants.DELEGATION_TYPE_FULL);
                    break;

                default:
                    context.requestCredDeleg(true);
                    context.setOption(GSSConstants.DELEGATION_TYPE, new Integer(delegationType));
                    break;
            }
            if (gssMode != null)
                context.setOption(GSSConstants.GSS_MODE, gssMode);
        }
        catch (GSSException e)
        {
            throw new ChainedIOException("Failed to init GSI context", e);
        }
        GssSocketFactory factory = GssSocketFactory.getDefault();
        socket = factory.createSocket(url.getHost(), port, context);
        ((GssSocket) socket).setAuthorization(authorization);

        if (context.getMutualAuthState())
        {
            System.err.println("TCP @ " + socket.getLocalPort() + ": Mutual authentication took place!");
            try
            {
                System.err.println("Client is " + context.getSrcName());
                System.err.println("Server is " + context.getTargName());
            }
            catch (GSSException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
