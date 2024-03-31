package consoleTasks;

public class FunctionFactory {
    public static Evaluatable createFunction(String type, double a) {
        switch (type) {
            case "FFunction":
                return new FFunction(a);
            case "FileListInterpolation":
                return new FileListInterpolation();
            default:
                throw new IllegalArgumentException("Непідтримуваний тип функції");
        }
    }
}
