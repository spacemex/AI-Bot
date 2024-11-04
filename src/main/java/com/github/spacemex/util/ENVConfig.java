package com.github.spacemex.util;

import io.github.cdimascio.dotenv.Dotenv;

public class ENVConfig {

    private final Dotenv dotenv;

    public ENVConfig() {
        dotenv = Dotenv.configure().load();
    }

    public Dotenv Env() {
        return dotenv;}
}
