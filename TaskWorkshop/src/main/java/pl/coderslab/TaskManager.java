package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add(1)", "remove(2)", "list(3)", "exit(4)"};
    static String[][] tasks;

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
                    break;
                case "list":
                case "3":
                    printTab(tasks);
                    break;
                case "exit":
                case "4":
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
        System.out.println("Please enter the task description: ");
        String description = scanner.nextLine();
        System.out.println("Please enter the task due date: ");
        String dueDate = scanner.nextLine();
        System.out.println("Please enter the task important: true/false: ");
        String important = scanner.nextLine();

        // Zwiększamy tablicę o 1 i uzupełniamy pobranymi danymi
        tasks = new String[tasks.length + 1][tasks[0].length + 1];
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = important;


    }

}
