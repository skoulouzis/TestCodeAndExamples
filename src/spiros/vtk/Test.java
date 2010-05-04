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

package spiros.vtk;

import vtk.vtkActor;
import vtk.vtkConeSource;
import vtk.vtkDataSetReader;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;
import vtk.vtkRenderWindow;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkRenderer;
import vtk.vtkSphereSource;

public class Test
{

    public static final String dataDir = "/home/alogo/workspace/netbeans/testCodeAndExamples/testData/vtk";
    // in the static contructor we load in the native code
    // The libraries must be in your path to work

    static
    {
        System.loadLibrary("vtkCommon");
        System.loadLibrary("vtkCommonJava");

        System.loadLibrary("vtkFiltering");
        System.loadLibrary("vtkFilteringJava");

        System.loadLibrary("vtkIO");
        System.loadLibrary("vtkIOJava");

        System.loadLibrary("vtkImaging");
        System.loadLibrary("vtkImagingJava");

        System.loadLibrary("vtkGraphics");
        System.loadLibrary("vtkGraphicsJava");

        System.loadLibrary("vtkRendering");
        System.loadLibrary("vtkRenderingJava");

        System.loadLibrary("vtkzlib");
        System.loadLibrary("vtkjpeg");
        System.loadLibrary("vtktiff");
        System.loadLibrary("vtkpng");
        System.loadLibrary("vtkexpat");
        System.loadLibrary("vtkfreetype");
        System.loadLibrary("vtkftgl");

        try
        {
            System.loadLibrary("vtkHybrid");
            System.loadLibrary("vtkHybridJava");
            System.loadLibrary("vtkHybridJava");
        }
        catch (Throwable e)
        {
            System.out.println("cannot load vtkHybrid,   skipping...");
        }
        try
        {
            System.loadLibrary("vtkVolumeRendering");
            System.loadLibrary("vtkVolumeRenderingJava");
        }
        catch (Throwable e)
        {
            System.out.println("cannot load vtkVolumeRendering,skipping...");
        }
        // try {
        // System.loadLibrary("vtkParallel");
        // System.loadLibrary("vtkParallelJava");
        // } catch (Throwable e) {
        // System.out.println("cannot load vtkParallelJava,skipping...");
        // }
    }

    // the main function
    public static void main(String[] args)
    {
        // sphere();
        cone();
        // readData();
        // tornado();
        // polyData();

    }

    public static void readData()
    {
        //        
        // String file = dataDir+"/Data/polyEx.vtk";
        // vtkStructuredPointsReader reader = new vtkStructuredPointsReader();
        // reader.SetFileName(file);
        // reader.Update();

        // vtkPolyDataReader reader = new vtkPolyDataReader();
        // reader.SetFileName(fileName);
        // reader.Update();
        // file = dataDir+"/polygonsPbalke.vtk";
        // vtkPolyDataReader pld = new vtkPolyDataReader();
        // pld.SetFileName(file);
        // pld.Update();
    }

    public static void tornado()
    {
        // renderer = vtkRenderer::New();
        // vtkRenderer renderer = new vtkRenderer();
        //        
        getDataSet(dataDir + "/uvadata/tornado.vtk");
    }

    public static void getDataSet(String path)
    {
        vtkDataSetReader dataSetReader = new vtkDataSetReader();
        dataSetReader.DebugOn();
        dataSetReader.SetFileName(path);

        System.out.println("GetNormalsName: " + dataSetReader.GetNormalsName());
        System.out.println("GetNumberOfFieldDataInFile: " + dataSetReader.GetNumberOfFieldDataInFile());
        dataSetReader.Update();
    }

    public static void sphere()
    {
        // create sphere geometry
        vtkSphereSource sphere = new vtkSphereSource();
        sphere.SetRadius(1.0);
        sphere.SetThetaResolution(18);
        sphere.SetPhiResolution(18);

        // map to graphics objects
        vtkPolyDataMapper map = new vtkPolyDataMapper();
        map.SetInput(sphere.GetOutput());

        // actor coordinates geometry, properties, transformation
        vtkActor aSphere = new vtkActor();
        aSphere.SetMapper(map);
        aSphere.GetProperty().SetColor(0, 0, 1); // color blue

        // a renderer for the data
        vtkRenderer ren1 = new vtkRenderer();
        ren1.AddActor(aSphere);
        ren1.SetBackground(1, 1, 1); // background color white

        // a render window to display the contents
        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(ren1);
        renWin.SetSize(300, 300);

        // an interactor to allow control of the objects
        vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();
        iren.SetRenderWindow(renWin);

        // trigger the rendering and start the interaction renWin.Render();
        iren.Start();
    }

    public static void polyData()
    {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        vtkSphereSource sphere = new vtkSphereSource();
        sphere.SetThetaResolution(36);
        sphere.SetPhiResolution(18);
        sphere.Update();
        vtkPolyData pts = sphere.GetOutput();

        System.out.printf("Number of points: %d\n", pts.GetNumberOfPoints());
        System.out.printf("Number of cells: %d\n", pts.GetNumberOfCells());
    }

    public static void cone()
    {
        //
        // Next we create an instance of vtkConeSource and set some of its
        // properties. The instance of vtkConeSource "cone" is part of a
        // visualization pipeline (it is a source process object); it produces
        // data
        // (output type is vtkPolyData) which other filters may process.
        //
        vtkConeSource cone = new vtkConeSource();
        cone.SetHeight(3.0);
        cone.SetRadius(1.0);
        cone.SetResolution(10);

        //
        // In this example we terminate the pipeline with a mapper process
        // object.
        // (Intermediate filters such as vtkShrinkPolyData could be inserted in
        // between the source and the mapper.) We create an instance of
        // vtkPolyDataMapper to map the polygonal data into graphics primitives.
        // We
        // connect the output of the cone souece to the input of this mapper.
        //
        vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
        coneMapper.SetInputConnection(cone.GetOutputPort());

        // Create an actor to represent the cone. The actor orchestrates
        // rendering
        // of the mapper's graphics primitives. An actor also refers to
        // properties
        // via a vtkProperty instance, and includes an internal transformation
        // matrix. We set this actor's mapper to be coneMapper which we created
        // above.
        //
        vtkActor coneActor = new vtkActor();
        coneActor.SetMapper(coneMapper);

        //
        // Create the Renderer and assign actors to it. A renderer is like a
        // viewport. It is part or all of a window on the screen and it is
        // responsible for drawing the actors it has. We also set the background
        // color here
        //
        vtkRenderer ren1 = new vtkRenderer();
        ren1.AddActor(coneActor);
        ren1.SetBackground(0.1, 0.2, 0.4);

        //
        // Finally we create the render window which will show up on the screen
        // We put our renderer into the render window using AddRenderer. We also
        // set the size to be 300 pixels by 300
        //
        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(ren1);
        renWin.SetSize(300, 300);

        //
        // now we loop over 360 degreeees and render the cone each time
        //
        int i;
        for (i = 0; i < 360; ++i)
        {
            // render the image
            renWin.Render();
            // rotate the active camera by one degree
            ren1.GetActiveCamera().Azimuth(1);
        }
    }
}
