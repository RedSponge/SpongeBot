package com.redsponge.bot.command;

import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    private Map<String, ICommand> commands;

    public CommandManager() {
        commands = new HashMap<>();
    }

    public void register(ICommand... commands) {
        for(ICommand c : commands) {
            this.commands.put(c.name(), c);
        }
    }

    public void run(MessageReceivedEvent event, Message m, User user, Member member) {
        String content = m.getContentRaw();
        if(!content.startsWith(Reference.prefix)) return;
        content = content.substring(Reference.prefix.length());
        String command = content.split(" ")[0];
        String[] args = new String[0];
        try {
            args = Arrays.copyOfRange(content.split(" "), 1, content.split(" ").length);
        } catch (IllegalArgumentException e) {
            //IGNORE
        }
        System.out.println("RUNNING COMMAND " + command + " WITH ARGS " + Arrays.toString(args));

        if(commands.get(command) != null) {
            commands.get(command).execute(args, event);
        }
    }

}
