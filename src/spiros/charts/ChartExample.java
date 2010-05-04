package spiros.charts;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class ChartExample extends JFrame
{

    public static void main(String args[])
    {
        go1();
    }

    private static final long serialVersionUID = 1L;

    private DefaultPieDataset dataset;

    private JFreeChart jfc;

    private static void go1()
    {

        ChartExample j = new ChartExample();

        j.setTitle("Example Chart...");

        j.setSize(640, 430);

        j.setValue("UNO", new Double(20.0));

        j.setValue("DUE", new Double(10.0));

        j.setValue("TRE", new Double(20.0));

        j.setValue("QUATTRO", new Double(30.0));

        j.setValue("CINQUE", new Double(20.0));

        j.setChar("Example Chart...");

        j.Show();
    }

    public ChartExample()

    {
        dataset = new DefaultPieDataset();

    }

    public void setValue(String title, Double numDouble)

    {

        dataset.setValue(title, numDouble);

    }

    public void setChar(String title)

    {

        jfc = ChartFactory.createPieChart(title, dataset, true, true, false);

        PiePlot pp = (PiePlot) jfc.getPlot();

        pp.setSectionOutlinesVisible(false);

        Font font = new Font("SansSerif", Font.PLAIN, 12);

        pp.setLabelFont(font);

        pp.setNoDataMessage("Nessun Dato Inserito");

        pp.setCircular(false);

        pp.setLabelGap(0.02);

    }

    private JPanel createPanel()
    {

        return new ChartPanel(jfc);

    }

    public void Show()
    {

        setContentPane(createPanel());

        setVisible(true);

    }
}
