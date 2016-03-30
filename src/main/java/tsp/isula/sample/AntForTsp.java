package tsp.isula.sample;

import isula.aco.Ant;

import java.util.List;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class AntForTsp extends Ant<Integer, TspEnvironment> {
    public AntForTsp(int numberOfCities) {
    }

    @Override
    public List<Integer> getNeighbourhood(TspEnvironment environment) {
        return null;
    }

    @Override
    public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution, TspEnvironment environment) {
        return null;
    }

    @Override
    public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution, TspEnvironment environment) {
        return null;
    }

    @Override
    public void setPheromoneTrailValue(Integer solutionComponent, TspEnvironment environment, Double value) {

    }

    @Override
    public double getSolutionCost(TspEnvironment environment) {
        return 0;
    }

    @Override
    public boolean isSolutionReady(TspEnvironment environment) {
        return false;
    }
}
