package GoOnline.controllers;

import GoOnline.domain.Game.Game;
import GoOnline.dto.GameData;
import GoOnline.dto.MoveDTO;
import GoOnline.dto.ScoreDTO;
import GoOnline.services.GameService;
import GoOnline.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;


    @GetMapping("/game/lobby")
    public String getLobby(Model model, Authentication authentication) {
        GameData gameData;
        try {
            Game g = userService.getPlayerGame(authentication.getName());
            gameData = g.getGameData();
        } catch (Exception e) {
            gameData = new GameData();
        }
        List<GameData> activeGames = gameService.getActiveGames();
        if (activeGames == null) activeGames = new ArrayList<>();
        model.addAttribute("ownGame", gameData);
        model.addAttribute("gamesList", activeGames);
        return "lobbyPage";
    }


    @GetMapping("/game/join/{gameID}")
    public String joinGame(@PathVariable("gameID") int gameID, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        if (!gameService.joinGame(gameID, authentication.getName())) {
            redirectAttributes.addAttribute("gameInProgress");
            return "lobbyPage";
        }
        model.addAttribute("gameID", gameID);
        model.addAttribute("gameData", gameService.getGameData(gameID));
        // model.addAttribute("moves",gameService.getGameMoves(gameID));
        return "gamePage";
    }

    @GetMapping("/game/moves/{gameID}")
    public @ResponseBody
    List<MoveDTO> getMoves(@PathVariable("gameID") int gameID) {
        return gameService.getGameMoves(gameID);
    }


    @GetMapping("/game/create")
    public String createGame(Authentication authentication, @RequestParam("bot") boolean bot) {
        int gameID = gameService.createGame(authentication.getName(), bot);
        return "redirect:/game/join/" + gameID;
    }


    @GetMapping(value = "/game/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScoreDTO> getScore(Authentication authentication) {
        ScoreDTO score = gameService.getScore(authentication.getName());
        if (score == null) return ResponseEntity.status(500).build();
        return ResponseEntity.ok(score);
    }

    @MessageMapping("/stomp/{gameID}")
    @SendTo("/topic/stomp/{gameID}")
    public String sendMessage(@DestinationVariable("gameID") int gameID, @Payload String message, Principal principal) {
        MoveDTO move = gson.fromJson(message, MoveDTO.class);
        try {
            if (move != null) {
                move.setUsername(principal.getName());
                return gson.toJson(gameService.move(principal.getName(), move));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "ERROR";
        }
        return "ERROR";
    }

}
