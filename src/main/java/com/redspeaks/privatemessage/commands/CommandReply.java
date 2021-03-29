package com.redspeaks.privatemessage.commands;

import com.redspeaks.privatemessage.PrivateMessage;
import com.redspeaks.privatemessage.util.Utility;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandReply extends Command {

    private PrivateMessage plugin;

    public CommandReply(PrivateMessage plugin) {
        super("reply", "pm.reply", "r");

        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) {
            sender.sendMessage(Utility.colorize("&c&lYou &fhave no permission to do that"));
            return;
        }

        if(args.length <= 1) {
            sender.sendMessage(Utility.colorize("&c&lUsage &f/whisper" +  "<player> <message>"));
            return;
        }

        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Utility.colorize("&c&lYou &fmust be a player to do that"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(!plugin.data.containsKey(player.getUniqueId().toString())) {
            sender.sendMessage(Utility.colorize("&c&lCannot &ffind that player"));
            return;
        }
        ProxiedPlayer target = plugin.getProxy().getPlayer(plugin.data.get(player.getUniqueId().toString()));
        if(target == null || !target.isConnected()) {
            sender.sendMessage(Utility.colorize("&c&lCannot &ffind that player"));
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }

        String message = builder.toString().trim();
        String sender_message = plugin.getConfig().getString("Sender");
        String recipient_message = plugin.getConfig().getString("Recipient");
        sender.sendMessage(Utility.parseMessage(player, sender_message, message, target));
        target.sendMessage(Utility.parseMessage(player, recipient_message, message, target));
        plugin.data.put(target.getUniqueId().toString(), player.getUniqueId().toString());
    }
}
