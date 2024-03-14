package org.acme.generators;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
class NumberGeneratorTest {

    @BeforeEach
    void setUp(){
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextGaussian()).thenReturn(1.0);
        NumbersGenerator.setRandom(random);
    }

    @Test
    void testGenerateNormalDistributedNumber(){
        double result = NumbersGenerator.generateNormalDistributedNumber(0, 1);
        assertEquals(1, result);

        result = NumbersGenerator.generateNormalDistributedNumber(10, 1);
        assertEquals(11, result);

        result = NumbersGenerator.generateNormalDistributedNumber(3, 1);
        assertEquals(4, result);

        result = NumbersGenerator.generateNormalDistributedNumber(3, 1);
        assertEquals(4, result);
    }

    @Test
    void testGenerateNormalDistributedNumber2() {
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextGaussian()).thenReturn(2.0);

        NumbersGenerator.setRandom(random);

        double result = NumbersGenerator.generateNormalDistributedNumber(3, 4);
        assertEquals(7, result);
    }
}
