package club.aurorapvp.commandsigns.util;

import club.aurorapvp.commandsigns.CommandSigns;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;

public class SignUtil {

  public static void updateSignLine(Sign sign, int index, String text) {
    String line = text;

    if (CommandSigns.isPlaceholderApiInstalled()) {
      line = PlaceholderAPI.setPlaceholders(null, text);
    }

    Component component = MiniMessage.miniMessage().deserialize(line);

    sign.getSide(Side.FRONT).line(index, component);

    sign.update();
  }

  public static void updateSignLines(Sign sign, List<String> lines) {
    for (int i = 0; i < 4; i++) {
      updateSignLine(sign, i, lines.get(i));
    }
  }
}
