package com.redsponge.bot.command.fun;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.MalformedCommandException;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class CommandFace implements ICommand {

    public static final HashMap<Integer, String> faces;

    static {
        faces = new HashMap<>();
        faces.put(1, "(.-.)");
        faces.put(2, "(^v^)");
        faces.put(3, "(^.-)");
        faces.put(4, "(\\*o\\*)");
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length == 0) {
            displayHelp(event);
            return;
        }
        String face;
        try {
            face = faces.get(Integer.parseInt(args[0]));
            if(face == null) throw new MalformedCommandException();
        } catch (NumberFormatException e) {
            displayHelp(event);
            return;
        } catch (MalformedCommandException e) {
            event.getChannel().sendMessage("Unknown Face!").queue();
            return;
        }
        event.getChannel().sendMessage(face).queue();
        event.getMessage().delete().queue();
    }

    @Override
    public String getName() {
        return "face";
    }

    @Override
    public String getDescription() {
        return "displays a face";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public String[] getUsages() {
        return new String[] {"[number of face]"};
    }

    @Override
    public String[] getAliases() {
        return new String[] {"emote"};
    }
}
