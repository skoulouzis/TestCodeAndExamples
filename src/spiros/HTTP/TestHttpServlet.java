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

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author alogo
 */
public class TestHttpServlet
{

    public void test1(String servlet, String str) throws Exception
    {
        // String stringToReverse = URLEncoder.encode(str, "UTF-8");

        URL url = new URL(servlet);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(str);
        out.close();

        // BufferedReader in = new BufferedReader(new
        // InputStreamReader(connection.getInputStream()));
        //
        // String decodedString;
        // String strUrl="";
        //
        // while ((decodedString = in.readLine()) != null) {
        // strUrl = strUrl + decodedString;
        // }
        // System.out.println("'"+strUrl+"'");
        // in.close();

        // url = new URL(strUrl);
        // connection = url.openConnection();
        // connection.setDoOutput(true);
        // out = new OutputStreamWriter(connection.getOutputStream());
        // out.write(str);
        // out.close();
        // in = new BufferedReader(new
        // InputStreamReader(connection.getInputStream()));
        //
        // while ((decodedString = in.readLine()) != null) {
        // System.out.println(decodedString);
        // }
        // in.close();

    }
}
