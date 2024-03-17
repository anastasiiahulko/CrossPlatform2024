import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayManipulator {

    // Створення масиву заданого типу та розміру
    public static Object createArray(Class<?> componentType, int length) {
        return Array.newInstance(componentType, length);
    }

    // Зміна розміру масиву зі збереженням значень
    public static Object resizeArray(Object array, int newLength) {
        Class<?> componentType = array.getClass().getComponentType();
        Object newArray = Array.newInstance(componentType, newLength);
        int length = Math.min(Array.getLength(array), newLength);
        System.arraycopy(array, 0, newArray, 0, length);
        return newArray;
    }

    // Перетворення масиву на рядок
    public static String arrayToString(Object array) {
        Class<?> componentType = array.getClass().getComponentType();
        int length = Array.getLength(array);
        String componentTypeName = componentType.getCanonicalName();

        String prefix = componentTypeName + "[" + length + "]";

        if (componentType.isPrimitive()) {
            // Create an array of Objects and populate it
            Object[] objArray = new Object[length];
            for (int i = 0; i < length; i++) {
                objArray[i] = Array.get(array, i);
            }
            return prefix + " = " + Arrays.toString(objArray);
        }

        // If not a primitive array, use Arrays.deepToString() directly
        return prefix + " = " + Arrays.deepToString((Object[]) array);
    }




    // Перетворення двовимірної матриці на рядок
    public static String matrixToString(Object matrix) {
        StringBuilder sb = new StringBuilder();
        int rows = Array.getLength(matrix);
        sb.append(arrayToString(matrix));
        sb.append('\n');
        for (int i = 0; i < rows; i++) {
            sb.append(arrayToString(Array.get(matrix, i)));
            if (i < rows - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Створення та використання масивів
        int[] intArray = (int[]) createArray(int.class, 5);
        System.out.println(arrayToString(intArray));

        intArray[0] = 1;
        intArray[1] = 2;
        intArray[2] = 3;
        System.out.println(arrayToString(intArray));

        intArray = (int[]) resizeArray(intArray, 10);
        System.out.println(arrayToString(intArray));

        // Перетворення масиву на рядок
        System.out.println(arrayToString(intArray));

        // Створення та використання матриць
        int[][] intMatrix = (int[][]) createArray(int[].class, 3);
        for (int i = 0; i < intMatrix.length; i++) {
            intMatrix[i] = (int[]) createArray(int.class, 5);
            for (int j = 0; j < intMatrix[i].length; j++) {
                intMatrix[i][j] = i * 10 + j;
            }
        }
        System.out.println(matrixToString(intMatrix));

        intMatrix = (int[][]) resizeArray(intMatrix, 4);
        for (int i = 0; i < intMatrix.length; i++) {
            if (intMatrix[i] == null) {
                intMatrix[i] = (int[]) createArray(int.class, 6);
            }
        }
        System.out.println(matrixToString(intMatrix));
    }
}
