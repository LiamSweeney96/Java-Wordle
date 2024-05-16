package comp1721.cwk1;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class Accessible {


    private final int gameNumber;
    private final String target;
    private final String[] guesses = new String[10];

    /*
        One parameter constructor to create a WordList
        object containing the required words from the
        specified file.

        Calculates difference between the date of the
        first ever game of Wordle and the current date
        to find the gameNumber of today.

        Initialises the target word using the gameNumber
        field.

    */
    public Accessible (String filename) throws IOException {

        WordList wordsList = new WordList(filename);
        LocalDate firstGame = LocalDate.of(2021, 6, 19);
        LocalDate todayDate = LocalDate.now();

        long dayDiff = DAYS.between(firstGame, todayDate);
        gameNumber = Math.toIntExact(dayDiff);
        target = wordsList.getWord(gameNumber);
    }

     /*
        Two parameter constructor to initialise
        gameNumber and target using the given
        parameters.
        Creates a WordList object to retrieve the
        correct filename.
     */

    public Accessible (int num, String filename) throws IOException {

        WordList wordsList = new WordList(filename);
        gameNumber = num;
        target = wordsList.getWord(gameNumber);

    }

    public String compareWith (String target) {

        Scanner sc = new Scanner(System.in);
        String chosenWord = sc.nextLine();
        System.out.println(chosenWord);
        String[] arrayChosen = chosenWord.split("");
        String[] output = new String[chosenWord.length()];

        List<String> targetList = List.of(target.split(""));
        ArrayList<String> targetArrayList = new ArrayList<>(targetList);

        String[] guess = new String[] {"", "1st", "2nd", "3rd", "4th", "5th", "6th"};

        for (int i = 0; i < arrayChosen.length; i++) {
            for (int j = 0; j < targetArrayList.size(); j++) {

                if (Objects.equals(arrayChosen[i], targetArrayList.get(i))) {
                    output[i] =  guess[i] + " perfect";
                    targetArrayList.set(i, "");
                    break;
                }
                if (Objects.equals(arrayChosen[i],targetArrayList.get(j))) {
                    output[i] = guess[i] + " correct but in wrong place";
                    targetArrayList.set(j, "");
                    break;
                }
                output[i] = arrayChosen[i];
            }
        }
        return String.join(", ", output);
    }

    public void play () {

        int guessNum = 1;
        Guess guessObj = new Guess(guessNum);
        System.out.printf("Wordle %d\n\n", gameNumber);
        String chosenWord = guessObj.getChosenWord();
        guessNum = guessObj.getGuessNumber();
        boolean gameOver = false;

        for (int i = 1; i < 7; i++) {

            if (gameOver) {
                break;
            }
            System.out.printf("Enter guess (%d/6):", guessNum);

            if (chosenWord == null) {
                guessObj.readFromPlayer();
            }
            System.out.println(compareWith(target));
            guesses[i] = compareWith(target);

            if (guessObj.matches(target)) {
                if (guessNum == 1) {
                    System.out.println("Superb - Got it in one!");
                    gameOver = true;

                }
                if (guessNum >= 2 && guessNum <= 5) {
                    System.out.println("Well done!");
                    gameOver = true;
                }
                if (guessNum == 6) {
                    System.out.println("That was a close call!");
                    gameOver = true;
                }
            }
            if (guessNum == 6 && !guessObj.matches(target)) {
                System.out.println("Nope, better luck next time!");
                System.out.println(target);
                gameOver = true;
            }
            guessNum++;

        }
    }

    public void save (String filename) throws IOException {

        Path path = Paths.get(filename);

        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(path))) {
            for (int i = 1; i < 7; i++) {
                out.println(guesses[i]);
                if (i == 6) {
                    out.close();
                }
            }

        } catch (IOException e) {
            System.out.println("IOException Occurred");
        }
    }
}