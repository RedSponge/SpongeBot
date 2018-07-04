package com.redsponge.bot.command;

public class UnknownCommandException extends RuntimeException {

    public UnknownCommandException() {
        super("Unknown Command/Category");
    }
}
