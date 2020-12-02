package tsp.isula.sample;

import isula.aco.ConfigurationProvider;
import isula.aco.tsp.AntForTsp;
import isula.aco.tsp.TspEnvironment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains the algorithm configuration of the Ant System algorithm described in
 * Section 6.3 of the Clever Algorithms book by Jason Brownlee.
 */
public class TspProblemConfiguration implements ConfigurationProvider {

    private double initialPheromoneValue;

    /**
     * In the algorithm described in the book, the initial pheromone value was a function of the quality of a
     * random solution. That logic is included in this constructor.
     *
     * @param environment TSP environment with coordinate information.
     */
    public TspProblemConfiguration(TspEnvironment environment) {
        List<Integer> randomSolution = new ArrayList<>();
        int numberOfCities = environment.getProblemRepresentation().length;

        for (int cityIndex = 0; cityIndex < numberOfCities; cityIndex += 1) {
            randomSolution.add(cityIndex);
        }

        Collections.shuffle(randomSolution);

        double randomQuality = AntForTsp.getTotalDistance(randomSolution, environment);
        this.initialPheromoneValue = numberOfCities / randomQuality;
    }

    public int getNumberOfAnts() {
        return 30;
    }

    public double getEvaporationRatio() {
        return 1 - 0.6;
    }

    public int getNumberOfIterations() {
        return 100;
    }


    public double getInitialPheromoneValue() {
        return this.initialPheromoneValue;
    }

    @Override
    public double getHeuristicImportance() {
        return 2.5;
    }

    @Override
    public double getPheromoneImportance() {
        return 1.0;
    }


}
