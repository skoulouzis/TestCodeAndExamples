package spiros.java3d;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Test3D
{

    public static void main(String[] args)
    {
        Test3D g = new Test3D();
        g.show();
    }

    private void show()
    {
        SimpleUniverse universe = new SimpleUniverse();

        BranchGroup group = new BranchGroup();

        group.addChild(new ColorCube(0.3));

        universe.getViewingPlatform().setNominalViewingTransform();

        universe.addBranchGraph(group);
    }

}
