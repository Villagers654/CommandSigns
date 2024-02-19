package club.aurorapvp.commandsigns.commands;

import club.aurorapvp.commandsigns.CommandSigns;
import club.aurorapvp.commandsigns.modules.CommandSign;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;

@CommandAlias("commandsigns|cs")
public class ReloadCommand extends BaseCommand {

  @Subcommand("reload")
  @CommandPermission("commandsigns.admin")
  @Description("Reloads all plugin data")
  @SuppressWarnings("unused")
  public void onReload() {
    long startTime = System.currentTimeMillis();

    CommandSign.reloadAll();
    CommandSigns.getInstance().getLang().reload();

    CommandSigns.getInstance()
        .getLogger()
        .info("CommandSigns reloaded in " + (System.currentTimeMillis() - startTime) + "ms");
  }
}
