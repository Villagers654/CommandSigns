package club.aurorapvp.commandsigns.events.listeners;

import club.aurorapvp.commandsigns.modules.CommandSign;
import io.papermc.paper.event.player.AsyncChatEvent;
import java.util.Objects;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvents implements Listener {
  @EventHandler
  public void onPlayerChat(AsyncChatEvent event) {
    if (CommandSign.isEditor(event.getPlayer()) && CommandSign.isSignBeingEdited()) {
      CommandSign.getUnfinishedSign().createCommandList(event.getPlayer(),
          PlainTextComponentSerializer.plainText().serialize(event.message()));

      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (CommandSign.isSign(Objects.requireNonNull(event.getClickedBlock()).getState())) {
      for (String str : Objects.requireNonNull(
              CommandSign.getSign(event.getClickedBlock().getState()))
          .getCommands()) {
        event.getPlayer().performCommand(str);

        event.setCancelled(true);
      }
    }
  }
}