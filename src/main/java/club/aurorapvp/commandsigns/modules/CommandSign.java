package club.aurorapvp.commandsigns.modules;

import club.aurorapvp.commandsigns.CommandSigns;
import club.aurorapvp.commandsigns.config.Lang;
import club.aurorapvp.commandsigns.data.SignDataHandler;
import club.aurorapvp.commandsigns.util.SignUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandSign {
  private static final Set<CommandSign> COMMAND_SIGNS = new HashSet<>();
  private static CommandSign unfinishedSign;
  private static Player signEditor;
  private static boolean signEdited;
  private final List<String> commands = new ArrayList<>();
  private Sign commandSign;
  private final SignDataHandler data;
  private final String name;

  public CommandSign(String name) {
    this.name = name;
    this.data = new SignDataHandler(this);

    COMMAND_SIGNS.add(this);

    if (this.exists()) {
      this.reload();
      this.updateLines();
    }
  }

  public static void init() {
    File file = new File(CommandSigns.INSTANCE.getDataFolder(), "/signs.yml");
    YamlConfiguration framesData = YamlConfiguration.loadConfiguration(file);
    ConfigurationSection section = framesData.getConfigurationSection("signs");

    if (section == null) {
      return;
    }

    for (String name : section.getKeys(false)) {
      new CommandSign(name);
    }

    new BukkitRunnable() {
      @Override
      public void run() {
        for (CommandSign sign : COMMAND_SIGNS) {
          sign.updateLines();
        }
      }
    }.runTaskTimer(CommandSigns.INSTANCE, 0L, 20L);

    CommandSigns.INSTANCE.getLogger().info("All signs loaded!");
  }

  public boolean exists() {
    return data.exists() && data.getLocation().getBlock().getType().name().endsWith("_SIGN");
  }

  public String getName() {
    return name;
  }

  public Location getLocation() {
    return commandSign.getLocation();
  }

  public Sign getSign() {
    return commandSign;
  }

  public List<String> getCommands() {
    return commands;
  }

  public void reload() {
    BlockState block = data.getLocation().getBlock().getState();

    if (block instanceof Sign sign) {
      this.commandSign = sign;
    } else {
      throw new IllegalStateException("CommandSign must be a sign");
    }

    this.commands.addAll(data.getCommands());
  }

  public void create(Player p) {
    this.commandSign = (Sign) Objects.requireNonNull(p.getTargetBlockExact(5)).getState();
    this.data.create();
  }

  public void remove(Player p) {
    COMMAND_SIGNS.remove(this);

    this.data.delete();

    p.sendMessage(Lang.getComponent("removed"));
  }

  public void edit(Player p) {
    unfinishedSign = this;
    signEdited = true;
    signEditor = p;

    p.sendMessage(Lang.getComponent("add-command"));
  }

  public void updateLines() {
    List<String> lines = data.getLines();

    SignUtil.updateSignLines(commandSign, lines);
  }

  public void createCommandList(Player p, String str) {
    if (str.equals("stop")) {
      unfinishedSign = null;
      signEdited = false;
      signEditor = null;

      data.setCommands(commands);
      data.save();

      p.sendMessage(Lang.getComponent("commands-saved"));
    } else {
      commands.add(str);
      p.sendMessage(Lang.getComponent("command-added"));
    }
  }

  public static CommandSign getUnfinishedSign() {
    return unfinishedSign;
  }

  public static boolean isEditor(Player p) {
    return p == signEditor;
  }

  public static boolean isSignBeingEdited() {
    return signEdited;
  }

  public static boolean isSign(BlockState block) {
    if (!(block instanceof Sign sign)) {
      return false;
    }

    for (CommandSign commandSign : COMMAND_SIGNS) {
      if (commandSign.getSign().equals(sign)) {
        return true;
      }
    }
    return false;
  }

  public static CommandSign getSign(Sign sign) {
    for (CommandSign commandSign : COMMAND_SIGNS) {
      if (commandSign.getSign().equals(sign)) {
        return commandSign;
      }
    }
    return null;
  }

  public static CommandSign getSign(BlockState block) {
    if (!(block instanceof Sign sign)) {
      return null;
    }

    for (CommandSign commandSign : COMMAND_SIGNS) {
      if (commandSign.getSign().equals(sign)) {
        return commandSign;
      }
    }
    return null;
  }
}