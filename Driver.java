import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Test the no-argument constructor (represents the polynomial 0). Result should be 0.
        Polynomial p = new Polynomial();
        System.out.println("Evaluating polynomial p (0): p(3) = " + p.evaluate(3));

        // Test the constructor with non-zero coefficients and exponents (unordered)
        double[] c1 = {5, -2, 6};
        int[] e1 = {2, 1, 0};
        Polynomial p1 = new Polynomial(c1, e1);

        // Another unordered polynomial
        double[] c2 = {-9, 3};
        int[] e2 = {0, 1};
        Polynomial p2 = new Polynomial(c2, e2);

        // Adding two polynomials
        Polynomial sum = p1.add(p2);
        System.out.println("Evaluating sum polynomial (6x^2 - 2x + 5 - 9 + 3x): sum(0.1) = " + sum.evaluate(0.1));

        // Check if 1 is a root of the sum polynomial
        if (sum.hasRoot(1)) {
            System.out.println("1 is a root of the sum polynomial.");
        } else {
            System.out.println("1 is not a root of the sum polynomial.");
        }

        // Test the multiply method
        Polynomial product = p1.multiply(p2);
        System.out.println("Evaluating product polynomial: product(1) = " + product.evaluate(1));

        // Test file initialization
        try {
            File inputFile = new File("polynomial.txt");
            Polynomial p3 = new Polynomial(inputFile);
            System.out.println("Evaluating polynomial from file: p3(2) = " + p3.evaluate(2));
        } catch (IOException e) {
            System.out.println("Error reading polynomial from file: " + e.getMessage());
        }

        // Test saving polynomial to a file
        try {
            sum.saveToFile("result.txt");
            System.out.println("Polynomial saved to result.txt: " + sum);
        } catch (IOException e) {
            System.out.println("Error saving polynomial to file: " + e.getMessage());
        }
    }
}
