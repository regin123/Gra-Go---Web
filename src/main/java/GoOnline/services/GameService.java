package GoOnline.services;

import GoOnline.domain.Bot;
import GoOnline.domain.Game.Game;
import GoOnline.domain.Game.GameStatus;
import GoOnline.domain.Game.Move;
import GoOnline.domain.Player;
import GoOnline.dto.GameData;
import GoOnline.dto.MoveDTO;
import GoOnline.dto.ScoreDTO;
import GoOnline.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private UserService userService;

    /**
     * Stworz gre
     *
     * @param name username
     * @return gameID
     */
    public int createGame(String name, boolean withBot) {
        Player p = userService.getPlayer(name);
        Game g = new Game(p, 19);
        if (p.getGame() != null) {
            p.getGame().setGameStatus(GameStatus.INTERRUPTED);
            gameRepository.save(p.getGame());
        }
        p.setGame(g);
        if (withBot) g.setWithBot(true);
        return gameRepository.save(g);
    }

    public GameData getGameData(int gameID) {
        Game g = gameRepository.getGame(gameID);
        if (g == null) return new GameData();
        return g.getGameData();
    }

    public List<MoveDTO> getGameMoves(int gameID) {
        Game g = gameRepository.getGame(gameID);
        if (g == null) return new ArrayList<>();
        List<MoveDTO> result = new ArrayList<>();
        for (Move m : g.getMoves()) result.add(m.getDTO());
        return result;
    }

    /**
     * Dolacz do gry
     *
     * @param gameID
     * @param name
     * @return czy sie udalo dolaczyc
     */
    public boolean joinGame(int gameID, String name) {
        Player p = userService.getPlayer(name);
        Game g = gameRepository.getGame(gameID);
        boolean result = g.addPlayer(p);
        if (result) {
            if (gameID != g.getGameID() && p.getGame() != null) {
                p.getGame().setGameStatus(GameStatus.INTERRUPTED);
                gameRepository.save(p.getGame());
            }
            p.setGame(g);
            gameRepository.save(g);
            return true;
        }
        return false;
    }

    public List<GameData> getActiveGames() {
        List<GameData> result = gameRepository.getActiveGames();
        if (result == null) return new ArrayList<>();
        return result;
    }

    public List<MoveDTO> move(String username, MoveDTO move) throws Exception {
        Player p = userService.getPlayer(username);
        if (p == null) return new ArrayList<>();
        switch (move.getCommandType()) {
            case "MOVE" -> {
                List<Move> moveList = p.move(move.getX(), move.getY());
                List<MoveDTO> result = new ArrayList<>();
                for (Move m : moveList) result.add(m.getDTO());
                gameRepository.save(p.getGame());
                botMove(p.getGame());
                return result;
            }
            case "PASS" -> {
                Move res = p.pass();
                gameRepository.save(p.getGame());
                botMove(p.getGame());
                return Collections.singletonList(res.getDTO());
            }
            case "SURRENDER" -> {
                Move res = p.surrender();
                gameRepository.save(p.getGame());
                return Collections.singletonList(res.getDTO());
            }
        }
        return new ArrayList<>();
    }

    public ScoreDTO getScore(String username) {
        Player p = userService.getPlayer(username);
        if (p != null) {
            ScoreDTO s = new ScoreDTO();
            Point point = p.getScore();
            s.setOwn((int) point.getX());
            s.setOpponent((int) point.getY());
            return s;
        }
        return null;
    }

    public boolean isWhite(int gameID, String username) {
        Game g = gameRepository.getGame(gameID);
        if (g != null) return g.getOwner().getUsername().equals(username);
        return false;
    }

    public void botMove(Game game) {
        //TODO - wywołuje się zawsze
        if (!game.isWithBot()) {
            return;
        }
        if (game.getPlayers().size() == 2) {
            game.getPlayers().removeIf(p -> !p.getUsername().equals(game.getOwnerUsername()));
        }
        Bot bot = new Bot(game.getBoardSize(), game);
        bot.setPassword("XX");
        game.addPlayer(bot);
        //Bot bot = new Bot(game.getBoardSize(), game);
        List<Move> moves = bot.doMove();
        System.out.println(moves.size());
        List<MoveDTO> moveDtoList = new ArrayList<MoveDTO>();
        for (Move move : moves) {
            MoveDTO moveDTO = move.getDTO();
            moveDtoList.add(moveDTO);
        }
        gameRepository.save(game);
        template.convertAndSend("/topic/stomp/" + game.getGameID(), moveDtoList);
    }
}
