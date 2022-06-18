package net.novorex.listener;



import net.novorex.Main;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
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
        event.setJoinMessage("§fServer §8➝ §7 [+] " + player.getName());
        String playerName = player.getName();



        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

        String teamName = config.getString("Team");

        if(player.hasPlayedBefore()) {
            //NORMAL JOIN
        } else {
            //FIRST JOIN
            World world = player.getWorld();
            world.setDifficulty(Difficulty.NORMAL);
            player.teleport(new Location(world, 430, 200, -600));
            player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
            Bukkit.dispatchCommand(console, "scoreboard teams add - ");
            Bukkit.dispatchCommand(console, "scoreboard teams join - " + playerName);
            player.sendTitle("CRAFTATTACK 1.19", "Willkommen " + playerName + "!", 20, 120, 20);
            Bukkit.getScheduler().runTaskLater(Main.instance, () ->Bukkit.dispatchCommand(console, "kit give starter " + playerName), 20L * 10);

        }





        //setTablistHeaderAndFooter(player, "§fMinecraft §bHEXXIT §fII", "§6by Novorex.net / §6§oSponsor: Nitrado.net");



        String prefix = String.format("%s", teamName);
        if (prefix == null) {
            prefix = "";
        } else prefix = prefix + " ";

        if(player.isOp()) {
            player.setPlayerListName("§r§l#" + prefix + "" + player.getDisplayName());
        } else {
            player.setPlayerListName("§r#" + prefix + "" + player.getDisplayName());
        }

        if (teamName.matches("-")) {
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.setPlayerListName("" + player.getDisplayName()), 20L * 3);
        }
            /*
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("3s");
                if(player.isOp()) {
                    player.setPlayerListName("§r§l" + prefix + " " + player.getDisplayName() + " " + suffix + "");
                } else {
                    player.setPlayerListName("§r" + prefix + " " + player.getDisplayName() + " " + suffix + "");
                }
            }
        }, 60L); //60 Tick = 3s
        */
    }
    /**
    public void setTablistHeaderAndFooter(Player player, String h, String f) {
        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + h + "\"}");
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + f + "\"}");

        modifyPrivateField(packetPlayOutPlayerListHeaderFooter, "a", header);
        modifyPrivateField(packetPlayOutPlayerListHeaderFooter, "b", footer);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerListHeaderFooter);
    }
**/
    private void modifyPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}