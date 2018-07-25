package com.redsponge.bot.command.game;

import com.redsponge.bot.command.Permission;
import com.redsponge.bot.games.Game;
import com.redsponge.bot.games.GameTAKI;

public class CommandTAKI extends GameCommand {

    @Override
    public Class<? extends Game> getGameClass() {
        return GameTAKI.class;
    }

    @Override
    public String getName() {
        return "taki";
    }

    @Override
    public String getDescription() {
        return "Play TAKI!";
    }

    @Override
    public Permission getPermission() {
        return Permission.GOD;
    }
}
