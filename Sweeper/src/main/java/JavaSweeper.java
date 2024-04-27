import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
import Sweeper.Coords;
import Sweeper.Game;
import Sweeper.Ranges;

public class JavaSweeper extends JFrame{
  private Game game;
  private JPanel panel;
  private JLabel label;
  private final int COLS = 9; // количество столбцов
  private final int ROWS = 9; // количество строк
  private final int IMAGE_SIZE = 50; // задаёт размеры картинки
  private final int BOMBS = 10;

  public static void main(String[] args) {
    new JavaSweeper();
  }

  private JavaSweeper() { // Конструктор приватный, потому что JFrame не надо запускать извне
    game = new Game(COLS, ROWS, BOMBS);
    game.Start();
    setImages();
    initLabel();
    initPanel();
    initFrame();
  }

  private void initLabel() {
    label = new JLabel("Welcome!");
    add(label, BorderLayout.SOUTH);
  }

  private void initPanel() {
    panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Coords coords : Ranges.getAllCoords()) {
          g.drawImage((Image)game.getBox(coords).image,
                  coords.x * IMAGE_SIZE, coords.y * IMAGE_SIZE, this);
        }
      }
    };
    panel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {  // Отслеживание действий мышки
        int x = e.getX() / IMAGE_SIZE;
        int y = e.getY() / IMAGE_SIZE;
        Coords coords = new Coords(x, y); // На какую часть поля было нажатие
        if (e.getButton() == MouseEvent.BUTTON1) { // Левая кнопка мыши
          game.pressLeftButton(coords);
        }
        if (e.getButton() == MouseEvent.BUTTON3) { // Провая кнопка мыши
          game.pressRightButton(coords);
        }
        if (e.getButton() == MouseEvent.BUTTON2) { // Нажата средняя кнопка
          game.Start();
        }
        label.setText(getMessage()); // После нажатия кнопок состояние игры может измениться
        panel.repaint();
      }
    });
    panel.setPreferredSize(new Dimension(
            Ranges.getSize().x * IMAGE_SIZE,
            Ranges.getSize().y * IMAGE_SIZE));
    add(panel); // Добавить панель
  }

  private String getMessage() {
    switch (game.getState()) {
      case PLAYED : return "Make your move deliberately.";
      case BOMBED : return "Unfortunately, you lost :(";
      case WINNER : return "Congratulations, you have won! :)";
      default : return "?";
    }
  }

  private void initFrame() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // При нажатии крестика закрывать frame
    setTitle("Sweeper");
    setResizable(false); // Размер окна менять нельзя
    setVisible(true);
    pack();
    setLocationRelativeTo(null); // Установить окно по центру
    setIconImage(getImage("icon"));
  }

  private void setImages() {
    for (Box box : Box.values()) {  // Для каждого экземпляра box устанавливаем картинку
      box.image = getImage(box.name().toLowerCase());
    }
  }

  private Image getImage(String name) {
    String filename = "img/" + name + ".png";
    ImageIcon icon = new ImageIcon(getClass().getResource(filename));
    return icon.getImage();
  }
}
