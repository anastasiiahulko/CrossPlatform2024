package consoleTasks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DerivativeApplication {

    public static void main(String[] args) throws IOException {
        Evaluatable[] functs = new Evaluatable[3];
        functs[0] = FunctionFactory.createFunction("FFunction", 0.5);
        functs[1] = FunctionFactory.createFunction("FileListInterpolation", 0.0);

        try {
            ((FileListInterpolation)functs[1]).readFromFile("TblFunc.dat");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex; // Re-throw the exception instead of exiting the program
        }

        String fileName ;
        for (Evaluatable f : functs) {
            System.out.println("Функція: " + f.getClass().getSimpleName());
            fileName = f.getClass().getSimpleName() + ".dat";
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            for (double x = 1.5; x <= 6.5; x += 0.05) {
                double funcValue = f.evalf(x);
                double derivativeValue = NumMethods.der(x, 1.0e-4, f);
                System.out.println("x: " + x + "\tf: " + funcValue + "\tf': " + derivativeValue);
                out.printf("%16.6e%16.6e%16.6e\n", x, funcValue, derivativeValue);
            }
            System.out.println("\n");
            out.close();
        }
    }
}
