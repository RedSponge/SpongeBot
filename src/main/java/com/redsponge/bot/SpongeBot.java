package com.redsponge.bot;

import com.redsponge.bot.command.CommandManager;
import com.redsponge.bot.command.Commands;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.chat.CommandDeleteMessages;
import com.redsponge.bot.command.fun.CommandQuestion;
import com.redsponge.bot.command.fun.CommandSay;
import com.redsponge.bot.command.general.CommandHelp;
import com.redsponge.bot.command.general.CommandHelp.CommandH_elp_;
import com.redsponge.bot.command.general.CommandServers;
import com.redsponge.bot.command.utility.CommandId;
import com.redsponge.bot.command.utility.CommandPing;
import com.redsponge.bot.event.OnMessage;
import com.redsponge.bot.event.ServerListeners;
import com.redsponge.bot.role.RoleContainer;
import com.redsponge.bot.role.Roles;
import com.redsponge.bot.util.Reference;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.Guild;

import javax.security.auth.login.LoginException;

public class SpongeBot {

    public JDA jda;
    public CommandManager commandManager;
    public AudioPlayerManager audioPlayerManager;

    public static SpongeBot instance;

    public SpongeBot() {
        instance = this;
        HerokuServerHandler.createHandler();
        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(Reference.TOKEN);

            audioPlayerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(audioPlayerManager);

            registerCommands();
            registerEvents(builder);

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
        new SpongeBot();
    }

}
