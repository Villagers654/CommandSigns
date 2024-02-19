package club.aurorapvp.commandsigns.config;

import club.aurorapvp.commandsigns.CommandSigns;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

public class Lang {

  private static final HashMap<String, String> PLACEHOLDERS = new HashMap<>();
  private static final File FILE = new File(CommandSigns.getInstance().getDataFolder(), "lang.yml");
  private static YamlConfiguration lang;

  public Lang() {
    this.reload();
    this.generateDefaults();
  }

  public void generateDefaults() {
    final HashMap<String, String> DEFAULTS = new HashMap<>();

    for (Object path : getYaml().getKeys(false).toArray()) {
      if (Objects.requireNonNull(getYaml().getString((String) path)).startsWith("~")
          && Objects.requireNonNull(getYaml().getString((String) path)).endsWith("~")) {
        PLACEHOLDERS.put(
            (String) path,
            Objects.requireNonNull(getYaml().getString((String) path)).replace("~", ""));
      }
    }

    DEFAULTS.put("prefix", "~<gradient:#FFAA00:#FF55FF><bold>CommandSigns ><reset>~");
    DEFAULTS.put("created", "prefix <gradient:#FFAA00:#FF55FF>Command sign created!");
    DEFAULTS.put("removed", "prefix <gradient:#FFAA00:#FF55FF>Command sign removed!");
    DEFAULTS.put("not-a-sign", "prefix <gradient:#FFAA00:#FF55FF>That block is not a sign!");
    DEFAULTS.put("command-added", "prefix <gradient:#FFAA00:#FF55FF>Command added!");
    DEFAULTS.put("commands-saved", "prefix <gradient:#FFAA00:#FF55FF>All commands saved!");
    DEFAULTS.put("add-command",
        "prefix <gradient:#FFAA00:#FF55FF>Type the commands you want to add to the sign in chat. Exclude the '/'");

    for (String path : DEFAULTS.keySet()) {
      if (!getYaml().contains(path) || getYaml().getString(path) == null) {
        getYaml().set(path, DEFAULTS.get(path));
      }
    }

    try {
      getYaml().save(FILE);
    } catch (IOException e) {
      CommandSigns.getInstance().getLogger().log(Level.SEVERE, "Failed to save lang file", e);
    }

    for (Object path : getYaml().getKeys(false).toArray()) {
      if (Objects.requireNonNull(getYaml().getString((String) path)).startsWith("~")
          && Objects.requireNonNull(getYaml().getString((String) path)).endsWith("~")) {
        PLACEHOLDERS.put(
            (String) path,
            Objects.requireNonNull(getYaml().getString((String) path)).replace("~", ""));
      }
    }
  }

  public Component getComponent(String message) {
    String pathString = getYaml().getString(message);
    assert pathString != null;

    for (String placeholder : PLACEHOLDERS.keySet()) {
      if (pathString.contains(placeholder)) {
        pathString = pathString.replace(placeholder, PLACEHOLDERS.get(placeholder));
      }
    }
    return MiniMessage.miniMessage().deserialize(pathString);
  }

  public YamlConfiguration getYaml() {
    return lang;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void reload() {
    if (!FILE.exists()) {
      try {
        FILE.getParentFile().mkdirs();
        FILE.createNewFile();

        lang = YamlConfiguration.loadConfiguration(FILE);

        this.generateDefaults();
      } catch (IOException e) {
        CommandSigns.getInstance().getLogger().log(Level.SEVERE, "Failed to generate lang file", e);
      }
    }
    lang = YamlConfiguration.loadConfiguration(FILE);
    CommandSigns.getInstance().getLogger().info("Lang reloaded!");
  }
}
