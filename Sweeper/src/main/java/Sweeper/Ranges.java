package Sweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ranges {  // Описаны размеры и остальные характеристики поля
  private static Coords size; // Размеры доски по оси x и оси y
  private static ArrayList<Coords> allCoords; // Список всех координат

  private static Random random = new Random();

  public static void setSize(Coords _size) {
    size = _size;
    allCoords = new ArrayList<Coords>();
    for (int y = 0; y < size.y; y++) {
      for (int x = 0; x < size.x; x++) {
        allCoords.add(new Coords(x, y));
      }
    }
  }

  public static Coords getSize() {
    return size;
  }

  public static ArrayList<Coords> getAllCoords() {
    return allCoords;
  }

  static boolean inRange(Coords coords) {
    return  coords.x >= 0 && coords.x < size.x &&
            coords.y >= 0 && coords.y < size.y;
  }

  static Coords getRandomCoord() { // Получение случайной координаты на поле
    return new Coords(random.nextInt(size.x), random.nextInt(size.y));
  }

  static  ArrayList<Coords> getCoordsAround(Coords coords) {  // Нужно для проставления цифр вокруг бомб
    Coords around;
    ArrayList<Coords> list = new ArrayList<Coords>();
    for (int x = coords.x - 1; x <= coords.x + 1; ++x) {
      for (int y = coords.y - 1; y <= coords.y + 1; ++y) {
        if (inRange(around = new Coords(x, y))) {
          if (!around.equals(coords)) {
            list.add(around);
          }
        }
      }
    }
    return list;
  }
}
