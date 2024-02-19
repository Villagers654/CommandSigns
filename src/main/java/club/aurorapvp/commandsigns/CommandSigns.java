package club.aurorapvp.commandsigns;

import club.aurorapvp.commandsigns.commands.CommandManager;
import club.aurorapvp.commandsigns.config.Lang;
import club.aurorapvp.commandsigns.events.EventManager;
import club.aurorapvp.commandsigns.modules.CommandSign;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandSigns extends JavaPlugin {

  private static CommandSigns INSTANCE;
  private Lang lang;

  public static CommandSigns getInstance() {
    return INSTANCE;
  }

  public Lang getLang() {
    return lang;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    INSTANCE = this;

    // Initialize lang file
    lang = new Lang();

    CommandManager.init();
    EventManager.init();
    CommandSign.init();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
