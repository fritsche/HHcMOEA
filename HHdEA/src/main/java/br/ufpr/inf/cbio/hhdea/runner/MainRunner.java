/*
 * Copyright (C) 2018 Gian Fritsche <gmfritsche@inf.ufpr.br>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.ufpr.inf.cbio.hhdea.runner;

import br.ufpr.inf.cbio.hhdea.config.AlgorithmConfigurationFactory;
import br.ufpr.inf.cbio.hhdea.problem.InvertedProblem;
import br.ufpr.inf.cbio.hhdea.problem.MaF.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ2;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ3;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ4;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.problem.multiobjective.wfg.WFG2;
import org.uma.jmetal.problem.multiobjective.wfg.WFG3;
import org.uma.jmetal.problem.multiobjective.wfg.WFG4;
import org.uma.jmetal.problem.multiobjective.wfg.WFG5;
import org.uma.jmetal.problem.multiobjective.wfg.WFG6;
import org.uma.jmetal.problem.multiobjective.wfg.WFG7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG8;
import org.uma.jmetal.problem.multiobjective.wfg.WFG9;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author Gian Fritsche <gmfritsche@inf.ufpr.br>
 * @param <S>
 * @param <Result>
 */
public class MainRunner<S extends Solution<?>, Result> extends ExecuteAlgorithms<S, Result> {

    private final int id;

    public static List<ExperimentProblem<DoubleSolution>> getProblemList(String problem, int m) {
        List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();

        if (problem.startsWith("Minus")) {
            problemList.add(new ExperimentProblem<>(new InvertedProblem((DoubleProblem) getProblemList(problem.substring(5), m).get(0).getProblem(), problem)));
            return problemList;
        }

        int k, l;
        switch (problem) {
            case "MaF01":
                k = 10;
                problemList.add(new ExperimentProblem<>(new MaF01(m + k - 1, m)));
                break;
            case "MaF02":
                k = 10;
                problemList.add(new ExperimentProblem<>(new MaF02(m + k - 1, m)));
                break;
            case "MaF03":
                k = 10;
                problemList.add(new ExperimentProblem<>(new MaF03(m + k - 1, m)));
                break;
            case "MaF04":
                k = 10;
                problemList.add(new ExperimentProblem<>(new MaF04(m + k - 1, m)));
                break;
            case "MaF05":
                k = 10;
                problemList.add(new ExperimentProblem<>(new MaF05(m + k - 1, m)));
                break;
            case "MaF06":
                k = 10;
                problemList.add(new ExperimentProblem<>(new MaF06(m + k - 1, m)));
                break;
            case "MaF07":
                k = 20;
                problemList.add(new ExperimentProblem<>(new MaF07(m + k - 1, m)));
                break;
            case "MaF08":
                problemList.add(new ExperimentProblem<>(new MaF08(2, m)));
                break;
            case "MaF09":
                problemList.add(new ExperimentProblem<>(new MaF09(2, m)));
                break;
            case "MaF10":
                k = m - 1;
                l = 10;
                problemList.add(new ExperimentProblem<>(new MaF10(k + l, m)));
                break;
            case "MaF11":
                k = m - 1;
                l = 10;
                problemList.add(new ExperimentProblem<>(new MaF11(k + l, m)));
                break;
            case "MaF12":
                k = m - 1;
                l = 10;
                problemList.add(new ExperimentProblem<>(new MaF12(k + l, m)));
                break;
            case "MaF13":
                problemList.add(new ExperimentProblem<>(new MaF13(5, m)));
                break;
            case "MaF14":
                problemList.add(new ExperimentProblem<>(new MaF14(m * 20, m)));
                break;
            case "MaF15":
                problemList.add(new ExperimentProblem<>(new MaF15(m * 20, m)));
                break;
            case "DTLZ1":
                k = 5;
                problemList.add(new ExperimentProblem<>(new DTLZ1(m + k - 1, m)));
                break;
            case "DTLZ2":
                k = 10;
                problemList.add(new ExperimentProblem<>(new DTLZ2(m + k - 1, m)));
                break;
            case "DTLZ3":
                k = 10;
                problemList.add(new ExperimentProblem<>(new DTLZ3(m + k - 1, m)));
                break;
            case "DTLZ4":
                k = 10;
                problemList.add(new ExperimentProblem<>(new DTLZ4(m + k - 1, m)));
                break;
            case "DTLZ7":
                k = 20;
                problemList.add(new ExperimentProblem<>(new DTLZ7(m + k - 1, m)));
            case "WFG1":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG1(k, 20, m)));
                break;
            case "WFG2":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG2(k, 20, m)));
                break;
            case "WFG3":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG3(k, 20, m)));
                break;
            case "WFG4":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG4(k, 20, m)));
                break;
            case "WFG5":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG5(k, 20, m)));
                break;
            case "WFG6":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG6(k, 20, m)));
                break;
            case "WFG7":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG7(k, 20, m)));
                break;
            case "WFG8":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG8(k, 20, m)));
                break;
            case "WFG9":
                k = 2 * (m - 1);
                problemList.add(new ExperimentProblem<>(new WFG9(k, 20, m)));
                break;
            default:
                throw new JMetalException("There is no configurations for " + problem + " problem");
        }
        return problemList;
    }

    public static void main(String[] args) {

        // do not print info
        JMetalLogger.logger.setLevel(Level.WARNING);

        if (args.length != 6) {
            throw new JMetalException("Needed arguments: "
                    + "outputDirectory algorithm problem m id seed");
        }

        int i = 0;
        String experimentBaseDirectory = args[i++];
        String algorithm = args[i++];
        String problem = args[i++];
        int m = Integer.parseInt(args[i++]);
        int id = Integer.parseInt(args[i++]);
        int seed = Integer.parseInt(args[i++]);
        int popSize = getPopSize(m);
        int generations = getGenerationsNumber(problem, m, popSize);

        JMetalRandom.getInstance().setSeed(seed);

        List<ExperimentProblem<DoubleSolution>> problemList = getProblemList(problem, m);
        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

        algorithms.add(
                new ExperimentAlgorithm<>(AlgorithmConfigurationFactory
                        .getAlgorithmConfiguration(algorithm)
                        .configure(problemList.get(0).getProblem(), popSize, generations),
                        problemList.get(0).getTag()));

        ExperimentBuilder<DoubleSolution, List<DoubleSolution>> study = new ExperimentBuilder<>(Integer.toString(m));

        study.setAlgorithmList(algorithms);

        study.setProblemList(problemList);

        study.setExperimentBaseDirectory(experimentBaseDirectory);

        study.setOutputParetoFrontFileName(
                "FUN");
        study.setOutputParetoSetFileName(
                "VAR");
        study.setIndependentRuns(
                1);
        study.setNumberOfCores(
                1);
        Experiment<DoubleSolution, List<DoubleSolution>> experiment = study.build();

        new MainRunner(experiment, id)
                .run();

    }

    @Override
    public void run() {
        JMetalLogger.logger.info("ExecuteAlgorithms: Preparing output directory");
        super.prepareOutputDirectory();
        Experiment<S, Result> experiment = getExperiment();
        experiment.getAlgorithmList().get(0).runAlgorithm(this.id, experiment);
    }

    public static int getGenerationsNumber(String problem, int m, int popSize) {

        int generations = 0;

        if (problem.startsWith("Minus")) {
            return getGenerationsNumber(problem.substring(5), m, popSize);
        } else if (problem.startsWith("MaF")) {
            return Math.max(100000, 10000 * m) / popSize;
        }

        switch (m) {
            case 3:
                switch (problem) {
                    case "DTLZ1":
                        generations = 400; // DTLZ1
                        break;
                    case "DTLZ2":
                        generations = 250; // DTLZ2
                        break;
                    case "DTLZ3":
                        generations = 1000; // DTLZ3
                        break;
                    case "DTLZ4":
                        generations = 600; // DTLZ4
                        break;
                    case "DTLZ7":
                        generations = 1000; // DTLZ7
                        break;
                    case "WFG1":
                    case "WFG2":
                    case "WFG3":
                    case "WFG4":
                    case "WFG5":
                    case "WFG6":
                    case "WFG7":
                    case "WFG8":
                    case "WFG9":
                        generations = 400; // WFG
                        break;
                }
                break;
            case 5:
                switch (problem) {
                    case "DTLZ1":
                        generations = 600; // DTLZ1
                        break;
                    case "DTLZ2":
                        generations = 350; // DTLZ2
                        break;
                    case "DTLZ3":
                        generations = 1000; // DTLZ3
                        break;
                    case "DTLZ4":
                        generations = 1000; // DTLZ4
                        break;
                    case "DTLZ7":
                        generations = 1000; // DTLZ7
                        break;
                    case "WFG1":
                    case "WFG2":
                    case "WFG3":
                    case "WFG4":
                    case "WFG5":
                    case "WFG6":
                    case "WFG7":
                    case "WFG8":
                    case "WFG9":
                        generations = 750; // WFG
                        break;
                }
                break;
            case 8:
                switch (problem) {
                    case "DTLZ1":
                        generations = 750; // DTLZ1
                        break;
                    case "DTLZ2":
                        generations = 500; // DTLZ2
                        break;
                    case "DTLZ3":
                        generations = 1000; // DTLZ3
                        break;
                    case "DTLZ4":
                        generations = 1250; // DTLZ4
                        break;
                    case "DTLZ7":
                        generations = 1000; // DTLZ7
                        break;
                    case "WFG1":
                    case "WFG2":
                    case "WFG3":
                    case "WFG4":
                    case "WFG5":
                    case "WFG6":
                    case "WFG7":
                    case "WFG8":
                    case "WFG9":
                        generations = 1500; // WFG
                        break;
                }
                break;
            case 10:
                switch (problem) {
                    case "DTLZ1":
                        generations = 1000;  // DTLZ1
                        break;
                    case "DTLZ2":
                        generations = 750;   // DTLZ2
                        break;
                    case "DTLZ3":
                        generations = 1500;  // DTLZ3
                        break;
                    case "DTLZ4":
                        generations = 2000;  // DTLZ4
                        break;
                    case "DTLZ7":
                        generations = 1500; // DTLZ7
                        break;
                    case "WFG1":
                    case "WFG2":
                    case "WFG3":
                    case "WFG4":
                    case "WFG5":
                    case "WFG6":
                    case "WFG7":
                    case "WFG8":
                    case "WFG9":
                        generations = 2000; // WFG
                        break;
                }
                break;
            case 15:
                switch (problem) {
                    case "DTLZ1":
                        generations = 1500;  // DTLZ1
                        break;
                    case "DTLZ2":
                        generations = 1000;  // DTLZ2
                        break;
                    case "DTLZ3":
                        generations = 2000;  // DTLZ3
                        break;
                    case "DTLZ4":
                        generations = 3000;  // DTLZ4
                        break;
                    case "DTLZ7":
                        generations = 2000; // DTLZ7
                        break;
                    case "WFG1":
                    case "WFG2":
                    case "WFG3":
                    case "WFG4":
                    case "WFG5":
                    case "WFG6":
                    case "WFG7":
                    case "WFG8":
                    case "WFG9":
                        generations = 3000; // WFG
                        break;
                }
                break;
            default:
                throw new JMetalException("There is no configurations for " + m + " objectives");
        }
        return generations;
    }

    public static int getPopSize(int m) {
        int popSize = 0;
        switch (m) {
            case 3:
                popSize = 91;
                break;
            case 5:
                popSize = 210;
                break;
            case 8:
                popSize = 156;
                break;
            case 10:
                popSize = 275;
                break;
            case 15:
                popSize = 135;
                break;
        }
        return popSize;
    }

    public MainRunner(Experiment<S, Result> configuration, int id) {
        super(configuration);
        this.id = id;
    }
}
