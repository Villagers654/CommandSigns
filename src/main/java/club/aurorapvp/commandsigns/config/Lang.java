package club.aurorapvp.commandsigns.config;

import club.aurorapvp.commandsigns.CommandSigns;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

public class Lang {
  private static final HashMap<String, String> PLACEHOLDERS = new HashMap<>();
  private static final File FILE = new File(CommandSigns.INSTANCE.getDataFolder(), "lang.yml");
  private static YamlConfiguration lang;

  public static void init() {
    reload();
    generateDefaults();
  }

  public static void generateDefaults() {
    final HashMap<String, String> DEFAULTS = new HashMap<>();

    for (Object path : get().getKeys(false).toArray()) {
      if (Objects.requireNonNull(get().getString((String) path)).startsWith("~") &&
          Objects.requireNonNull(get().getString((String) path)).endsWith("~")) {
        PLACEHOLDERS.put((String) path, Objects.requireNonNull(get().getString((String) path))
            .replace("~", ""));
      }
    }

    DEFAULTS.put("prefix", "~<gradient:#FFAA00:#FF55FF><bold>CommandSigns ><reset>~");
    DEFAULTS.put("created", "prefix <gradient:#FFAA00:#FF55FF>Command sign created!");
    DEFAULTS.put("removed", "prefix <gradient:#FFAA00:#FF55FF>Command sign removed!");
    DEFAULTS.put("not-a-sign", "prefix <gradient:#FFAA00:#FF55FF>That block is not a sign!");
    DEFAULTS.put("command-added", "prefix <gradient:#FFAA00:#FF55FF>Command added!");
    DEFAULTS.put("commands-saved", "prefix <gradient:#FFAA00:#FF55FF>All commands saved!");
    DEFAULTS.put("add-command", "prefix <gradient:#FFAA00:#FF55FF>Type the commands you want to add to the sign in chat. Exclude the '/'");

    for (String path : DEFAULTS.keySet()) {
    if (!get().contains(path) || get().getString(path) == null) {
      get().set(path, DEFAULTS.get(path));
    }
  }

    try {
    get().save(FILE);
  } catch (
  IOException e) {
    CommandSigns.INSTANCE.getLogger().severe("Failed to save lang file");
  }

    for (Object path : get().getKeys(false).toArray()) {
    if (Objects.requireNonNull(get().getString((String) path)).startsWith("~") &&
        Objects.requireNonNull(get().getString((String) path)).endsWith("~")) {
      PLACEHOLDERS.put((String) path, Objects.requireNonNull(get().getString((String) path))
          .replace("~", ""));
    }
  }
}

  public static String getString(String message) {
    String pathString = get().getString(message);
    for (String placeholder : PLACEHOLDERS.keySet()) {
      assert pathString != null;
      if (pathString.contains(placeholder)) {
        pathString = pathString.replace(placeholder,
            PLACEHOLDERS.get(placeholder));
      }
    }
    return pathString;
  }

  public static Component formatComponent(String message, Object... args) {
    String pathString = get().getString(message);
    assert pathString != null;
    for (String placeholder : PLACEHOLDERS.keySet()) {
      if (pathString.contains(placeholder)) {
        pathString = pathString.replace(placeholder,
            PLACEHOLDERS.get(placeholder));
      }
    }

    pathString = String.format(pathString, args);

    return MiniMessage.miniMessage().deserialize(pathString);
  }

  public static Component getComponent(String message) {
    String pathString = get().getString(message);
    assert pathString != null;

    for (String placeholder : PLACEHOLDERS.keySet()) {
      if (pathString.contains(placeholder)) {
        pathString = pathString.replace(placeholder,
            PLACEHOLDERS.get(placeholder));
      }
    }
    return MiniMessage.miniMessage().deserialize(pathString);
  }

  public static YamlConfiguration get() {
    return lang;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void reload() {
    if (!FILE.exists()) {
      try {
        FILE.getParentFile().mkdirs();
        FILE.createNewFile();
      } catch (IOException e) {
        CommandSigns.INSTANCE.getLogger().severe("Failed to generate lang file");
      }
    }
    lang = YamlConfiguration.loadConfiguration(FILE);
    CommandSigns.INSTANCE.getLogger().info("Lang reloaded!");
  }
}