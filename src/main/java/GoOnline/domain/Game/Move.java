package GoOnline.domain.Game;

import GoOnline.domain.Player;
import GoOnline.dto.MoveDTO;

import javax.persistence.*;

@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne //jednostronna relacja
    private Player player;

    @Column(name = "x")
    private int x;
    @Column(name = "y")
    private int y;

    @ManyToOne
    @JoinColumn(name = "gameID", nullable = false)
    private Game game;

    @Enumerated(value = EnumType.STRING)
    private GridState color;

    //@Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MoveType moveType;

    private int number;


    public MoveDTO getDTO() {
        MoveDTO m = new MoveDTO();
        m.setUsername(player.getUsername());
        m.setColor(color.name());
        m.setCommandType(moveType.name());
        m.setX(x);
        m.setY(y);
        return m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GridState getColor() {
        return color;
    }

    public void setColor(GridState color) {
        this.color = color;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
