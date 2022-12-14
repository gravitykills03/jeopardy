package com.jeopardy.controller;

import com.apps.util.Console;
import com.apps.util.Prompter;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameController {
    private GameBoard gameBoard;

    /**
     * Constructor for GameController
     */
    public GameController() {
        gameBoard = new GameBoard();
    }

    /**
     * Calls necessary methods in correct order to play a single game
     */
    public void run() {
        welcomeScreen();
        playerSetup();
        playSJ();
        playDoubleJeopardy();
        playFJ();
        displayResults();
    }

    /**
     * Calls the necessary files with ASCII art to display welcome screen
     */
    private void welcomeScreen() {
        String welcome = null;
        String credits = null;

        try {
            welcome = Files.readString(Path.of("images/welcome.txt"));
            credits = Files.readString(Path.of("images/credits.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Console.clear();
        System.out.println(welcome);

        Console.pause(3000L);
        Console.clear();

        System.out.println(credits);

        Console.pause(3000L);
        Console.clear();
    }

    /**
     * Prompts the user to enter their player's name
     */
    private void playerSetup() {

        Prompter prompter = new Prompter(new Scanner(System.in));
        displayBanner("rules.txt");
        Console.pause(2000L);

        displayAd("images/cocacola.jpg", 3000L);


        String name = prompter.prompt("Enter player name: ");
        gameBoard.playerSetup(name);
    }

    /**
     * Play Single Jeopardy round.
     * Calls helper method to display appropriate banner(s)
     * Utilizes GameBoard methods for displaying game board and necessary prompts
     */
    private void playSJ() {
        while(!gameBoard.isJeopardyComplete()) {
            displayBanner("sj.txt");
            gameBoard.displaySingleJeopardyBoard();
            System.out.println();
            System.out.println("SCORE:");
            System.out.print("\t");
            gameBoard.displayPlayerWithScore();
            System.out.print("\n\n");

            gameBoard.promptForQuestion();
            Console.clear();
        }

        displayBanner("sjComplete.txt");
        Console.pause(3000L);

        displayAd("images/debitcard.jpg", 3000L);
    }

    /**
     * Play Double Jeopardy round.
     * Calls helper method to display appropriate banner(s)
     * Utilizes GameBoard methods for displaying game board and necessary prompts
     */
    private void playDoubleJeopardy() {
        while(!gameBoard.isDoubleJeopardyComplete()) {
            displayBanner("dj.txt");


            gameBoard.displayDoubleJeopardyBoard();
            System.out.println();
            System.out.println("\t\tSCORE:");
            System.out.print("\t\t\t");
            gameBoard.displayPlayerWithScore();
            System.out.print("\n\n");

            gameBoard.promptForDoubleJeopardyQuestion();
            Console.clear();
        }

        displayBanner("djComplete.txt");

        displayAd("images/prizes.jpg", 3000L);

    }

    /**
     * Play Final Jeopardy round.
     * Calls helper method to display appropriate banner(s)
     * Utilizes GameBoard methods for displaying game board and necessary prompts
     */
    private void playFJ() {
        displayBanner("fj.txt");
        Console.pause(1500L);
        System.out.println();
        System.out.println("\t\tSCORE:");
        System.out.print("\t\t\t");
        gameBoard.displayPlayerWithScore();
        System.out.print("\n\n");

        gameBoard.promptForFinalJeopardyQuestion();

        Console.pause(2500L);
        Console.clear();

        displayAd("images/zebra.jpg", 3000L);
    }

    /**
     * Helper method used by the play* methods to display player info
     */
    private void displayResults() {
        displayBanner("gameOver.txt");
        System.out.println();
        System.out.println("FINAL SCORE!!\n");
        gameBoard.displayPlayerWithScore();
        System.out.printf("\nTHANK YOU FOR PLAYING!!\n");
        System.out.printf("\n\nCopyright 2022 - Trebek's Legacy LLC\n");

        displayAd("images/trebek.gif", 4500L);
    }

    /**
     * Helper method to display Advertisements
     *
     * @param target - name of the file containing ad
     */
    private void displayAd(String target, long delay) {
        JFrame adver = new JFrame();

        adver.add(new JLabel(new ImageIcon(target)));
        adver.pack();
        adver.setSize(600, 400);
        adver.toFront();
        adver.setAlwaysOnTop(true);
        adver.setVisible(true);

        Console.pause(delay);
        adver.dispatchEvent(new WindowEvent(adver, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Helper method to display an ASCII art as a banner.
     *
     * @param target - name of file containing ASCII art
     */
    private void displayBanner(String target) {
        String banner = null;

        try {
            banner = Files.readString(Path.of("images/" + target));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Console.clear();
        System.out.println(banner);
        Console.blankLines(3);
    }
}