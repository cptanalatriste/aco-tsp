package tsp.isula.sample;

import isula.aco.Ant;
import isula.aco.AntColony;

/**
 * Created by Carlos G. Gavidia on 30/03/2016.
 */
public class TspAntColony extends AntColony<Integer, TspEnvironment> {

    private final int numberOfCities;

    public TspAntColony(int numberOfAnts, int numberOfCities) {
        super(numberOfAnts);
        this.numberOfCities = numberOfCities;
    }

    @Override
    protected Ant<Integer, TspEnvironment> createAnt(TspEnvironment environment) {
        AntForTsp ant = new AntForTsp(numberOfCities);
        return ant;
    }
}
