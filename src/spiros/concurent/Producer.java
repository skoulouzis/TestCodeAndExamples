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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author alogo
 */
public class Producer
{
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    final int numWorkers = 1;

    Server[] workers = new Server[numWorkers];

    /** Creates a new instance of Producer */
    public Producer()
    {
        for (int i = 0; i < workers.length; i++)
        {
            workers[i] = new Server(queue);
            workers[i].start();
        }
    }

    public void write(int data)
    {
        try
        {
            queue.put(data);

            // // Add special end-of-stream markers to terminate the workers
            // for (int i=0; i<workers.length; i++) {
            // queue.put(Server.NO_MORE_WORK);
            // }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
