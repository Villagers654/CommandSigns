package club.aurorapvp.commandsigns.events;

import club.aurorapvp.commandsigns.CommandSigns;
import club.aurorapvp.commandsigns.events.listeners.SignEvents;
import org.bukkit.Bukkit;

public class EventManager {

  public static void init() {
    Bukkit.getPluginManager().registerEvents(new SignEvents(), CommandSigns.getInstance());
  }
}
