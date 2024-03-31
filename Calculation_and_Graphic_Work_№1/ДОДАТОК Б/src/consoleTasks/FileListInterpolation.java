package consoleTasks;

import java.io.*;
import java.util.StringTokenizer;

public class FileListInterpolation extends ListInterpolation implements Evaluatable {

    public FileListInterpolation() {
        super();
    }

    public void readFromFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String s = in.readLine(); // читання рядка із заголовками стовпців
        clear();
        while ((s = in.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            DataPoint dataPoint = new DataPoint(2);
            dataPoint.setCoord(0, x);
            dataPoint.setCoord(1, y);
            addPoint(dataPoint);
        }
        in.close();
    }

    public void writeToFile(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        out.printf("%9s%25s\n", "x", "y");
        for (int i = 0; i < numPoints(); i++) {
            DataPoint dataPoint = getPoint(i);
            out.println(dataPoint.getCoord(0) + "\t" + dataPoint.getCoord(1));
        }
        out.close();
    }
}
