package edu.cnm.deepdive.craps.controller;

import edu.cnm.deepdive.craps.model.Game;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class Main {

  private int wins;
  private int losses;
  private Random rnd;


  public static void main(String[] args) {
    new Main().run();
  }

  private Main() {
    wins = 0;
    losses = 0;
    rnd = new SecureRandom();

  }

  private void play() {
    Game game = new Game(rnd);
    Game.State state = game.play();
    if(state == Game.State.WIN){
      wins++;
    }else{
      losses++;
    }
    for (Game.Roll roll : game.getRolls()) {
      System.out.print(roll);
    }
  }

  public void run() {


      try (Scanner scanner = new Scanner(System.in)) {
        do {
          play();
          System.out.printf("Wins: %d; Losses: %d. %nPlay again?%n", wins, losses);
          String input = scanner.next().trim();
          if(!input.isEmpty()&&input.toLowerCase().charAt(0) != 'y'){
            break;
          }

      }
      while (true);
    }
  }
}