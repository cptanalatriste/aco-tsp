package tsp.isula.sample;

import isula.aco.Ant;
import isula.aco.ConfigurationProvider;
import isula.aco.algorithms.antsystem.OfflinePheromoneUpdate;

/**
 * Created by Carlos G. Gavidia on 31/03/2016.
 */
public class TspUpdatePheromoneMatrix extends OfflinePheromoneUpdate<Integer, TspEnvironment> {

    @Override
    protected double getNewPheromoneValue(Ant<Integer, TspEnvironment> ant, Integer positionInSolution,
                                          Integer solutionComponent, TspEnvironment environment, ConfigurationProvider configurationProvider) {
        Double contribution = 1 / ant.getSolutionCost(environment);
        return ant.getPheromoneTrailValue(solutionComponent, positionInSolution, environment) + contribution;
    }
}
