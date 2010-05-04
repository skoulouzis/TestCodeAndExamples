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
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PresentationServlet extends HttpServlet
{

    private static class ItemNotFoundException extends Exception
    {

        ItemNotFoundException()
        {
            super("Item not found");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String item = req.getParameter("item");
        if (item == null)
        {
            req.setAttribute("exception", new ItemNotFoundException());
            getServletContext().getRequestDispatcher("/servlet/ErrorServlet").forward(req, res);
        }
        else
        {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.print("<HTML><HEAD><TITLE>Item " + item + "</TITLE>" + "</HEAD><BODY>Item " + item + ":<P>");
            getServletContext().getRequestDispatcher("/servlet/ItemServlet?item=" + item).include(req, res);
            out.print("</BODY></HTML>");
        }
    }
}
