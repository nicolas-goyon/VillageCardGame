package org.acme.generators;

import java.security.SecureRandom;

public class NumbersGenerator {

    private static SecureRandom random = new SecureRandom();

    private NumbersGenerator(){
        throw new IllegalStateException("Utility class");
    }

    public static void setRandom(SecureRandom random){
        NumbersGenerator.random = random;
    }

    public static Double generateNormalDistributedNumber(double mean, double variance) {
        double standardDeviation = Math.sqrt(variance);
        return random.nextGaussian() * standardDeviation + mean;
    }
}
