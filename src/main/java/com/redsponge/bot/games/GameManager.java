package com.redsponge.bot.games;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameManager {

    private List<Game> runningGames;
    private List<User> playingUsers;
    private HashMap<User, Game> catalogedUsers;

    public GameManager() {
        runningGames = new ArrayList<Game>();
        playingUsers = new ArrayList<User>();
        catalogedUsers = new HashMap<>();
    }

    public void handle(PrivateMessageReceivedEvent event) {
        User u = event.getAuthor();
        Game g = catalogedUsers.get(u);
        if(g != null)
            g.recieve(event);
    }

    public void startGame(Class<? extends Game> gameType, User[] playingUsers) {
        try {
            Game g = (gameType.getConstructor(User[].class).newInstance(new Object[] {playingUsers}));
            runningGames.add(g);
            this.playingUsers.addAll(Arrays.asList(playingUsers));
            for(User u : playingUsers) {
                catalogedUsers.put(u, g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endGame(Game game) {
        runningGames.remove(game);
        playingUsers.removeAll(Arrays.asList(game.players));
        for(User u : game.players) {
            catalogedUsers.remove(u, game);
        }
    }



}
