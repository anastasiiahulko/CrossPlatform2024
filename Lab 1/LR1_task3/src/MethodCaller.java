import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodCaller {

    public static void callMethod(Object obj, String methodName, List<Number> parameters) throws FunctionNotFoundException {
        try {
            // Отримуємо масив типів параметрів
            Class<?>[] parameterTypes = new Class<?>[parameters.size()];
            Object[] parameterValues = new Object[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                parameterTypes[i] = double.class;
                parameterValues[i] = parameters.get(i).doubleValue();
            }

            // Отримуємо метод за ім'ям та типами параметрів
            Method method = findMethod(obj.getClass(), methodName, parameterTypes);

            // Виводимо інформацію про типи та значення параметрів
            System.out.println("Типи: " + getTypeList(parameterTypes));
            System.out.println("Значення: " + parameters);

            // Викликаємо метод на об'єкті з вказаними параметрами
            Object result = method.invoke(obj, parameterValues);

            // Виводимо результат
            System.out.println("Результат виклику: " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException("Функція не знайдена", e);
        }
    }

    // Метод для пошуку методу з вказаними параметрами
    private static Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            // Перевірка на перевантажені методи
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterCount() == parameterTypes.length) {
                    return method;
                }
            }
            throw e;
        }
    }

    // Метод для отримання рядка з типами параметрів
    private static String getTypeList(Class<?>[] parameterTypes) {
        StringBuilder types = new StringBuilder();
        types.append("[");
        for (Class<?> type : parameterTypes) {
            types.append(type.getSimpleName()).append(", ");
        }
        types.delete(types.length() - 2, types.length());
        types.append("]");
        return types.toString();
    }

    public static void main(String[] args) {
        try {
            // Створюємо об'єкт
            TestClass testClass = new TestClass(1.0);

            // Викликаємо метод з різними параметрами
            callMethod(testClass, "myMethod", List.of(1.0));
            callMethod(testClass, "myMethod", List.of(1.0, 1.0));

            // Print information about the object
            System.out.println(testClass);
        } catch (FunctionNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// Приклад класу з методом
class TestClass {
    private double a;

    // Конструктор з параметром
    public TestClass(double a) {
        this.a = a;
    }

    public double myMethod(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    public double myMethod(double x, double y) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(y);
    }

    @Override
    public String toString() {
        return "TestClass [a=" + a + ", exp(-abs(a)*x)*sin(x)]";
    }
}

// Клас виключення
class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

