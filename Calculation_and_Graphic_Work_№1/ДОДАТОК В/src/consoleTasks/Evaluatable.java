package consoleTasks;

public interface Evaluatable {
    double evalf(double x);
    // Метод для отримання символьної похідної, якщо можливо
    default String derivativeSymbolic(String variable) {
        return "Not implemented";
    }
}
