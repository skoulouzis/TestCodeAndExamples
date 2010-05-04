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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author alogo
 */
public class FITServer extends HttpServlet
{

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        FileInputStream fis = null;
        {
            ServletInputStream sin = null;
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream()));
                fis = new FileInputStream(in.readLine());
                IOUtils.copy(in, resp.getOutputStream());

            }
            catch (IOException ex)
            {
                Logger.getLogger(FITServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                try
                {
                    fis.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(FITServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                try
                {
                    sin.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(FITServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // public void doPost(HttpServletRequest req, HttpServletResponse resp) {
    // BufferedOutputStream out = null;
    // try {
    // out = new BufferedOutputStream(resp.getOutputStream());
    // call(out);
    // } catch (IOException ex) {
    // Logger.getLogger(FITServer.class.getName()).log(Level.SEVERE, null, ex);
    // } finally {
    // try {
    // out.flush();
    // out.close();
    // } catch (IOException ex) {
    // Logger.getLogger(FITServer.class.getName()).log(Level.SEVERE, null, ex);
    // }
    // }
    //        
    // }

    public void call(BufferedOutputStream out)
    {
        Process p = null;
        String line = null;
        String montageHome = "/home/alogo/workspace/Montage_v3.0/";
        String module = "bin/mAdd";
        String[] args = { "-p /home/alogo/workspace/montage_101/projdir ",
                "/home/alogo/workspace/montage_101/images.tbl", "/home/alogo/workspace/montage_101/template.hdr", "-" };
        try
        {
            Runtime rt = Runtime.getRuntime();
            String cmd = montageHome + module + " " + args[0] + " " + args[1] + " " + args[2] + " " + args[3];
            System.out.println("cmd: " + cmd);
            p = rt.exec(cmd);

            // output = new StringBuffer();
            BufferedInputStream in = new BufferedInputStream(p.getInputStream(), 1024);
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = in.read(data)) != -1)
            {
                // copy stream
                out.write(data, 0, len);
                // System.out.println(new String(data,0,len));
            }
            p.exitValue();
            p.waitFor();
            p.getInputStream().close();
            in.close();
            p.destroy();
            System.out.println("Exit: " + p.exitValue());
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
