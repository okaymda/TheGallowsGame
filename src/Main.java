import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final int MAX_ERRORS = 6;

    static String[] gallow = {
            "  ____\n |    |\n |    \n |    \n |    \n_|_",
            "  ____\n |    |\n |    O\n |    \n |    \n_|_",
            "  ____\n |    |\n |    O\n |    |\n |    \n_|_",
            "  ____\n |    |\n |    O\n |   /|\n |    \n_|_",
            "  ____\n |    |\n |    O\n |   /|\\\n |    \n_|_",
            "  ____\n |    |\n |    O\n |   /|\\\n |   / \n_|_",
            "  ____\n |    |\n |    O\n |   /|\\\n |   / \\\n_|_"
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Начать новую игру");
            System.out.println("2. Выйти из приложения");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    exitGame();
                    return;
                default:
                    System.out.println("Неверный ввод. Пожалуйста, выберите 1 или 2.");
            }
        }
    }

    public static void startGame() throws IOException {
        String wordToGuess = getRandomWord();
        HashSet<Character> noGuessedLetters = new HashSet<>();
        char[] guessedLetters = new char[wordToGuess.length()];
        int errors = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // текущее состояния виселицы и уже угаданные буквы в слове
            System.out.println("\nВаша виселица:");
            drawgallow(errors);
            System.out.println("Угаданные буквы: " + String.valueOf(guessedLetters));
            System.out.println("Ошибки: " + errors + " " + noGuessedLetters);

            // ввод буквы
            System.out.print("Введите букву: ");

            char guess = scanner.nextLine().charAt(0);

            if ((int) guess >= 1072 && (int) guess <= 1105) {
                // есть ли введенная буква в загаданном слове
                boolean found = false;
                for (int i = 0; i < wordToGuess.length(); i++) {
                    if (wordToGuess.charAt(i) == guess) {
                        guessedLetters[i] = guess;
                        found = true;
                    }
                }

                // проверка на ошибку
                if (!found) {
                    if (!noGuessedLetters.contains(guess))
                        errors++;
                    noGuessedLetters.add(guess);
                    System.out.println("Неверная буква!");
                }

                // проверка на победу или поражение
                if (errors >= MAX_ERRORS) {
                    System.out.println("\nВаша виселица:");
                    drawgallow(errors);
                    System.out.println("Вы проиграли. Загаданное слово было: " + wordToGuess);
                    break;
                }

                if (isWordGuessed(guessedLetters)) {
                    System.out.println("\nПоздравляем! Вы угадали слово: " + wordToGuess);
                    break;
                }
            } else
                System.out.println("\nНевалидный символ! Вводите только маленькие буквы кириллицы");
        }
    }

    public static void drawgallow(int errors) {
        if (errors >= 0 && errors < gallow.length) {
            System.out.println(gallow[errors]);
        } else {
            System.out.println(gallow[gallow.length - 1]);
        }
    }


    public static boolean isWordGuessed(char[] guessedLetters) {
        for (char letter : guessedLetters) {
            if (letter == '\0') {
                return false;
            }
        }
        return true;
    }

    public static String getRandomWord() throws IOException {
        List<String> wordList = Files.readAllLines(Paths.get("src/resources/words.txt"));
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    public static void exitGame() {
        System.out.println("До свидания!");
        System.exit(0);
    }
}