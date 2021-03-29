package com.redspeaks.privatemessage;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.*;
import java.util.HashMap;

public final class PrivateMessage extends Plugin {

    private File file;
    private Configuration config;
    public HashMap<String, String> data = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Has loaded");

        // setup config
        saveDefaultConfig();
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
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
