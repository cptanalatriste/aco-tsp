package tsp.isula.sample;

import isula.aco.*;
import isula.aco.algorithms.antsystem.OfflinePheromoneUpdate;
import isula.aco.algorithms.antsystem.PerformEvaporation;
import isula.aco.algorithms.antsystem.RandomNodeSelection;
import isula.aco.algorithms.antsystem.StartPheromoneMatrix;
import isula.aco.exception.InvalidInputException;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class solves the Berlin52 instance of the TSPLIB repository using an Ant System algorithm,
 * trying to emulate the procedure present in Section 6.3 of Clever Algorithms by
 * Jason Brownlee.
 */
public class AcoTspWithIsula {

    private static Logger logger = Logger.getLogger(AcoTspWithIsula.class.getName());

    public static void main(String... args) throws IOException, InvalidInputException, ConfigurationException {
        logger.info("ACO FOR THE TRAVELING SALESMAN PROBLEM");

        String fileName = "C:\\Users\\Carlos G. Gavidia\\git\\aco-tsp\\src\\main\\resources\\berlin52.tsp";
        logger.info("fileName : " + fileName);

        double[][] problemRepresentation = getRepresentationFromFile(fileName);

        ProblemConfiguration configurationProvider = new ProblemConfiguration(problemRepresentation);
        AntColony<Integer, TspEnvironment> colony = getAntColony(configurationProvider);
        TspEnvironment environment = new TspEnvironment(problemRepresentation);

        AcoProblemSolver<Integer, TspEnvironment> solver = new AcoProblemSolver<>();
        solver.initialize(environment, colony, configurationProvider);
        solver.addDaemonActions(new StartPheromoneMatrix<Integer, TspEnvironment>(),
                new PerformEvaporation<Integer, TspEnvironment>());

        solver.addDaemonActions(getPheromoneUpdatePolicy());

        solver.getAntColony().addAntPolicies(new RandomNodeSelection<Integer, TspEnvironment>());
        solver.solveProblem();
    }

    /**
     * Produces an Ant Colony instance for the TSP problem.
     *
     * @param configurationProvider Algorithm configuration.
     * @return Ant Colony instance.
     */
    private static AntColony<Integer, TspEnvironment> getAntColony(final ProblemConfiguration configurationProvider) {
        return new AntColony<Integer, TspEnvironment>(configurationProvider.getNumberOfAnts()) {
            @Override
            protected Ant<Integer, TspEnvironment> createAnt(TspEnvironment environment) {
                int initialReference = new Random().nextInt(environment.getNumberOfCities());
                return new AntForTsp(environment.getNumberOfCities(), initialReference);
            }
        };
    }

    /**
     * On TSP, the pheromone value update procedure depends on the distance of the generated routes.
     *
     * @return A daemon action that implements this procedure.
     */
    private static DaemonAction<Integer, TspEnvironment> getPheromoneUpdatePolicy() {
        return new OfflinePheromoneUpdate<Integer, TspEnvironment>() {
            @Override
            protected double getNewPheromoneValue(Ant<Integer, TspEnvironment> ant,
                                                  Integer positionInSolution,
                                                  Integer solutionComponent,
                                                  TspEnvironment environment,
                                                  ConfigurationProvider configurationProvider) {
                Double contribution = 1 / ant.getSolutionCost(environment);
                return ant.getPheromoneTrailValue(solutionComponent, positionInSolution, environment) + contribution;
            }
        };
    }

    private static double[][] getRepresentationFromFile(String fileName) throws IOException {
        List<Double> xCoordinates = new ArrayList<>();
        List<Double> yCoordinates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");

                if (tokens.length == 3) {
                    xCoordinates.add(Double.parseDouble(tokens[1]));
                    yCoordinates.add(Double.parseDouble(tokens[2]));
                }
            }
        }

        double[][] representation = new double[xCoordinates.size()][2];
        for (int index = 0; index < xCoordinates.size(); index += 1) {
            representation[index][0] = xCoordinates.get(index);
            representation[index][1] = yCoordinates.get(index);

        }

        return representation;
    }


}
