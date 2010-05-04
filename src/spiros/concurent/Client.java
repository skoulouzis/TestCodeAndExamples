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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author alogo
 */
public class Client extends Thread
{
    private Socket socket;

    private DataOutputStream out;

    private DataInputStream in;

    BlockingQueue<Integer> q;

    static final Integer NO_MORE_WORK = new Integer(0);

    /** Creates a new instance of Client */
    public Client(BlockingQueue<Integer> q)
    {
        this.q = q;

        try
        {
            socket = new Socket("localhost", 8199);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (UnknownHostException ex)
        {
            ex.printStackTrace();
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

                int data = in.readInt();
                q.put(data);
                if (data == NO_MORE_WORK)
                {
                    break;
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (InterruptedException ex)
        {

        }
    }
}
