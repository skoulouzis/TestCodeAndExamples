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

package spiros.HTTP;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReverseServlet1 extends HttpServlet
{

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {

            ServletInputStream sin = req.getInputStream();

            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());

            writer.write("\n" + "I am The other reeee");
            System.out.println("I am The other reeee");

            ServletContext context = getServletContext();
            InputStream fis = (InputStream) context.getAttribute("file.in");

            byte[] input = new byte[128];
            int c, count = 0;
            while ((c = fis.read(input, count, input.length - count)) != -1)
            {
                writer.write(new String(input));
            }

            // writer.write("\n"+this.getServletInfo());
            // writer.write("\n"+this.getServletName());
            // writer.write("\n"+this.getClass().getName());
            // writer.write("\n"+this.getClass().getSimpleName());
            // while ((c = sin.read(input, count, input.length - count)) != -1)
            // {
            // count += c;
            // String inString = new String(input);
            // writer.write(inString);
            // }
            writer.flush();
            writer.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(ReverseServlet1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
