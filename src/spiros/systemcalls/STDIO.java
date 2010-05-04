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

package spiros.systemcalls;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author alogo
 */
public class STDIO
{
    private static String montageHome = "/home/alogo/workspace/Montage_v3.0/bin/";

    private static String montageData = "/home/alogo/workspace/montage_101/";

    private static String[] args = { "-h 0", "-", "-", montageData + "/template.hdr" };

    public void call1()
    {
        try
        {
            OutputStream stdin = null;
            InputStream stderr = null;
            InputStream stdout = null;

            // launch EXE and grab stdin/stdout and stderr
            String cmd = montageHome + "mProjectPP" + " " + args[0] + " " + args[1] + " " + args[2] + " " + args[3];
            Process process = Runtime.getRuntime().exec(cmd);
            stdin = process.getOutputStream();
            stderr = process.getErrorStream();
            stdout = process.getInputStream();

            FileInputStream ins = new FileInputStream(
                    "/home/alogo/workspace/montage_101/rawdir/2mass-atlas-990214n-j1100244.fits");
            // "write" the parms into stdin
            int len = IOUtils.copy(ins, stdin);
            stdin.flush();
            stdin.close();

            // clean up if any output in stdout
            FileOutputStream fos = new FileOutputStream("/tmp/file");
            len = IOUtils.copy(stdout, fos);
            stdout.close();
            fos.close();

            // clean up if any output in stderr
            // brCleanUp = new BufferedReader(new InputStreamReader(stderr));
            // while ((line = brCleanUp.readLine()) != null) {
            // System.out.println ("[Stderr] " + line);
            // }
            // brCleanUp.close();
            // System.out.println ("Out: " + process.waitFor());
        }
        // catch (InterruptedException ex) {
        // Logger.getLogger(STDIO.class.getName()).log(Level.SEVERE, null, ex);
        // }
        catch (IOException ex)
        {
            Logger.getLogger(STDIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
