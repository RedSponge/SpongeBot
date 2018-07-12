package com.redsponge.bot.command.utility;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.temporal.ChronoUnit;

/**
 * Ping code snippet taken from jagrosh
 * view their file at https://github.com/jagrosh/Spectra/blob/master/src/spectra/commands/Ping.java
 */

public class CommandPing implements ICommand {

    private static Color purple = new Color(0xA800FF);
    private static Color lightBlue = new Color(0x00A0FF);

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        String vowel = "aeiou".charAt((int)(Math.random()*5))+"";
        event.getChannel().sendMessage("P"+vowel+"ng: ...").queue( m -> {
            if(m!=null) {
                m.editMessage("P" + vowel + "ng: " + event.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS) + "ms").queue();
            }
//        EmbedBuilder builder = new EmbedBuilder().setColor(getColor(ping)).addField(":Ping:", Long.toString(ping), true);
//        event.getChannel().sendMessage(builder.build()).queue();
    });
    }

    private Color getColor(long ping) {
        if(ping < 100)
            return Color.GREEN;
        if(ping < 200)
            return Color.CYAN;
        if(ping < 400)
            return lightBlue;
        if(ping < 800)
            return purple;
        if(ping < 10000)
            return Color.PINK;
        return Color.RED;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.UTILITY;
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Pong! Shows latency.";
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }
}
