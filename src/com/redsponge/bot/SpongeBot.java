package com.redsponge.bot;

import com.redsponge.bot.command.CommandManager;
import com.redsponge.bot.command.CommandPing;
import com.redsponge.bot.event.OnMessage;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class SpongeBot {

    public JDA jda;
    public CommandManager commandManager;

    public static SpongeBot instance;

    public SpongeBot() {
        instance = this;
        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken("NOOOOOOOOOOOOOOPE SRY");

            registerCommands();
            registerEvents(builder);

            jda = builder.buildBlocking();
        } catch (LoginException|InterruptedException e) {
            System.err.println("Couldn't connect!");
            e.printStackTrace();
        }
    }

    private void registerEvents(JDABuilder jda) {
        jda.addEventListener(new OnMessage());
    }

    private void registerCommands() {
        commandManager = new CommandManager();
        commandManager.register(new CommandPing());
    }

    public static void main(String[] args) {
        new SpongeBot();
    }

}
