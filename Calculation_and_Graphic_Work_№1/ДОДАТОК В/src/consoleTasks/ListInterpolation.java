package consoleTasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListInterpolation {
    private List<DataPoint> data;

    public ListInterpolation() {
        data = new ArrayList<>();
    }

    public void clear() {
        data.clear();
    }

    public int numPoints() {
        return data.size();
    }

    public void addPoint(DataPoint pt) {
        data.add(pt);
    }

    public DataPoint getPoint(int i) {
        return data.get(i);
    }

    public void setPoint(int i, DataPoint pt) {
        data.set(i, pt);
    }

    public void removeLastPoint() {
        data.remove(data.size() - 1);
    }

    public void sort() {
        Collections.sort(data, (pt1, pt2) -> Double.compare(pt1.getCoord(0), pt2.getCoord(0)));
    }

    public double evalf(double x) {
        // Implement interpolation logic here
        return 0.0; // Placeholder value
    }
}
