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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author alogo
 */
public class TestFITServer
{

    public void test1(String servlet, String str) throws Exception
    {
        // String stringToReverse = URLEncoder.encode(str, "UTF-8");
        System.out.println("servlet:" + servlet);
        URL url = new URL(servlet);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write("/home/alogo/workspace/montage_101/rawdir/2mass-atlas-990214n-j1100244.fits\n");
        out.flush();
        out.close();

        BufferedInputStream in = new BufferedInputStream(connection.getInputStream(), 1024);

        FileOutputStream fos = new FileOutputStream("/tmp/File.fits");
        IOUtils.copy(in, fos);
        // byte[] data = new byte[1024];
        // int len = 0;
        // while ((len = in.read(data)) != -1) {
        // fos.write(data, 0, len);
        // }
        call(in);
        // in.close();
        // fos.flush();
        // fos.close();
    }

    public void call(BufferedInputStream in)
    {
        Process p = null;
        String line = null;
        String montageHome = "/home/alogo/workspace/Montage_v3.0/";
        String module = "bin/mJPEG";
        String[] args = { "-gray -", "20%", "99.98%", "loglog", "-out /tmp/file.jpg" };

        try
        {
            // in = new BufferedInputStream(new
            // FileInputStream("/home/alogo/workspace/montage_101/final/m101_uncorrected.fits"));
            Runtime rt = Runtime.getRuntime();
            String cmd = montageHome + module + " " + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " "
                    + args[4];
            System.out.println("cmd: " + cmd);
            p = rt.exec(cmd);

            // BufferedWriter bw = new BufferedWriter(new
            // OutputStreamWriter(p.getOutputStream()));

            // output = new StringBuffer();
            BufferedOutputStream out = new BufferedOutputStream(p.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = in.read(data)) != -1)
            {
                // copy stream
                out.write(data, 0, len);
                // bw.write(new String(data,0,len));
            }
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null)
            {
                System.out.println("out: " + line);
            }

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
