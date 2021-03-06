package com.epi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class CountInversions {
  // @include
  public static <T extends Comparable<T>> int countInversions(List<T> A) {
    return countInversionsHelper(A, 0, A.size());
  }

  private static <T extends Comparable<T>> int countInversionsHelper(List<T> a,
      int start, int end) {
    if (end - start <= 1) {
      return 0;
    }

    int mid = start + ((end - start) >> 1);
    return countInversionsHelper(a, start, mid)
        + countInversionsHelper(a, mid, end) + merge(a, start, mid, end);
  }

  private static <T extends Comparable<T>> int merge(List<T> A, int start,
      int mid, int end) {
    ArrayList<T> sortedA = new ArrayList<T>();
    int leftStart = start, rightStart = mid, inverCount = 0;

    while (leftStart < mid && rightStart < end) {
      if (A.get(leftStart).compareTo(A.get(rightStart)) <= 0) {
        sortedA.add(A.get(leftStart++));
      } else {
        // A[leftStart:mid - 1] will be the inversions.
        inverCount += mid - leftStart;
        sortedA.add(A.get(rightStart++));
      }
    }
    sortedA.addAll(A.subList(leftStart, mid));
    sortedA.addAll(A.subList(rightStart, end));

    // Update A with sortedA.
    for (T t : sortedA) {
      A.set(start++, t);
    }
    return inverCount;
  }

  // @exclude

  // O(n^2) check of inversions
  private static <T extends Comparable<T>> int n2Check(List<T> a) {
    int count = 0;
    for (int i = 0; i < a.size(); ++i) {
      for (int j = i + 1; j < a.size(); ++j) {
        if (a.get(i).compareTo(a.get(j)) > 0) {
          ++count;
        }
      }
    }
    System.out.println(count);
    return count;
  }

  public static void main(String[] args) {
    Random r = new Random();
    for (int times = 0; times < 1000; ++times) {
      int n;
      if (args.length == 1) {
        n = Integer.parseInt(args[0]);
      } else {
        n = r.nextInt(10000) + 1;
      }
      ArrayList<Integer> A = new ArrayList<Integer>();
      for (int i = 0; i < n; ++i) {
        A.add(r.nextInt(2000001) - 1000000);
      }
      assert (n2Check(A) == countInversions(A));
    }
  }
}
