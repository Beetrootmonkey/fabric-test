package com.beetrootmonkey.fabrictest;

public class PlayerUtils {

  public static int getLevelExperience(int level) {
    if (level >= 30) {
      return 112 + (level - 30) * 9;
    }
    return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;

  }

}
