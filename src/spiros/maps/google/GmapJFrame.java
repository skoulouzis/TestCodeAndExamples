package spiros.maps.google;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import nl.uva.vlet.exception.VlServerException;
import nl.uva.vlet.util.bdii.BDIIQuery;
import nl.uva.vlet.util.bdii.BdiiException;
import nl.uva.vlet.util.bdii.info.glue.GlueObject;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class GmapJFrame extends javax.swing.JFrame
{

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                GmapJFrame inst = new GmapJFrame();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
            }
        });
    }

    public GmapJFrame()
    {
        super();
        initGUI();

        mapExample1();

        // mapExample2();

    }

    private void mapExample2()
    {

    }

    private void mapExample1()
    {

    }

    private void initGUI()
    {
        try
        {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pack();
            setSize(400, 300);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Vector<GlueObject> testSimpleQuery(String type) throws URISyntaxException, BdiiException,
            VlServerException
    {
        // ldap://localhost:389/o=JNDITutorial
        String searchPhrase;
        BDIIQuery q = new BDIIQuery(new URI("ldap://bdii.grid.sara.nl:2170/"));

        String VO = "pvier";
        searchPhrase = "(&(objectClass=" + type + "))";
        // searchPhrase = "(&(objectClass=" + type
        // + ")(GlueSAAccessControlBaseRule=" + VO + "))";

        ArrayList<SearchResult> res = q.query(searchPhrase);

        Vector<GlueObject> allOfTheSA = new Vector<GlueObject>();

        for (int i = 0; i < res.size(); i++)
        {
            javax.naming.directory.SearchResult theRes = res.get(i);
            Attributes attr = theRes.getAttributes();
            NamingEnumeration<? extends Attribute> allAttr = attr.getAll();
            try
            {
                // debug("-------------");
                GlueObject GlueSA = new GlueObject(type, allAttr);
                allOfTheSA.add(GlueSA);

            }
            catch (NamingException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return allOfTheSA;
    }

}

class GoogleMapsTileProvider
{
    private static final String VERSION = "2.75";

    private static final int minZoom = 1;

    private static final int maxZoom = 16;

    private static final int mapZoom = 17;

    private static final int tileSize = 256;

    private static final boolean xr2l = true;

    private static final boolean yt2b = true;

    // private static final String baseURL =
    // "http://mt1.google.com/mt?n=404&v=w" + VERSION;
    private static final String baseURL = "http://wms.jpl.nasa.gov/wms.cgi?";

    private static final String x = "x";

    private static final String y = "y";

    private static final String z = "zoom";

    private static final TileFactoryInfo GOOGLE_MAPS_TILE_INFO = new TileFactoryInfo(minZoom, maxZoom, mapZoom,
            tileSize, xr2l, yt2b, baseURL, x, y, z);

    public static TileFactory getDefaultTileFactory()
    {
        return (new DefaultTileFactory(GOOGLE_MAPS_TILE_INFO));
    }
}
