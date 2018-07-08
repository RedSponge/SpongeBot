package com.redsponge.bot.command.utility;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandInvite implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", Click the link below!\n" + getInviteLink()).queue();
    }

    public String getInviteLink() {
        return "https://discordapp.com/api/oauth2/authorize?client_id=463698658995732480&permissions=268692518&scope=bot";
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Displays and invite link for you!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.UTILITY;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"inv"};
    }
}
