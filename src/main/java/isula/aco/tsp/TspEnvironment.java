package isula.aco.tsp;

import isula.aco.Environment;

import java.util.logging.Logger;

/**
 * The Environment type is for storing problem-specific information. In the TSP scenario, is only relateed
 * to the number of cities.
 */
public class TspEnvironment extends Environment {

    private static Logger logger = Logger.getLogger(TspEnvironment.class.getName());

    private final double[][] problemRepresentation;
    private final EdgeWeightType edgeWeightType;

    public TspEnvironment(double[][] problemGraph, EdgeWeightType edgeWeightType) {
        super();
        this.problemRepresentation = problemGraph;
        this.setPheromoneMatrix(createPheromoneMatrix());
        this.edgeWeightType = edgeWeightType;

        int numberOfCities = problemGraph.length;

        logger.info("Number of cities: " + numberOfCities);
        logger.info("Edge weight type: " + this.edgeWeightType);

    }


    public int getNumberOfCities() {
        return getProblemRepresentation().length;
    }

    public double[][] getProblemRepresentation() {
        return this.problemRepresentation;
    }


    /**
     * The pheromone matrix in the TSP problem stores a pheromone value per city and per position of this city on
     * the route. That explains the dimensions selected for the pheromone matrix.
     *
     * @return Pheromone matrix instance.
     */
    @Override
    protected double[][] createPheromoneMatrix() {
        if (this.problemRepresentation != null) {
            int numberOfCities = getNumberOfCities();
            return new double[numberOfCities][numberOfCities];
        }

        return null;

    }

    public EdgeWeightType getEdgeWeightType() {
        return edgeWeightType;
    }
}
