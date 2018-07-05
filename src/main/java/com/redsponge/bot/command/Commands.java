package com.redsponge.bot.command;

import com.redsponge.bot.command.chat.CommandDeleteMessages;
import com.redsponge.bot.command.fun.CommandQuestion;
import com.redsponge.bot.command.fun.CommandSay;
import com.redsponge.bot.command.general.CommandHelp;
import com.redsponge.bot.command.general.CommandHelp.CommandH_elp_;
import com.redsponge.bot.command.general.CommandServers;
import com.redsponge.bot.command.music.CommandPlay;
import com.redsponge.bot.command.utility.CommandId;
import com.redsponge.bot.command.utility.CommandPing;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Commands {

    public static final ICommand CommandDeleteMessages = new CommandDeleteMessages();

    public static final ICommand CommandQuestion = new CommandQuestion();
    public static final ICommand CommandSay = new CommandSay();

    public static final ICommand CommandHelp = new CommandHelp();
    public static final ICommand CommandHelpTroll = new CommandH_elp_();
    public static final ICommand CommandServers = new CommandServers();

    public static final ICommand CommandId = new CommandId();
    public static final ICommand CommandPing = new CommandPing();
    
    public static final ICommand CommandDice = new CommandDice();

    public static final List<ICommand> ALL;

    static {
        ALL = new ArrayList<>();
        for(Field f : Commands.class.getDeclaredFields()) {
            if(f.getType().equals(ICommand.class)) {
                try {
                    ALL.add((ICommand) f.get(null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
