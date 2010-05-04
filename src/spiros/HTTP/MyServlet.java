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

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet

{

    public void doGet(HttpServletRequest request, HttpServletResponse response)

    throws IOException, ServletException

    {
        // http://localhost:8080/axis/MyServlet?file=fits
        System.out.println("Got somthing  " + request.getRequestURI());
        System.out.println("New req " + request.getQueryString());
        Iterator it = request.getParameterMap().keySet().iterator();

        while (it.hasNext())
        {
            System.out.println("Key " + it.next());
            it.getClass();
        }

        // PrintWriter out = response.getWriter();
        // FileInputStream fis = new FileInputStream("/tmp/file");

        // out.println("<html>");

        // out.println("<head>");
        //
        // out.println("<title>MyServlet</title>");
        //
        // out.println("</head>");
        //
        // out.println("<body>");

        // out.println("<h1>MyServlet</h1>");

        // out.println("<p>Hello, my servlet works!");
        // IOUtils.copy( fis,out);
        // fis.close();
        // out.flush();
        // out.close();
        // out.println("</body>");

        // out.println("</html>");

    }

}