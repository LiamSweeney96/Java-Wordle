package com.example.gui;

import java.util.*;

public class Guess {

  private static final Scanner INPUT = new Scanner(System.in);
  private final int guessNumber;
  private String chosenWord;

  /*
     One parameter constructor which validates
     the int num parameter and assigns the value
     to guessNumber if it is valid. Also throws
     a GameException if an invalid guessNumber occurs.
  */
  public Guess (int num) {

    if (num > 0 && num < 7) {
      guessNumber = num;
    }
    else {
      throw new comp1721.cwk1.GameException("Game exception thrown!");
    }
  }

  /*
     Validates the int num parameter and word parameter,
     assigns the values to guessNumber and chosenWord
     respectively if they are valid. Also ensures
     chosenWord is converted to upper case.
     Throws a GameException if either parameter is
     invalid.
  */
  public Guess (int num, String word) {

    if (num > 0 && num < 7) {
      guessNumber = num;
    }
    else {
      throw new comp1721.cwk1.GameException("Game exception thrown!");
    }
    if (word.matches(".*[a-zA-Z]+.*") && word.length() == 5) {
      chosenWord = word.toUpperCase();
    }
    else {
      throw new comp1721.cwk1.GameException("Game exception thrown!");
    }
  }

  // Returns the number of guesses currently made
  public int getGuessNumber () {
    return guessNumber;
  }

  // Returns the guessed word
  public String getChosenWord () {
    return chosenWord;
  }

  /*
      Method to take user input to get the guess
      if an argument has not been supplied.
  */
  public void readFromPlayer () {

    chosenWord = INPUT.nextLine().toUpperCase();

    if (!chosenWord.matches(".*[a-zA-Z]+.*") || !(chosenWord.length() == 5)) {
      System.out.println("Invalid guess, enter new guess: ");
      readFromPlayer();
    }
  }

  /*
      Method to compare the guessed word with the
      target word.
      Will be output a certain way depending on how
      accurate the players guess is.
      Green background for a letter in the correct place,
      yellow if the letter is in the wrong place,
      and white if the letter is not in the target word.
  */
  public String compareWith (String target) {

    String[] arrayChosen = chosenWord.split("");
    String[] output = new String[chosenWord.length()];

    List<String> targetList = List.of(target.split(""));
    ArrayList<String> targetArrayList = new ArrayList<>(targetList);

    for (int i = 0; i < arrayChosen.length; i++) {
      for (int j = 0; j < targetArrayList.size(); j++) {

        if (Objects.equals(arrayChosen[i], targetArrayList.get(i))) {
          output[i] = "\033[30;102m " + arrayChosen[i] + " \033[0m";
          targetArrayList.set(i, "");
          break;
        }
        if (Objects.equals(arrayChosen[i],targetArrayList.get(j))) {
          output[i] = "\033[30;103m " + arrayChosen[i] + " \033[0m";
          targetArrayList.set(j, "");
          break;
        }
        output[i] = "\033[30;107m " + arrayChosen[i] + " \033[0m";
      }
    }
    return String.join("", output);
  }

  /*
     Method to check whether the guess is equal to the
     target word. Returns true if guess is correct.
  */
  public boolean matches (String target) {
    return Objects.equals(chosenWord, target);
  }
}
