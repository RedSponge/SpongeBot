package com.redsponge.bot.command.fun;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandQuestion implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length == 0) {
            event.getChannel().sendMessage("Ask a question :)").queue();
        } else if((new Random().nextInt(2)) == 1) {
            event.getChannel().sendMessage("yes").queue();
        } else {
            event.getChannel().sendMessage("no").queue();
        }
    }

    @Override
    public String getName() {
        return "question";
    }

    @Override
    public String getDescription() {
        return "Ask a question, get an answer";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"8ball"};
    }

    @Override
    public String[] getUsages() {
        return new String[] {"[Question]"};
    }
}
