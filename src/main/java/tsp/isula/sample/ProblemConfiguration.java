package tsp.isula.sample;

import isula.aco.ConfigurationProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class ProblemConfiguration implements ConfigurationProvider {

    private double initialPheromoneValue;

    public ProblemConfiguration(double[][] problemRepresentation) {
        List<Integer> randomSolution = new ArrayList<>();
        int numberOfCities = problemRepresentation.length;

        for (int cityIndex = 0; cityIndex < numberOfCities; cityIndex += 1) {
            randomSolution.add(cityIndex);
        }

        Collections.shuffle(randomSolution);

        double randomQuality = AntForTsp.getTotalDistance(
                randomSolution.toArray(new Integer[randomSolution.size()]),
                problemRepresentation);
        this.initialPheromoneValue = numberOfCities / randomQuality;
    }

    public int getNumberOfAnts() {
        return 10;
    }

    public double getEvaporationRatio() {
        return 0.6;
    }

    public int getNumberOfIterations() {
        return 50;
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
