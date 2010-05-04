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

package spiros.vtk.flowField;

import vtk.vtkActor;
import vtk.vtkActorCollection;
import vtk.vtkAlgorithmOutput;
import vtk.vtkArrowSource;
import vtk.vtkConeSource;
import vtk.vtkContourFilter;
import vtk.vtkDataSetCollection;
import vtk.vtkDataSetReader;
import vtk.vtkGlyph3D;
import vtk.vtkInteractorStyleTrackballCamera;
import vtk.vtkLineSource;
import vtk.vtkLookupTable;
import vtk.vtkMaskPoints;
import vtk.vtkOutlineCornerFilter;
import vtk.vtkPointSource;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;
import vtk.vtkRenderWindow;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkRenderer;
import vtk.vtkRungeKutta4;
import vtk.vtkStreamLine;
import vtk.vtkVectorNorm;
import vtk.vtkWindowToImageFilter;

/**
 * 
 * @author alogo
 */
public class FlowField
{

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

    private String workingDirectory = "testData/vtk/uvadata/";

    private vtkDataSetReader dataSetReader;

    private vtkRenderer renderer;

    private double[] odjectDimentions;

    private double[] odjectCenter;

    private double[] scalarRange;

    private vtkPointSource point;

    private int theNumOfPoints = 150;

    private vtkStreamLine streamLine;

    private vtkRungeKutta4 rungeKutta4;

    private vtkPolyDataMapper polyDataMapper;

    private vtkActor streamtubeActor;

    private vtkActor streamlineActor;

    private vtkActorCollection actorCollection;

    private vtkActor streamtubeActor2;

    private vtkMaskPoints maskPoints;

    private vtkGlyph3D glyph;

    private vtkActor glyphActor;

    private int theRatio;

    private boolean dataLoaded;

    private vtkDataSetCollection dataSetCollection;

    public FlowField()
    {

        renderer = new vtkRenderer();
        rungeKutta4 = new vtkRungeKutta4();
        streamtubeActor = new vtkActor();
        actorCollection = new vtkActorCollection();
        streamtubeActor2 = new vtkActor();

        theRatio = 50;

    }

    public void run1()
    {
        // tornado();
        // srmxStreamlines();
        // tornadoGlyphs();

        carotid();
        carotidGlyphs();

        vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(800, 600);
        renWin.OffScreenRenderingOn();

        iren.SetRenderWindow(renWin);

        vtkInteractorStyleTrackballCamera style = new vtkInteractorStyleTrackballCamera();
        iren.SetInteractorStyle(style);

        renWin.Render();
        iren.Start();

        vtkWindowToImageFilter w2i = new vtkWindowToImageFilter();
        w2i.SetInput(renWin);

        actorCollection.InitTraversal();
    }

    public void run2()
    {
        tornado();
        srmxStreamlines();
        // tornadoGlyphs();

        vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(800, 600);
        renWin.OffScreenRenderingOn();

        iren.SetRenderWindow(renWin);

        vtkInteractorStyleTrackballCamera style = new vtkInteractorStyleTrackballCamera();
        iren.SetInteractorStyle(style);

        renWin.Render();
        iren.Start();

        vtkWindowToImageFilter w2i = new vtkWindowToImageFilter();
        w2i.SetInput(renWin);

        actorCollection.InitTraversal();

    }

    public void run3()
    {
        tornado();
        // srmxStreamlines();
        tornadoGlyphs();

        vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(800, 600);
        renWin.OffScreenRenderingOn();

        iren.SetRenderWindow(renWin);

        vtkInteractorStyleTrackballCamera style = new vtkInteractorStyleTrackballCamera();
        iren.SetInteractorStyle(style);

        renWin.Render();
        iren.Start();

        vtkWindowToImageFilter w2i = new vtkWindowToImageFilter();
        w2i.SetInput(renWin);

        actorCollection.InitTraversal();
    }

    private void tornado()
    {
        // workingDirectory =
        // "/home/alogo/workspace/netbeans/VisualizationService/testData/";
        String file = "/tornado.vtk";
        // String file ="/velocity.vtk";
        extractData(workingDirectory + file, 1, false);
        scalarRange[1] = 25;
    }

    private void carotid()
    {

        renderer = new vtkRenderer();

        workingDirectory = "/home/skoulouz/workspace/netbeans/VisualizationService/testData";

        // String fileName = workingDirectory + "/velocity.vtk";
        // String fileName = workingDirectory + "/vel1.vtk";
        // String fileName = workingDirectory + "/vel1new.vtk";
        String fileName = workingDirectory + "/cutted.vtk";

        extractData(fileName, 0.2, true);

        // if (!dataLoaded) {
        // String path;
        // // path = new char[100];
        // dataSetCollection = new vtkDataSetCollection();
        //
        // for (int i = 7800; i < 8020; i = i + 20) {
        // // path = new char[100];
        //
        // path = workingDirectory + "/carotid-bifurcation." + i + ".vtk";
        // // sprintf(path,"/carotid-bifurcation.%d.vtk",i);
        // getDataSet(path);
        // dataSetCollection.AddItem(dataSetReader.GetOutput());
        // }
        // dataLoaded = true;
        // }
    }

    private void extractData(String path, double opacity, boolean norm)
    {

        getDataSet(path);

        vtkLookupTable lookupTable = new vtkLookupTable();
        lookupTable.SetNumberOfColors(1);
        lookupTable.SetSaturationRange(0, 0.1);
        lookupTable.Build();

        vtkPolyDataMapper polyDataMapper = new vtkPolyDataMapper();
        polyDataMapper.SetLookupTable(lookupTable);

        if (norm)
        {

            vtkVectorNorm vectorNorm = new vtkVectorNorm();
            vectorNorm.NormalizeOn();
            vectorNorm.SetInputConnection(dataSetReader.GetOutputPort());

            vtkContourFilter contourFilter = new vtkContourFilter();
            contourFilter.SetValue(0, 0.01);
            contourFilter.SetInput(vectorNorm.GetOutput());

            polyDataMapper.SetInputConnection(contourFilter.GetOutputPort());
            polyDataMapper.ScalarVisibilityOn();
            polyDataMapper.SetScalarModeToUsePointData();
        }
        else
        {

            vtkContourFilter contourFilter = new vtkContourFilter();
            contourFilter.SetValue(0, 0.1);
            contourFilter.SetInput(dataSetReader.GetOutput());

            polyDataMapper.SetInputConnection(contourFilter.GetOutputPort());
            polyDataMapper.ScalarVisibilityOn();
            polyDataMapper.SetScalarModeToUsePointData();

        }

        // renderer.RemoveActor(mixerActor);
        // mixerActor.Delete();
        vtkActor mixerActor = new vtkActor();
        mixerActor.SetMapper(polyDataMapper);
        mixerActor.GetProperty().SetOpacity(opacity);
        renderer.AddActor(mixerActor);

        odjectDimentions = dataSetReader.GetOutput().GetBounds();
        odjectCenter = dataSetReader.GetOutput().GetCenter();
        scalarRange = dataSetReader.GetOutput().GetScalarRange();

        // -------------------------------
        // --------------- Outline-----------

        vtkOutlineCornerFilter outlineCornerFilter = new vtkOutlineCornerFilter();
        outlineCornerFilter.SetInput(dataSetReader.GetOutput());
        outlineCornerFilter.SetCornerFactor(0.3);

        polyDataMapper = new vtkPolyDataMapper();
        polyDataMapper.SetInput(outlineCornerFilter.GetOutput());

        // renderer.RemoveActor(actor);

        vtkActor actor = new vtkActor();
        actor.SetMapper(polyDataMapper);
        // actorCollection.AddItem(actor);
        renderer.AddActor(actor);
        renderer.SetBackground(0, 0, 0.2);
    }

    private void getDataSet(String path)
    {

        dataSetReader = new vtkDataSetReader();
        dataSetReader.SetFileName(path);
        dataSetReader.Update();
    }

    private void srmxStreamlines()
    {

        point = new vtkPointSource();
        point.SetNumberOfPoints(theNumOfPoints);
        point.SetRadius(odjectDimentions[3] / 2);
        point.SetCenter(odjectDimentions[0], odjectCenter[1], odjectCenter[2]);
        point.SetDistributionToUniform();

        streamlines(point.GetOutput(), dataSetReader.GetOutputPort());
    }

    private void streamlines(vtkPolyData source, vtkAlgorithmOutput input)
    {

        streamLine = new vtkStreamLine();
        streamLine.SetIntegrator(rungeKutta4);
        streamLine.SetSource(source);
        streamLine.SetStepLength(1.0);
        streamLine.SetNumberOfThreads(2);
        streamLine.SetIntegrationStepLength(2.0);
        streamLine.SetIntegrationDirectionToIntegrateBothDirections();
        streamLine.SetMaximumPropagationTime(300);
        streamLine.SetTerminalSpeed(0.1);
        streamLine.SpeedScalarsOn();
        streamLine.SetInputConnection(input);

        polyDataMapper = new vtkPolyDataMapper();
        polyDataMapper.SetInputConnection(streamLine.GetOutputPort());
        polyDataMapper.SetScalarRange(scalarRange);
        polyDataMapper.SetScalarModeToUsePointData();
        polyDataMapper.SetScalarRange(scalarRange[0], scalarRange[1]);
        polyDataMapper.ScalarVisibilityOn();

        renderer.RemoveActor(streamtubeActor);

        streamlineActor = new vtkActor();
        streamlineActor.SetMapper(polyDataMapper);
        actorCollection.AddItem(streamlineActor);
        renderer.AddActor(streamlineActor);

        // createColorBar(polyDataMapper.GetLookupTable());
    }

    private void tornadoGlyphs()
    {
        streamtubeActor2.VisibilityOff();

        vtkArrowSource arrow = new vtkArrowSource();

        glyphs(dataSetReader.GetOutputPort(), arrow.GetOutput(), true, 0.2);
    }

    private void carotidGlyphs()
    {
        vtkVectorNorm vectorNorm = new vtkVectorNorm();
        vectorNorm.NormalizeOn();
        vectorNorm.SetInputConnection(dataSetReader.GetOutputPort());

        int theControlSelection = 1;

        switch (theControlSelection)
        {
            case 0:
            {
                vtkArrowSource source = new vtkArrowSource();
                // glyphs(vectorNorm.GetOutputPort(),source.GetOutputPort(),false,10);
                glyphs(vectorNorm.GetOutputPort(), source.GetOutput(), false, 10);
            }
                break;

            case 1:
            {
                vtkConeSource source = new vtkConeSource();
                source.SetHeight(0.5);
                source.SetRadius(0.2);
                source.SetResolution(8);
                // glyphs(vectorNorm.GetOutputPort(),source.GetOutputPort(),false,10);
                glyphs(vectorNorm.GetOutputPort(), source.GetOutput(), false, 10);
            }
                break;
            case 2:
            {
                vtkLineSource source = new vtkLineSource();
                // glyphs(vectorNorm.GetOutputPort(),source.GetOutputPort(),false,10);
                glyphs(vectorNorm.GetOutputPort(), source.GetOutput(), false, 10);
            }
                break;
        }

        // vtkArrowSource *arrow = vtkArrowSource::New();

        // glyphs(vectorNorm.GetOutputPort(),arrow.GetOutputPort(),false,10);
    }

    private void glyphs(vtkAlgorithmOutput input, vtkPolyData source, boolean vectorMode, double scaleFactor)
    {

        maskPoints = new vtkMaskPoints();
        maskPoints.SetOnRatio(theRatio);
        maskPoints.RandomModeOn();
        maskPoints.SetInputConnection(input);

        glyph = new vtkGlyph3D();
        glyph.SetInputConnection(maskPoints.GetOutputPort());
        glyph.SetScaleFactor(scaleFactor);

        glyph.SetSource(source);
        // glyph.SetSourceConnection(source);

        if (vectorMode)
        {
            glyph.SetVectorModeToUseVector();
            glyph.SetColorModeToColorByVector();
            glyph.SetScaleModeToScaleByVector();
            glyph.SetIndexModeToVector();
        }

        polyDataMapper = new vtkPolyDataMapper();
        polyDataMapper.SetInputConnection(glyph.GetOutputPort());
        polyDataMapper.SetScalarModeToUsePointData();
        polyDataMapper.SetScalarRange(scalarRange[0], scalarRange[1]);
        polyDataMapper.ScalarVisibilityOn();

        // renderer.RemoveActor(glyphActor);
        // glyphActor.Delete();
        glyphActor = new vtkActor();
        glyphActor.SetMapper(polyDataMapper);
        // actorCollection.AddItem(glyphActor);

        renderer.AddActor(glyphActor);
    }
}
