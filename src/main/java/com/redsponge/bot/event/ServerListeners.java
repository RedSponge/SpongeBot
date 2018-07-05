package com.redsponge.bot.event;

import com.redsponge.bot.role.Roles;
import net.dv8tion.jda.core.events.guild.GuildAvailableEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class ServerListeners extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Roles.addAllToServers(event.getGuild());
            }
        }, 1000);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Roles.removeAllFromServer(event.getGuild());
    }
}
