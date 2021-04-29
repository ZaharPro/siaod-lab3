package com.company;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final WeightingFairQueue<String> queue = new WeightingFairQueue<>();

    public static void main(String[] args) {
        String message = "Enter operation:\n" +
                "Add in max priority queue = 1\n" +
                "Peek = 2\n" +
                "Poll = 3\n" +
                "Contains = 4\n" +
                "Remove = 5\n" +
                "Clear = 6\n" +
                "Create new weight queue = 7\n" +
                "Offer in weight queue = 8\n" +
                "Remove weight queue = 9\n" +
                "Print = 10\n" +
                "Exit = 0";
        boolean inLoop = true;
        do {
            System.out.println(message);
            switch (SCANNER.nextLine()) {
                case "1" -> {
                    System.out.println("Enter line");
                    queue.offer(SCANNER.nextLine());
                }
                case "2" -> System.out.println(queue.peek());
                case "3" -> System.out.println(queue.poll());
                case "4" -> {
                    System.out.println("Enter line");
                    System.out.println(queue.contains(SCANNER.nextLine()));
                }
                case "5" -> {
                    System.out.println("Enter line");
                    if (queue.remove(SCANNER.nextLine())) {
                        System.out.println("Removed");
                    } else {
                        System.out.println("Element not found");
                    }
                }
                case "6" -> {
                    queue.clear();
                    System.out.println("Cleared");
                }
                case "7" -> {
                    System.out.println("Enter weight");
                    int weight = scanInt();
                    if (weight < 1) {
                        System.out.println("Illegal weight");
                    } else {
                        queue.createNewWQ(weight);
                        System.out.println("Complete");
                    }
                }
                case "8" -> {
                    System.out.println("Size weight queues = " + queue.sizeWQ());
                    int index = scanInt();
                    if (index < 0 || index >= queue.sizeWQ()) {
                        System.out.println("Index out of bounds");
                    } else {
                        System.out.println("Enter line");
                        queue.offerInWQ(SCANNER.nextLine(), index);
                        System.out.println("Complete");
                    }
                }
                case "9" -> {
                    System.out.println("Size weight queues = " + queue.sizeWQ());
                    int index = scanInt();
                    if (index < 0 || index >= queue.sizeWQ()) {
                        System.out.println("Index out of bounds");
                    } else {
                        queue.removeWQ(index);
                        System.out.println("Removed");
                    }
                }
                case "10" -> System.out.println(queue.toString());
                case "0" -> inLoop = false;
                default -> System.out.println("Error");
            }
        } while (inLoop);
    }
    private static int scanInt() {
        int value = 0;
        if (SCANNER.hasNextInt())
            value = SCANNER.nextInt();
        SCANNER.nextLine();
        return value;
    }
    private static void test() {
        queue.createNewWQ(2);
        queue.createNewWQ(2);
        queue.createNewWQ(1);

        queue.offer("qwertyu");
        queue.offer("12345678");
        queue.offer("poiuytr");
        queue.offer(",mnbvc");

        queue.offerInWQ("1234567", 0);
        queue.offerInWQ("8765", 0);
        queue.offerInWQ("98765", 0);
        queue.offerInWQ("dfghjk", 1);
        queue.offerInWQ("poiuytre", 1);
        queue.offerInWQ("rtyuii", 2);

        System.out.println(queue.toString());
        while (queue.size() > 0) {
            System.out.println(queue.poll());
            System.out.println(queue.toString());
            System.out.println();
        }
    }
}