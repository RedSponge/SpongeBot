package com.redsponge.bot.command;

import com.redsponge.bot.role.Roles;
import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public interface Permission {

    public static final Permission GOD = new Permission() {
        @Override
        public int getPermissionStrength() {
            return 5;
        }
    };

    public static final Permission DEV = new Permission() {
        @Override
        public int getPermissionStrength() {
            return 4;
        }
    };

    public static final Permission ADMIN = new Permission() {
        @Override
        public int getPermissionStrength() {
            return 3;
        }
    };

    public static final Permission MOD = new Permission() {
        @Override
        public int getPermissionStrength() {
            return 2;
        }
    };

    public static final Permission USER = new Permission() {
        @Override
        public int getPermissionStrength() {
            return 1;
        }
    };

    int getPermissionStrength();

    default boolean hasPermission(Member member, Guild server) {
        return getPermissionLevel(member, server) >= getPermissionStrength();
    }

    static int getPermissionLevel(Member m, Guild server) {
        if(Reference.whitelisted.contains(m.getUser().getId())) return 5;
        if(m.getRoles().contains(Roles.DEV.get(server))) return 4;
        if(m.getRoles().contains(Roles.ADMIN.get(server))) return 3;
        if(m.getRoles().contains(Roles.MOD.get(server))) return 2;
        return 1;
    }

}
