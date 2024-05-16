package comp1721.cwk1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class Game {

    private final int gameNumber;
    private final String target;
    private final String[] guesses = new String[10];
    int gamesPlayed = 0;
    int gamesWon = 0, currentStreak = 0, numGuesses = 0;
    int oneCount = 0, twoCount = 0, threeCount = 0, fourCount = 0, fiveCount = 0, sixCount = 0;
    String message = "";
    boolean gameWon = false;

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
    public Game(String filename) throws IOException {

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
    public Game(int num, String filename) throws IOException {

        WordList wordsList = new WordList(filename);
        gameNumber = num;
        target = wordsList.getWord(gameNumber);

    }

    /*
        This method plays the Wordle game.
        Loops until max number of guesses have
        been reached.
        Creates a guess object to run each method
        in the Guess class where relevant.
        Prints player guess in the correct
        format, with relevant messages for
        winning/losing the game.
    */
    public void play() {

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
            System.out.println(guessObj.compareWith(target));
            guesses[i] = guessObj.compareWith(target);

            if (guessObj.matches(target)) {
                if (guessNum == 1) {
                    System.out.println("Superb - Got it in one!");
                    message = "Correct - 1 Guess";
                    oneCount += 1;
                    numGuesses = 1;
                }
                if (guessNum == 2) {
                    System.out.println("Well done!");
                    message = "Correct - 2 Guesses!";
                    twoCount += 1;
                    numGuesses = 2;
                }
                if (guessNum == 3) {
                    System.out.println("Well done!");
                    message = "Correct - 3 Guesses!";
                    threeCount += 1;
                    numGuesses = 3;
                }
                if (guessNum == 4) {
                    System.out.println("Well done!");
                    message = "Correct - 4 Guesses!";
                    fourCount += 1;
                    numGuesses = 4;
                }
                if (guessNum == 5) {
                    System.out.println("Well done!");
                    message = "Correct - 5 Guesses!";
                    fiveCount += 1;
                    numGuesses = 5;
                }
                if (guessNum == 6) {
                    System.out.println("That was a close call!");
                    message = "Correct - 6 Guesses!";
                    sixCount += 1;
                    numGuesses = 6;
                }
                gameOver = true;
                currentStreak += 1;
                gamesPlayed += 1;
                gamesWon += 1;
                gameWon = true;
            }
            if (guessNum == 6 && !guessObj.matches(target)) {
                System.out.println("Nope, better luck next time!");
                System.out.println(target);
                gameOver = true;
                currentStreak = 0;
                gamesPlayed += 1;
                message = "Incorrect Guess!";
                numGuesses = 6;
            }
            guessNum++;
        }
    }

    // Returns the number of guesses taken.
    public int getGuesses() {
        return numGuesses;
    }

    /*
        Saves the last play through of the game by writing the
        output string to a text file.
    */
    public void save(String filename) {

        Path path = Paths.get(filename);

        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(path))) {
            for (int i = 1; i < guesses.length; i++) {
                out.println(guesses[i]);

                if (i == getGuesses()) {
                    out.close();
                }
            }

        } catch (IOException e) {
            System.out.println("IOException Occurred");
        }
    }

    // Returns the relevant message for the number guessed
    public String guessCorrect() {
        return message;
    }

    /*
        Reads the stats and assigns them to variables.
        Prints the game stats to a text file.
    */
    public void history(String filename) {

        try (FileWriter out = new FileWriter(filename, true)) {

            File valueFile = new File("build/values.txt");
            Scanner sc = new Scanner(new FileReader(valueFile));

            int gameNum = Integer.parseInt(sc.nextLine());
            gameNum += 1;

            out.write("Game Number: " + gameNum);
            out.write("\n");
            out.write("Game Guess: " + guessCorrect());
            out.write("\n");
            out.write("Guesses Made: " + numGuesses);
            out.write("\n");
            out.write("\n");


        } catch (IOException e) {
            System.out.println("IOException Occurred");
        }
    }

    /*
        Prints detailed statistics in the terminal once
        a game has finished. Read's values from a text file,
        then updates them with the previous games stats
        and prints.
    */
    public void displayStats() throws FileNotFoundException {

        File valueFile = new File("build/values.txt");

        Scanner sc = new Scanner(new FileReader(valueFile));
        int gameNum = Integer.parseInt(sc.nextLine());
        double wins = Double.parseDouble(sc.nextLine());
        int winStreak = Integer.parseInt(sc.nextLine());
        int losses = Integer.parseInt(sc.nextLine());
        int longestWin = Integer.parseInt(sc.nextLine());
        double percent = 0;

        if (winStreak > longestWin) {
            longestWin = winStreak;
        }

        if (wins > 0) {
            percent = wins / gameNum * 100;
        }

        if (valueFile.isFile()) {
            try (FileWriter writer = new FileWriter(valueFile)) {
                writer.write(String.valueOf(gameNum += 1));
                if (gameWon) {
                    writer.write("\n");
                    writer.write(String.valueOf(wins += 1));
                    writer.write("\n");
                    writer.write(String.valueOf(winStreak += 1));
                    writer.write("\n");
                    writer.write(String.valueOf(losses));
                    writer.write("\n");
                    if (winStreak > longestWin) {
                        longestWin = winStreak;
                    }
                    writer.write(String.valueOf(longestWin));
                }
                if (!gameWon) {
                    writer.write("\n");
                    writer.write(String.valueOf(wins));
                    writer.write("\n");
                    writer.write(String.valueOf(0));
                    winStreak = 0;
                    writer.write("\n");
                    losses += 1;
                    writer.write(String.valueOf(losses));
                    writer.write("\n");
                    if (winStreak > longestWin) {
                        longestWin = winStreak;
                    }
                    writer.write(String.valueOf(longestWin));
                }
                if (wins > 0) {
                    percent = wins / gameNum * 100;
                }
                writer.write("\n");
                writer.write(String.valueOf(percent));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(" ");
        System.out.println("Games Played: " + gameNum);
        System.out.printf("Number of Wins: %.0f", wins);
        System.out.println(" ");
        System.out.println("Current Win Streak: " + winStreak);
        System.out.println("Number of Losses: " + losses);
        if (winStreak > longestWin) {
            longestWin = winStreak;
        }
        System.out.println("Longest Win Streak: " + (longestWin));
        System.out.printf("Percentage of Wins: %.2f%%", percent);
        System.out.println(" ");

    }

    /*
        Method to create a histogram containing the amount
        of times a player has won the game on a specific
        guess. Stores these values in a text file in order
        to update them later.
    */
    public void getHistogram () throws FileNotFoundException {

        File valueTwoFile = new File("build/valuesTwo.txt");
        Scanner scan = new Scanner(new FileReader(valueTwoFile));

        int guessOne = Integer.parseInt(scan.nextLine());
        int guessTwo = Integer.parseInt(scan.nextLine());
        int guessThree = Integer.parseInt(scan.nextLine());
        int guessFour = Integer.parseInt(scan.nextLine());
        int guessFive = Integer.parseInt(scan.nextLine());
        int guessSix = Integer.parseInt(scan.nextLine());

        if (oneCount == 1) {
            guessOne += 1;
        }
        if (twoCount == 1) {
            guessTwo += 1;
        }
        if (threeCount == 1) {
            guessThree += 1;
        }
        if (fourCount == 1) {
            guessFour += 1;
        }
        if (fiveCount == 1) {
            guessFive += 1;
        }
        if (sixCount == 1) {
            guessSix += 1;
        }

        if (valueTwoFile.isFile()) {
            try (FileWriter writer = new FileWriter(valueTwoFile)) {
                writer.write(String.valueOf(guessOne));
                writer.write("\n");
                writer.write(String.valueOf(guessTwo));
                writer.write("\n");
                writer.write(String.valueOf(guessThree));
                writer.write("\n");
                writer.write(String.valueOf(guessFour));
                writer.write("\n");
                writer.write(String.valueOf(guessFive));
                writer.write("\n");
                writer.write(String.valueOf(guessSix));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[] starArray = new String[7];
        starArray[0] = "";
        starArray[1] = String.valueOf(convert(guessOne));
        starArray[2] = String.valueOf(convert(guessTwo));
        starArray[3] = String.valueOf(convert(guessThree));
        starArray[4] = String.valueOf(convert(guessFour));
        starArray[5] = String.valueOf(convert(guessFive));
        starArray[6] = String.valueOf(convert(guessSix));

        System.out.println(" ");
        System.out.println("Histogram Guess Tally:");
        for (int i = 1; i < starArray.length; i++) {
            String label = i + " : " + starArray[i];
            System.out.println(label + starArray[i]);
        }
    }

    // Method to convert guess count into stars for the histogram.
    public String convert (int n) {
        StringBuilder str = new StringBuilder();
        if (n == 1) {
            str.append('*');
        }
        else {
            for (int j = 1; j < n; j++) {
                str.append('*');
            }
        }
        return str.toString();
    }

}



