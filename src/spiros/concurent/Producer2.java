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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author alogo
 */
public class Producer2 implements Runnable
{
    private Thread t;

    private BlockingQueue<Object> queue;

    private Integer DONE = -999;

    /** Creates a new instance of Producer2 */
    public Producer2(BlockingQueue<Object> queue)
    {
        this.queue = queue;
        t = new Thread(this);
        getT().start();
    }

    public void run()
    {
        FileInputStream in = null;
        try
        {
            in = new FileInputStream("exampleStream.xml");
            int len = 0;
            int count;
            byte[] data = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = in.read(data)) != -1)
            {
                baos.write(data, 0, len);
                queue.put(baos.toByteArray());
                baos.reset();

            }
            queue.put(DONE);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Thread getT()
    {
        return t;
    }
}
