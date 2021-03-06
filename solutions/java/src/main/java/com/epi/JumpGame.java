package com.epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dontrelax on 3/8/14.
 */
public class JumpGame {
  // @include
  public static boolean canReach(final List<Integer> A) {
    int step = 0, lastReach = 0, reach = 0;
    for (int i = 0; i < A.size(); ++i) {
      if (i > lastReach) {
        if (lastReach == reach) {
          return false;
        }
        lastReach = reach;
        ++step;
      }
      reach = Math.max(reach, i + A.get(i));
    }
    return true;
  }

  // @exclude

  private static void smallTest() {
    List<Integer> A = Arrays.asList(2, 3, 1, 1, 4);
    assert (canReach(A));
    A = Arrays.asList(3, 2, 1, 0, 4);
    assert (!canReach(A));
    A = Arrays.asList(3, 2, 1, -10, 4);
    assert (!canReach(A));
    A = Arrays.asList(2, 3, -1, -1, 4);
    assert (canReach(A));
    A = Arrays.asList(2, 2, -1, -1, 100);
    assert (!canReach(A));
  }

  public static void main(String[] args) {
    smallTest();
    Random r = new Random();
    int n;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    } else {
      n = r.nextInt(1000) + 1;
    }
    List<Integer> A = new ArrayList<Integer>();
    for (int i = 0; i < n; i++) {
      A.add(r.nextInt(10) + 1);
    }
    System.out.println(canReach(A));
  }
}
