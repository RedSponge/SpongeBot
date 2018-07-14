package com.redsponge.bot.util;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.general.CommandHelp;
import net.dv8tion.jda.core.OnlineStatus;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Reference {

    public static final String prefix = ">";

    public static final String TOKEN = System.getenv("TOKEN");

    public static final List<String> whitelisted = Arrays.asList("263995600641589248", "244111430956089344", "338761258075422721", "199158557705371648");

    public static final Color BOT_COLOR = new Color(0xFFA500);

    public static final String ROLE_NORMAL = "spongebot_member";
    public static final String ROLE_MOD = "spongebot_mod";
    public static final String ROLE_ADMIN = "spongebot_admin";
    public static final String ROLE_DEV = "spongebot_dev";

    public static final String PLAYING = prefix + "h(elp)";

    public static final List<OnlineStatus> CAN_BE_INVITED_TO_GAMES = Arrays.asList(OnlineStatus.ONLINE, OnlineStatus.DO_NOT_DISTURB);
}
