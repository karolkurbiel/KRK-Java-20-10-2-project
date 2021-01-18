package pl.itacademy.tictac;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.itacademy.tictac.domain.Player;
import pl.itacademy.tictac.exception.PlayerAlreadyExistsException;
import pl.itacademy.tictac.exception.PlayerNotFoundException;
import pl.itacademy.tictac.exception.WrongPasswordsException;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player registerPlayer(String name, String password) {
        Player player = playerRepository.getByName(name);
        if (nonNull(player)) {
            throw new PlayerAlreadyExistsException("Player already exists! Try to log in!");
        }

        player = new Player(name, password);
        playerRepository.save(player);
        return player;
    }

    public Player getPlayerByNameAndPassword(String name, String password) {
        Player expectedPlayer = new Player(name, password);
        Player existingPlayer = Optional.ofNullable(playerRepository.getByName(name))
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        if(expectedPlayer.equals(existingPlayer)){
            return existingPlayer;
        }
        else{
            throw new WrongPasswordsException("Wrong password!");

        }
    }

    public Player getByName(String name){
       Player player = playerRepository.getByName(name);
       return player;
    }
}
