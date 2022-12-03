package com.gmail.projectCrypt.backend.cryptData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;


// recursion, merge sort, linked list, stacks and queues, binary search


public class Validation {
    public boolean firstNamecheck(String firstName){
        LinkedList<Character> fName = new LinkedList<Character>();
        //adding string to LinkedList
        for (char c : firstName.toLowerCase().toCharArray()) {
            fName.add(c);
        }



        //Checking linked list
        for (int i = 0; i < firstName.length(); i++) {
            if ((int)fName.getFirst()< 97 || (int)fName.getFirst() > 122){
                return false;


            }
            else{
                fName.removeFirst();
            }



        }
        return true;
    }


    public boolean lastNamecheck(String lastName){

        //Using two stacks to implement a queue as the O complexity of this algorithm becomes O(1)
        Stack<Character> s1 = new Stack<Character>();
        Stack<Character> s2  = new Stack<Character>();


        for(char c: lastName.toLowerCase().toCharArray()){
            s1.push(c);

        }



        for(int i=0;i<lastName.length();i++) {
            if (s2.empty()) {
                while (!s1.empty()) {
                    s2.push(s1.peek());
                    s1.pop();
                }
            }

            // return the top item from s2
            char x = s2.peek();
            s2.pop();
            //if the character is not in the alphabet then false is returned
            if ((int)x< 97 || (int)x > 122){
                return false;


            }





        }
        return true;

    }


    public boolean emailcheck(String email){

        char[] emaillist = email.toCharArray();
        //Convert the character array into integers with the UTF-8 value
        int[] charnumbers = new int[email.length()];

        for (int i = 0; i < email.length(); i++){
            charnumbers[i] = emaillist[i];


        }
        //Creating a clone of the character numbers array to use for comparison
        int [] charnumber2 = charnumbers.clone();
        //Sort the emails characters
        mergeSort(charnumbers);
        //Check if @ is present in the email
        if(binarySearch(charnumbers)){
            //If @ symbol at beginning or end print false else print true
            if(charnumber2[0] == 64 || charnumber2[charnumber2.length-1] == 64){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }






    }

    static boolean binarySearch(int[] charnumbers)
    {
        int x = 64;
        int left = 0;
        int right = charnumbers.length - 1;
        int symbolindex = 0;
        //Loop through the array
        while (left <= right) {
            //Find the middle of the array
            int middle = left + (right - 1) / 2;

            // Check if the @ symbol is present at the middle
            if (charnumbers[middle] == x)
                return true;

            // If the number at the middle is less than the ASCII value of @, look at the right side of the array
            if (charnumbers[middle] < x)
                left = middle + 1;

                // If the number at the middle is greater than the ASCII value of @, look at the left side of the array
            else
                right = middle - 1;
        }
        //Return  false if @ symbol not found
        return false;

    }

    private static void mergeSort(int[] charnumbers) {
        //check if array is less than 2 characters then return those numbers
        int n = charnumbers.length;
        if (n < 2) {
            return;
        }
        //Split the array into two arrays left and right
        int middle = n / 2;
        int[] left = new int[middle];
        int[] right = new int[n - middle];

        //Add the contents of the original array into the two left and right arrays
        for (int i = 0; i < middle; i++) {
            left[i] = charnumbers[i];
        }
        for (int i = middle; i < n; i++) {
            right[i - middle] = charnumbers[i];
        }
        //Continue splitting those arrays using recursion
        mergeSort(left);
        mergeSort(right);
        //Merge the arrays once they are all split into one array
        merge(charnumbers, left, right);
    }

    private static void merge(
            int[] charnumbers, int[] left, int[] right) {

        int middle = charnumbers.length / 2;
        int l = middle;
        int r = charnumbers.length - middle;

        int i = 0, j = 0, k = 0;
        //Merging and sorting the temporary arrays
        while (i < l && j < r) {
            if (left[i] <= right[j]) {
                charnumbers[k] = left[i];
                k++;
                i++;
            }
            else {
                charnumbers[k] = right[j];
                k++;
                j++;
            }
        }
        //Add remaining numbers from both temporary arrays
        while (i < l) {
            charnumbers[k++] = left[i++];
        }
        while (j < r) {
            charnumbers[k++] = right[j++];
        }






    }
}
