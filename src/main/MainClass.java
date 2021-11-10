package main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        getCommand("mini").setExecutor(new CommandClass());
        Bukkit.getPluginManager().registerEvents(new EventManager(), this);
    }
}
