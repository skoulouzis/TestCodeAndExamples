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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.edit.PDPageContentStream;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.pdmodel.font.PDSimpleFont;
import org.pdfbox.pdmodel.font.PDType1Font;
import org.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.pdfbox.util.PDFTextStripper;

/**
 * 
 * @author alogo
 */
public class createPDF
{

    /** Creates a new instance of createPDF */
    public createPDF()
    {
    }

    public void go1(int pdfSize)
    {
        PDDocument doc = null;
        int size = 0;
        try
        {
            doc = new PDDocument();

            PDPage page;
            PDFont font = PDType1Font.COURIER;
            String data;

            for (int j = 0; j < pdfSize; j++)
            {
                System.out.println("page " + j);
                page = new PDPage();
                doc.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                contentStream.beginText();
                contentStream.setFont(font, 11);
                contentStream.moveTextPositionByAmount(30, 700);
                java.util.Random r = new java.util.Random();
                String token = Long.toString(Math.abs(r.nextLong()), 36);
                for (int i = 0; i < 30; i++)
                {
                    data = " These are some test data for testing a test. And now some numbers: " + i + " "
                            + r.nextInt();
                    contentStream.moveTextPositionByAmount(0, -10);
                    contentStream.drawString(data);
                    size = size + data.getBytes().length;
                }
                PDXObjectImage ximage = null;
                String image = "/home/alogo/Pictures/suncombo1.jpg";
                if (image.toLowerCase().endsWith(".jpg"))
                {
                    ximage = new PDJpeg(doc, new FileInputStream(image));
                }
                else if (image.toLowerCase().endsWith(".tif") || image.toLowerCase().endsWith(".tiff"))
                {
                    ximage = new PDCcitt(doc, new RandomAccessFile(new File(image), "r"));
                }
                else
                {
                    // BufferedImage awtImage = ImageIO.read( new File( image )
                    // );
                    // ximage = new PDPixelMap(doc, awtImage);
                    throw new IOException("Image type not supported:" + image);
                }

                // contentStream.moveTextPositionByAmount( 0, -10);
                contentStream.drawImage(ximage, 50, 50);

                if (j >= (pdfSize - 1))
                {
                    data = "That's all.... THE END IS HERE!!";
                }
                else
                {
                    data = "The End of page" + (j + 1) + "/" + pdfSize;
                }

                contentStream.moveTextPositionByAmount(0, -10);
                contentStream.drawString(data);
                size = size + data.getBytes().length;

                contentStream.endText();
                contentStream.close();
            }
            doc.save("/home/alogo/testTrans/client/test-" + size + "b.pdf");
            doc.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.out.println("wrote " + size + " kbytes");
    }

    /**
     * Create a PDF document with some text.
     * 
     * @param text
     *            The stream of text data.
     * 
     * @return The document with the text in it.
     * 
     * @throws IOException
     *             If there is an error writing the data.
     */
    public PDDocument createPDFFromText(Reader text) throws IOException
    {
        PDDocument doc = null;
        int fontSize = 10;
        PDSimpleFont font = PDType1Font.HELVETICA;
        try
        {

            int margin = 40;
            float height = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000;

            // calculate font height and increase by 5 percent.
            height = height * fontSize * 1.05f;
            doc = new PDDocument();
            BufferedReader data = new BufferedReader(text);
            String nextLine = null;
            PDPage page = new PDPage();
            PDPageContentStream contentStream = null;
            float y = -1;
            float maxStringLength = page.getMediaBox().getWidth() - 2 * margin;
            while ((nextLine = data.readLine()) != null)
            {

                String[] lineWords = nextLine.trim().split(" ");
                int lineIndex = 0;
                while (lineIndex < lineWords.length)
                {
                    StringBuffer nextLineToDraw = new StringBuffer();
                    float lengthIfUsingNextWord = 0;
                    do
                    {
                        nextLineToDraw.append(lineWords[lineIndex]);
                        nextLineToDraw.append(" ");
                        lineIndex++;
                        if (lineIndex < lineWords.length)
                        {
                            String lineWithNextWord = nextLineToDraw.toString() + lineWords[lineIndex];
                            lengthIfUsingNextWord = (font.getStringWidth(lineWithNextWord) / 1000) * fontSize;
                        }
                    } while (lineIndex < lineWords.length && lengthIfUsingNextWord < maxStringLength);
                    if (y < margin)
                    {
                        page = new PDPage();
                        doc.addPage(page);
                        if (contentStream != null)
                        {
                            contentStream.endText();
                            contentStream.close();
                        }
                        contentStream = new PDPageContentStream(doc, page);
                        contentStream.setFont(font, fontSize);
                        contentStream.beginText();
                        y = page.getMediaBox().getHeight() - margin + height;
                        contentStream.moveTextPositionByAmount(margin, y);

                    }
                    // System.out.println( "Drawing string at " + x + "," + y );

                    contentStream.moveTextPositionByAmount(0, -height);
                    y -= height;
                    contentStream.drawString(nextLineToDraw.toString());
                }

            }
            if (contentStream != null)
            {
                contentStream.endText();
                contentStream.close();
            }
        }
        catch (IOException io)
        {
            if (doc != null)
            {
                doc.close();
            }
            throw io;
        }
        return doc;
    }

    public void streamPDFContent()
    {
        PDDocument doc;
        byte[] tmp = new byte[4048];
        try
        {
            doc = new PDDocument();

            FileInputStream fis = new FileInputStream("/home/alogo/testTrans/GridComputing.pdf");

            // PDStream pdfStream = new PDStream(doc,fis,true);

            // fis.read(tmp);
            // COSString costr = new COSString(tmp);
            // PDFParser pa = new PDFParser(fis);

            // PDFTextStripper stripper = new PDFTextStripper();

            // com.lowagie.text.pdf.ByteBuffer by = new
            // com.lowagie.text.pdf.ByteBuffer();
            // by.append(tmp);
            // System.out.println(by.toString("ISO8859_1"));

            // com.lowagie.text.pdf.RandomAccessFileOrArray raa = new
            // com.lowagie.text.pdf.RandomAccessFileOrArray(tmp);
            // com.lowagie.text.pdf.RandomAccessFileOrArray raa = new
            // com.lowagie.text.pdf.RandomAccessFileOrArray(fis);
            //
            // // raa.close();
            //
            // com.lowagie.text.pdf.PdfString pdfString= new
            // com.lowagie.text.pdf.PdfString(tmp);
            //
            // System.out.println(pdfString.toUnicodeString());

            // PdfReader reader = new
            // PdfReader("/home/alogo/testTrans/GridComputing.pdf");

            // PDFParser parser = new PDFParser(new ByteArrayInputStream( tmp )
            // );
            // parser.parse();
            //
            //
            // PDFTextStripper stripper = new PDFTextStripper();
            // System.out.println( stripper.getText( parser.getDocument()) );
            // int num = reader.getNumberOfPages();
            // com.lowagie.text.pdf.PdfStamper stamp=null;
            // try {
            // stamp = new com.lowagie.text.pdf.PdfStamper(reader, new
            // FileOutputStream("/home/alogo/testTrans/stamped.pdf"));
            // com.lowagie.text.pdf.PdfContentByte under =
            // stamp.getUnderContent(1);
            // // change the content beneath page 1
            // com.lowagie.text.pdf.PdfContentByte over =
            // stamp.getOverContent(1);
            // // change the content on top of page 1
            // stamp.close();
            // } catch (FileNotFoundException ex) {
            // ex.printStackTrace();
            // } catch (DocumentException ex) {
            // ex.printStackTrace();
            // } catch (IOException ex) {
            // ex.printStackTrace();
            // }

            // System.out.println(
            // parseUsingPDFBox("/home/alogo/testTrans/GridComputing.pdf") );

            PDFParser parser = new PDFParser(fis);
            parser.parse();
            fis.close();
            doc = parser.getPDDocument();

            PDFTextStripper stripper = new PDFTextStripper();

            System.out.println(stripper.getText(doc));
            doc.close();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private String parseUsingPDFBox(String filename)
    {
        PDFTextStripper stripper = null;
        PDDocument doc = null;
        try
        {
            doc = PDDocument.load(filename);
            stripper = new PDFTextStripper();

            return stripper.getText(doc);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    // public String GetTextFromPdf(String filename) throws Exception{
    // String temp=null;
    // PDDocument pdfdocument=null;
    // FileInputStream is=new FileInputStream(filename);
    // PDFParser parser = new PDFParser( is );
    // parser.parse();
    // pdfdocument = parser.getPDDocument();
    // ByteArrayOutputStream out = new ByteArrayOutputStream();
    // OutputStreamWriter writer = new OutputStreamWriter( out );
    // PDFTextStripper stripper = new PDFTextStripper();
    // stripper.writeText(pdfdocument.getDocument(), writer );
    // writer.close();
    // byte[] contents = out.toByteArray();
    // String ts=new String(contents);
    // System.out.println("the string length is"+contents.length+"\n");
    // return ts;
    // }

    // public void myLoad(String file){
    // com.asprise.util.pdf.PDFReader reader;
    // try {
    // reader = new com.asprise.util.pdf.PDFReader(new File(file));
    // reader.open(); // open the file.
    // int pages = reader.getNumberOfPages();
    //            
    // reader.extractTextFromPage(10);
    // // for(int i=0; i < pages; i++) {
    // // String text = reader.extractTextFromPage(i);
    // // System.out.println("Page " + i + ": " + text);
    // // }
    //            
    // reader.close(); // finally, close the file.
    // }catch(Exception ex){
    // ex.printStackTrace();
    // }
    //        
    //        
    // }
}
