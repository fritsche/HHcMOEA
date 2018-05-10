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
package br.ufpr.inf.cbio.hhdea.algorithm.MOEADD;

import br.ufpr.inf.cbio.hhdea.config.AlgorithmConfiguration;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;

/**
 *
 * @author Gian Fritsche <gmfritsche@inf.ufpr.br>
 */
public class MOEADDConfiguration implements AlgorithmConfiguration<MOEADD<?>> {

    protected double crossoverProbability;
    protected double crossoverDistributionIndex;
    protected double mutationProbability;
    protected double mutationDistributionIndex;
    protected Problem problem;
    private int generations;
    private int popSize;
    protected int maxEvaluations;
    protected CrossoverOperator<DoubleSolution> crossover;
    protected MutationOperator<DoubleSolution> mutation;

    @Override
    public void setup() {
        crossoverProbability = 1.0;
        crossoverDistributionIndex = 30.0;
        mutationProbability = 1.0 / problem.getNumberOfVariables();
        mutationDistributionIndex = 20.0;
        maxEvaluations = generations * popSize;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
        mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
    }

    @Override
    public MOEADD configure(Problem problem, int popSize, int generations) {

        this.problem = problem;
        this.generations = generations;
        this.popSize = popSize;

        setup();

        return new MOEADDBuilder(problem).setCrossover(crossover)
                .setMutation(mutation)
                .setMaxEvaluations(maxEvaluations)
                .setPopulationSize(popSize).build();
    }

}
