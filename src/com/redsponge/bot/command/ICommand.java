package com.redsponge.bot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public interface ICommand {

    void execute(String[] args, MessageReceivedEvent event);

    String name();

}
