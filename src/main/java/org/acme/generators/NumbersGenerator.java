package org.acme.generators;

import java.security.SecureRandom;

public class NumbersGenerator {

    private NumbersGenerator(){
        throw new IllegalStateException("Utility class");
    }

    public static Double generateNormalDistributedNumber(double mean, double variance) {
        SecureRandom random = new SecureRandom();
        double standardDeviation = Math.sqrt(variance);
        return random.nextGaussian() * standardDeviation + mean;
    }
}
