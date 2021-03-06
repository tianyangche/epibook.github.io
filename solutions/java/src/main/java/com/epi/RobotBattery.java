// Copyright (c) 2013 Elements of Programming Interviews. All rights reserved.
package com.epi;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.epi.utils.Utils;

public class RobotBattery {

  // @include
  static int findBatteryCapacity(List<Integer> h) {
    int minHeight = Integer.MAX_VALUE, capacity = 0;
    for (Integer height : h) {
      capacity = max(capacity, height - minHeight);
      minHeight = min(minHeight, height);
    }
    return capacity;
  }

  // @exclude

  // O(n^2) checking answer.
  static int checkAns(List<Integer> h) {
    int cap = 0;
    for (int i = 1; i < h.size(); ++i) {
      for (int j = 0; j < i; ++j) {
        cap = max(cap, h.get(i) - h.get(j));
      }
    }
    return cap;
  }

  public static void main(String[] args) {
    Random gen = new Random();
    for (int times = 0; times < 1000; ++times) {
      int n;
      if (args.length == 1) {
        n = Integer.valueOf(args[0]);
      } else {
        n = gen.nextInt(10000) + 1;
      }
      List<Integer> A = new ArrayList<Integer>();
      for (int i = 0; i < n; ++i) {
        A.add(Utils.nextPositiveInt(gen));
      }
      System.out.println(findBatteryCapacity(A));
      assert (checkAns(A) == findBatteryCapacity(A));
    }
  }

}
