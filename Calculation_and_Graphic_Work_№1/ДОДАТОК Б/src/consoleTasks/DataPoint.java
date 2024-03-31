package consoleTasks;

public class DataPoint {
    private double[] coords;

    public DataPoint(int num) {
        this.coords = new double[num];
    }

    public void setCoord(int index, double value) {
        coords[index] = value;
    }

    public double getCoord(int index) {
        return coords[index];
    }

    public int dimensions() {
        return coords.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < coords.length; i++) {
            sb.append(coords[i]);
            if (i < coords.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
