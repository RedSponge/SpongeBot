package com.redsponge.bot.event;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.CommandManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OnMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        long ping = System.currentTimeMillis();

        SpongeBot.instance.commandManager.run(event, event.getMessage(), event.getAuthor(), event.getMember());


    }
}
