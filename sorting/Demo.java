package sorting;

import java.util.Arrays;
import java.util.Objects;

public class Demo extends  Sort {
    // benchmark randomly generated arrays
    public static void benchmark(String algorithm) {

        int[] randomArray = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        int i;
        for (i = 0; i < randomArray.length; i++) {
            int[] generatedArray = generateRandomArray(randomArray[i]);
            double start = System.nanoTime();
            switch (algorithm) {
                case "insertionSort":
                    insertionSort(generatedArray);
                    break;
                case "bubbleSort":
                    bubbleSort(generatedArray);
                    break;
                case "shellSort":
                    shellSort(generatedArray);
                    break;
                case "quickSort":
                    quickSort(generatedArray);
                    break;
                case "mergeSort":
                    mergeSort(generatedArray);
                    break;
                case "upgradedQuickSort":
                    upgradedQuickSort(generatedArray);
                    break;
            }
            double end = System.nanoTime();
            // print result of end time minus start time, algorithm name, and size of array
            System.out.println(algorithm + "; Size " + generatedArray.length + "; time: " + (end - start));
        }
    }
    // benchmark already sorted array
    public static void benchmarkSorted(String algorithm) {

        int[] randomArray = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        int i;
        for (i = 0; i < randomArray.length; i++) {
            int[] generatedArray = generateRandomArray(randomArray[i]);
            quickSort(generatedArray); // sort it already
            double start = System.nanoTime();
            switch (algorithm) {
                case "insertionSort":
                    insertionSort(generatedArray);
                    break;
                case "bubbleSort":
                    bubbleSort(generatedArray);
                    break;
                case "shellSort":
                    shellSort(generatedArray);
                    break;
                case "quickSort":
                    quickSort(generatedArray);
                    break;
                case "mergeSort":
                    mergeSort(generatedArray);
                    break;
                case "upgradedQuickSort":
                    upgradedQuickSort(generatedArray);
                    break;
            }
            double end = System.nanoTime();
            // print result of end time minus start time, algorithm name, and size of array
            System.out.println(algorithm + "; Sorted Already, Size: " + generatedArray.length + "; time: " + (end - start));
        }
    }
    // benchmark reverse sorted arrays
    public static void benchmarkReverseSorted(String algorithm) {

        int[] randomArray = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        int i;
        for (i = 0; i < randomArray.length; i++) {
            int[] generatedArray = generateRandomArray(randomArray[i]);
            Arrays.sort(generatedArray); // reverse sort it already using java's method (sorts in ascending order)
            double start = System.nanoTime();
            switch (algorithm) {
                case "insertionSort":
                    insertionSort(generatedArray);
                    break;
                case "bubbleSort":
                    bubbleSort(generatedArray);
                    break;
                case "shellSort":
                    shellSort(generatedArray);
                    break;
                case "quickSort":
                    quickSort(generatedArray);
                    break;
                case "mergeSort":
                    mergeSort(generatedArray);
                    break;
                case "upgradedQuickSort":
                    upgradedQuickSort(generatedArray);
                    break;
            }
            double end = System.nanoTime();
            // print result of end time minus start time, algorithm name, and size of array
            System.out.println(algorithm + "; Reverse Sorted, Size: " + generatedArray.length + "; time: " + (end - start));
        }
    }
    // execute all the sorting
    public static void main(String[] args) {

		/*
		Carry out your timing for the methods here:
		-> Generate Sorted, unsorted, random arrays of size "size"
		-> Run the sorting algorithms on these arrays and time them
		-> Printout the results like this:
			long time = *get the the runtime*
			System.out.printf(" ____sort time: %ld\n",time);
		*/
        benchmark("insertionSort");
        benchmark("bubbleSort");
        benchmark("shellSort");
        benchmark("quickSort");
        benchmark("mergeSort");
        benchmark("upgradedQuickSort");
        benchmark();

        benchmarkSorted("insertionSort");
        benchmarkSorted("bubbleSort");
        benchmarkSorted("shellSort");
        benchmarkSorted("quickSort");
        benchmarkSorted("mergeSort");
        benchmarkSorted("upgradedQuickSort");
        benchmarkSorted();

        benchmarkReverseSorted("insertionSort");
        benchmarkReverseSorted("bubbleSort");
        benchmarkReverseSorted("shellSort");
        benchmarkReverseSorted("quickSort");
        benchmarkReverseSorted("mergeSort");
        benchmarkReverseSorted("upgradedQuickSort");
        benchmarkReverseSorted();

    }
    // to benchmark java's sorting algorithm
    public static void benchmark() {
        int[] randomArray = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        int i;
        for (i = 0; i < randomArray.length; i++) {
            int[] generatedArray = generateRandomArray(randomArray[i]);
            double start = System.nanoTime();
            Arrays.sort(generatedArray);
            double end = System.nanoTime();
            System.out.println("Java's sorting algorithm; Sorted Already, Size " + generatedArray.length + "; time: " + (end - start));
            // works same as the other benchmark method, this is only for java's given sorting algo
        }
    }
    // benchmark java's algorithm for already sorted array
    public static void benchmarkSorted() {
        int[] randomArray = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        int i;
        for (i = 0; i < randomArray.length; i++) {
            int[] generatedArray = generateRandomArray(randomArray[i]);
            Arrays.sort(generatedArray); // sort it already
            double start = System.nanoTime();
            Arrays.sort(generatedArray);
            double end = System.nanoTime();
            System.out.println("Java's sorting algorithm; Already Sorted, Size " + generatedArray.length + "; time: " + (end - start));
            // works same as the other benchmark method, this is only for java's given sorting algo
        }
    }
    // benchmark java's algorithm for reverse sorted array
    public static void benchmarkReverseSorted() {
        int[] randomArray = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        int i;
        for (i = 0; i < randomArray.length; i++) {
            int[] generatedArray = generateRandomArray(randomArray[i]);
            quickSort(generatedArray); // reverse sort it already using my method (descending order, java's method does ascending order)
            double start = System.nanoTime();
            Arrays.sort(generatedArray);
            double end = System.nanoTime();
            System.out.println("Java's sorting algorithm; Reverse Sorted, Size " + generatedArray.length + "; time: " + (end - start));
            // works same as the other benchmark method, this is only for java's given sorting algo
        }
    }
}



