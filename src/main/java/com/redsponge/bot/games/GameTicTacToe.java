package com.redsponge.bot.games;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameTicTacToe extends Game {

    private Message[] boards;
    private Message[] rematchMessages;
    private boolean[] wantRematch;
    private boolean[] respondedForRematch;
    private String[][] board;
    private int currentTurn;
    private boolean gameRunning;
    private String spacing;

    private boolean numbers;

    private static final String X_MARK = "X";
    private static final String O_MARK = "O";
    private static final String NONE_MARK = "";

    private Timer t;

    public GameTicTacToe(User... players) {
        super(players);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 50; i++) {
            sb.append("\n");
        }
        sb.append("====================================");
        spacing = sb.toString();
        t = new Timer();
        init();
    }

    private void init() {
        boards = new Message[players.length];
        for(int i = 0; i < boards.length; i++) {
            userChannels[i].sendMessage(spacing).queue();
            boards[i] = userChannels[i].sendMessage("-").complete();
        }
        board = new String[][]{
                {NONE_MARK, NONE_MARK, NONE_MARK},
                {NONE_MARK, NONE_MARK, NONE_MARK},
                {NONE_MARK, NONE_MARK, NONE_MARK}
        };
        currentTurn = 1;

        wantRematch = new boolean[2];
        rematchMessages = new Message[2];
        respondedForRematch = new boolean[2];

        userChannels[0].sendMessage("You are X").queue();
        userChannels[1].sendMessage("You are O").queue();

        gameRunning = true;

        render();
        switchTurns();
    }

    @Override
    public boolean playerInput(int player, PrivateMessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().split(" ")[0].equals("chat")) {
            String[] toSend = event.getMessage().getContentRaw().split(" ");
            toSend = Arrays.copyOfRange(toSend, 1, toSend.length);
            String s = String.join(" ", toSend);
            if(s.trim().length() > 0)
                userChannels[1-player].sendMessage("The Other Player Says: " + s).queue();
            return false;
        }
        if(!gameRunning) {
            if(!respondedForRematch[player]) {
                String content = event.getMessage().getContentRaw().toLowerCase();
                if(content.equals("y") || content.equals("yes")) {
                    wantRematch[player] = true;
                    respondedForRematch[player] = true;
                    checkForRematch();
                    return true;
                } else if(content.equals("n") || content.equals("no")) {
                    wantRematch[player] = false;
                    respondedForRematch[player] = true;
                    render();
                    end();
                    return true;
                } else {
                    event.getChannel().sendMessage("Y(es) OR N(o)").queue();
                }
            }
            return true;
        }
        try {
            if(event.getMessage().getContentRaw().startsWith("end")) {
                broadcast("GAME ENDED BY PLAYER " + (player + 1));
                end();
                return false;
            }
            if (currentTurn == player) {
                int num = Integer.parseInt(event.getMessage().getContentRaw().split(" ")[0])-1;
                if(num < 0 || num >= 9) return false;
                String s = getAt(num);
                if(s.equals(NONE_MARK)) {
                    if(player == 0) {
                        setAt(num, X_MARK);
                    } else {
                        setAt(num , O_MARK);
                    }
                    checkVictory();
                    switchTurns();
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void render() {
        if(gameRunning) {
            StringBuilder b = new StringBuilder();

            String dashes = "-------------";

            b.append("```\n");
            b.append(dashes);
            for (int i = 0; i < 3; i++) {
                b.append("\n| ");
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals(NONE_MARK)) {
                        b.append((i * 3) + j + 1);
                    } else {
                        b.append(board[i][j]);
                    }
                    b.append(" | ");
                }
                b.append("\n").append(dashes);
            }

            b.append("```");

            for (Message m : boards) {
                m.editMessage(b.toString().trim().replace(" [", "[").replace("] ", "]")).queue();
            }
        } else {
            String player1Status = ":thinking:";
            if(respondedForRematch[0]) {
                player1Status = wantRematch[0] ? ":white_check_mark:" : ":x:";
            }

            String player2Status = ":thinking:";
            if(respondedForRematch[1]) {
                player2Status = wantRematch[1] ? ":white_check_mark:" : ":x:";
            }

            for(Message m : rematchMessages) {
                m.editMessage("Player 1: " + player1Status + "\nPlayer 2: " + player2Status).queue();
            }
        }
    }

    private String getAt(int i) {
        return board[i / 3][i % 3];
    }

    private void setAt(int i, String to) {
        board[i/3][i%3] = to;
    }

    private int asId(int i, int j) {
        return i*3+j;
    }

    private void checkVictory() {
        for(int i = 0; i < 3; i++) {
            if(board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals(NONE_MARK)) {
                announceVictory(board[i][0], asId(i, 0), asId(i, 1), asId(i, 2));
                return;
            }
            if(board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals(NONE_MARK)) {
                announceVictory(board[0][i], asId(0, i), asId(1, i), asId(2, i));
                return;
            }
        }
        if(board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(NONE_MARK)) {
            announceVictory(board[1][1], asId(0, 0), asId(1, 1), asId(2, 2));
            return;
        }
        if(board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals(NONE_MARK)) {
            announceVictory(board[1][1], asId(0, 2), asId(1, 1), asId(2, 0));
            return;
        }
        boolean full = true;
        for(String[] row : board) {
            for(String c : row) {
                full = full && !c.equals(NONE_MARK);
            }
        }
        if(full) {
            announceVictory(null);
        }
    }

    private void switchTurns() {
        if(currentTurn == 0) currentTurn = 1;
        else currentTurn = 0;

        Message m = userChannels[currentTurn].sendMessage("It is your turn now!").complete();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                m.delete().queue();
            }
        }, 3000);
    }

    private void announceVictory(String c, int... points) {
        if(c == null) {
            userChannels[0].sendMessage("Its a draw!").queue();
            userChannels[1].sendMessage("Its a draw!").queue();
        } else {
            for (int point : points) {
                highlight(point);
            }

            render();
            int winner = c.equals(X_MARK) ? 0 : 1;
            userChannels[winner].sendMessage("YOU WIN!").queue();
            userChannels[1 - winner].sendMessage("YOU LOSE!").queue();
        }
        for(int i = 0; i < 2; i++) {
            userChannels[i].sendMessage("REMATCH?").queue();
            rematchMessages[i] = userChannels[i].sendMessage("Player 1: :thinking:\nPlayer 2: :thinking:").complete();
            userChannels[i].sendMessage("Y(es)/N(o)").queue();
        }
        checkForRematch();
    }

    private void checkForRematch() {
        gameRunning = false;
        boolean doRematch = true;

        for(boolean b : wantRematch) {
            doRematch = doRematch && b;
        }

        if(doRematch) {
            render();
            broadcast("STARTING NEW GAME");
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    init();
                }
            }, 3000);
        }
    }

    private void highlight(int id) {
        setAt(id, "[" + getAt(id) + "]");
    }

}
