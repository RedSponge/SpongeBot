package com.redsponge.bot.command.general;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.UnknownCommandException;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.Random;
import java.util.StringJoiner;

public class CommandHelp implements ICommand {

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        String[] argsLower = new String[args.length];

        EmbedBuilder builder = new EmbedBuilder();
        Random r = new Random();
        builder.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
        boolean def = false;
            try {
                if(SpongeBot.instance.commandManager.getCommandsSorted().get(args[0]) != null) {
                    buildSpecificCommandHelp(builder, SpongeBot.instance.commandManager.getCommandsSorted().get(args[0]));
                } else {
                    CommandCategory c = CommandCategory.valueOfTitle(args[0].toLowerCase());
                    buildCommandCategoryHelp(builder, c);
                }
            } catch (Exception e) {
                if(e instanceof UnknownCommandException) {
                    event.getChannel().sendMessage(e.getMessage()).queue();
                }
                buildCommandHelp(builder);
            }
        builder.setFooter("SpongeBot by RedSponge!", null);
        event.getMessage().getChannel().sendMessage(builder.build()).queue();
    }

    private void buildCommandHelp(EmbedBuilder builder) {
        builder.setTitle("__\"help [category]\" or \"help [command]\" for more info__");
        for(CommandCategory c : CommandCategory.values()) {
            if(!c.displayInHelp()) continue;
            StringJoiner joiner = new StringJoiner(", ");

            for(ICommand command : SpongeBot.instance.commandManager.getCommandCategories().get(c)) {
                if(command.showInHelp())
                    joiner.add("`" + command.getName() + "`");
            }

            builder.addField(c.getDisplay(), joiner.toString(), true);
        }
    }

    private void buildCommandCategoryHelp(EmbedBuilder builder, CommandCategory category) {
        if(!category.displayInHelp()) throw new UnknownCommandException();
        builder.setTitle("Help For Command Category \"" + category.getTitle() + "\"");
        StringBuilder sb = new StringBuilder();
        for(ICommand c : SpongeBot.instance.commandManager.getCommandCategories().get(category)) {
            if(c.showInHelp())
                sb.append("**").append(c.getName()).append("** - ").append(c.getDescription()).append("\n");
        }
        builder.addField("", sb.toString(), false);
    }

    private void buildSpecificCommandHelp(EmbedBuilder builder, ICommand command) {
        if(!command.showInHelp()) {
            throw new UnknownCommandException();
        }
        builder.setTitle("Help For \"" + command.getName() + "\"");

        builder.addField("Name:", command.getName(), false);
        builder.addField("Description: ", command.getDescription(), false);
        builder.addField("Usage:", "`" + Reference.prefix + String.join(" || " + Reference.prefix,command.usages()) + "`", false);
        builder.addField("Aliases: ", String.join(", ", command.getUsages()), false);

    }

    @Override
    public String[] getAliases() {
        return new String[] {"h"};
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String[] usages() {
        return new String[] {getName(), "help [getCategory]", "help [command]"};
    }

    @Override
    public String getDescription() {
        return "Displays info about stuff!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GENERAL;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    public static class CommandH_elp_ implements ICommand{

        @Override
        public void execute(String[] args, MessageReceivedEvent event) {
            event.getMessage().delete().queue();
            event.getAuthor().openPrivateChannel().queue((channel -> {
                channel.sendMessage("You should use " + Reference.prefix + "h or " + Reference.prefix + "help... not " + Reference.prefix + "h(elp)").queue();
            }));
        }

        @Override
        public String getName() {
            return "h(elp)";
        }

        @Override
        public boolean showInHelp() {
            return false;
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public CommandCategory getCategory() {
            return CommandCategory.GENERAL;
        }

        @Override
        public Permission getPermission() {
            return Permission.USER;
        }
    }
}
