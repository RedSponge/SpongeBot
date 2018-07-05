package com.redsponge.bot.command.chat;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class CommandDeleteMessages implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length == 0) {
            event.getChannel().sendMessage("How many messages to delete?").queue();
            return;
        }
        int toDelete = Integer.parseInt(args[0])+1;
        MessageHistory history = new MessageHistory(event.getChannel());
        List<Message> messages = history.retrievePast(toDelete).complete();
        for(int i = 0; i < messages.size(); i++) {
            if(i == messages.size()-1) messages.get(i).delete().queue(aVoid -> {
                Message m = event.getChannel().sendMessage("Successfully deleted the last " + (toDelete-1) + " messages!").complete();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        m.delete().queue();
                    }
                }, 3000);
            });
            else messages.get(i).delete().queue();
        }
    }

    @Override
    public String getName() {
        return "deleteMessages";
    }

    @Override
    public String getDescription() {
        return "Delete the latest n messages from chat";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.CHAT;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"delMsg", "deleteMessage"};
    }

    @Override
    public String[] getUsages() {
        return new String[] {"deleteMessages [number]"};
    }

    @Override
    public Permission getPermission() {
        return Permission.MOD;
    }
}
