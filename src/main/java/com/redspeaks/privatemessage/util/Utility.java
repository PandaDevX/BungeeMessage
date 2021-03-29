package com.redspeaks.privatemessage.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public final class Utility {

    public static TextComponent colorize(String message) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static TextComponent parseMessage(ProxiedPlayer proxiedPlayer, String message, String context, ProxiedPlayer recipient) {
        message = message.replace("{player}", proxiedPlayer.getName());
        message = message.replace("{displayname}", proxiedPlayer.getDisplayName());
        message = message.replace("{message}", context);
        message = message.replace("{recipient}", recipient.getName());
        message = message.replace("{recipient_dn}", recipient.getDisplayName());
        return colorize(message);
    }
}
