package net.novorex.listener;



import net.novorex.Main;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        Player player = event.getPlayer();
        String playerName = player.getName();
        event.setJoinMessage("§fServer §8➝ §7 [+] " + player.getName());

        if(player.isOp()) {
            player.setPlayerListName("§r§l" + player.getDisplayName());
        } else {
            player.setPlayerListName("§r" + player.getDisplayName());
        }

        if (player.hasPlayedBefore()) {
            //NORMAL JOIN
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "team add -"), 10L * 10);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "team join - " + playerName), 20L * 10);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "team modify - nametagVisibility hideForOwnTeam"), 30L * 10);
        } else {
            //FIRST JOIN
            World world = player.getWorld();
            world.setDifficulty(Difficulty.NORMAL);
            player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
            player.teleport(new Location(world, 450, 200, -600));
            Bukkit.dispatchCommand(console, "team join - " + playerName);
            player.sendTitle("CRAFTATTACK 1.19", "Willkommen " + playerName + "!", 20, 120, 20);

        }


    }
}