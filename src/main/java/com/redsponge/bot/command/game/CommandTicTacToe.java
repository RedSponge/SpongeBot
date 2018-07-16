package com.redsponge.bot.command.game;

import com.redsponge.bot.command.Permission;
import com.redsponge.bot.games.Game;
import com.redsponge.bot.games.GameTicTacToe;
import net.dv8tion.jda.core.entities.Member;

import java.util.HashMap;

public class CommandTicTacToe extends GameCommand {

    private HashMap<Member, Member> invites;

    public CommandTicTacToe() {
        invites = new HashMap<Member, Member>();
    }

    @Override
    public Class<? extends Game> getGameClass() {
        return GameTicTacToe.class;
    }

    @Override
    public String getName() {
        return "tictactoe";
    }

    @Override
    public String getDescription() {
        return "Play TicTacToe with someone!";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"ttt", "xmd"};
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }
}
