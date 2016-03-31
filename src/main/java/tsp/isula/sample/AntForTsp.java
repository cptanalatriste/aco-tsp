package tsp.isula.sample;

import isula.aco.Ant;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class AntForTsp extends Ant<Integer, TspEnvironment> {

    private static Logger logger = Logger.getLogger(AntForTsp.class.getName());

    private static final double DELTA = Float.MIN_VALUE;
    private final int initialReference;

    public AntForTsp(int numberOfCities, int initialReference) {
        super();
        this.setSolution(new Integer[numberOfCities]);
        this.initialReference = initialReference;
    }


    @Override
    public boolean isSolutionReady(TspEnvironment environment) {
        return getCurrentIndex() == environment.getNumberOfCities();
    }


    @Override
    public double getSolutionCost(TspEnvironment environment) {
        return getTotalDistance(getSolution(), environment.getProblemGraph());
    }


    @Override
    public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution, TspEnvironment environment) {
        Integer lastComponent = this.initialReference;
        if (getCurrentIndex() > 0) {
            lastComponent = this.getSolution()[getCurrentIndex() - 1];
        }
        double distance = getDistance(lastComponent, solutionComponent, environment.getProblemGraph()) + DELTA;
        return 1 / distance;
    }

    @Override
    public List<Integer> getNeighbourhood(TspEnvironment environment) {
        List<Integer> neighbourhood = new ArrayList<>();

        for (int cityIndex = 0; cityIndex < environment.getNumberOfCities(); cityIndex += 1) {
            if (!this.isNodeVisited(cityIndex)) {
                neighbourhood.add(cityIndex);
            }
        }

        return neighbourhood;
    }

    @Override
    public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
                                         TspEnvironment environment) {
        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        return pheromoneMatrix[solutionComponent][positionInSolution];
    }


    @Override
    public void setPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution, TspEnvironment environment, Double value) {
        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        //TODO(cgavidia): This method should also have the position in solution as a parameter. Verify impact in other projects.
        //TODO(cgavidia): Fix the bug present on the Flow Shop solution.
        pheromoneMatrix[solutionComponent][positionInSolution] = value;
    }


    public static double getTotalDistance(Integer[] route, double[][] problemRepresentation) {
        double totalDistance = 0.0;

        for (int solutionIndex = 1; solutionIndex < route.length; solutionIndex += 1) {
            int previousSolutionIndex = solutionIndex - 1;
            totalDistance += getDistance(route[previousSolutionIndex], route[solutionIndex], problemRepresentation);
        }

        return totalDistance;
    }

    public static double getDistance(int anIndex, int anotherIndex, double[][] problemRepresentation) {
        double[] aCoordinate = getCityCoordinates(anIndex, problemRepresentation);
        double[] anotherCoordinate = getCityCoordinates(anotherIndex, problemRepresentation);

        return new EuclideanDistance().compute(aCoordinate, anotherCoordinate);

    }

    private static double[] getCityCoordinates(int index, double[][] problemRepresentation) {
        return new double[]{problemRepresentation[index][0],
                problemRepresentation[index][1]};

    }


}
