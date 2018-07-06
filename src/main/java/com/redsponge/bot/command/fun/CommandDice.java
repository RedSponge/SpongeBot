package com.redsponge.bot.command.fun;

import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandDice implements ICommand {

    private static final int rollCap = 5;


    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        Random r = new Random();
        int rolls = 1;
        int min = 1;
        int max = 6;
        if(args.length == 1 || args.length == 3) {
            try {
                rolls = Integer.parseInt(args[0]);
                if(rolls < 1) throw new Exception();
            } catch (Exception e) {
                event.getChannel().sendMessage("Cannot roll " + args[0] + " times!").queue();
            }
        }
        if(args.length == 2 || args.length == 3) {
            int addition = args.length == 2 ? 0 : 1;
            try {
                min = Integer.parseInt(args[0+addition]);
                max = Integer.parseInt(args[1+addition]);
            } catch (Exception e) {
                event.getChannel().sendMessage("Couldn't work with these numbers as min and max!").queue();
            }
        }


        if(rolls > rollCap) {
            event.getChannel().sendMessage("Cannot roll more than " + rollCap + " times").queue();
            return;
        }
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
        System.out.println("Rolls: " + rolls);
        System.out.println("Randomify: " + r.nextInt(max));
        for(int i = 0; i < rolls; i++) {
            event.getChannel().sendMessage("" + r.nextInt(max) + min).queue();
        }
    }

    @Override
    public String getName() {
        return "dice";
    }

    @Override
    public String getDescription() {
        return "Roll a dice!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"roll"};
    }

    @Override
    public String[] getUsages() {
        return new String[] {"", "[rolls]", "[min] [max]", "[rolls] [min] [max]"};
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }
}
