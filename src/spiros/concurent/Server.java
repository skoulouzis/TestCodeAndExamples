/*
   Copyright 2009 S. Koulouzis

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.  
 */

package spiros.concurent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author alogo
 */
public class Server extends Thread
{
    private ServerSocket _server;

    private Socket socket;

    // private ObjectOutputStream out;
    // private ObjectInputStream in;

    private DataOutputStream out;

    private DataInputStream in;

    static final Integer NO_MORE_WORK = new Integer(0);

    BlockingQueue<Integer> q;

    /** Creates a new instance of Server */
    public Server(BlockingQueue<Integer> q)
    {
        this.q = q;
        try
        {
            _server = new ServerSocket(8199);
            socket = _server.accept();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while (true)
            {

                System.out.println("Got new connection" + socket.getLocalPort());
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                Integer x = q.take();

                if (x == NO_MORE_WORK)
                {
                    out.writeInt(x);
                    break;
                }
                out.writeInt(x);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

}
