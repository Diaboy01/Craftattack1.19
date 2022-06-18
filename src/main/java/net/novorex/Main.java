package net.novorex;

//import net.novorex.command.*;
import net.novorex.listener.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin {

    public static Main instance;


    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;


        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerMotdListener(), this);
        pluginManager.registerEvents(new ExplosionListener(), this);
        pluginManager.registerEvents(new PlayerLeaveListener(), this);

        //getCommand("role").setExecutor(new Role());
        //getCommand("wtp").setExecutor(new WorldTeleport());



        /*
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                }
            }
        }, 20*5, 20);
        */
        /*
        WorldCreator worldCreator = new WorldCreator(GAME_WORLD_NAME);
        worldCreator.generateStructures(false);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generator(new EmptyChunkGenerator());
        World world = worldCreator.createWorld();
        new Location(world, 0, 64, 0).getBlock().setType(Material.GRASS);
        world.setSpawnLocation(0, 66, 0);
        */

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player, (String) null);
            Bukkit.getPluginManager().callEvent(playerJoinEvent);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}