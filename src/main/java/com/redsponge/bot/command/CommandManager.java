package com.redsponge.bot.command;

import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    private Map<String, ICommand> commands;
    private Map<CommandCategory, List<ICommand>> commandCategories;

    public CommandManager() {
        commands = new HashMap<>();
        commandCategories = new HashMap<>();
        for(CommandCategory c : CommandCategory.values()) {
            commandCategories.put(c, new ArrayList<>());
        }
    }

    public void register(ICommand... commands) {
        for(ICommand c : commands) {
            this.commands.put(c.getName(), c);
            this.commandCategories.get(c.getCategory()).add(c);
            for(String alias : c.getAliases()) {
                this.commands.put(alias, c);
            }
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
            ICommand toRun = commands.get(command);
            if(!toRun.getPermission().hasPermission(member, m.getGuild())) {
                System.out.println(Permission.getPermissionLevel(member, event.getGuild()));
                event.getChannel().sendMessage(user.getAsMention() + ", you do not have permissions to use this command!").queue();
                return;
            }
            toRun.execute(args, event);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public Map<String, ICommand> getCommandsSorted() {
        return commands;
    }

    public Map<CommandCategory, List<ICommand>> getCommandCategories() {
        return commandCategories;
    }
}
