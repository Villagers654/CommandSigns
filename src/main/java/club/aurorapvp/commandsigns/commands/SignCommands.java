package club.aurorapvp.commandsigns.commands;

import club.aurorapvp.commandsigns.config.Lang;
import club.aurorapvp.commandsigns.modules.CommandSign;
import club.aurorapvp.commandsigns.util.SignUtil;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import java.util.Arrays;
import java.util.Objects;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

@CommandAlias("commandsigns|cs")
public class SignCommands extends BaseCommand {
  @Subcommand("create")
  @CommandPermission("commandsigns.admin")
  @Description("Creates a command sign")
  @SuppressWarnings("unused")
  public void onCreate(Player p, String name) {
    BlockState block = Objects.requireNonNull(p.getTargetBlockExact(5)).getState();

    if (!(block instanceof Sign)) {
      p.sendMessage(Lang.getComponent("not-a-sign"));
      return;
    }

    CommandSign sign = new CommandSign(name);

    sign.create(p);
    sign.edit(p);
  }

  // TODO
  @Subcommand("remove")
  @CommandPermission("commandsigns.admin")
  @Description("Removes a command sign")
  @SuppressWarnings("unused")
  public void onRemove(Player p) {
    BlockState block = Objects.requireNonNull(p.getTargetBlockExact(5)).getState();

    if (!(block instanceof Sign sign)) {
      p.sendMessage(Lang.getComponent("not-a-sign"));
      return;
    }

    Objects.requireNonNull(CommandSign.getSign(sign)).remove(p);
  }

  @Subcommand("edit")
  @CommandPermission("commandsigns.admin")
  @Description("Edits a command sign")
  @SuppressWarnings("unused")
  public void onEdit(Player p) {
    BlockState block = Objects.requireNonNull(p.getTargetBlockExact(5)).getState();

    if (!(block instanceof Sign sign)) {
      p.sendMessage(Lang.getComponent("not-a-sign"));
      return;
    }

    Objects.requireNonNull(CommandSign.getSign(sign)).edit(p);
  }

  @Subcommand("set")
  @CommandPermission("commandsigns.admin")
  @Description("Sets the text contents of a line of a sign")
  @SuppressWarnings("unused")
  public void onSet(Player p, String... text) {
    int index = Integer.parseInt(text[0]);

    String[] newText = Arrays.copyOfRange(text, 1, text.length);

    String concatenatedText = String.join(" ", newText);

    BlockState block = Objects.requireNonNull(p.getTargetBlockExact(5)).getState();

    if (!(block instanceof Sign sign)) {
      p.sendMessage(Lang.getComponent("not-a-sign"));
      return;
    }

    SignUtil.updateSignLine(sign, index - 1, concatenatedText);
  }
}
