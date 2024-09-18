public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        coefficients = new double[]{0}; // Polynomial: 0
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone(); // Cloning to avoid reference issues
    }

    public Polynomial add(Polynomial other) {
        int maxDegree = Math.max(this.coefficients.length, other.coefficients.length);
        double[] resultCoefficients = new double[maxDegree];

        for (int i = 0; i < maxDegree; i++) {
            double thisCoeff = (i < this.coefficients.length) ? this.coefficients[i] : 0;
            double otherCoeff = (i < other.coefficients.length) ? other.coefficients[i] : 0;
            resultCoefficients[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(resultCoefficients);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i); // Evaluate each term: a_i * x^i
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0; // A root is where the polynomial evaluates to 0
    }
}