package com.redsponge.bot.event;

import com.redsponge.bot.SpongeBot;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OnShutDown extends ListenerAdapter {

    @Override
    public void onShutdown(ShutdownEvent event) {
        SpongeBot.instance.gameManager.shutdown();
        SpongeBot.instance.commandManager.shutdown();
    }
}
