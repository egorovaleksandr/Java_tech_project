package Sweeper;

class Matrix {
  private Box[][] matrix;

  Matrix(Box defaultBox) {
    matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
    for (Coords coords : Ranges.getAllCoords()) {
      matrix[coords.x][coords.y] = defaultBox;
    }
  }

  Box get(Coords coords) {
    if (Ranges.inRange(coords)) {
      return matrix[coords.x][coords.y];
    }
    return null;
  }

  void set(Coords coords, Box box) {
    if (Ranges.inRange(coords)) {
      matrix[coords.x][coords.y] = box;
    }
  }
}
