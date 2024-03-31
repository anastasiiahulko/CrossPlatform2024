package test;

// jcm1.0-config.jar

import edu.hws.jcm.data.Expression;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;

public class JcmTest {
    public static void main(String[] args) {
        Parser parser = new Parser(Parser.STANDARD_FUNCTIONS);
        Variable var = new Variable("x");
        Variable par = new Variable("a");
        parser.add(var);
        parser.add(par);
        String funStr = "sin(a*x)/x";
        Expression fun = parser.parse(funStr);
        Expression der = fun.derivative(var);
        System.out.println("f(x) = " + fun);
        System.out.println("f'(x) = " + der);
        par.setVal(1.0);
        for (double x = -3; x <= 3; x += 0.1) {
            var.setVal(x);
            System.out.println(x + "\t" + fun.getVal() + "\t" + der.getVal());
        }
        parser.remove("a");
        funStr = "sin(x)/x";
        fun = parser.parse(funStr);
        der = fun.derivative(var);
        System.out.println("f(x) = " + fun);
        System.out.println("f'(x) = " + der);
        for (double x = -3; x <= 3; x += 0.1) {
            var.setVal(x);
            System.out.println(x + "\t" + fun.getVal() + "\t" + der.getVal());
        }
    }
}
