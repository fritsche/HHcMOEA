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
import br.ufpr.inf.cbio.hhdea.problem.MaF.*;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
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
public class MaFRunner<S extends Solution<?>, Result> extends ExecuteAlgorithms<S, Result> {

    private final int id;

    public static void main(String[] args) {

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

        JMetalRandom.getInstance().setSeed(seed);

        List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();
        List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();

        int d = 0;
        int k, l;
        switch (problem) {
            case "MaF01":
                k = 10;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF01(d, m)));
                break;
            case "MaF02":
                k = 10;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF02(d, m)));
                break;
            case "MaF03":
                k = 10;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF03(d, m)));
                break;
            case "MaF04":
                k = 10;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF04(d, m)));
                break;
            case "MaF05":
                k = 10;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF05(d, m)));
                break;
            case "MaF06":
                k = 10;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF06(d, m)));
                break;
            case "MaF07":
                k = 20;
                d = m + k - 1;
                problemList.add(new ExperimentProblem<>(new MaF07(d, m)));
                break;
            case "MaF08":
                d = 2;
                problemList.add(new ExperimentProblem<>(new MaF08(d, m)));
                break;
            case "MaF09":
                d = 2;
                problemList.add(new ExperimentProblem<>(new MaF09(d, m)));
                break;
            case "MaF10":
                k = m - 1;
                l = 10;
                d = k + l;
                problemList.add(new ExperimentProblem<>(new MaF10(d, m)));
                break;
            case "MaF11":
                k = m - 1;
                l = 10;
                d = k + l;
                problemList.add(new ExperimentProblem<>(new MaF11(d, m)));
                break;
            case "MaF12":
                k = m - 1;
                l = 10;
                d = k + l;
                problemList.add(new ExperimentProblem<>(new MaF12(d, m)));
                break;
            case "MaF13":
                d = 5;
                problemList.add(new ExperimentProblem<>(new MaF13(d, m)));
                break;
            case "MaF14":
                d = m * 20;
                problemList.add(new ExperimentProblem<>(new MaF14(d, m)));
                break;
            case "MaF15":
                d = m * 20;
                problemList.add(new ExperimentProblem<>(new MaF15(d, m)));
                break;
            default:
                throw new JMetalException("There is no configurations for " + problem + " problem");
        }

        int generations = Math.max(100000, 10000 * d) / popSize;

        algorithms.add(
                new ExperimentAlgorithm<>(AlgorithmConfigurationFactory
                        .getAlgorithmConfiguration(algorithm)
                        .configure(problemList.get(0).getProblem(), popSize, generations),
                        problemList.get(0).getTag()));

        ExperimentBuilder<DoubleSolution, List<DoubleSolution>> study = new ExperimentBuilder<>(Integer.toString(m));
        study.setAlgorithmList(algorithms);
        study.setProblemList(problemList);
        study.setExperimentBaseDirectory(experimentBaseDirectory);
        study.setOutputParetoFrontFileName("FUN");
        study.setOutputParetoSetFileName("VAR");
        study.setIndependentRuns(1);
        study.setNumberOfCores(1);
        Experiment<DoubleSolution, List<DoubleSolution>> experiment = study.build();

        new MaFRunner(experiment, id).run();

    }

    @Override
    public void run() {
        super.prepareOutputDirectory();
        Experiment<S, Result> experiment = getExperiment();
        experiment.getAlgorithmList().get(0).runAlgorithm(this.id, experiment);
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

    public MaFRunner(Experiment<S, Result> configuration, int id) {
        super(configuration);
        this.id = id;
    }
}
