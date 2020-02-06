package GoOnline.repositories;

import GoOnline.domain.Game.Game;
import GoOnline.domain.Game.GameStatus;
import GoOnline.dto.GameData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GameRepository {

    @Autowired
    private SessionFactory sessionFactory;


    public Game getGame(int gameID) {
        Game game = null;
        try (Session session = sessionFactory.openSession()) {
            game = session.get(Game.class, gameID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    //zwraca ID zapisanej gry, bodajze metoda session.saveOrUpdate zwraca ID
    public int save(Game g) {
        Transaction transaction = null;
        int id = -1;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(g);
            //zapisujemy otrzymane id
            id = g.getGameID();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return id;
    }

    //wez gry ktore maja status WAITING
    public List<GameData> getActiveGames() {
        List<GameData> gamesData = new ArrayList<GameData>();
        List<Game> games = new ArrayList<Game>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String query = "SELECT * FROM game WHERE gameStatus = :status ORDER BY rand() LIMIT 5";
            games = session
                    .createNativeQuery(query, Game.class)
                    .setParameter("status", GameStatus.WAITING.name())
                    .getResultList();

            for (Game g : games) gamesData.add(g.getGameData());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return gamesData;
    }
}
