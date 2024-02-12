package club.aurorapvp.commandsigns.data;

import club.aurorapvp.commandsigns.CommandSigns;
import club.aurorapvp.commandsigns.modules.CommandSign;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class SignDataHandler {
  private static final File FILE = new File(CommandSigns.INSTANCE.getDataFolder(), "/signs.yml");
  private static final YamlConfiguration
      signsData = YamlConfiguration.loadConfiguration(FILE);
  private final CommandSign sign;

  public SignDataHandler(CommandSign sign) {
    this.sign = sign;
  }

  public boolean exists() {
    return get().contains("signs." + sign.getName());
  }

  public Location getLocation() {
    return get().getLocation("signs." + sign.getName() + ".location");
  }

  public List<String> getCommands() {
    return get().getStringList("signs." + sign.getName() + ".commands");
  }

  public void setCommands(List<String> commands) {
    get().set("signs." + sign.getName() + ".commands", commands);
    save();
  }

  public void setLine(int index, String text) {
    get().set("signs." + sign.getName() + ".line." + index, text);
    save();
  }

  public List<String> getLines() {
    List<String> lines = new ArrayList<>();

    ConfigurationSection section =
        signsData.getConfigurationSection("signs." + sign.getName() + ".line.");

    assert section != null;
    for (int i = 0; i <= 3; i++) {
      lines.add(section.getString(String.valueOf(i)));
    }

    return lines;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void save() {
    try {
      if (!FILE.exists()) {
        FILE.getParentFile().mkdirs();
        FILE.createNewFile();
      }
      get().save(FILE);
    } catch (IOException e) {
      CommandSigns.INSTANCE.getLogger().severe("Failed to save sign data");
    }
  }

  public void delete() {
    get().set("signs." + sign.getName(), null);
    save();
  }

  public void create() {
    get().set("signs." + sign.getName() + ".location", sign.getLocation());

    for (int i = 0; i <= 3; i++) {
      get().set("signs." + sign.getName() + ".line." + i, "");
    }

    save();
  }

  public static YamlConfiguration get() {
    return signsData;
  }
}
