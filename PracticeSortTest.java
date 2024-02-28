package com.luxoft.trainings.jmh.practice;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

// to remove warning in Java 9+ you can use -Djdk.module.illegalAccess=deny
@State(Scope.Thread)
public class PracticeSortTest {

    private Integer[] arr;

    private Integer[] getRandomArray(int size) {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (new Random()).nextInt();
        }
        return arr;
    }

    @Setup(Level.Invocation)
    public void setup() {
        arr = getRandomArray(10_000);
        Arrays.sort(arr, Collections.reverseOrder());
    }

    @Benchmark
    public void bubbleSortTest() {
        SortingAlgorithm.bubbleSort(arr);
    }

    @Benchmark
    public void quickSortTest() {
        SortingAlgorithm.quickSort(arr);
    }

    @Benchmark
    public void mergeSortTest() {
        Arrays.sort(arr); //java.util.Arrays.useLegacyMergeSort
    }

//    @Benchmark
//    public void timSortTest() {
//        Arrays.sort(arr);
//    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(PracticeSortTest.class.getSimpleName())
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(3))
                .measurementIterations(2)
                .measurementTime(TimeValue.seconds(3))
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
