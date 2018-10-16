package edu.cnm.deepdive.craps.model;

import java.awt.Point;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Game {

  private State state = State.COME_OUT;
  private int point;
  private Random rnd;
  private List<Roll> rolls;

  public Game(Random rnd) {
    this.rnd = rnd;
    rolls = new LinkedList<>();
  }

  public State roll() {
    int[] dice = {
        1 + rnd.nextInt(6),
        1 + rnd.nextInt(6)
    };
    int total = dice[0] + dice[1];

    State state = this.state.roll(total, point);
    if (this.state == state.COME_OUT && state == State.POINT) {
      point = total;
    }
    this.state = state;
    rolls.add(new Roll(dice, state));
    return state;
  }

  public State play() {
    while (state != State.WIN && state != State.LOSS) {
      roll();
    }
    return state;
  }

  public State getState() {
    return state;
  }

  public List<Roll> getRolls() {
    return new LinkedList<>(rolls);
  }

  public static class Roll {

    private final int[] dice;
    private final State state;

    @Override
    public String toString() {
      return String.format("%s %s%n", Arrays.toString(dice), state);
    }

    public Roll(int[] dice, State state) {
      this.dice = Arrays.copyOf(dice, 2);
      this.state = state;
    }

    public int[] getDice() {
      return Arrays.copyOf(dice, 2);
    }

    public State getState() {
      return state;
    }
  }

  public enum State {

    COME_OUT {
      @Override
      public State roll(int total, int point) {
        switch (total) {
          case 2:
          case 3:
          case 12:
            return LOSS;
          case 7:
          case 11:
            return WIN;
          default:
            return POINT;

        }
      }
    },

    WIN,
    LOSS,
    POINT {
      @Override
      public State roll(int total, int point) {
        if (total == point) {
          return WIN;
        } else if (total == 7) {
          return LOSS;
        } else {
          return POINT;
        }
      }
    };

    public State roll(int total, int point) {
      return this;
    }

  }
}
