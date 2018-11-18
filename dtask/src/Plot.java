import java.awt.Color;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;

class Plot extends JFrame {

    public Plot( String applicationTitle, String chartTitle , List<Integer> alist) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "" ,
                "" ,
                createDataset(alist) ,
                PlotOrientation.VERTICAL ,
                false , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );


        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset(List<Integer> alist) {
        final XYSeries correl = new XYSeries( "Correl" );
        for(int i=0;i<alist.size();i++){
            correl.add(i,alist.get(i));
        }


        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( correl );

        return dataset;
    }

    public static void main( String[ ] args ) {
        ArrayList<Integer> al = new ArrayList<>();
        al.add(2);
        al.add(3);
        al.add(4);
        Plot chart = new Plot("ПФАК",
                "ПФАК",al);
        chart.pack( );

        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}