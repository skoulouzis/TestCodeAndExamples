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
public class Reader
{
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    final int numWorkers = 1;

    Client[] workers = new Client[numWorkers];

    /** Creates a new instance of Reader */
    public Reader()
    {
        for (int i = 0; i < workers.length; i++)
        {
            workers[i] = new Client(queue);
            workers[i].start();
        }
    }

    public Integer read()
    {
        try
        {
            return queue.take();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

}
