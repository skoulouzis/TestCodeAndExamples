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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author alogo
 */
public class MImgtbl
{

    public static String call()
    {
        Process p = null;
        String line = null;
        StringBuffer output = null;
        String montageHome = "/home/alogo/workspace/Montage_v3.0/";
        try
        {
            Runtime rt = Runtime.getRuntime();
            System.out.println("cmd: " + montageHome
                    + "/bin/mImgtbl /home/alogo/workspace/montage_101/rawdir /dev/null");
            p = rt.exec(montageHome + "/bin/mImgtbl /home/alogo/workspace/montage_101/rawdir out");

            output = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null)
            {
                // copy stream
                output.append(line);
                output.append("\n");
            }
            p.waitFor();
            p.getInputStream().close();
            br.close();
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
        return output.toString();
    }

    public static String call2()
    {
        StringBuffer output = null;
        try
        {
            String montageHome = "/home/alogo/workspace/montage_101/";
            String module = "a.out";
            String[] args = { "-gray -", "20%", "99.98%", "loglog", "-out /tmp/file.jpg" };
            Process p = null;

            Runtime rt = Runtime.getRuntime();
            String cmd = montageHome + module; // + args[0] + " " + args[1] +
                                               // " " + args[2] + " " + args[3]
                                               // + " " + args[4];
            System.out.println("cmd: " + cmd);
            p = rt.exec(cmd);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            bw.write(args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
            bw.flush();

            String line;
            output = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null)
            {
                // copy stream
                output.append(line);
                output.append("\n");
            }

            p.waitFor();
            p.getInputStream().close();
            br.close();
            p.destroy();
            System.out.println("Exit: " + p.exitValue());
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(MImgtbl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(MImgtbl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output.toString();
    }

    public static void call3()
    {
        String montageHome = "/home/alogo/workspace/montage_101/";
        String module = "a.out";
        String cmd = montageHome + module;
        // String cmd = "perl PerlTest.pl";
        // String[] cmd = { "perl", "PerlTest.pl" };
        // String cmd = "java JavaTest";

        System.out.println("********** Start of Test **********");
        Runtime rt = Runtime.getRuntime();

        Process proc = null;
        try
        {
            proc = rt.exec(cmd);

            InputStreamReader isrStdout = new InputStreamReader(proc.getInputStream());
            InputStreamReader isrStderr = new InputStreamReader(proc.getErrorStream());
            OutputStream os = proc.getOutputStream();

            BufferedReader brStdout = new BufferedReader(isrStdout);
            BufferedReader brStderr = new BufferedReader(isrStderr);

            String line = null;
            String msg = "HELLO\n";

            line = brStdout.readLine();
            System.out.println(line);
            line = brStderr.readLine();
            System.out.println(line);
            line = brStdout.readLine();
            System.out.println(line);
            os.write(msg.getBytes());
            os.flush();
            line = brStdout.readLine();
            System.out.println(line);

        }
        catch (IOException io)
        {
            System.out.println("got IO exception " + io);
            io.printStackTrace();
        }
        System.out.println("********** End of Test **********");
    }

    public static void call4()
    {
        try
        {
            Process p = Runtime.getRuntime().exec("/home/alogo/workspace/montage_101/a.out");
            BufferedInputStream buffer = new BufferedInputStream(p.getInputStream());
            BufferedReader commandResult = new BufferedReader(new InputStreamReader(buffer));

            BufferedOutputStream bufferout = new BufferedOutputStream(p.getOutputStream());
            PrintWriter commandInput = new PrintWriter((new OutputStreamWriter(bufferout)), true);
            String s = null;

            // while (true) {
            // s = commandResult.readLine();
            // if (s.equals("Dattapuram")) {
            // break;
            // }
            // System.out.println("Output: " + s);
            // System.out.flush();
            // }

            commandInput.write("I Like New York");
            commandInput.write("exit");

            while ((s = commandResult.readLine()) != null)
            {
                System.out.println("Output: " + s);
            }

            commandInput.close();
            commandResult.close();

            if (p.exitValue() != 0)
            {
                System.out.println(" -- p.exitValue() != 0");
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception ::" + e);
        }
    }
}
