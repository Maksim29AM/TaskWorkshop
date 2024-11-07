package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;
    static final String[] OPTIONS = {
            ConsoleColors.YELLOW + "add(1)" + ConsoleColors.RESET,
            ConsoleColors.YELLOW + "remove(2)" + ConsoleColors.RESET,
            ConsoleColors.YELLOW + "list(3)" + ConsoleColors.RESET,
            ConsoleColors.YELLOW + "exit(4)" + ConsoleColors.RESET
    };

    public static void main(String[] args) {

        Scanner scannerTasks = new Scanner(System.in);

        tasks = loadDataToTab(FILE_NAME);
        printOptions(OPTIONS);

        while (scannerTasks.hasNextLine()) {
            String line = scannerTasks.nextLine();
            switch (line) {
                case "add":
                case "1":
                    addTask();
                    break;
                case "remove":
                case "2":
                    removeTask(tasks, getTheNumber());
                    System.out.println("Value was successfully removed");
                    break;
                case "list":
                case "3":
                    printTab(tasks);
                    break;
                case "exit":
                case "4":
                    saveTabToFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED + "The program has ended. Thank you for using it!");
                    System.exit(500);
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "Please enter a valid option!" + ConsoleColors.RESET);
            }
            printOptions(OPTIONS);
        }

    }

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] loadDataToTab(String fileName) {
        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File not exist.");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void addTask() {
        // Pobieramy informacje od użytkownika
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.YELLOW + "Please enter the task description: " + ConsoleColors.YELLOW);
        String description = scanner.nextLine();
        System.out.println(ConsoleColors.YELLOW + "Please enter the task due date (yyyy-mm-dd): " + ConsoleColors.YELLOW);
        String dueDate = scanner.nextLine();
        System.out.println(ConsoleColors.YELLOW + "Please enter the task important: true/false: " + ConsoleColors.YELLOW);
        String important = scanner.nextLine();

        // Zwiększamy tablicę o 1 i uzupełniamy pobranymi danymi
        String[][] updatedTasks = new String[tasks.length + 1][3];
        for (int i = 0; i < tasks.length; i++) {
            System.arraycopy(tasks[i], 0, updatedTasks[i], 0, tasks[i].length);
        }
        updatedTasks[tasks.length][0] = description;
        updatedTasks[tasks.length][1] = dueDate;
        updatedTasks[tasks.length][2] = important;

        tasks = updatedTasks;


    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");

        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            n = scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    // sprawdzamy czy podany index nie jest większy niż rozmiar tablicy
    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
    }


    public static void saveTabToFile(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
