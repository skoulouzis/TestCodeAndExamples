package spiros.HTTP;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReverseServlet extends HttpServlet
{
    private static String message = "Error during Servlet processing";

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            int len = req.getContentLength();
            byte[] input = new byte[len];

            ServletInputStream sin = req.getInputStream();
            int c, count = 0;
            while ((c = sin.read(input, count, input.length - count)) != -1)
            {
                count += c;
            }
            sin.close();

            String inString = new String(input);
            int index = inString.indexOf("=");
            if (index == -1)
            {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(message);
                resp.getWriter().close();
                return;
            }
            String value = inString.substring(index + 1);

            // decode application/x-www-form-urlencoded string
            String decodedString = URLDecoder.decode(value, "UTF-8");

            // reverse the String
            String reverseStr = (new StringBuffer(decodedString)).reverse().toString();

            // set the response code and write the response data
            resp.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());

            writer.write(reverseStr);
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            try
            {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            }
            catch (IOException ioe)
            {
            }
        }

    }

}
