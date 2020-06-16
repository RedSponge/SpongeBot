package com.redsponge.bot.command.game;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.games.Game;
import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class GameCommand implements ICommand {

    protected HashMap<Member, Member> invitations;

    public GameCommand() {
        invitations = new HashMap<Member, Member>();
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length < 2) {
            displayHelp(event);
            return;
        }

        if(args[0].equals("invite")) {
            if(isMention(args[1], event.getMessage(), 0)) {
                Member sender = event.getMember();
                Member sendTo = event.getMessage().getMentionedMembers().get(0);

                if(sender.getUser().getId().equals(sendTo.getUser().getId())) {
                    reply(event, "You cannot invite yourself!");
                } else if(!validInviter(sender)) {
                    reply(event, "You have already invited someone!");
                } else if (!validMember(sendTo)) {
                    reply(event, "You cannot invite this person! They're either offline or a bot!");
                } else {
                    putInvite(event, sender, sendTo);
                }
            } else if(args[1].equals("cancel")) {
                if(invitations.keySet().contains(event.getMember())) {
                    invitations.remove(event.getMember());
                    reply(event, "You canceled your invitation!");
                } else {
                    reply(event, "You haven't invited anyone!");
                }
            }
        } else if(args[0].equals("accept")) {
            if(isMention(args[1], event.getMessage(), 0)) {
                Member inviter = event.getMessage().getMentionedMembers().get(0);
                Member invited = event.getMember();

                if(invitations.get(inviter) == null || !invitations.get(inviter).getUser().getId().equals(invited.getUser().getId())) {
                    reply(event, "You weren't invited by " + inviter.getAsMention());
                } else {
                    startGame(inviter, invited);
                }
            } else {
                reply(event, "Invalid Usage!");
                displayHelp(event);
            }
        }
    }

    @Override
    public String[] getUsages() {
        return new String[] {"invite [user]", "invite cancel", "accept [from]"};
    }

    private void putInvite(MessageReceivedEvent event, Member from, Member to) {
        invitations.put(from, to);
        event.getChannel().sendMessage(to.getAsMention() + ", use " + Reference.prefix + getName() + " accept " + from.getAsMention() + " to accept their invitations").queue();
    }
    
    private void startGame(Member inviter, Member invited) {
        invitations.remove(inviter, invited);
        ArrayList<Member> members = new ArrayList<>(Arrays.asList(inviter, invited));
        SpongeBot.instance.gameManager.startGame(getGameClass(), members.stream().map(Member::getUser).toArray(User[]::new));
        
    }

    public abstract Class<? extends Game> getGameClass();

    private boolean validMember(Member m) {
        if(m.getUser().isBot()) return false;

        return Reference.CAN_BE_INVITED_TO_GAMES.contains(m.getOnlineStatus());
    }

    private boolean validInviter(Member m) {
        return !invitations.keySet().contains(m);
    }

    private String normalizeMention(String s) {
        return s.replaceAll("!", "");
    }

    private boolean isMention(String s, Message m, int position) {
        try {
            String a = normalizeMention(s);
            String b = normalizeMention(m.getMentionedMembers().get(position).getAsMention());
            System.out.println(a + " " + b);
            return a.equals(b);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GAME;
    }
}
