package sorting;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.lang.reflect.Method;

// contains all the sorting algorithms for assignment 5
public class Sort {
    // does insertion sort
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int currentValue = array[i];
            int j = i - 1;
            // stored the original value in current value
            while (j >= 0 && array[j] < currentValue) { // while the element preceding is lower, switch the values
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = currentValue; // update the currentvalue to proper position to avoid doubling an element
        }
    }
    // does bubble sort
    public static void bubbleSort(int[] array) {
        boolean pass = false; // if bubble sort pass doesn't change a value, then stop bubble sorting
        for (int i = 1; i < array.length; i++) {
            int currentValue = array[i];
            if (i - 1 >= 0 && array[i - 1] < currentValue) {
                array[i] = array[i - 1];
                pass = true; // update pass if something needs to be changed
                array[i - 1] = currentValue;
            }
        }
        if (pass) { // keep bubble sorting if additional pass required
            bubbleSort(array);
        }
    }
    // does shell sort
    public static void shellSort(int[] array) {
        int k = 0;
        // picks the maximum value at which the shell sort can be conducted
        while (Math.pow(2, (k+1)) - 1 < array.length) {
            k++;
        }
        // factor value is the starting value for shell sort
        int factor = (int)Math.pow(2, k) - 1;
        // decrement k and update the factor value for each pass, the final k value will be 1
        // when k value is 1, every single element will be assessed and sorted
        for (; k > 0; k--) {
            factor = (int)Math.pow(2, k) - 1;
            for (int i = factor; i < array.length; i++) {
                int currentValue = array[i];
                int j = i - factor;
                boolean change = false;
                while (j >= 0 && array[j] < currentValue) {
                    array[j + factor] = array[j];
                    j -= factor;
                }
                array[j + factor] = currentValue;
            }
        }
    }
    // does quickSort, with the help of quickSort helper array
    public static void quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
    }
    // does the quick sorting
    private static void quickSortHelper(int[] array, int start, int end) {
        // when the start and end values meet, the array is sorted and the recursion loop can be broken
        if (start >= end) {
            return;
        }
        // start boundary at element before array, the boundary seperates the terms greater and less than the pivot
        int boundary = start - 1;
        int pivot = array[end];
        int index = start;
        // increment the boundary and switch the values with the boundary value
        while (index <= end) {
            if (array[index] >= pivot) {
                ++boundary;
                switchValues(array, index, boundary);
            }
            index++;
        }
        // call the quick sort on the left side of the pivot, as well as the right side of the pivot
        quickSortHelper(array, start, boundary - 1);
        quickSortHelper(array, boundary + 1, end);
    }
    // helper method for quickSort, switches the values at index1 and index2
    private static void switchValues(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    // does merge sort
    public static void mergeSort(int[] array) {
        // when the array is size 1 or lower, the merge sort can return the values
        if (array.length < 2) {
            return;
        }
        // the middle value seperates the arrays
        int middleValue = array.length / 2;
        // create the left side array
        int[] left = new int[middleValue];
        for (int i = 0; i < middleValue; i++) {
            left[i] = array[i];
        }
        // creates the right side array
        int[] right = new int[array.length - middleValue];
        for (int i = middleValue; i < array.length; i++) {
            right[i - middleValue] = array[i];
        }
        // keep calling merge sort until array is size 1
        mergeSort(left);
        mergeSort(right);
        // calls merge sort helper method in order to sort the left and right values in the given array
        mergeSortHelper(left, right, array);
    }
    // helps merge sort above
    private static void mergeSortHelper(int[] left, int[] right, int[] sortedArray){
        int i = 0; // i is to increment over left array
        int j = 0; // j is to increment over right array
        int k = 0; // k is to increment over the final sorted array
        // increment over left and right arrays to determine order of values in the sorted array
        // values will be put into sorted array index k, k will only be incremented after comparison
        // i and j will only be incremented if the next value of left or right array respectively needs to be assessed
        while (i < left.length && j < right.length) {
            if (left[i] >= right[j]) {
                sortedArray[k] = left[i];
                k++;
                i++;
            }
            else {
                sortedArray[k] = right[j];
                j++;
                k++;
            }
        }
        // remaining items in either array will be put into final sorted array
        while (i < left.length) {
            sortedArray[k] = left[i];
            k++;
            i++;
        }
        while (j < right.length) {
            sortedArray[k] = right[j];
            k++;
            j++;
        }
    }
    // upgraded quick sort method requires help of 2 helper methods, and insertion sort from above
    public static void upgradedQuickSort(int[] array) {
        upgradedQuickSortHelper(array, 0, array.length - 1);
        upgradedMergeSort(array);
        insertionSort(array);
    }
    // k is instantiated outside of this method, the k is not allowed to go higher than 3, this can be changed by directly
    // changing the if statement block in the method
    private static void upgradedMergeSort(int[] array) {
        // break out of recursion not when the array is completely sorted, only at specified k value
        if (k > 3) {
            return;
        }
        int middleValue = array.length / 2;
        int[] left = new int[middleValue];
        for (int i = 0; i < middleValue; i++) {
            left[i] = array[i];
        }
        int[] right = new int[array.length - middleValue];
        for (int i = middleValue; i < array.length; i++) {
            right[i - middleValue] = array[i];
        }
        k++;
        // recursive call
        upgradedMergeSort(left);
        upgradedMergeSort(right);
        // helper method, works same as the one up above, for the regular mergeSort helper method
        upgradedMergeSortHelper(left, right, array);
    }
    // helps merge sort portion of upgradedQuickSort method
    private static void upgradedMergeSortHelper(int[] left, int[] right, int[] sortedArray) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] >= right[j]) {
                sortedArray[k] = left[i];
                k++;
                i++;
            } else {
                sortedArray[k] = right[j];
                j++;
                k++;
            }
        }
        // remaining items
        while (i < left.length) {
            sortedArray[k] = left[i];
            k++;
            i++;
        }
        while (j < right.length) {
            sortedArray[k] = right[j];
            k++;
            j++;
        }
    }
    // depth is assigned 0 to start, this field is only for upgradedQuickSort
    private static int depth = 0;
    // k is set to 0 to start, this field is only used for upgradedMergeSort which is part of upgradedQuickSort
    private static int k = 0;
    // helper method for quickSort portion of upgradedQuickSort method, works similarly to regular quickSort
    // stops at specified depth (3), this can be changed by directly changing it within the method
    private static void upgradedQuickSortHelper(int[] array, int start, int end) {
        int boundary = start - 1;
        int pivot = array[end];
        int index = start;
        int subarrayEnd = end;
        for (; depth+1 < 3; depth++) {
            while (subarrayEnd <= end) {
                pivot = array[subarrayEnd];
                while (index <= subarrayEnd) {
                    if (array[index] >= pivot) {
                        ++boundary;
                        switchValues(array, index, boundary);
                    }
                    index++;
                }
                subarrayEnd += (array.length / (depth+1)) ;
            }
            subarrayEnd = subarrayEnd % array.length;
        }
    }
    // this method generates the random array
    public static int[] generateRandomArray(int n){
        int[] array = new int[n];
        for (int i = 0; i < n - 1; i++) {
            // multiply by 100000 because arraySize used in methods is 5000, generates sufficient amount of random numbers
            // range for numbers is integers between 0 and 100000
            array[i] = (int)(Math.random() * 100000);
        }
        // return int array
        return array;
    }
    // filepath is given in input of method readCommands
    public static void readCommands(String filepath) throws IOException {
        // reads the file
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine();
            Method method = null;
            // reads line by line, until the line is null
            while (line != null) {
                // seperate around commas, colons, spaces, and brackets
                // first element of string array splitted is the name of the method, the rest are the numbers in the array
                line = line.replaceAll("[^a-zA-Z0-9-,:]", " ").replaceAll(" ", "").replace(":", ",");
                String[] splitted = line.split(",");
                String methodName = splitted[0]; // first element of string array is the methodName
                int[] integersToSort = new int[splitted.length - 1];
                int index = 1;
                // for the int array integersToSort
                while (index < splitted.length) {
                    integersToSort[index - 1] = Integer.parseInt(splitted[index]);
                    index++;
                }
                // determines algorithm to execute then executes the algorithm
                // compare the methodName in the file to the string version of the method name, and then execute actual method
                switch (methodName) {
                    case "insertionSort":
                        insertionSort(integersToSort);
                        System.out.println(methodName + " " + Arrays.toString(integersToSort));
                        break;
                    case "bubbleSort":
                        bubbleSort(integersToSort);
                        System.out.println(methodName + " " + Arrays.toString(integersToSort));
                        break;
                    case "shellSort":
                        shellSort(integersToSort);
                        System.out.println(methodName + " " + Arrays.toString(integersToSort));
                        break;
                    case "quickSort":
                        quickSort(integersToSort);
                        System.out.println(methodName + " " + Arrays.toString(integersToSort));
                        break;
                    case "mergeSort":
                        mergeSort(integersToSort);
                        System.out.println(methodName + " " + Arrays.toString(integersToSort));
                        break;
                    case "upgradedQuickSort":
                        upgradedMergeSort(integersToSort);
                        System.out.println(methodName + " " + Arrays.toString(integersToSort));
                        break;
                }
                line = br.readLine(); // reads the next line
            }
        }
        // catches the exception from the try statement block previous
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // main method, simply reads commands from the input file
    // the filepath in readCommands is my filepath for the given txt file in the assignment
    // can update the main method to change filepath and test other txt files
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        readCommands("C:\\Users\\neeta\\IdeaProjects\\Sort\\src\\Sort.txt");
    }
}

