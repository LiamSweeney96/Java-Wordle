// Main program for COMP1721 Coursework 1
// DO NOT CHANGE THIS!

package comp1721.cwk1;

import java.io.IOException;
import java.util.Objects;


public class Wordle {
  public static void main(String[] args) throws IOException {
    Game game;
    HelloApplication hello;

    if (args.length > 0 && Objects.equals(args[0], "GUI")) {
      // Runs the GUI
      hello = new HelloApplication();
    }
    if (args.length > 0) {
      // Runs the normal game
      game = new Game(Integer.parseInt(args[0]), "data/words.txt");

    }
    else {
      // Play today's game
      game = new Game("data/words.txt");
    }
    game.play();
    game.save("build/lastgame.txt");
    game.history("build/history.txt");
    game.displayStats();
    game.getHistogram();
  }
}
