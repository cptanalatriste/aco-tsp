package tsp.isula.sample;

import isula.aco.exception.InvalidInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class AcoTspWithIsula {

    private static Logger logger = Logger.getLogger(AcoTspWithIsula.class.getName());

    public static void main(String... args) throws IOException, InvalidInputException {
        logger.info("ACO FOR THE TRAVELING SALESMAN PROBLEM");
        ProblemConfiguration problemConfiguration = new ProblemConfiguration();

        String fileName = problemConfiguration.getFileName();
        logger.info("fileName : " + fileName);

        double[][] problemRepresentation = getRepresentationFromFile(fileName);
        TspProblemSolver problemSolver = new TspProblemSolver(problemRepresentation, problemConfiguration);

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
