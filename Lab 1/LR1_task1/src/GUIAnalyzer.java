import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAnalyzer {
    private JFrame frame;
    private JTextField classNameField;
    private JTextArea resultArea;

    public GUIAnalyzer() {
        frame = new JFrame("Class Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Top Panel for Class Name Input
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel classNameLabel = new JLabel("Enter the full name of the class:");
        classNameField = new JTextField(20);
        topPanel.add(classNameLabel);
        topPanel.add(classNameField);

        // Middle Panel for Result Display
        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Bottom Panel for Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton analyzeButton = new JButton("Analyze");
        JButton clearButton = new JButton("Clear");
        JButton completeButton = new JButton("Complete");

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeClass();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classNameField.setText("");
                resultArea.setText("");
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the program
            }
        });

        bottomPanel.add(analyzeButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(completeButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void analyzeClass() {
        String className = classNameField.getText();
        try {
            String analysisResult = ClassAnalyzer.analyzeClass(className);
            resultArea.setText(analysisResult);
        } catch (ClassNotFoundException ex) {
            resultArea.setText("Class not found: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIAnalyzer();
            }
        });
    }
}
