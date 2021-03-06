package com.epi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class ClosestStars {
  public static class Star implements Comparable<Star>, Serializable {
    private int id;
    private double x, y, z;

    public Star() {
    }

    public Star(int id, double x, double y, double z) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public double getX() {
      return x;
    }

    public void setX(double x) {
      this.x = x;
    }

    public double getY() {
      return y;
    }

    public void setY(double y) {
      this.y = y;
    }

    public double getZ() {
      return z;
    }

    public void setZ(double z) {
      this.z = z;
    }

    public double distance() {
      return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public int compareTo(Star s) {
      return Double.valueOf(distance()).compareTo(s.distance());
    }
  }

  // @include
  public static ArrayList<Star> findClosestKStars(InputStream sin, int k) {
    // Use maxHeap to find the closest k stars.
    PriorityQueue<Star> maxHeap = new PriorityQueue<Star>();
    try {
      ObjectInputStream osin = new ObjectInputStream(sin);

      // Record the first k stars.
      while (true) {
        Star s = (Star) osin.readObject();

        if (maxHeap.size() == k) {
          // Compare the top of heap with the incoming star.
          Star farStar = maxHeap.peek();
          if (s.compareTo(farStar) < 0) {
            maxHeap.remove();
            maxHeap.add(s);
          }
        } else {
          maxHeap.add(s);
        }
      }
    } catch (IOException e) {
      // Do nothing, was read last element in stream
    } catch (ClassNotFoundException e) {
      System.out.println("ClassNotFoundException: " + e.getMessage());
    }

    // Store the closest k stars.
    ArrayList<Star> closestStars = new ArrayList<Star>();
    while (!maxHeap.isEmpty()) {
      closestStars.add(maxHeap.remove());
    }
    return closestStars;
  }

  // @exclude

  private static int partition(ArrayList<Star> stars, int left, int right,
      int pivotIndex) {
    double pivotValue = stars.get(pivotIndex).distance();
    Collections.swap(stars, pivotIndex, right);
    int lessIndex = left;
    for (int i = left; i < right; ++i) {
      if (stars.get(i).distance() < pivotValue) {
        Collections.swap(stars, i, lessIndex++);
      }
    }
    Collections.swap(stars, right, lessIndex);
    return lessIndex;
  }

  private static ArrayList<Star> selectK(ArrayList<Star> stars, int k) {
    if (stars.size() <= k) {
      return stars;
    }

    Random r = new Random();
    int left = 0, right = stars.size() - 1, pivotIndex = -1;
    while (left <= right) {
      pivotIndex = partition(stars, left, right, r.nextInt(right - left + 1)
          + left);
      if (k - 1 < pivotIndex) {
        right = pivotIndex - 1;
      } else if (k - 1 > pivotIndex) {
        left = pivotIndex + 1;
      } else { // k - 1 == pivotIndex
        break;
      }
    }

    ArrayList<Star> closestStars = new ArrayList<Star>();
    double dist = stars.get(pivotIndex).distance();
    for (int i = 0; i < stars.size(); ++i) {
      if (dist >= stars.get(i).distance()) {
        closestStars.add(stars.get(i));
      }
    }
    return closestStars;
  }

  public static void main(String[] args) {
    Random r = new Random();
    for (int times = 0; times < 1000; ++times) {
      int num, k;
      if (args.length == 1) {
        num = Integer.parseInt(args[0]);
        k = r.nextInt(num) + 1;
      } else if (args.length == 2) {
        num = Integer.parseInt(args[0]);
        k = Integer.parseInt(args[1]);
      } else {
        num = r.nextInt(10000) + 1;
        k = r.nextInt(num) + 1;
      }
      ArrayList<Star> stars = new ArrayList<Star>();
      // randomly generate num of stars
      for (int i = 0; i < num; ++i) {
        stars.add(new Star(i, r.nextInt(100001), r.nextInt(100001), r
            .nextInt(100001)));
      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ByteArrayInputStream sin = null;
      try {
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        for (Star s : stars) {
          oos.writeObject(s);
          // System.out.println(s.distance());
        }
        oos.close();
        sin = new ByteArrayInputStream(baos.toByteArray());
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
      ArrayList<Star> closestStars = findClosestKStars(sin, k);
      ArrayList<Star> selectedStars = selectK(stars, k);
      Collections.sort(selectedStars);
      Collections.sort(stars);
      System.out.println(k);
      // assert(stars.get(0).getID() == closestStars.get(0).getID());
      assert (stars.get(k - 1).distance() == selectedStars.get(
          selectedStars.size() - 1).distance());
    }
  }

}
