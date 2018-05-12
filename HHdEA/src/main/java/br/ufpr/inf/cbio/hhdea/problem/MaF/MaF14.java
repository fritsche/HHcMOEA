package br.ufpr.inf.cbio.hhdea.problem.MaF;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

/**
 * Class representing problem MaF14
 */
public class MaF14 extends AbstractDoubleProblem {

    private final String name;
    public static int nk14;
    public static int sublen14[], len14[];

    /**
     * Creates a MaF14 problem instance
     *
     * @param numberOfVariables Number of variables
     * @param numberOfObjectives Number of objective functions
     */
    public MaF14(Integer numberOfVariables,
            Integer numberOfObjectives) {

        nk14 = 2;
        double[] c = new double[numberOfObjectives];
        c[0] = 3.8 * 0.1 * (1 - 0.1);
        double sumc = 0;
        sumc += c[0];
        for (int i = 1; i < numberOfObjectives; i++) {
            c[i] = 3.8 * c[i - 1] * (1 - c[i - 1]);
            sumc += c[i];
        }

        int[] sublen = new int[numberOfObjectives];
        int[] len = new int[numberOfObjectives + 1];
        len[0] = 0;
        for (int i = 0; i < numberOfObjectives; i++) {
            sublen[i] = (int) Math.ceil(Math.round(c[i] / sumc * numberOfVariables) / (double) nk14);
            len[i + 1] = len[i] + (nk14 * sublen[i]);
        }
        sublen14 = sublen;
        len14 = len;
//re-update numberOfObjectives_,numberOfVariables_
        numberOfVariables = numberOfObjectives - 1 + len[numberOfObjectives];

        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(numberOfObjectives);
        setNumberOfConstraints(0);
        this.name = "MaF14";

        List<Double> lower = new ArrayList<>(getNumberOfVariables()), upper = new ArrayList<>(getNumberOfVariables());

        for (int var = 0; var < numberOfVariables; var++) {
            lower.add(0.0);
            upper.add(1.0);
        } //for

        setLowerLimit(lower);
        setUpperLimit(upper);

    }

    /**
     * Evaluates a solution
     *
     * @param solution The solution to evaluate
     */
    @Override
    public void evaluate(DoubleSolution solution) {

        int numberOfVariables_ = solution.getNumberOfVariables();
        int numberOfObjectives_ = solution.getNumberOfObjectives();

        double[] x = new double[numberOfVariables_];
        double[] f = new double[numberOfObjectives_];

        for (int i = 0; i < numberOfVariables_; i++) {
            x[i] = solution.getVariableValue(i);
        }

//	change x
        for (int i = numberOfObjectives_ - 1; i < numberOfVariables_; i++) {
            x[i] = (1 + (i + 1) / (double) numberOfVariables_) * x[i] - 10 * x[0];
        }
//	evaluate eta,g
        double[] g = new double[numberOfObjectives_];
        double sub1;
        for (int i = 0; i < numberOfObjectives_; i = i + 2) {
            double[] tx = new double[sublen14[i]];
            sub1 = 0;
            for (int j = 0; j < nk14; j++) {
                System.arraycopy(x, len14[i] + numberOfObjectives_ - 1 + j * sublen14[i], tx, 0, sublen14[i]);
                sub1 += Rastrigin(tx);
            }
            g[i] = sub1 / (nk14 * sublen14[i]);
        }

        for (int i = 1; i < numberOfObjectives_; i = i + 2) {
            double[] tx = new double[sublen14[i]];
            sub1 = 0;
            for (int j = 0; j < nk14; j++) {
                System.arraycopy(x, len14[i] + numberOfObjectives_ - 1 + j * sublen14[i], tx, 0, sublen14[i]);
                sub1 += Rosenbrock(tx);
            }
            g[i] = sub1 / (nk14 * sublen14[i]);
        }

//	evaluate fm,fm-1,...,2,f1
        double subf1 = 1;
        f[numberOfObjectives_ - 1] = (1 - x[0]) * (1 + g[numberOfObjectives_ - 1]);
        for (int i = numberOfObjectives_ - 2; i > 0; i--) {
            subf1 *= x[numberOfObjectives_ - i - 2];
            f[i] = subf1 * (1 - x[numberOfObjectives_ - i - 1]) * (1 + g[i]);
        }
        f[0] = subf1 * x[numberOfObjectives_ - 2] * (1 + g[0]);

        for (int i = 0; i < numberOfObjectives_; i++) {
            solution.setObjective(i, f[i]);
        }

    }

    public static double Rastrigin(double[] x) {
        double eta = 0;
        for (int i = 0; i < x.length; i++) {
            eta += (Math.pow(x[i], 2) - 10 * Math.cos(2 * Math.PI * x[i]) + 10);
        }
        return eta;
    }

    public static double Rosenbrock(double[] x) {
        double eta = 0;
        for (int i = 0; i < x.length - 1; i++) {
            eta += (100 * Math.pow(Math.pow(x[i], 2) - x[i + 1], 2) + Math.pow((x[i] - 1), 2));
        }
        return eta;
    }

    @Override
    public String getName() {
        return name;
    }
}
