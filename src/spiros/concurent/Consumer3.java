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

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

/**
 * 
 * @author alogo
 */
public class Consumer3 implements Runnable
{

    private Thread t;

    private Integer DONE = -999;

    private MyBlockingQueue q;

    /** Creates a new instance of Consumer3 */
    public Consumer3(MyBlockingQueue q)
    {
        this.q = q;
        t = new Thread(this);
        getT().start();
    }

    public void run()
    {
        byte[] data = new byte[100];
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Object obj = null;

        try
        {
            FileOutputStream out = new FileOutputStream("exampleStreamRead.xml");
            // XMLStreamReader reader =
            // XMLInputFactory.newInstance().createXMLStreamReader(in);
            while (true)
            {

                obj = q.pop();
                if (obj.getClass().isArray())
                {
                    data = (byte[]) obj;
                    // System.out.println(new String(data));
                    out.write(data);
                    out.flush();
                }
                else
                {
                    break;
                }
            }
            out.flush();
            out.flush();
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
