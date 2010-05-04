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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.pdfbox.exceptions.CryptographyException;
import org.pdfbox.exceptions.InvalidPasswordException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.util.PDFTextStripper;

/**
 * 
 * @author alogo
 */
public class ExtractPDFPage
{

    /** Creates a new instance of ExtractPDFPage */
    public ExtractPDFPage()
    {

    }

    public void go()
    {
        byte[] tmp = new byte[100000];
        byte[] tmp1 = new byte[2048];
        try
        {
            PDDocument doc = new PDDocument();
            // File file = new
            // File("/home/alogo/workspace/AIDA/pdfDir/testPdfs/test-492994717b.pdf");
            // RandomAccessFile raf = new RandomAccessFile(file, "r");
            // FileInputStream fis = new
            // FileInputStream("/home/alogo/workspace/AIDA/pdfDir/testPdfs/test-492994717b.pdf");
            //            
            doc = PDDocument.load("/home/alogo/workspace/AIDA/pdfDir/testPdfs/test-492994717b.pdf");
            List pages = doc.getDocumentCatalog().getAllPages();

            PDPage page = (PDPage) pages.get(1);

            System.out.println(new String(page.getContents().getByteArray()));

            // PDStream contents = page.getContents();
            // PDFStreamParser parser = new PDFStreamParser(
            // contents.getStream() );
            // parser.parse();
            //                
            // PDFTextStripper stripper = new PDFTextStripper();

            // fis.read(tmp);
            // myParser p = new myParser(tmp);
            // p.parse();

            // System.out.println(new String(tmp));

            // p.getPdfSource().read(tmp1);
            // System.out.println(new String(tmp1));

            // doc = p.getPDDocument();
            // doc.getNumberOfPages();

            // doc.save("/home/alogo/testTrans/GridComputing_TEST.pdf");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    //    
    // public void go2(){
    // File file = new File("/home/alogo/testTrans/GridComputing.pdf");
    // RandomAccessFile raf;
    // try {
    // raf = new RandomAccessFile(file, "r");
    // FileChannel channel = raf.getChannel();
    // ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0,
    // channel.size());
    // PDFFile pdffile = new PDFFile(buf);
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // }
    //
    // }

    public String pdf2Txt(File file, String saveTo)
    {

        long start = System.currentTimeMillis();
        File txtFile = null;
        try
        {
            PDDocument pdfDocument = PDDocument.load(file);
            if (pdfDocument.isEncrypted())
            {
                // Just try using the default password and move on
                pdfDocument.decrypt("");
            }

            txtFile = new File(saveTo + "/" + file.getName() + ".txt");

            FileWriter fileWriter = new FileWriter(txtFile);
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.writeText(pdfDocument, fileWriter);
            pdfDocument.close();
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (CryptographyException ex)
        {
            ex.printStackTrace();
        }
        catch (InvalidPasswordException ex)
        {
            ex.printStackTrace();
        }
        return txtFile.getAbsolutePath();
    }
}
