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
package br.ufpr.inf.cbio.hhdea.algorithm.NSGAII;

import br.ufpr.inf.cbio.hhdea.algorithm.HHdEA.CooperativeAlgorithm;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

/**
 *
 * @author Gian Fritsche <gmfritsche@inf.ufpr.br>
 * @param <S>
 */
public class CONSGAII<S extends Solution<?>> extends NSGAII implements CooperativeAlgorithm<S> {

    public CONSGAII(Problem problem, int maxEvaluations, int populationSize, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator, Comparator dominanceComparator, SolutionListEvaluator evaluator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, dominanceComparator, evaluator);
    }

    @Override
    public void init(int populationSize) {
        setMaxPopulationSize(populationSize + (populationSize % 2));
        population = createInitialPopulation();
        population = evaluatePopulation(population);
    }

    @Override
    public void doIteration() {
        List<S> offspringPopulation;
        List<S> matingPopulation;
        matingPopulation = selection(population);
        offspringPopulation = reproduction(matingPopulation);
        offspringPopulation = evaluatePopulation(offspringPopulation);
        population = replacement(population, offspringPopulation);
    }

    @Override
    public void receive(List<S> solutions) {
        population = replacement(population, solutions);
    }

}
