package gui;

//jfreechart-1.0.19.jar
//exp4j-0.4.8.jar

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class JFreeChartGUI extends JFrame {
    private JTextField textFieldFunction;
    private JTextField textFieldStart;
    private JTextField textFieldStop;
    private JTextField textFieldStep;
    private XYSeries seriesFunction;
    private XYSeries seriesDerivative;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFreeChartGUI frame = new JFreeChartGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JFreeChartGUI() {
        setTitle("Function Plotter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelInput = new JPanel();
        getContentPane().add(panelInput, BorderLayout.NORTH);

        JLabel lblFunction = new JLabel("Function f(x):");
        panelInput.add(lblFunction);

        textFieldFunction = new JTextField();
        textFieldFunction.setText("Math.sin(x)");
        panelInput.add(textFieldFunction);
        textFieldFunction.setColumns(10);

        JLabel lblStart = new JLabel("Start:");
        panelInput.add(lblStart);

        textFieldStart = new JTextField();
        textFieldStart.setText("-10");
        panelInput.add(textFieldStart);
        textFieldStart.setColumns(5);

        JLabel lblStop = new JLabel("Stop:");
        panelInput.add(lblStop);

        textFieldStop = new JTextField();
        textFieldStop.setText("10");
        panelInput.add(textFieldStop);
        textFieldStop.setColumns(5);

        JLabel lblStep = new JLabel("Step:");
        panelInput.add(lblStep);

        textFieldStep = new JTextField();
        textFieldStep.setText("0.1");
        panelInput.add(textFieldStep);
        textFieldStep.setColumns(5);

        JButton btnPlot = new JButton("Plot");
        btnPlot.addActionListener(e -> plotFunction());
        panelInput.add(btnPlot);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> System.exit(0));
        panelInput.add(btnExit);

        JPanel panelChart = new JPanel();
        getContentPane().add(panelChart, BorderLayout.CENTER);
        panelChart.setLayout(new BorderLayout());

        seriesFunction = new XYSeries("Function");
        seriesDerivative = new XYSeries("Derivative");

        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        panelChart.add(chartPanel);
    }

    private void plotFunction() {
        seriesFunction.clear();
        seriesDerivative.clear();

        String functionExpression = textFieldFunction.getText();
        double start = Double.parseDouble(textFieldStart.getText());
        double stop = Double.parseDouble(textFieldStop.getText());
        double step = Double.parseDouble(textFieldStep.getText());

        for (double x = start; x <= stop; x += step) {
            double yFunction = evalFunction(functionExpression, x);
            seriesFunction.add(x, yFunction);

            double yDerivative = evalDerivative(functionExpression, x);
            seriesDerivative.add(x, yDerivative);
        }
    }


    private double evalFunction(String expression, double x) {
        Expression exp4jExpression = new ExpressionBuilder(expression).variables("x").build().setVariable("x", x);
        return exp4jExpression.evaluate();
    }

    private double evalDerivative(String expression, double x) {
        double h = 1e-5;
        double derivative = (evalFunction(expression, x + h) - evalFunction(expression, x - h)) / (2 * h);
        return derivative;
    }

    private JFreeChart createChart() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesFunction);
        dataset.addSeries(seriesDerivative);

        JFreeChart chart = ChartFactory.createXYLineChart("Function Plot",
                "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        return chart;
    }
}
