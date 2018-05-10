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

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.experiment.ExperimentComponent;
import org.uma.jmetal.util.experiment.Experiment;

import java.io.File;

public class ExecuteAlgorithms<S extends Solution<?>, Result> implements ExperimentComponent {
  private Experiment<S, Result> experiment;

  /** Constructor
     * @param configuration */
  public ExecuteAlgorithms(Experiment<S, Result> configuration) {
    this.experiment = configuration ;
  }

  protected Experiment<S, Result> getExperiment(){
      return experiment;
  }
  
  @Override
  public void run() {
    JMetalLogger.logger.info("ExecuteAlgorithms: Preparing output directory");
    prepareOutputDirectory() ;

    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
            "" + this.experiment.getNumberOfCores());

    for (int i = 0; i < experiment.getIndependentRuns(); i++) {
      final int id = i ;

      experiment.getAlgorithmList()
              .parallelStream()
              .forEach(algorithm -> algorithm.runAlgorithm(id, experiment)) ;
    }
  }



  protected void prepareOutputDirectory() {
    if (experimentDirectoryDoesNotExist()) {
      createExperimentDirectory() ;
    }
  }

  private boolean experimentDirectoryDoesNotExist() {
    File experimentDirectory;
    experimentDirectory = new File(experiment.getExperimentBaseDirectory());
    return !(experimentDirectory.exists() && experimentDirectory.isDirectory());
  }

  private void createExperimentDirectory() {
    File experimentDirectory;
    experimentDirectory = new File(experiment.getExperimentBaseDirectory());

    if (experimentDirectory.exists()) {
      experimentDirectory.delete() ;
    }

    boolean result ;
    result = new File(experiment.getExperimentBaseDirectory()).mkdirs() ;
    if (!result) {
      throw new JMetalException("Error creating experiment directory: " +
          experiment.getExperimentBaseDirectory()) ;
    }
  }
}