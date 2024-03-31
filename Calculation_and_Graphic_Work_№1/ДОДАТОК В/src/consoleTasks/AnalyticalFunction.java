package consoleTasks;

//exp4j-0.4.8.jar

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class AnalyticalFunction implements Evaluatable {
    private String expression;
    private Expression exp4jExpression; // Об'єкт для обчислення

    public AnalyticalFunction(String expression) {
        this.expression = expression;
        this.exp4jExpression = new ExpressionBuilder(expression).variable("x").build();
    }

    @Override
    public double evalf(double x) {
        return exp4jExpression.setVariable("x", x).evaluate();
    }

    // Метод для числового обчислення похідної
    public double evalfDerivative(double x) {
        double h = 1e-5; // Вибір малого значення h для апроксимації
        double derivative = (evalf(x + h) - evalf(x - h)) / (2 * h);
        return derivative;
    }
}
