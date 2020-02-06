package GoOnline.services;

import GoOnline.domain.Game.Game;
import GoOnline.domain.Player;
import GoOnline.dto.LoginData;
import GoOnline.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.getPlayer(s);
    }

    public Player getPlayer(String name) {
        return userRepository.getPlayer(name);
    }

    public void registerUser(LoginData loginData) throws AccountException {
        UserDetails p = loadUserByUsername(loginData.getUsername());
        if (p != null) throw new AccountException();
        Player player = new Player();
        player.setUsername(loginData.getUsername());
        player.setPassword(passwordEncoder.encode(loginData.getPassword()));
        userRepository.save(player);
    }

    public Game getPlayerGame(String name) throws Exception {
        Player p = userRepository.getPlayer(name);
        if (p == null) throw new Exception();
        return p.getGame();
    }
}
