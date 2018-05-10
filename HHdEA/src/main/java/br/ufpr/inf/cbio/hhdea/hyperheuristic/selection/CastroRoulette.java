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
package br.ufpr.inf.cbio.hhdea.hyperheuristic.selection;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author Gian Fritsche <gmfritsche@inf.ufpr.br>
 * @param <T>
 */
public class CastroRoulette<T> extends SelectionFunction<T> {

    private final JMetalRandom random;
    private int size;
    private double[] probabilities;
    private int s; // last lowLeveHeuristic selected
    private final double minProbability = 0.002;
    private final double x = 5.0;
    private double increment;

    public CastroRoulette() {
        random = JMetalRandom.getInstance();
    }

    @Override
    public void init() {
        size = lowlevelheuristics.size();
        probabilities = new double[size];

        double dsize = (double) size;
        for (int i = 0; i < size; ++i) {
            probabilities[i] = (1.0 / dsize);
        }

        s = 0;

        increment = (1.0 / dsize) / x;

    }

    @Override
    public T getNext() {
        double rand = random.nextDouble();
        double sum = probabilities[0];
        s = 0;
        for (int i = 1; i < size && rand > sum; ++i, s++) {
            sum += probabilities[i];
        }

        System.out.println("ROULETTE: " + s);

        return lowlevelheuristics.get(s);
    }

    @Override
    public void creditAssignment(double reward) {
        if (reward >= 0.0) {
            probabilities[s] += increment;
            double dsize = (double) size;
            for (int i = 0; i < size; i++) {
                if ((probabilities[i] - (increment / dsize)) > minProbability) {
                    probabilities[i] -= (increment / dsize);
                } else {
                    probabilities[s] -= (increment / dsize);
                }
            }
        } else {
            if (probabilities[s] - increment < minProbability) {
                increment = probabilities[s] - minProbability;
                probabilities[s] = minProbability;
            } else {
                probabilities[s] -= increment;
            }
            for (int i = 0; i < size; i++) {
                if ((probabilities[i] + (increment / size)) < 1 - (minProbability * (size - 1))) {
                    probabilities[i] += (increment / size);
                } else {
                    probabilities[s] += (increment / size);
                }
            }
        }
    }

}
