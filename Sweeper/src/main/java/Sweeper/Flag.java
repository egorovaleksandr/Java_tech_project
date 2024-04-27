package Sweeper;

class Flag {
  private  Matrix flagMap;
  private int countOfClosedBoxes;

  void start() {
    flagMap = new Matrix(Box.CLOSED);
    countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
  }

  Box get(Coords coords) {
    return flagMap.get(coords);
  }

  public void setOpened(Coords coords) {
    flagMap.set(coords, Box.OPENED); // Открыть клетку
    --countOfClosedBoxes;
  }

  private void setClosedToBox(Coords coords) { // Нужно для того чтобы можно было убирать флажки
    flagMap.set(coords, Box.CLOSED);
  }
  public void setFlagedToBox(Coords coords) {
    flagMap.set(coords, Box.FLAGED); // Поставить флажок на клетку
  }

  public void toggleFlagedToBox(Coords coords) {
    switch (flagMap.get(coords)) {
      case FLAGED : setClosedToBox(coords);
      break;

      case CLOSED : setFlagedToBox(coords);
      break;
    }
  }

  public int getCountOfClosedBoxes() {
    return countOfClosedBoxes;
  }

  void setBombedToBox(Coords coords) {
    flagMap.set(coords, Box.BOMBED);
  }

  void setOpenedToClosedBomb(Coords coords) {
    if (flagMap.get(coords) == Box.CLOSED) {
      flagMap.set(coords, Box.OPENED);
    }
  }

  void setNoBombToFlagedSafeBox(Coords coords) {
    if (flagMap.get(coords) == Box.FLAGED) {
      flagMap.set(coords, Box.NOBOMB);
    }
  }

  public int getCountOfFlagedBoxesAround(Coords coords) {
    int count = 0;
    for (Coords around : Ranges.getCoordsAround(coords)) {
      if (flagMap.get(around) == Box.FLAGED) {
        ++count;
      }
    }
    return count;
  }
}
