package tsp.isula.sample;

import isula.aco.Environment;
import isula.aco.exception.InvalidInputException;

import java.util.logging.Logger;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class TspEnvironment extends Environment {

    private static Logger logger = Logger.getLogger(TspEnvironment.class.getName());

    private final int numberOfCities;

    /**
     * Creates an Environment for the Ants to traverse.
     *
     * @param problemGraph Graph representation of the problem to be solved.
     * @throws InvalidInputException When the problem graph is incorrectly formed.
     */
    public TspEnvironment(double[][] problemGraph) throws InvalidInputException {
        super(problemGraph);
        this.numberOfCities = problemGraph.length;

        logger.info("Number of cities: " + numberOfCities);
    }


    public int getNumberOfCities() {
        return getProblemGraph().length;
    }


    @Override
    protected double[][] createPheromoneMatrix() {
        int numberOfCities = getNumberOfCities();
        return new double[numberOfCities][numberOfCities];
    }
}
