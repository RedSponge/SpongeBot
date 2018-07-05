package com.redsponge.bot.command.general;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandServers implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("I'm currently running on `" + SpongeBot.instance.jda.getGuilds().size() + "` servers!").queue();
    }

    @Override
    public String getName() {
        return "servers";
    }

    @Override
    public String getDescription() {
        return "Displays how many servers is the bot on";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GENERAL;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }
}
