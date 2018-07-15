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

//    @Override
//    public void execute(String[] args, MessageReceivedEvent event) {
//        if(args.length < 2) {
//            displayHelp(event);
//            return;
//        }
//        if(args[0].equals("invite")) {
//            if(args[1].equals("cancel")) {
//                if(invites.containsKey(event.getMember())) {
//                    invites.remove(event.getMember());
//                } else {
//                    event.getChannel().sendMessage("You didn't invite anyone!").queue();
//                }
//            } else if(isMention(event.getMessage(), args[1], 0)){
//                if(invites.containsKey(event.getMember())) {
//                    event.getChannel().sendMessage("You already invited someone! use tictactoe invite cancel to cancel it").queue();
//                } else {
//                    if(event.getMember() == event.getMessage().getMentionedMembers().get(0)) {
//                        event.getChannel().sendMessage("You cannot invite yourself!").queue();
//                        return;
//                    } else if(!isValidUser(event.getMessage().getMentionedMembers().get(0))) {
//                        event.getChannel().sendMessage("You cannot invite this person! they're either offline or a bot").queue();
//                        return;
//                    }
//                    invites.put(event.getMember(), event.getMessage().getMentionedMembers().get(0));
//                    event.getChannel().sendMessage("Invited " + event.getMessage().getMentionedMembers().get(0).getAsMention() + " to a game!").queue();
//                    event.getChannel().sendMessage(event.getMessage().getMentionedMembers().get(0).getAsMention() + ", use tictactoe accept " + event.getAuthor().getAsMention() + " to enter the game").queue();
//                }
//            }
//        } else if(args[0].equals("accept")) {
//            if(isMention(event.getMessage(), args[1], 0)) {
//                Member invitor = event.getMessage().getMentionedMembers().get(0);
//                if(invites.get(invitor) == (event.getMember())) {
//                    startGame(invitor, event.getMember());
//                } else {
//                    event.getChannel().sendMessage("You weren't invited by " + invitor.getAsMention() + "!").queue();
//                }
//            }
//        }
//    }


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
