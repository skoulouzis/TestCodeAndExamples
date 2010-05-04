package spiros.maps.openStreet;

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

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class OpenstreetmapJFrame extends javax.swing.JFrame
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
                OpenstreetmapJFrame inst = new OpenstreetmapJFrame();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
            }
        });
    }

    public OpenstreetmapJFrame()
    {
        super();
        initGUI();

        // mapExample1();

        mapExample2();

    }

    private void mapExample2()
    {
        org.jdesktop.swingx.JXMapKit jXMapKit1;

        jXMapKit1 = new org.jdesktop.swingx.JXMapKit();

        jXMapKit1.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);

        jXMapKit1.setDataProviderCreditShown(true);
        jXMapKit1.setName("jXMapKit1");

        // jXMapKit1.setCenterPosition(new GeoPosition(52, 22, 23, 4, 53, 32));
        // jXMapKit1.setAddressLocation(new GeoPosition(41.881944,-87.627778));
        jXMapKit1.setAddressLocation(new GeoPosition(52.373056, 4.892222));

        this.add(jXMapKit1);
    }

    private void mapExample1()
    {
        final int max = 18;
        // http://us.maps2.yimg.com/us.png.maps.yimg.com/png?v=3.1.0&t=m
        // http://tile.openstreetmap.org

        TileFactoryInfo info = new TileFactoryInfo(0, max, max, 256, true, true, "http://tile.openstreetmap.org", "x",
                "y", "z")
        {
            public String getTileUrl(int x, int y, int zoom)
            {
                zoom = max - zoom;
                return this.baseURL + "/" + zoom + "/" + x + "/" + y + ".png";
            }
        };

        // TileFactoryInfo info = new TileFactoryInfo(0, // min level
        // 8, // max allowed level
        // 9, // max level
        // 256, // tile size
        // true, true, // x/y orientation is normal
        // "http://wesmilepretty.com/gmap2/", // base url
        // "x", "y", "z" // url args for x, y and z
        // )
        // {
        // public String getTileUrl(int x, int y, int zoom)
        // {
        // int wow_zoom = 9 - zoom;
        // String url = this.baseURL;
        // if (y >= Math.pow(2, wow_zoom - 1))
        // {
        // url = "http://int2e.com/gmapoutland2/";
        // }
        // return url + "zoom" + wow_zoom + "maps/" + x + "_" + y
        // + "_" + wow_zoom + ".jpg";
        // }
        // };

        // info.setDefaultZoomLevel(0);

        TileFactory tf = new DefaultTileFactory(info);

        JXMapKit map = new JXMapKit();
        map.setTileFactory(tf);

        map.setCenterPosition(new GeoPosition(52, 22, 23, 4, 53, 32)); // Amsterdam

        // get grid site loccations
        Vector<GlueObject> sites = null;
        String longitude;
        String latitude;

        // create a Set of waypoints
        // Set<Waypoint> waypoints = new HashSet<Waypoint>();
        // try
        // {
        // sites = testSimpleQuery("GlueSite");
        // for (int i = 0; i < sites.size(); i++)
        // {
        // longitude = (String) sites.get(i).getAttribute(
        // "GlueSiteLongitude");
        // if (longitude != null)
        // {
        // longitude = longitude.replace("#", "0");
        // }
        //
        // latitude = (String) sites.get(i).getAttribute(
        // "GlueSiteLatitude");
        // if (longitude != null)
        // {
        // latitude = latitude.replace("#", "0");
        // }
        //
        // if (longitude != null)
        // {
        // waypoints.add(new Waypoint(Double.valueOf(longitude),
        // Double.valueOf(latitude)));
        // }
        // }
        //
        // }
        // catch (BdiiException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // catch (VlServerException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // catch (URISyntaxException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // crate a WaypointPainter to draw the points
        // WaypointPainter painter = new WaypointPainter();
        // painter.setWaypoints(waypoints);
        // map.getMainMap().setOverlayPainter(painter);

        // JXMapViewer mapViewer = new JXMapViewer();
        // mapViewer.setTileFactory(tf);
        // mapViewer.setCenterPosition(new GeoPosition(52, 22, 23, 4, 53, 32));
        // // Amsterdam

        this.add(map);
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
