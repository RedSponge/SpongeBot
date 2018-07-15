package com.redsponge.bot.command.game;

import com.redsponge.bot.command.Permission;
import com.redsponge.bot.games.Game;
import com.redsponge.bot.games.GameRockPaperScissors;
import net.dv8tion.jda.core.entities.User;

public class CommandRockPaperScissors extends GameCommand {

    @Override
    public Class<? extends Game> getGameClass() {
        return GameRockPaperScissors.class;
    }

    @Override
    public String getName() {
        return "rockpaperscissors";
    }

    @Override
    public String getDescription() {
        return "Play Rock Paper Scissors!";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"rps"};
    }

    @Override
    public Permission getPermission() {
        return Permission.GOD;
    }
}
