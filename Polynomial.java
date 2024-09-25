import java.io.*;
import java.util.ArrayList;

public class Polynomial {
    private double[] cf;
    private int[] exp;       

    public Polynomial() {
        this.cf = new double[]{0};
        this.exp = new int[]{0};
    }

    public Polynomial(double[] cf, int[] exp) {
        this.cf = cf.clone();
        this.exp = exp.clone();
    }

    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        br.close();
        parsePolynomial(line);
    }

    private void parsePolynomial(String polyString) {
        ArrayList<Double> coeffList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();
        
        String[] terms = polyString.split("(?=[+-])");

        for (String term : terms) {
            term = term.trim();
            if (term.contains("x")) {
                String[] parts = term.split("x\\^?");
                double coeff = parts[0].isEmpty() || parts[0].equals("+") ? 1 : (parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]));
                int exponent = parts.length == 1 ? 1 : Integer.parseInt(parts[1]);
                coeffList.add(coeff);
                expList.add(exponent);
            } else {
                coeffList.add(Double.parseDouble(term));
                expList.add(0);
            }
        }

        this.cf = new double[coeffList.size()];
        this.exp = new int[expList.size()];
        for (int i = 0; i < coeffList.size(); i++) {
            cf[i] = coeffList.get(i);
            exp[i] = expList.get(i);
        }
    }

    public Polynomial add(Polynomial other) {
        ArrayList<Double> resultCoeffs = new ArrayList<>();
        ArrayList<Integer> resultExps = new ArrayList<>();

        int i = 0, j = 0;
        while (i < this.cf.length || j < other.cf.length) {
            if (i < this.cf.length && (j >= other.cf.length || this.exp[i] < other.exp[j])) {
                resultCoeffs.add(this.cf[i]);
                resultExps.add(this.exp[i]);
                i++;
            } else if (j < other.cf.length && (i >= this.cf.length || other.exp[j] < this.exp[i])) {
                resultCoeffs.add(other.cf[j]);
                resultExps.add(other.exp[j]);
                j++;
            } else {
                resultCoeffs.add(this.cf[i] + other.cf[j]);
                resultExps.add(this.exp[i]);
                i++;
                j++;
            }
        }

        return new Polynomial(toDoubleArray(resultCoeffs), toIntArray(resultExps));
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < cf.length; i++) {
            result += cf[i] * Math.pow(x, exp[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial other) {
        ArrayList<Double> resultCoeffs = new ArrayList<>();
        ArrayList<Integer> resultExps = new ArrayList<>();

        for (int i = 0; i < this.cf.length; i++) {
            for (int j = 0; j < other.cf.length; j++) {
                double newCoeff = this.cf[i] * other.cf[j];
                int newExp = this.exp[i] + other.exp[j];

                int index = resultExps.indexOf(newExp);
                if (index >= 0) {
                    resultCoeffs.set(index, resultCoeffs.get(index) + newCoeff);
                } else {
                    resultCoeffs.add(newCoeff);
                    resultExps.add(newExp);
                }
            }
        }

        return new Polynomial(toDoubleArray(resultCoeffs), toIntArray(resultExps));
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(toString());
        bw.close();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cf.length; i++) {
            if (i > 0 && cf[i] > 0) {
                sb.append("+");
            }
            sb.append(cf[i]);
            if (exp[i] > 0) {
                sb.append("x");
                if (exp[i] > 1) {
                    sb.append("^").append(exp[i]);
                }
            }
        }
        return sb.toString();
    }

    private double[] toDoubleArray(ArrayList<Double> list) {
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private int[] toIntArray(ArrayList<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}