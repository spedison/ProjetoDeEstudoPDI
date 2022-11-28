package br.com.spedison.mostragraficos;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMostraSenos extends JFrame {

    public void imprimeSeno()  {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 1400);

        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = -5.; x < 5.; x+=.1) {
            double y = Math.sin(x);
            data.add(x, y);
        }

        XYPlot plot = new XYPlot(data);
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data,lines);
        Color color = Color.BLUE;
        plot.getPointRenderers(data).get(0).setColor(color);
        plot.getLineRenderers(data).get(0).setColor(color);
    }

    public static void main(String[] args)  {

        MainMostraSenos mms = new MainMostraSenos();
        mms.imprimeSeno();
        mms.setVisible(true);
    }
}