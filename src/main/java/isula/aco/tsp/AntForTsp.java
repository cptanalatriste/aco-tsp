package isula.aco.tsp;

import isula.aco.Ant;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An specialized Ant for building solutions for the TSP problem. It is designed according the algorithm described in
 * Section 6.3 of Clever Algorithms by Jason Brownlee.
 */
public class AntForTsp extends Ant<Integer, TspEnvironment> {

    private static final double DELTA = Float.MIN_VALUE;
    private final int numberOfCities;
    private int initialReference;

    public AntForTsp(int numberOfCities) {
        super();
        this.numberOfCities = numberOfCities;
        this.setSolution(new ArrayList<>());
    }

    @Override
    public void clear() {
        super.clear();
        this.initialReference = new Random().nextInt(this.numberOfCities);
    }

    /**
     * On TSP, a solution is ready when all the cities are part of the solution.
     *
     * @param environment Environment instance with problem information.
     * @return True if the solution is ready.
     */
    @Override
    public boolean isSolutionReady(TspEnvironment environment) {
        return getCurrentIndex() == environment.getNumberOfCities();
    }


    /**
     * On TSP, the cost of a solution is the total distance traversed by the salesman.
     *
     * @param environment Environment instance with problem information.
     * @return Total distance.
     */
    @Override
    public double getSolutionCost(TspEnvironment environment) {
        return getTotalDistance(getSolution(), environment);
    }

    @Override
    public double getSolutionCost(TspEnvironment environment, List<Integer> solution) {
        return getTotalDistance(solution, environment);
    }


    /**
     * The heuristic contribution in TSP is related to the added travel distance given by selecting an specific component.
     * According to the algorithm on the book, when the solution is empty we take a random city as a reference.
     *
     * @param solutionComponent  Solution component.
     * @param positionInSolution Position of this component in the solution.
     * @param environment        Environment instance with problem information.
     * @return Heuristic contribution.
     */
    @Override
    public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution, TspEnvironment environment) {
        Integer lastComponent = this.initialReference;
        if (getCurrentIndex() > 0) {
            lastComponent = this.getSolution().get(getCurrentIndex() - 1);
        }
        double distance = getDistance(lastComponent, solutionComponent, environment) + DELTA;
        return 1 / distance;
    }

    /**
     * Just retrieves a value from the pheromone matrix.
     *
     * @param solutionComponent  Solution component.
     * @param positionInSolution Position of this component in the solution.
     * @param environment        Environment instance with problem information.
     */
    @Override
    public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
                                         TspEnvironment environment) {

        Integer previousComponent = this.initialReference;
        if (positionInSolution > 0) {
            previousComponent = getSolution().get(positionInSolution - 1);
        }

        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        return pheromoneMatrix[solutionComponent][previousComponent];
    }

    /**
     * On TSP, the neighbourhood is given by the non-visited cities.
     *
     * @param environment Environment instance with problem information.
     */
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


    /**
     * Just updates the pheromone matrix.
     *
     * @param solutionComponent  Solution component.
     * @param positionInSolution Position of this component in the solution.
     * @param environment        Environment instance with problem information.
     * @param value              New pheromone value.
     */
    @Override
    public void setPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
                                       TspEnvironment environment, Double value) {
        Integer previousComponent = this.initialReference;
        if (positionInSolution > 0) {
            previousComponent = getSolution().get(positionInSolution - 1);
        }

        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        pheromoneMatrix[solutionComponent][previousComponent] = value;
        pheromoneMatrix[previousComponent][solutionComponent] = value;

    }


    /**
     * Calculates the total distance of a route for the salesman.
     *
     * @param route       Route to evaluate.
     * @param environment Environment with coordinate information.
     * @return Total distance.
     */
    public static double getTotalDistance(List<Integer> route, TspEnvironment environment) {
        double totalDistance = 0.0;

        for (int solutionIndex = 1; solutionIndex < route.size(); solutionIndex += 1) {
            int previousSolutionIndex = solutionIndex - 1;
            totalDistance += getDistance(route.get(previousSolutionIndex), route.get(solutionIndex), environment);
        }

        totalDistance += getDistance(route.get(route.size() - 1), route.get(0), environment);

        return totalDistance;
    }

    /**
     * Calculates the distance between two cities.
     *
     * @param anIndex      Index of a city.
     * @param anotherIndex Index of another city.
     * @param environment  Environment with Coordinate information.
     * @return Distance between these cities.
     */
    public static double getDistance(int anIndex, int anotherIndex, TspEnvironment environment) {
        if (EdgeWeightType.EUCLIDEAN_DISTANCE.equals(environment.getEdgeWeightType())) {
            return getEuclideanDistance(anIndex, anotherIndex, environment.getProblemRepresentation());
        } else if (EdgeWeightType.PSEUDO_EUCLIDEAN_DISTANCE.equals(environment.getEdgeWeightType())) {
            return getPseudoEuclideanDistance(anIndex, anotherIndex, environment.getProblemRepresentation());
        }

        throw new RuntimeException("Edge Weight Type unespecified or not supported");
    }


    private static double getEuclideanDistance(int anIndex, int anotherIndex, double[][] problemRepresentation) {
        double[] aCoordinate = getCityCoordinates(anIndex, problemRepresentation);
        double[] anotherCoordinate = getCityCoordinates(anotherIndex, problemRepresentation);

        return Math.round(new EuclideanDistance().compute(aCoordinate, anotherCoordinate));
    }

    private static double getPseudoEuclideanDistance(int anIndex, int anotherIndex, double[][] problemRepresentation) {
        double[] aCoordinate = getCityCoordinates(anIndex, problemRepresentation);
        double[] anotherCoordinate = getCityCoordinates(anotherIndex, problemRepresentation);

        double xDelta = aCoordinate[0] - anotherCoordinate[0];
        double yDelta = aCoordinate[1] - anotherCoordinate[1];
        double candidateDistance = Math.sqrt((Math.pow(xDelta, 2) + Math.pow(yDelta, 2)) / 10.0);
        long roundCandidateDistance = Math.round(candidateDistance);

        if (roundCandidateDistance < candidateDistance) {
            return roundCandidateDistance + 1;
        }

        return roundCandidateDistance;
    }

    /**
     * Extracts the coordinates of a city from the coordinates array.
     *
     * @param index                 City index.
     * @param problemRepresentation Coordinates array.
     * @return Coordinates as a double array.
     */
    private static double[] getCityCoordinates(int index, double[][] problemRepresentation) {
        return new double[] {problemRepresentation[index][0],
            problemRepresentation[index][1]};

    }


}
