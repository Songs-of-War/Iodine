package hu.trigary.iodine.bukkit;

import hu.trigary.iodine.api.IodineApi;
import hu.trigary.iodine.api.gui.GuiElements;
import hu.trigary.iodine.api.gui.IodineGui;
import hu.trigary.iodine.api.player.IodinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A class which exists solely for testing purposes.
 */
public class TestCommandListener implements Listener {
	private final IodineBukkitPlugin plugin;
	private IodineGui gui;
	
	public TestCommandListener(@NotNull IodineBukkitPlugin plugin) {
		this.plugin = plugin;
	}
	
	
	
	@EventHandler(ignoreCancelled = true)
	public void onCommand(@NotNull PlayerCommandPreprocessEvent event) {
		if (!event.getMessage().equals("/iodine")) {
			return;
		}
		
		event.setCancelled(true);
		IodinePlayer player = IodineApi.get().getPlayer(event.getPlayer().getUniqueId());
		if (!player.isModded()) {
			player.sendMessage("You are not modded!");
			return;
		}
		
		if (gui != null) {
			gui.openFor(player);
			return;
		}
		
		IodineApi.get().createGui()
				.addElement(GuiElements.CONTAINER_LINEAR, c -> {
					gui = (IodineGui) c.getRoot();
					c.setOrientation(true);
					
					gui.addElement(GuiElements.BUTTON, e -> c.makeChildLast(e)
							.setText("Sample button")
							.setTooltip("Sample tooltip")
							.setPaddingBottom(5));
					
					gui.addElement(GuiElements.CONTAINER_LINEAR, cc -> {
						c.makeChildLast(cc).setPaddingBottom(5);
						gui.addElement(GuiElements.TEXTURE, e -> cc.makeChildLast(e)
								.setPaddingRight(5)
								.setTooltip("Interpolate")
								.setInterpolating(true)
								.setWidth(50)
								.setHeight(50));
						gui.addElement(GuiElements.TEXTURE, e -> cc.makeChildLast(e)
								.setPaddingRight(5)
								.setTooltip("Repeat/cut")
								.setInterpolating(false)
								.setWidth(50)
								.setHeight(50));
					});
					
					gui.addElement(GuiElements.CONTAINER_LINEAR, cc -> {
						c.makeChildLast(cc).setPaddingBottom(5);
						gui.addElement(GuiElements.TEXTURE, e -> cc.makeChildLast(e)
								.setPaddingRight(5)
								.setTooltip("Interpolate")
								.setInterpolating(true)
								.setWidth(10)
								.setHeight(10));
						gui.addElement(GuiElements.TEXTURE, e -> cc.makeChildLast(e)
								.setPaddingRight(5)
								.setTooltip("Repeat/cut")
								.setInterpolating(false)
								.setWidth(10)
								.setHeight(10));
					});
					
					gui.addElement(GuiElements.BUTTON, e -> c.makeChildLast(e)
							.setText("Sample button")
							.setTooltip("Sample tooltip"));
				})
				.onClosed((g, p, byPlayer) -> p.sendMessage("You closed the GUI"))
				.openFor(player);
	}
}
