package Sweeper;

public class Game {
  private final Bomb bomb;
  private final Flag flag;
  private GameState state;
  public Game(int cols, int rows, int bombs) {
    Ranges.setSize(new Coords(cols, rows));
    bomb = new Bomb(bombs);
    flag = new Flag();
    state = GameState.PLAYED;
  }

  public void Start() {
    bomb.start();
    flag.start();
    state = GameState.PLAYED;
  }
  public Box getBox(Coords coords) {
    if (flag.get(coords) == Box.OPENED) {
      return bomb.get(coords);
    }
    return flag.get(coords);
  }

  public void pressLeftButton(Coords coords) { // Было нажатие левой кнопкой мыши, надо открыть клетку
    if (gameOver()) return;
    openBox(coords); // Открыть клетку
    checkWinner();
  }

  private void checkWinner() { // Проверка на победу
    if (state == GameState.PLAYED) {
      if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) { // Игрок выигрывает, когда количество неоткрытых клеток совпадает с количеством бомб
        state = GameState.WINNER;
      }
    }
  }

  public void openBox(Coords coords) {
    switch (flag.get(coords)) {
      case OPENED : setOpenedToClosedBoxesAroundNumber(coords); return;
      case FLAGED : return;
      case CLOSED :
        switch (bomb.get(coords)) {
          case ZERO : openBoxesAround(coords); return;
          case BOMB : openBombs(coords); return;
          default : flag.setOpened(coords);
        }
    }
  }

  private void openBombs(Coords bombed) {
    state = GameState.BOMBED;
    flag.setBombedToBox(bombed);
    for (Coords coords : Ranges.getAllCoords()) {
      if (bomb.get(coords) == Box.BOMB) {
        flag.setOpenedToClosedBomb(coords); // Открыть клетку со спрятанной бомбой
      } else {
        flag.setNoBombToFlagedSafeBox(coords); // Показать, что на помеченной флажком безопасной клетке нет бомбы
      }
    }
  }

  public void openBoxesAround(Coords coords) { // Нужна для открытия клеток вокруг "нулевых"
    flag.setOpened(coords);
    for (Coords around : Ranges.getCoordsAround(coords)) {
      openBox(around);
    }
  }

  public void pressRightButton(Coords coords) { // Было нажатие правой кнопки мыши
    if (gameOver()) return;
    flag.toggleFlagedToBox(coords); // Поставить флажок на клетку, если его нет, и снять, если есть
  }

  public GameState getState() {
    return state;
  }

  private boolean gameOver() {
    if (state == GameState.PLAYED) {
      return false;
    }
    Start();
    return true;
  }

  public void setOpenedToClosedBoxesAroundNumber(Coords coords) {
    if (bomb.get(coords) != Box.BOMBED) {
      if (flag.getCountOfFlagedBoxesAround(coords) == bomb.get(coords).getNumber()) {
        for (Coords around : Ranges.getCoordsAround(coords)) {
          if(flag.get(around) == Box.CLOSED) {
            openBox(around);
          }
        }
      }
    }
  }
}
