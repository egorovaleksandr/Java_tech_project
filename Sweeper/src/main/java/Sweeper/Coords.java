package Sweeper;

public class Coords {
  public int x;
  public int y;

  public Coords(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Coords) {
      Coords to = (Coords) o;
      return to.x == x && to.y == y;
    }
    return super.equals(o);
  }
}
