package com.redsponge.bot;

import com.redsponge.bot.command.CommandManager;
import com.redsponge.bot.command.Commands;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.event.OnMessage;
import com.redsponge.bot.event.OnShutDown;
import com.redsponge.bot.event.ServerListeners;
import com.redsponge.bot.games.GameManager;
import com.redsponge.bot.role.Roles;
import com.redsponge.bot.util.Reference;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.Guild;

import javax.security.auth.login.LoginException;

public class SpongeBot {

    public JDA jda;
    public CommandManager commandManager;
    public AudioPlayerManager audioPlayerManager;
    public GameManager gameManager;

    public static SpongeBot instance;

    public SpongeBot(boolean heroku) {
        instance = this;
        if(heroku) HerokuServerHandler.createHandler();
        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(Reference.TOKEN);

            audioPlayerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(audioPlayerManager);

            registerCommands();
            registerEvents(builder);
            gameManager = new GameManager();

            jda = builder.buildBlocking();

            jda.getPresence().setGame(Game.of(GameType.WATCHING, Reference.PLAYING));
            registerRoles();
        } catch (LoginException|InterruptedException e) {
            System.err.println("Couldn't connect!");
            e.printStackTrace();
        }
    }

    private void registerEvents(JDABuilder jda) {
        jda.addEventListener(new OnMessage());
        jda.addEventListener(new ServerListeners());
        jda.addEventListener(new OnShutDown());
    }


    private void registerCommands() {
        commandManager = new CommandManager();
        commandManager.register(Commands.ALL.toArray(new ICommand[0]));
    }

    private void registerRoles() {
        for(Guild g : jda.getGuilds()) {
            Roles.addAllToServers(g);
        }
    }

    public static void main(String[] args) {
        new SpongeBot(args.length > 0 && args[0].equals("heroku"));
    }

}
