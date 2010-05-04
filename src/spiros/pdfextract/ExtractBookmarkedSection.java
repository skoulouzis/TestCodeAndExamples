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

//import com.snowtide.pdf.Bookmark;
//import com.snowtide.pdf.OutputTarget;
//import com.snowtide.pdf.PDFTextStream;
//import com.snowtide.pdf.Page;
//import com.snowtide.pdf.layout.Block;
//import com.snowtide.pdf.layout.BlockParent;

public class ExtractBookmarkedSection
{
    /**
     * Extracts from the given PDF file only the text from the section that is
     * delimited by a PDF Bookmark with the given section title.
     */
    // public static String extractSectionText(File pdffile, String
    // sectionTitle)throws IOException {
    // PDFTextStream stream = new PDFTextStream(pdffile);
    // Page p = stream.getPage(2);
    // int starttop = -1;
    // StringBuffer sb = new StringBuffer(1024);
    // // OutputTarget tgt = OutputTarget.forBuffer(sb);
    // // removeBlocksAbove(p.getTextContent(), starttop);
    //        
    // char[] c = new char[20048];
    // p.getStream().read(c);
    //        
    // System.out.println(c);
    //        
    // stream.close();
    // Bookmark root = stream.getBookmarks();
    // List allbookmarks = root.getAllDescendants();
    // Collections.sort(allbookmarks, new DocumentOrderBookmarkComparator());
    // Bookmark bm;
    //        
    // int startpage, endpage;
    // float starttop, endtop;
    // starttop = endtop = startpage = endpage = -1;
    // for (int i = 0, len = allbookmarks.size(); i < len; i++) {
    // bm = (Bookmark)allbookmarks.get(i);
    // if (bm.getTitle().equals(sectionTitle)) {
    // startpage = bm.getPageNumber();
    // starttop = bm.getTopBound();
    // if (i + 1 < len) {
    // bm = (Bookmark)allbookmarks.get(i + 1);
    // endpage = bm.getPageNumber();
    // endtop = bm.getTopBound();
    // }
    // break;
    // }
    // }
    // // couldn't find section start from title
    // if (startpage == -1) return null;
    // // handle when we're extracting the last bookmarked section
    // if (endpage == -1) endpage = stream.getPageCnt() - 1;
    // Page page;
    // StringBuffer sb = new StringBuffer(1024);
    // OutputTarget tgt = OutputTarget.forBuffer(sb);
    // for (int i = startpage; i <= endpage; i++) {
    // page = stream.getPage(i);
    // if (i == startpage && starttop != -1) {
    // // remove all blocks above bookmark,
    // // if bookmark bound is defined
    // removeBlocksAbove(page.getTextContent(), starttop);
    // } else if (i == endpage && endtop != -1) {
    // // remove all blocks below end bookmark,
    // // if bookmark bound is defined
    // removeBlocksBelow(page.getTextContent(), endtop);
    // }
    // page.pipe(tgt);
    // }
    // stream.close();
    // return sb.toString();
    // return "";//sb.toString();
    // }

    // private static void removeBlocksAbove(BlockParent blocks, float pos) {
    // Block b;
    // for (int i = blocks.getChildCnt() - 1; i > -1; i--) {
    // b = blocks.getChild(i);
    // if (b.ypos() >= pos) {
    // blocks.removeChild(i);
    // } else {
    // removeBlocksAbove(b, pos);
    // }
    // }
    // }
    /**
     * Removes all of the child blocks within the given BlockParent instance
     * that are positioned below the given y-coordinate position.
     */
    // private static void removeBlocksBelow(BlockParent blocks, float pos) {
    // Block b;
    // for (int i = blocks.getChildCnt() - 1; i > -1; i--) {
    // b = blocks.getChild(i);
    // if (b.endypos() <= pos) {
    // blocks.removeChild(i);
    // } else {
    // removeBlocksAbove(b, pos);
    // }
    // }
    // }
    /**
     * Orders the Bookmarks within a List according to where they refer within
     * the document (technically, bookmarks can refer to any page, any location,
     * and not necessarily be in a typical reading order within the tree).
     */
    // private static class DocumentOrderBookmarkComparator implements
    // Comparator {
    // private Bookmark b1, b2;
    // public int compare(Object o1, Object o2) {
    // b1 = (Bookmark)o1;
    // b2 = (Bookmark)o2;
    // if (b1.getPageNumber() < b2.getPageNumber()) {
    // return -1;
    // } else if (b1.getPageNumber() > b2.getPageNumber()) {
    // return 1;
    // } else {
    // if (b1.getTopBound() < b2.getTopBound()) {
    // return -1;
    // } else if (b1.getTopBound() == b2.getTopBound()) {
    // return 0;
    // } else {
    // return 1;
    // }
    // }
    // }
    // }

}