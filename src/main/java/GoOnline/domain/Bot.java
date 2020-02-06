package GoOnline.domain;

import GoOnline.domain.Game.Game;
import GoOnline.domain.Game.GridState;
import GoOnline.domain.Game.Move;

import javax.persistence.Transient;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static GoOnline.domain.Game.GridState.BLACK;
import static GoOnline.domain.Game.GridState.EMPTY;

public class Bot extends Player {

    @Transient
    private Map<Point, GridState> board;

    public Bot() {

    }

    public Bot(int boardSize, Game game) {
        setGame(game);
        setUsername("bot");
        List<Move> moves = game.getMoves();
        moves.sort(Comparator.comparingInt(Move::getNumber));
        board = game.getBoard(moves);
    }


    //TODO - zmieniony kod
    public List<Move> doMove() {
        int max = 1;
        Point bestPoint = new Point(0, 0);

        for (Point p : board.keySet()) {
            if (board.get(p) == GridState.EMPTY && countPointBreaths(board, p) >= 2) {
                List<Point> list = new ArrayList<>();
                list.add(p);
                int temp = countGroupSize(p, list);
                if (temp > max) {
                    max = temp;
                    bestPoint = p;
                }
            }
        }
        if (max == 1) {
            return moveToRandom();
        }

        int x = (int) Math.round(bestPoint.getX());
        int y = (int) Math.round(bestPoint.getY());

        try {
            return move(x, y);
        } catch (Exception e) {
            return moveToRandom();
        }
    }

    private int countPointBreaths(Map<Point, GridState> map, Point point) {
        int breaths = 4;
        int x = (int) point.getX();
        int y = (int) point.getY();

        for (int i = 0; i < 3; i += 2)
            if (map.get(new Point(x, y - 1 + i)) != EMPTY) breaths--;

        for (int j = 0; j < 3; j += 2)
            if (map.get(new Point(x - 1 + j, y)) != EMPTY) breaths--;

        return breaths;
    }

    //obliczamy rozmiar grupy rekurencyjnie
    private int countGroupSize(Point point, List<Point> checked) {
        int sum = 1;
        checked.add(point);
        for (int i = 0; i < 3; i += 2) {
            Point temp = new Point((int) point.getX(), (int) point.getY() + i - 1);
            if (!checked.contains(temp) && board.get(temp) == BLACK) {
                sum += countGroupSize(temp, checked);
            }
        }
        for (int i = 0; i < 3; i += 2) {
            Point temp = new Point((int) point.getX() + i - 1, (int) point.getY());
            if (!checked.contains(temp) && board.get(temp) == BLACK) {
                sum += countGroupSize(temp, checked);
            }
        }
        return sum;
    }

    //TODO - zmieniony kod
    private List<Move> moveToRandom() {
        List<Point> emptyPlaceList = new ArrayList<>();
        //lista którą powinna nam dać game
        List<Move> m = null;
        for (Point p : board.keySet()) {
            if (board.get(p) == EMPTY) emptyPlaceList.add(p);
        }
        boolean done = false;
        while (emptyPlaceList.size() != 0) {
            int index = (int) (Math.random() * (emptyPlaceList.size() - 1));
            try {
                System.out.println((int) emptyPlaceList.get(index).getX() + "  " + (int) emptyPlaceList.get(index).getY());
                m = move((int) emptyPlaceList.get(index).getX(), (int) emptyPlaceList.get(index).getY());
                done = true;
                break;
            } catch (Exception e) {
                emptyPlaceList.remove(index);
            }

        }
        if (!done) try {
            pass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
