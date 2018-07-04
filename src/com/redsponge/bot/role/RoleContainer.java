package com.redsponge.bot.role;

import com.redsponge.bot.util.Reference;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.GuildManager;

import java.util.function.Consumer;

public class RoleContainer {

    private String roleName;

    public RoleContainer(String roleName) {
        this.roleName = roleName;
    }

    public void addToUser(Member member, Guild server) {
        GuildController controller = new GuildController(server);
        controller.addRolesToMember(member, server.getRolesByName(roleName, true)).queue();
    }

    public void removeFromUser(Member member, Guild server) {
        GuildController controller = new GuildController(server);
        controller.removeRolesFromMember(member, server.getRolesByName(roleName, true)).queue();;
    }

    public void createInServer(Guild server) {
        if(server.getRolesByName(roleName, true).size() > 0) return;
        GuildController controller = new GuildController(server);
        controller.createRole().setName(roleName).setColor(Reference.BOT_COLOR).setMentionable(false).queue();
    }

    public void deleteFromServer(Guild server) {
        if(server.getRolesByName(roleName, true).size() == 0) return;
        for(Role r : server.getRolesByName(roleName, true)) {
            r.delete().queue();
        }
    }

    public Role get(Guild server) {
        return server.getRolesByName(roleName, true).get(0);
    }

}
