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
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;


    public static void main(String[] args) {

        tasks = loadDataToTab(FILE_NAME);
        printOptions(OPTIONS);


    }

    public static void printOptions(String[] tab) {
        System.out.println("\033[0;34mPlease select an option\033[0m");
        for (String option : tab) {
            System.out.println(option);
        }
    }


    public static String[][] loadDataToTab(String fileName) {
        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File does not exist");
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
            System.out.println((i + " : "));
            for (int j = 0; j < tab[i].length; j++) {
                System.out.println(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
        String input = scanner.nextLine();
        switch (input) {
            case "exit":
                saveTabToFile(FILE_NAME, tasks);
                System.out.println("\033[0;32mHave a good day\033[0m");
                System.exit(0);
                break;
            case "add":
                addTask();
                break;
            case "remove":
                removeTask(tasks, getNumber());
                System.out.println("Value was successfully deleted");
                break;
            case "list":
                printTab(tasks);
                break;
            default:
                System.out.println("Incorrect Option, Please select one from the list");
        }
        printOptions(OPTIONS);
    }


    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add date");
        String date = scanner.nextLine();
        System.out.println("Is this task important: true/false");
        String important = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = date;
        tasks[tasks.length - 1][2] = important;
    }


    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }


    public static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");

        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect input, Please insert number greater or equal to 0");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }


    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element does not exist in 'tab' array");
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
