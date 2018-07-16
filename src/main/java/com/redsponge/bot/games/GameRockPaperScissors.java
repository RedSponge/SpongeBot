package com.redsponge.bot.games;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

public class GameRockPaperScissors extends Game {

    private Selection[] input;
    private Message[] displays;
    private boolean displayToAll;

    public GameRockPaperScissors(User... players) {
        super(players);
    }

    @Override
    public void init() {
        input = new Selection[2];
        displayToAll = false;
        displays = new Message[2];
        for (int i = 0; i < userChannels.length; i++) {
            displays[i] = userChannels[i].sendMessage("-").complete();
        }
        broadcast("Select Either Rock, Paper or Scissors");
    }

    @Override
    public boolean playerInput(int player, PrivateMessageReceivedEvent event) {
        if(input[player] != null) {
            event.getChannel().sendMessage("You have already selected something! Wait until the other player selects something").queue();
            return false;
        }
        String s = event.getMessage().getContentRaw().toLowerCase();
        Selection sel = Selection.fromId(s);
        if(sel == null) {
            event.getChannel().sendMessage("Choose Rock, Paper or Scissors").queue();
        } else {
            input[player] = sel;
        }
        finish();
        return true;
    }

    @Override
    public void render() {
        for (int i = 0; i < displays.length; i++) {
            Message display = displays[i];
            String msg = "Your Selection: %you || Their Selection: %theirs";
            msg = msg.replace("%you", input[i] == null ? ":thinking:" : input[i].icon);
            msg = msg.replace("%theirs", displayToAll ? input[1-i].icon : input[1-i] != null ? ":white_check_mark:" : ":thinking:");

            display.editMessage(msg).queue();
        }
    }

    private void finish() {
        boolean doFinish = true;
        for (Selection selection : input) {
            doFinish = doFinish && selection != null;
        }
        if(doFinish) {
            displayToAll = true;
            render();
            checkWinner();
            end();
        }
    }

    private void checkWinner() {
        if(input[0] == input[1]) {
            broadcast("Its a draw!");
        } else if(input[0].defeats(input[1])) {
            userChannels[0].sendMessage("You Win!").queue();
            userChannels[1].sendMessage("You Lose!").queue();
        } else {
            userChannels[0].sendMessage("You Win!").queue();
            userChannels[1].sendMessage("You Lose!").queue();
        }
    }

    enum Selection {
        ROCK("rock", ":black_medium_square:"),
        PAPER("paper", ":scroll:"),
        SCISSORS("scissors", ":scissors:");

        private String id, icon;

        Selection(String id, String icon) {
            this.id = id;
            this.icon = icon;
        }

        static Selection fromId(String id) {
            for(Selection s : Selection.values()) {
                if(s.id.equals(id)) {
                    return s;
                }
            }
            return null;
        }

        boolean defeats(Selection s) {
            return (this == PAPER && s == ROCK) || (this == ROCK && s == SCISSORS) || (this == SCISSORS && s == PAPER);
        }
    }
}
