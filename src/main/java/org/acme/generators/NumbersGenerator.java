package org.acme.generators;

import java.util.Random;

public class NumbersGenerator {

    public NumbersGenerator(){
        throw new IllegalStateException("Utility class");
    }

    public static Double generateNormalDistributedNumber(double mean, double variance) {
        Random random = new Random();
        double standardDeviation = Math.sqrt(variance);
        return random.nextGaussian() * standardDeviation + mean;
    }
}
