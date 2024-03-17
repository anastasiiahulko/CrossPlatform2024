import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ObjectSerializerDeserializer {

    // Метод, що серіалізує об'єкт в рядок
    public static String serializeObject(Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        sb.append(clazz.getName()).append(":");

        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName()).append("=").append(field.get(obj)).append(",");
        }

        return sb.toString();
    }

    // Метод, що десеріалізує рядок в об'єкт
    public static Object deserializeObject(String str) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        String[] parts = str.split(":");

        String className = parts[0];
        Class<?> clazz = Class.forName(className);

        // Отримання конструктора з параметрами для класу MyClass
        Constructor<?> constructor = clazz.getDeclaredConstructor(int.class, String.class, boolean.class);
        // Створення об'єкта з використанням отриманого конструктора та передачею параметрів конструктора
        Object obj = constructor.newInstance(10, "Hello", true);

        String[] fields = parts[1].split(",");
        for (String field : fields) {
            String[] keyValue = field.split("=");
            String fieldName = keyValue[0];
            String value = keyValue[1];

            // Видаляємо непотрібні символи "=" з імені поля
            fieldName = fieldName.replace("=", "");
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);

            if (f.getType() == int.class) {
                f.setInt(obj, Integer.parseInt(value));
            } else if (f.getType() == double.class) {
                f.setDouble(obj, Double.parseDouble(value));
            } else if (f.getType() == boolean.class) {
                f.setBoolean(obj, Boolean.parseBoolean(value));
            } else if (f.getType() == char.class) {
                f.setChar(obj, value.charAt(0));
            } else {
                f.set(obj, value);
            }
        }

        return obj;
    }

    public static void main(String[] args) {
        try {
            // Приклад використання
            MyClass obj = new MyClass(10, "Hello", true);
            String serialized = serializeObject(obj);
            System.out.println("Serialized object: " + serialized);

            Object deserialized = deserializeObject(serialized);
            System.out.println("Deserialized object: " + deserialized);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyClass {
    private int intValue;
    private String stringValue;
    private boolean booleanValue;

    public MyClass(int intValue, String stringValue, boolean booleanValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                ", booleanValue=" + booleanValue +
                '}';
    }
}
