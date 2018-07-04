package com.redsponge.bot.command.fun;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandSay implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length == 0) event.getChannel().sendMessage(">say [WHAT TO SAY]").queue();
        if(Permission.DEV.hasPermission(event.getMember(), event.getGuild())) {
            event.getChannel().sendMessage(String.join(" ", args)).queue();
            event.getMessage().delete().queue();
        } else {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " says: " + String.join(" ", args)).queue();
        }
    }

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "Says what you tell it to!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }
}
