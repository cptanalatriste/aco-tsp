package isula.aco.tsp;

import isula.aco.Environment;
import isula.aco.exception.InvalidInputException;

import java.util.logging.Logger;

/**
 * The Environment type is for storing problem-specific information. In the TSP scenario, is only relateed
 * to the number of cities.
 */
public class TspEnvironment extends Environment {

    private static Logger logger = Logger.getLogger(TspEnvironment.class.getName());

    private final int numberOfCities;

    public TspEnvironment(double[][] problemGraph) throws InvalidInputException {
        super(problemGraph);
        this.numberOfCities = problemGraph.length;
        logger.info("Number of cities: " + numberOfCities);
    }


    public int getNumberOfCities() {
        return getProblemGraph().length;
    }


    /**
     * The pheromone matrix in the TSP problem stores a pheromone value per city and per position of this city on
     * the route. That explains the dimensions selected for the pheromone matrix.
     *
     * @return Pheromone matrix instance.
     */
    @Override
    protected double[][] createPheromoneMatrix() {
        int numberOfCities = getNumberOfCities();
        return new double[numberOfCities][numberOfCities];
    }
}
