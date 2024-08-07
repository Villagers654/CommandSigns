package club.aurorapvp.commandsigns;

import club.aurorapvp.commandsigns.commands.CommandManager;
import club.aurorapvp.commandsigns.config.Lang;
import club.aurorapvp.commandsigns.events.EventManager;
import club.aurorapvp.commandsigns.modules.CommandSign;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandSigns extends JavaPlugin {

  private static CommandSigns INSTANCE;
  private Lang lang;
  private static boolean placeholderApiInstalled;

  public static CommandSigns getInstance() {
    return INSTANCE;
  }

  public Lang getLang() {
    return lang;
  }

  public static boolean isPlaceholderApiInstalled() {
    return placeholderApiInstalled;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    INSTANCE = this;

    // Initialize lang file
    lang = new Lang();

    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
      placeholderApiInstalled = true;
    }

    CommandManager.init();
    EventManager.init();
    CommandSign.init();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
