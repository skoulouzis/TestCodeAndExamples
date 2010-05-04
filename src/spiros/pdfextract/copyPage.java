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

import java.io.IOException;
import java.util.List;

import org.pdfbox.exceptions.COSVisitorException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;

/**
 * 
 * @author alogo
 */
public class copyPage
{

    /** Creates a new instance of copyPage */
    public copyPage()
    {
    }

    public void go(int pageNum)
    {
        PDDocument doc;
        try
        {
            doc = new PDDocument();
            PDDocument doc2 = new PDDocument();
            doc = PDDocument.load("/home/alogo/testTrans/client/testPDF.pdf");
            List pages = doc.getDocumentCatalog().getAllPages();
            PDPage page = (PDPage) pages.get(1);

            for (int i = 0; i < pageNum; i++)
            {
                // doc2.addPage(page);
                doc2.importPage(page);
            }
            doc2.save("/home/alogo/testTrans/client/testPDF2.pdf");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (COSVisitorException ex)
        {
            ex.printStackTrace();
        }

    }
}
