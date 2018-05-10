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
package br.ufpr.inf.cbio.hhdea.algorithm.HHdEA;

import br.ufpr.inf.cbio.hhdea.hyperheuristic.selection.CastroRoulette;
import br.ufpr.inf.cbio.hhdea.hyperheuristic.selection.SelectionFunction;
import br.ufpr.inf.cbio.hhdea.metrics.MetricsEvaluator;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

/**
 *
 * @author Gian Fritsche <gmfritsche@inf.ufpr.br>
 * @param <S>
 */
public class HHdEA<S extends Solution<?>> implements Algorithm<List<S>> {

    protected int maxGenerations;
    protected Problem<S> problem;
    protected List<CooperativeAlgorithm<S>> algorithms;
    private final int populationSize;
    private final String name;
    private MetricsEvaluator metrics;

    public HHdEA(List<CooperativeAlgorithm<S>> algorithms, int populationSize, int maxGenerations, Problem problem, String name) {
        this.algorithms = algorithms;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.problem = problem;
        this.name = name;
    }

    @Override
    public void run() {

        SelectionFunction<CooperativeAlgorithm> selection = new CastroRoulette<>();
        for (CooperativeAlgorithm alg : algorithms) {
            alg.init(populationSize);
            selection.add(alg);
        }
        selection.init();

        int generations = algorithms.size();
        this.metrics = new MetricsEvaluator(problem, populationSize);

        while (generations <= maxGenerations) {

            // heuristic selection
            CooperativeAlgorithm<S> alg = selection.getNext();

            // apply selected heuristic
            List<S> parents = new ArrayList<>();
            for (S s : alg.getPopulation()) {
                parents.add((S) s.copy());
            }
            alg.doIteration();
            generations++;
            List<S> offspring = new ArrayList<>();
            for (S s : alg.getPopulation()) {
                offspring.add((S) s.copy());
            }

            // extract metrics
            metrics.extractMetrics(parents, offspring);
            // metrics.log(alg.getClass().getSimpleName());

            // compute reward
            selection.creditAssignment(metrics.getMetric(MetricsEvaluator.Metrics.FIR_R2_THC));

            // move acceptance
            // ALL MOVES
            // cooperation phase
            for (CooperativeAlgorithm<S> neighbor : algorithms) {
                if (neighbor != alg) {
                    List<S> migrants = new ArrayList<>();
                    for (S s : alg.getPopulation()) {
                        migrants.add((S) s.copy());
                    }
                    neighbor.receive(migrants);
                }
            }
        }
    }

    @Override
    public List<S> getResult() {
        List<S> union = new ArrayList<>();
        for (CooperativeAlgorithm alg : algorithms) {
            union.addAll(alg.getPopulation());
        }
        return MOEADUtils.getSubsetOfEvenlyDistributedSolutions(SolutionListUtils.getNondominatedSolutions(union), 240);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Hyper-heuristics for distributed Evolutionary Algorithms";
    }

}
