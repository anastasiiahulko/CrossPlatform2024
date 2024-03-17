import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

public class ObjectAnalyzer {

    public static void analyzeObject(Object obj) {
        // Get the class of the object
        Class<?> clazz = obj.getClass();

        // Display the real type of the object
        System.out.println("Real type of the object: " + clazz.getSimpleName());

        // Display the state of the object (fields with values)
        System.out.println("State of the object:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                System.out.println(field.getType().getSimpleName() + " " + field.getName() + " = " + value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Display a list of open methods (public methods without parameters)
        System.out.println("List of open methods:");
        Method[] methods = clazz.getDeclaredMethods();
        int index = 1;
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                System.out.println(index++ + "). " + method.toString());
            }
        }

        // Allow the user to select a method to call
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sequence number of the method to call [1, " + (index - 1) + "]:");
        int methodIndex = scanner.nextInt();
        if (methodIndex >= 1 && methodIndex < index) {
            Method selectedMethod = methods[methodIndex - 1];
            try {
                Object result = selectedMethod.invoke(obj);
                System.out.println("The result of the method call: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid method index");
        }
    }

    public static void main(String[] args) {
        // Create an object for testing
        Check obj = new Check(3.0, 4.0);

        // Analyze the object
        analyzeObject(obj);
    }
}

class Check {
    private double x;
    private double y;

    public Check(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double Dist() {
        return Math.sqrt(x * x + y * y);
    }

    public void setRandomData() {
        // Method implementation
    }

    public String toString() {
        // Method implementation
        return "";
    }

    public void setData(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
