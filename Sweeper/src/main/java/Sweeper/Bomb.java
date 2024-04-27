package Sweeper;

import java.util.Random;

public class Bomb  {
  private Matrix bombMap;
  private int totalBombs;

  Bomb (int totalBombs) {
    this.totalBombs = totalBombs;
    fixBombsCount();
  }

  void start() {
    bombMap = new Matrix(Box.ZERO);
    for (int i = 0; i < totalBombs; ++i) {
      placeBomb();
    }
  }

  Box get(Coords coords) {
    return bombMap.get(coords);
  }

  private void fixBombsCount() {
    int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
    if (totalBombs > maxBombs) {
      totalBombs = maxBombs;
    }
  }

  private void placeBomb() {
    while (true) {
      Coords coords = Ranges.getRandomCoord();
      if (Box.BOMB == bombMap.get(coords)) {
        continue;
      }
      bombMap.set(coords, Box.BOMB);
      incNumsAroundBomb(coords);
      break;
    }
  }

  private void incNumsAroundBomb(Coords coords) {
    for (Coords around : Ranges.getCoordsAround(coords)) {
      if (Box.BOMB != bombMap.get(around)) {
        bombMap.set(around, bombMap.get(around).getNextNumberBox());
      }
    }
  }

  public int getTotalBombs() {
    return totalBombs;
  }
}
