package com.redsponge.bot.command;

import com.redsponge.bot.command.chat.CommandDeleteMessages;
import com.redsponge.bot.command.dev.CommandExecute;
import com.redsponge.bot.command.fun.CommandFace;
import com.redsponge.bot.command.fun.CommandQuestion;
import com.redsponge.bot.command.fun.CommandSay;
import com.redsponge.bot.command.fun.CommandDice;
import com.redsponge.bot.command.general.CommandHelp;
import com.redsponge.bot.command.general.CommandHelp.CommandH_elp_;
import com.redsponge.bot.command.general.CommandServers;
import com.redsponge.bot.command.image.CommandAvatar;
import com.redsponge.bot.command.music.CommandPlay;
import com.redsponge.bot.command.utility.CommandId;
import com.redsponge.bot.command.utility.CommandInvite;
import com.redsponge.bot.command.utility.CommandPing;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Commands {

    public static final ICommand COMMAND_DELETE_MESSAGES = new CommandDeleteMessages();

    public static final ICommand COMMAND_QUESTION = new CommandQuestion();
    public static final ICommand COMMAND_SAY = new CommandSay();

    public static final ICommand COMMAND_HELP = new CommandHelp();
    public static final ICommand COMMAND_HELP_TROLL = new CommandH_elp_();
    public static final ICommand COMMAND_SERVERS = new CommandServers();

    public static final ICommand COMMAND_ID = new CommandId();
    public static final ICommand COMMAND_PING = new CommandPing();
    public static final ICommand COMMAND_INVITE = new CommandInvite();
    
    public static final ICommand COMMAND_DICE = new CommandDice();

    public static final ICommand COMMAND_FACE = new CommandFace();

    public static final ICommand COMMAND_AVATAR = new CommandAvatar();

    public static final ICommand COMMAND_EXECUTE = new CommandExecute();

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
