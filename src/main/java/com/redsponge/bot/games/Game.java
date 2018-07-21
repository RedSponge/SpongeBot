package com.redsponge.bot.games;

import com.redsponge.bot.SpongeBot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Game {

    protected User[] players;
    protected PrivateChannel[] userChannels;
    protected static final Timer timer = new Timer();

    public Game(User... players) {
        this.players = players;
        userChannels = new PrivateChannel[players.length];
        for(int i = 0; i < userChannels.length; i++) {
            userChannels[i] = players[i].openPrivateChannel().complete();
        }
        init();
        render();
    }

    public User[] getPlayers() {
        return players;
    }

    public void recieve(PrivateMessageReceivedEvent event) {
        User author = event.getAuthor();
        for(int i = 0; i < players.length; i++) {
            if (author == players[i])
                if (playerInput(i, event)) {
                    render();
                }
        }
    }

    public abstract void init();

    public abstract boolean playerInput(int player, PrivateMessageReceivedEvent event);

    public abstract void render();

    public void end() {
        for (PrivateChannel userChannel : userChannels) {
            userChannel.sendMessage("GAME ENDED!").queue();
            userChannel.close().queue();
        }
        SpongeBot.instance.gameManager.endGame(this);
    }

    protected void broadcast(String msg) {
        for (PrivateChannel userChannel : userChannels) {
            userChannel.sendMessage(msg).queue();
        }
    }

    public void shutdown() {
        broadcast("ENDING GAME - BOT SHUTDOWN");
        end();
    }

    protected void tempMsg(int player, String message, int timeout) {
        userChannels[player].sendMessage(message).queue(
                m -> timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        m.delete().queue();
                    }
                }, timeout)
        );
    }

}
