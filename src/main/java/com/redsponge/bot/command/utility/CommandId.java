package com.redsponge.bot.command.utility;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandId implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length == 0) {
            event.getChannel().sendMessage("Id: " + event.getAuthor().getId()).queue();
        } else {
            try {
                event.getChannel().sendMessage("Id: " + event.getMessage().getMentionedMembers().get(0).getUser().getId()).queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("Id: " + event.getAuthor().getId()).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "id";
    }

    @Override
    public String getDescription() {
        return "if a user is mentioned, displays their id, else displays the sender's id";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.UTILITY;
    }

    @Override
    public Permission getPermission() {
        return Permission.MOD;
    }
}
