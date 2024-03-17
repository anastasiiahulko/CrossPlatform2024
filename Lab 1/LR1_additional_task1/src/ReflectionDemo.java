import java.lang.reflect.*;
import java.util.Scanner;

public class ReflectionDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Введення імені класу
        System.out.println("Enter class name:");
        String className = scanner.nextLine();

        try {
            // Завантаження класу за іменем
            Class<?> clazz = Class.forName(className);

            // Вивід інформації про конструктори класу
            System.out.println("Constructors:");
            Constructor<?>[] constructors = clazz.getConstructors();
            int index = 1;
            for (Constructor<?> constructor : constructors) {
                System.out.println(index + "). " + constructor);
                index++;
            }

            // Вибір конструктора
            System.out.println("Choose a constructor:");
            int constructorIndex = scanner.nextInt();
            Constructor<?> selectedConstructor = constructors[constructorIndex - 1];

            // Вивід параметрів конструктора та їх введення користувачем
            System.out.println("Parameters of the Constructor:");
            Class<?>[] parameterTypes = selectedConstructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                System.out.println("The beginning of creation of the " + parameterType.getSimpleName() + " object");
                System.out.println("Input value for parameter " + (i + 1) + ":");
                parameters[i] = getInputValue(parameterType, scanner);
                System.out.println("The end of creation of the " + parameterType.getSimpleName() + " object");
            }

            // Створення об'єкта за допомогою вибраного конструктора
            Object instance = selectedConstructor.newInstance(parameters);

            // Вивід інформації про методи класу
            System.out.println("Methods:");
            Method[] methods = clazz.getMethods();
            index = 1;
            for (Method method : methods) {
                System.out.println(index + "). " + method);
                index++;
            }

            // Вибір методу
            System.out.println("Choose a method:");
            int methodIndex = scanner.nextInt();
            Method selectedMethod = methods[methodIndex - 1];

            // Перевірка кількості параметрів методу
            int parameterCount = selectedMethod.getParameterCount();
            if (parameterCount > 0) {
                // Метод приймає аргументи
                Object[] methodArgs = new Object[parameterCount];
                for (int i = 0; i < parameterCount; i++) {
                    Class<?> parameterType = selectedMethod.getParameterTypes()[i];
                    System.out.println("The beginning of creation of the " + parameterType.getSimpleName() + " object");
                    System.out.println("Input value for argument " + (i + 1) + ":");
                    methodArgs[i] = getInputValue(parameterType, scanner);
                    System.out.println("The end of creation of the " + parameterType.getSimpleName() + " object");
                }
                // Виклик методу з аргументами
                Object result = selectedMethod.invoke(instance, methodArgs);
                System.out.println("Result of the method call: " + result);
            } else {
                // Метод не приймає аргументів
                if (selectedMethod.getName().equals("getClass")) {
                    Object result = selectedMethod.invoke(instance);
                    System.out.println("Result of the method call: " + result);
                } else {
                    Object result = selectedMethod.invoke(instance);
                    System.out.println("Result of the method call: " + result);
                }
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Метод для введення значення параметру з консолі
    private static Object getInputValue(Class<?> parameterType, Scanner scanner) {
        String input = scanner.next(); // Отримати введений рядок
        if (parameterType == int.class || parameterType == Integer.class) {
            return Integer.parseInt(input);
        } else if (parameterType == double.class || parameterType == Double.class) {
            return Double.parseDouble(input);
        } else if (parameterType == String.class) {
            return input;
        } else if (parameterType == boolean.class || parameterType == Boolean.class) {
            return Boolean.parseBoolean(input);
        } else if (parameterType == char.class || parameterType == Character.class) {
            if (input.length() == 1) {
                return input.charAt(0); // Отримати перший символ введеного рядка
            } else {
                System.out.println("Invalid input for char. Please enter a single character.");
                return null;
            }
        }
        return null;
    }

}
