package GoOnline.repositories;

import GoOnline.domain.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    //zwroc null gdy nie znaleziono
    public Player getPlayer(String username) {
        Player player = null;
        System.out.println(username);
        try (Session session = sessionFactory.openSession()) {
            player = session.get(Player.class, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    public void save(Player player) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            System.out.println("zapisuje");
            session.saveOrUpdate(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}

