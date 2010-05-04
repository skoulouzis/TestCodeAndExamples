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

package spiros.info;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author skoulouz
 */
public class Inforamtions
{

    public static String getIPOrHostName()
    {
        InetAddress addr;
        byte[] ipAddr;
        String ipAddrStr = "";
        String addrStr = null;
        try
        {
            addr = InetAddress.getLocalHost();

            // Get IP Address
            ipAddr = addr.getAddress();

            for (int i = 0; i < ipAddr.length; i++)
            {
                if (i > 0)
                {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }

            addr = InetAddress.getByName(ipAddrStr);
            addrStr = addr.getHostName();

        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();

        }
        return ipAddrStr;
    }
}
