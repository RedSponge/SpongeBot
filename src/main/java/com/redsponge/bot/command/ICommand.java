package com.redsponge.bot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public interface ICommand {

    void execute(String[] args, MessageReceivedEvent event);

    String getName();

    String getDescription();

    default String[] getUsages() {
        return new String[] {""};
    }

    CommandCategory getCategory();

    Permission getPermission();

    default boolean showInHelp() {
        return true;
    }

    default String[] getAliases() {return new String[0];}

    default String[] formUsages() {
        List<String> ar = new ArrayList<>(Collections.singletonList(getName()));
        ar.addAll(Arrays.asList(getAliases()));
        return ar.toArray(new String[0]);
    }

    default void displayHelp(MessageReceivedEvent event) {
        Commands.CommandHelp.execute(new String[] {getName()}, event);
    }

}
