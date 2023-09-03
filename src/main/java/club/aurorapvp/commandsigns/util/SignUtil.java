package club.aurorapvp.commandsigns.util;

import java.util.ArrayList;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;

public class SignUtil {

  public static void updateSignLine(Sign sign, int index, String text) {
    String parsedText = PlaceholderAPI.setPlaceholders(null, text);

    Component component = MiniMessage.miniMessage().deserialize(parsedText);

    sign.getSide(Side.FRONT).line(index, component);

    sign.update();
  }

  public static void updateSignLines(Sign sign, List<String> lines) {
    List<Component> components = new ArrayList<>();

    for (String line : lines) {
      String parsedText = PlaceholderAPI.setPlaceholders(null, line);

      components.add(MiniMessage.miniMessage().deserialize(parsedText));
    }

    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendSignChange(sign.getLocation(), components);
    }
  }
}
