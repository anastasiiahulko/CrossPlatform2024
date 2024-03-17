import java.lang.reflect.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassAnalyzer {

    private static final Logger logger = Logger.getLogger(ClassAnalyzer.class.getName());

    public static String analyzeClass(Class<?> clazz) {
        StringBuilder result = new StringBuilder();

        // Пакет та ім'я класу
        result.append("package ").append(clazz.getPackageName())
                .append(", Java Platform API Specification, version ")
                .append(System.getProperty("java.version"))
                .append("\n");

        // Модифікатори та ім'я класу
        result.append(Modifier.toString(clazz.getModifiers()))
                .append(" class ")
                .append(clazz.getSimpleName())
                .append(" ");

        // Базовий клас
        if (clazz.getSuperclass() != null) {
            result.append("extends ").append(clazz.getSuperclass().getSimpleName()).append(" ");
        }

        // Список реалізованих інтерфейсів
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            result.append("implements ");
            for (int i = 0; i < interfaces.length; i++) {
                result.append(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) {
                    result.append(", ");
                }
            }
            result.append(" ");
        }

        result.append("{\n");

        // Поля класу
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            result.append("  ").append(Modifier.toString(field.getModifiers()))
                    .append(" ")
                    .append(field.getType().getSimpleName())
                    .append(" ")
                    .append(field.getName())
                    .append(";\n");
        }

        // Конструктори класу
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            result.append("  ").append(Modifier.toString(constructor.getModifiers()))
                    .append(" ")
                    .append(clazz.getSimpleName())
                    .append("(");
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                result.append(parameterTypes[i].getSimpleName());
                if (i < parameterTypes.length - 1) {
                    result.append(", ");
                }
            }
            result.append(");\n");
        }

        // Методи класу
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            result.append("  ").append(Modifier.toString(method.getModifiers()))
                    .append(" ")
                    .append(method.getReturnType().getSimpleName())
                    .append(" ")
                    .append(method.getName())
                    .append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                result.append(parameterTypes[i].getSimpleName());
                if (i < parameterTypes.length - 1) {
                    result.append(", ");
                }
            }
            result.append(");\n");
        }

        result.append("}\n");
        return result.toString();
    }

    public static String analyzeClass(String className) throws ClassNotFoundException {
        return analyzeClass(Class.forName(className));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the full class name:");
        String className = scanner.nextLine();

        try {
            String analysisResult = ClassAnalyzer.analyzeClass(className);
            System.out.println(analysisResult);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
    }

}
