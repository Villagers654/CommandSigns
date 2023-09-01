package club.aurorapvp.commandsigns;

import club.aurorapvp.commandsigns.commands.CommandManager;
import club.aurorapvp.commandsigns.config.Lang;
import club.aurorapvp.commandsigns.events.EventManager;
import club.aurorapvp.commandsigns.modules.CommandSign;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandSigns extends JavaPlugin {

  public static CommandSigns INSTANCE;
  public static File DATA_FOLDER;

  @Override
  public void onEnable() {
    // Plugin startup logic
    INSTANCE = this;
    DATA_FOLDER = this.getDataFolder();

    CommandManager.init();
    Lang.init();
    EventManager.init();
    CommandSign.init();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
