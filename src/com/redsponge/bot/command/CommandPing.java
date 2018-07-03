package com.redsponge.bot.command;

import com.redsponge.bot.SpongeBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class CommandPing implements ICommand {

    private static Color purple = new Color(0xA800FF);
    private static Color lightblue = new Color(0x00A0FF);

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        long ping = event.getMessage().getCreationTime().getNano()/1000000;
        EmbedBuilder builder = new EmbedBuilder().setColor(getColor(ping)).addField(":Ping:", Long.toString(ping), true);
        event.getChannel().sendMessage(builder.build()).queue();
    }

    private Color getColor(long ping) {
        if(ping < 100)
            return Color.GREEN;
        if(ping < 200)
            return Color.CYAN;
        if(ping < 400)
            return lightblue;
        if(ping < 800)
            return purple;
        if(ping < 10000)
            return Color.PINK;
        return Color.RED;
    }

    @Override
    public String name() {
        return "ping";
    }
}
