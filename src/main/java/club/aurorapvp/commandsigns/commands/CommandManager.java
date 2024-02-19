package club.aurorapvp.commandsigns.commands;

import club.aurorapvp.commandsigns.CommandSigns;
import co.aikar.commands.PaperCommandManager;

public class CommandManager {

  public static PaperCommandManager MANAGER = new PaperCommandManager(CommandSigns.getInstance());

  public static void init() {
    MANAGER.registerCommand(new SignCommands());
  }
}
