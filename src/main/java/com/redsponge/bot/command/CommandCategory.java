package com.redsponge.bot.command;

public enum CommandCategory {

    GENERAL("General", ":popcorn:"),
    UTILITY("Utility", ":gear:"),
    FUN("Fun", ":smile:"),
    DEV("Dev", "", false),
    MUSIC("Music", ":musical_note:"),
    CHAT("Chat", ":speech_left:");

    private String title;
    private boolean displayInHelp;
    private String icon;

    CommandCategory(String title, String icon) {
        this(title, icon, true);
    }
    CommandCategory(String title, String icon, boolean displayInHelp) {
        this.title = title;
        this.icon = icon;
        this.displayInHelp = displayInHelp;
    }

    public String getTitle() {
        return title;
    }

    public boolean displayInHelp() {
        return displayInHelp;
    }

    public String getIcon() {
        return icon;
    }

    public String getDisplay() {
        return getIcon() + " " + getTitle();
    }

    public static CommandCategory valueOfTitle(String s){
        for(CommandCategory c : CommandCategory.values()) {
            if(s.equals(c.title.toLowerCase())) {
                return c;
            }
        }
        throw new UnknownCommandException();
    }
}
