package club.aurorapvp.commandsigns.util;

import club.aurorapvp.commandsigns.modules.CommandSign;
import java.util.List;
import java.util.Objects;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;

public class SignUtil {
  public static void updateSignLine(Sign sign, int index, String text) {
    Objects.requireNonNull(CommandSign.getSign(sign)).setLine(index, text);

    String parsedText = PlaceholderAPI.setPlaceholders(null, text);

    MiniMessage miniMessage = MiniMessage.miniMessage();
    Component component = miniMessage.deserialize(parsedText);

    sign.getSide(Side.FRONT).line(index - 1, component);

    sign.update();
  }

  public static void updateSignLines(Sign sign, List<String> lines) {
    for (int i = 0; i < lines.size(); i++) {
      String parsedText = PlaceholderAPI.setPlaceholders(null, lines.get(i));

      MiniMessage miniMessage = MiniMessage.miniMessage();
      Component component = miniMessage.deserialize(parsedText);

      sign.getSide(Side.FRONT).line(i, component);
    }
    sign.update();
  }
}
