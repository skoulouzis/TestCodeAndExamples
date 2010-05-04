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

package spiros.pdfextract;

import org.pdfbox.PDFSplit;

/**
 * 
 * @author alogo
 */
public class splitPdf
{

    /** Creates a new instance of splitPdf */
    public splitPdf()
    {
        PDFSplit split = new PDFSplit();
        String[] ar = { "/home/alogo/workspace/AIDA/pdfDir/testPdfs/test-492994717b.pdf", "-password <password>",
                "-split <integer>" };
        try
        {
            split.main(ar);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        // split.split( ar );
    }

}
