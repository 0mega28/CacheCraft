package com.example;

import com.example.evictionpolicy.EvictionPolicy;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.example.CacheTestingStrategy.CacheCommand.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheTestingStrategy {
    private static class Timer {
        private final String name;
        private long start;
        private long totalTime = 0L;

        Timer(String name) {
            this.name = name;
        }

        void start() {
            this.start = System.nanoTime();
        }

        void stop() {
            long now = System.nanoTime();
            totalTime += now - start;
            start = now;
        }

        void end() {
            System.out.println("Timer: (" + name + ") took (ms): " + TimeUnit.NANOSECONDS.toMillis(totalTime));
        }
    }

    sealed interface CacheCommand permits Get, Put {
        record Get(int key) implements CacheCommand {
        }

        record Put(int key, int value) implements CacheCommand {
        }

        static CacheCommand generateCacheCommand(int capacity) {
            final int keyLow = 1, keyHigh = capacity * 5;
            final int valueLow = 1, valueHigh = capacity * 5;
            final int newKey = getRandom(keyLow, keyHigh);
            final int newValue = getRandom(valueLow, valueHigh);
            boolean generateGet = Math.random() > 0.5;

            return generateGet ?
                    new Get(newKey) :
                    new Put(newKey, newValue);
        }
    }

    private final static int CAPACITY = 100;
    private final Cache<Integer, Integer> cacheToTest;
    private final Cache<Integer, Integer> cacheDumb;
    private final Timer testTimer;
    private final Timer dumbTimer;

    private CacheTestingStrategy(
            EvictionPolicy<Integer> evictionPolicyToTest,
            EvictionPolicy<Integer> evictionPolicyDumb
    ) {
        cacheToTest = new CacheImpl<>(CAPACITY, evictionPolicyToTest);
        cacheDumb = new CacheImpl<>(CAPACITY, evictionPolicyDumb);
        testTimer = new Timer("Test Strategy");
        dumbTimer = new Timer("Dumb Strategy");
    }

    private static int getRandom(int low, int high) {
        return low + (int) (Math.random() * (high - low));
    }

    private void testCache(CacheCommand cacheCommand) {
        switch (cacheCommand) {
            case Get(int key) -> {
                testTimer.start();
                Optional<Integer> value = cacheToTest.get(key);
                testTimer.stop();

                dumbTimer.start();
                Optional<Integer> expected = cacheDumb.get(key);
                dumbTimer.stop();

                assertEquals(value, expected);
                assertEquals(cacheToTest.size(), cacheDumb.size());
            }
            case Put(int key, int value) -> {
                testTimer.start();
                cacheToTest.put(key, value);
                testTimer.stop();

                dumbTimer.start();
                cacheDumb.put(key, value);
                dumbTimer.stop();

                assertEquals(cacheToTest.size(), cacheDumb.size());
            }
        }
    }

    public static void doTest(EvictionPolicy<Integer> evictionPolicyToTest
            , EvictionPolicy<Integer> evictionPolicyDumb) {
        var cacheTestingStrategy = new CacheTestingStrategy(evictionPolicyToTest, evictionPolicyDumb);
        int numOfTestCases = 100_000;
        Stream
                .generate(() -> generateCacheCommand(CAPACITY))
                .limit(numOfTestCases)
                .forEach(cacheTestingStrategy::testCache);
        cacheTestingStrategy.testTimer.end();
        cacheTestingStrategy.dumbTimer.end();
    }
}
