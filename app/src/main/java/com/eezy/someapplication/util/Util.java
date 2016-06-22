package com.eezy.someapplication.util;

import android.support.v4.app.Fragment;

import com.eezy.someapplication.MainActivity;
import com.eezy.someapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {

    public static void launchFragment(MainActivity activity, Fragment fragment, Integer currentStep, Integer totalNumOfSteps, String TAG) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                .replace(R.id.fragment_container, fragment, TAG)
                .commit();
        activity.setSteps(currentStep, totalNumOfSteps);
    }

    public static void launchFragmentWithoutAnim(MainActivity activity, Fragment fragment, Integer currentStep, Integer totalNumOfSteps, String TAG) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, TAG)
                .commit();
        activity.setSteps(currentStep, totalNumOfSteps);
    }

    public static void shuffleArray(int[] array, Random r) {
        int index, temp;
        for (int i = array.length - 1; i > 0; i--)
        {
            index = r.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public static int[] combineArray(int[] array1, int[] array2) {
        int[] array1and2 = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, array1and2, 0, array1.length);
        System.arraycopy(array2, 0, array1and2, array1.length, array2.length);
        return array1and2;
    }

    public static int pickRandomly(int[] array, Random random) {
        int rnd = random.nextInt(array.length);
        return array[rnd];
    }

    public static int[] pickRandomly(int[] array, Random random, int numOfElements) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        Collections.shuffle(list, random);

        int[] result = new int[numOfElements];
        for (int i = 0; i < numOfElements; ++i) {
            result[i] = list.get(i);
        }

        return result;
    }
}