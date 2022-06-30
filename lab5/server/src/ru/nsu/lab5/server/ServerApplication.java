package ru.nsu.lab5.server;

import java.util.Scanner;

public class ServerApplication {
    private static final int SIMPLE_WORK = 1;
    private static final int WORK_WITH_JSON = 2;

    private static boolean startStatus = false;

    public static void main(String[] args) {
        AbstractServer server;

        Scanner scanner = new Scanner(System.in);
        while (!startStatus) {
            System.out.println("If you want to run Simple Server App, type 1. If you want to run Server App using JSON type 2");
            int value = scanner.nextInt();
            if (value == SIMPLE_WORK) {
                server = new SimpleServer();
                startStatus = true;
                server.run();
            }
            else if (value == WORK_WITH_JSON) {
                server = new JsonServer();
                startStatus= true;
                server.run();
            }
            else {
                System.out.println("You typed wrong mode");
            }
        }
    }
}
