// Copyright (c) 2013 Elements of Programming Interviews. All rights reserved.
package com.epi;

import java.util.ArrayList;
import java.util.Collections;

// @include
class Player implements Comparable<Player> {
  public Integer height;

  public Player(Integer h) {
    height = h;
  }

  @Override
  public int compareTo(Player that) {
    return height.compareTo(that.height);
  }
}

class Team implements Comparable<Team> {
  public Team(ArrayList<Integer> height) {
    for (Integer h : height) {
      members.add(new Player(h));
    }
  }

  @Override
  public int compareTo(Team that) {
    ArrayList<Player> thisSorted = sortHeightMembers();
    ArrayList<Player> thatSorted = that.sortHeightMembers();
    for (int i = 0; i < thisSorted.size() && i < thatSorted.size(); ++i) {
      if (thisSorted.get(i).compareTo(thatSorted.get(i)) >= 0) {
        return 1;
      }
    }
    return -1;
  }

  private ArrayList<Player> sortHeightMembers() {
    ArrayList<Player> sortedMembers = members;
    Collections.sort(sortedMembers);
    return sortedMembers;
  }

  private ArrayList<Player> members = new ArrayList<Player>();
}

// @exclude

class TeamPhoto1 {
  public static void main(String[] args) {
    ArrayList<Integer> height = new ArrayList<Integer>(3);
    height.add(0, 1);
    height.add(1, 5);
    height.add(2, 4);
    Team t1 = new Team(height);
    height.set(0, 2);
    height.set(1, 3);
    height.set(2, 4);
    Team t2 = new Team(height);
    assert (t1.compareTo(t2) >= 0 && t2.compareTo(t1) >= 0);
    height.set(0, 0);
    height.set(1, 3);
    height.set(2, 2);
    Team t3 = new Team(height);
    assert (t3.compareTo(t1) < 0 && t1.compareTo(t3) >= 0
        && t3.compareTo(t2) < 0 && t1.compareTo(t2) >= 0);
  }
}
