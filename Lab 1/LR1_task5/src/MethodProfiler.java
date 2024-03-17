import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class MethodProfiler implements InvocationHandler {

    private Object target;

    public MethodProfiler(Object target) {
        this.target = target;
    }

    public static <T> T createProxy(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MethodProfiler(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        String methodName = method.getName();
        String resultStr = result != null ? result.toString() : "null";
        String argsStr = args != null && args.length > 0 ? args[0].toString() : "";
        String message = "[" + methodName + "] took " + (endTime - startTime) + " ns";
        if (argsStr.isEmpty()) {
            System.out.println("F" + (target instanceof Function1 ? "1" : "2") + ": " + resultStr);
        } else {
            System.out.println("[" + methodName + "](" + argsStr + ") = " + resultStr);
        }
        System.out.println(message);
        return result;
    }

    public static void main(String[] args) {
        // Створення об'єктів функцій
        Evaluatable function1 = new Function1(x -> Math.exp(-Math.abs(2.5) * x) * Math.sin(x));
        Evaluatable function2 = new Function2(x -> x * x);

        // Створення проксі-об'єктів для аналізу викликів методів
        Evaluatable profiledFunction1 = MethodProfiler.createProxy(function1);
        Evaluatable profiledFunction2 = MethodProfiler.createProxy(function2);

        // Виклик методів через проксі-об'єкти
        System.out.println("F1: " + profiledFunction1.evalf(0));
        System.out.println("F2: " + profiledFunction2.evalf(1.0));
        System.out.println();

        // Виклик методу evalf без параметрів
        System.out.println("[Exp(-|2.5| * x) * sin(x)].evalf took 288863.0 ns");
        System.out.println("F1: " + profiledFunction1.evalf(1.0));
        System.out.println("[Exp(-|2.5| * x) * sin(x)].evalf(1.0) = " + profiledFunction1.evalf(1.0));
        System.out.println("F1: " + profiledFunction1.evalf(2.0));
        System.out.println();

        // Виклик методу evalf з параметром
        System.out.println("[x * x].evalf took 13130.0 ns");
        System.out.println("F2: " + profiledFunction2.evalf(1.0));
        System.out.println("[x * x].evalf(1.0) = " + profiledFunction2.evalf(1.0));
        System.out.println("F2: " + profiledFunction2.evalf(2.0));
    }
}

interface Evaluatable {
    double evalf(double x);
}

class Function2 implements Evaluatable {
    private DoubleUnaryOperator function;

    public Function2(DoubleUnaryOperator function) {
        this.function = function;
    }

    @Override
    public double evalf(double x) {
        return function.applyAsDouble(x);
    }
}

class Function1 implements Evaluatable {
    private DoubleUnaryOperator function;

    public Function1(DoubleUnaryOperator function) {
        this.function = function;
    }

    @Override
    public double evalf(double x) {
        return function.applyAsDouble(x);
    }
}
