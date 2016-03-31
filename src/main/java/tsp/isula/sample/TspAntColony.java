package tsp.isula.sample;

import isula.aco.Ant;
import isula.aco.AntColony;

import java.util.Random;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class TspAntColony extends AntColony<Integer, TspEnvironment> {

    private int numberOfCities;
    private int initialReference;


    public TspAntColony(int numberOfAnts, int numberOfCities) {
        super(numberOfAnts);
        this.numberOfCities = numberOfCities;
        this.initialReference = new Random().nextInt(numberOfCities);
    }

    @Override
    protected Ant<Integer, TspEnvironment> createAnt(TspEnvironment environment) {
        return new AntForTsp(numberOfCities, initialReference);
    }
}
