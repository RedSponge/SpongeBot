package com.redsponge.bot.command.image;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandAvatar implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        User getFrom = event.getAuthor();
        if(event.getMessage().getMentionedMembers().size() > 0) {
            getFrom = event.getMessage().getMentionedMembers().get(0).getUser();
        }
        String imageURL = getFrom.getAvatarUrl();
        if(imageURL == null) imageURL = getFrom.getDefaultAvatarUrl();

        event.getChannel().sendMessage("Avatar of " + getFrom.getAsMention() + " is: " + imageURL).queue();
    }

    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public String getDescription() {
        return "get the avatar of someone";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.IMAGE;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public String[] getUsages() {
        return new String[] {"", "[User]"};
    }

    @Override
    public String[] getAliases() {
        return new String[] {"aang", "korra"};
    }
}
