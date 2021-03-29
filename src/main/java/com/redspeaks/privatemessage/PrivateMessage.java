package com.redspeaks.privatemessage;

import com.google.common.io.ByteStreams;
import com.redspeaks.privatemessage.commands.CommandReply;
import com.redspeaks.privatemessage.commands.CommandWhisper;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

public final class PrivateMessage extends Plugin {

    private File file;
    private Configuration config;
    public HashMap<String, String> data = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Has loaded");

        // setup config
        saveDefaultConfig();

        // register commands
        new CommandWhisper(this);
        new CommandReply(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Has disabled");
    }

    public void saveDefaultConfig() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        file = new File(getDataFolder(), "config.yml");
        try {
            if(!file.exists()) {
                file.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                }
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);


        }catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not load config.yml");
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
