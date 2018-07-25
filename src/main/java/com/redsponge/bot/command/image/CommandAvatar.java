package com.redsponge.bot.command.image;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.EmoteImpl;
import net.dv8tion.jda.core.entities.impl.JDAImpl;
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

        EmbedBuilder mb = new EmbedBuilder();
        mb.setImage(imageURL + "?size=2048");

        Message m = new MessageBuilder().append("Avatar of ").append(getFrom.getAsMention()).append(" is: ").setEmbed(mb.build()).build();
        event.getChannel().sendMessage(m).queue();
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
