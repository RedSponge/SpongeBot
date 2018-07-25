package com.redsponge.bot.games;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import javax.smartcardio.Card;
import java.awt.DefaultKeyboardFocusManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameTAKI extends Game {

    private static final Timer t = new Timer();
    private Deck[] playerDecks;
    private int currentTurn;
    private TAKICard currentCard;

    public GameTAKI(User... players) {
        super(players);
    }

    @Override
    public void init() {
        playerDecks = new Deck[2];
        for (int i = 0; i < playerDecks.length; i++) {
            playerDecks[i] = Deck.generateNew();
        }
        currentCard = TAKICard.generateRandomCard();
        currentTurn = 1;
        switchTurns();
    }

    @Override
    public boolean playerInput(int player, PrivateMessageReceivedEvent event) {
        if(player != currentTurn) {
            tempMsg(player, "Its not your turn!", 3000);
            return false;
        }
        if(event.getMessage().getContentRaw().equals("takecard")) {
            playerDecks[player].addCard(TAKICard.generateRandomCard());
            switchTurns();
            return true;
        }
        int num;
        try {
            num = Integer.parseInt(event.getMessage().getContentRaw().split(" ")[0]);
            assert num > 0;
            assert num <= playerDecks[player].size();
            num--;
        } catch (AssertionError|Exception e) {
            tempMsg(player, "Please select a card (type a number)", 3000);
            return false;
        }
        Deck deck = playerDecks[player];
        TAKICard card = deck.getCard(num);
        if(card.canGoOn(currentCard)) {
            currentCard = card;
            deck.removeCard(card);
            if(deck.getCards().size() == 0) {
                broadcast("PLAYER " + (player + 1) + " WINS!");
                end();
                return false;
            }
            switchTurns();
            return true;
        } else {
            tempMsg(player, "This card cannot go there! use \"takecard\" to take a card", 3000);
        }

        return false;
    }

    @Override
    public void render() {
        for (int i = 0; i < players.length; i++) {
            PrivateChannel channel = userChannels[i];
            StringBuilder message = new StringBuilder();
            for(int ii = 0; ii < 20; ii++)
                message.append("-\n");
            message.append("=================\n");
            message.append("Current Card: ").append(currentCard.getRendered());

            Deck deck = playerDecks[i];
            message.append("\n\nYour Cards:\n");
            for (int j = 0; j < deck.getCards().size(); j++) {
                message.append(j+1).append(": ").append(deck.getCard(j).getRendered()).append("\n");
            }
            message.append("\n\nTheir Number Of Cards: ").append(playerDecks[1-i].getCards().size());
            if(playerDecks[1-i].size() == 1)
                message.append("\n**THE OTHER PLAYER HAS 1 CARD!**");
            if(currentTurn == i) message.append("\n\nIt is your turn");
            channel.sendMessage(message.toString()).queue();
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
}

class TAKICard {

    private static final Random RANDOM = new Random();

    private CardColor color;
    private int number;

    private TAKICard(CardColor color, int number) {
        this.color = color;
        this.number = number;
    }

    boolean canGoOn(TAKICard other) {
        return color == other.color || number == other.number;
    }

    String getRendered() {
        return number + "_" + color.toString();
    }

    static TAKICard generateRandomCard() {
        return new TAKICard(CardColor.random(), RANDOM.nextInt(9)+1);
    }

    enum CardColor {
        RED,
        YELLOW,
        GREEN,
        BLUE;

        private static final List<CardColor> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        static CardColor random() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }
}

class Deck {
    private List<TAKICard> cards;
    private Deck(int size) {
        System.out.println("This is called");
        cards = new ArrayList<TAKICard>();
        for (int i = 0; i < size; i++) {
            cards.add(TAKICard.generateRandomCard());
        }
    }

    public List<TAKICard> getCards() {
        return cards;
    }

    int size() {
        return cards.size();
    }

    void addCard(TAKICard card) {
        cards.add(card);
    }

    TAKICard getCard(int card) {
        return cards.get(card);
    }

    void removeCard(TAKICard card) {
        cards.remove(card);
    }

    static Deck generateNew() {
        return new Deck(5);
    }
}
