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

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;

/**
 *
 * @author Gian Fritsche <gmfritsche@inf.ufpr.br>
 * @param <S>
 */
public class HHdEABuilder<S extends Solution<?>> implements AlgorithmBuilder<HHdEA<S>> {

    private List<CooperativeAlgorithm> algorithms;
    private int populationSize;
    private int maxGenerations;
    private final Problem problem;
    private String name;

    public HHdEABuilder(Problem problem) {
        this.problem = problem;
        name = "HHdEA"; // default name
    }

    public String getName() {
        return name;
    }

    public HHdEABuilder setName(String name) {
        this.name = name;
        return this;
    }

    public List<CooperativeAlgorithm> getAlgorithms() {
        return algorithms;
    }

    public HHdEABuilder setAlgorithms(List<CooperativeAlgorithm> algorithms) {
        this.algorithms = algorithms;
        return this;
    }

    public HHdEABuilder addAlgorithm(CooperativeAlgorithm algorithm) {
        if (algorithms == null) {
            algorithms = new ArrayList<>();
        }
        algorithms.add(algorithm);
        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public HHdEABuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public HHdEABuilder setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
        return this;
    }

    @Override
    public HHdEA build() {
        return new HHdEA(algorithms, populationSize, maxGenerations, problem, name);
    }

}
