package tsp.isula.sample;

import isula.aco.AcoProblemSolver;
import isula.aco.ConfigurationProvider;
import isula.aco.exception.InvalidInputException;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class TspProblemSolver extends AcoProblemSolver<Integer, TspEnvironment> {

    public TspProblemSolver(double[][] problemRepresentation, ConfigurationProvider configurationProvider) throws InvalidInputException {
        TspEnvironment environment = new TspEnvironment(problemRepresentation);

        int numberOfAnts = configurationProvider.getNumberOfAnts();
        int numberOfCities = environment.getNumberOfCities();

        TspAntColony antColony = new TspAntColony(numberOfAnts, numberOfCities);
        antColony.buildColony(environment);

        this.setConfigurationProvider(configurationProvider);
        this.setEnvironment(environment);
        this.setAntColony(antColony);
    }
}
