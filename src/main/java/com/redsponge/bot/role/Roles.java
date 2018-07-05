package com.redsponge.bot.role;

import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.Guild;

public class Roles {

    public static final RoleContainer MEMBER = new RoleContainer(Reference.ROLE_NORMAL);
    public static final RoleContainer MOD = new RoleContainer(Reference.ROLE_MOD);
    public static final RoleContainer DEV = new RoleContainer(Reference.ROLE_DEV);
    public static final RoleContainer ADMIN = new RoleContainer(Reference.ROLE_ADMIN);
    public static final RoleContainer[] ALL = {MEMBER, MOD, DEV, ADMIN};


    public static void addAllToServers(Guild g) {
        for(RoleContainer c : ALL) {
            c.createInServer(g);
        }
    }


    public static void removeAllFromServer(Guild guild) {
        for(RoleContainer c : ALL) {
            c.deleteFromServer(guild);
        }
    }
}
