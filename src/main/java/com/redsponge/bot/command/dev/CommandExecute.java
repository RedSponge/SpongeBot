package com.redsponge.bot.command.dev;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.ReceivedMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandExecute implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length < 3) return;
        boolean success = false;
        if(args[0].equals("as")) {
            if(event.getMessage().getMentionedMembers().size() > 0) {
                if(!args[1].equals(event.getMessage().getMentionedMembers().get(0).getAsMention())) return;
                Member executeAs = event.getMessage().getMentionedMembers().get(0);
                String toExecute = Reference.prefix + String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                Message m = event.getMessage();
                modifyMessage(m, executeAs, toExecute);

                SpongeBot.instance.commandManager.run(event);
                success = true;
            }
        }

        if(success) {
            event.getMessage().delete().queue();
        }
    }

    @Override
    public String getName() {
        return "execute";
    }

    @Override
    public String getDescription() {
        return "Execute something";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DEV;
    }

    @Override
    public Permission getPermission() {
        return Permission.GOD;
    }

    @Override
    public boolean showInHelp() {
        return false;
    }

    private void modifyMessage(Message m, Member newAuthor, String newContent) {
        if(m.getClass() == ReceivedMessage.class) {
            try {
                Field authorField = m.getClass().getDeclaredField("author");
                authorField.setAccessible(true);
                authorField.set(m, newAuthor.getUser());

                Field contentField = m.getClass().getSuperclass().getDeclaredField("content");
                contentField.setAccessible(true);
                contentField.set(m, newContent);
            } catch (Exception e) {

                PrivateChannel channel = m.getAuthor().openPrivateChannel().complete();
                channel.sendMessage("EXCEPTION: " + e.getMessage()).queue();
            }
        } else {
            PrivateChannel channel = m.getAuthor().openPrivateChannel().complete();
            channel.sendMessage("Type of message isn't ReceivedMessage").queue();
        }
    }
}
