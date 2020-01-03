package hu.trigary.iodine.bukkit;

import hu.trigary.iodine.api.IodineApi;
import hu.trigary.iodine.api.gui.GuiElements;
import hu.trigary.iodine.api.gui.IodineColor;
import hu.trigary.iodine.api.gui.container.base.GuiBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Year;

/**
 * A class which exists solely for testing purposes.
 */
public class TestCommandListener implements Listener {
	private final IodineApi api = IodineApi.getInstance();
	private final IodinePlugin plugin;
	
	public TestCommandListener(@NotNull IodinePlugin plugin) {
		this.plugin = plugin;
	}
	
	
	
	@EventHandler(ignoreCancelled = true)
	public void onCommand(@NotNull PlayerCommandPreprocessEvent event) {
		if (!event.getMessage().equals("/iodine")) {
			return;
		}
		
		event.setCancelled(true);
		Player player = event.getPlayer();
		if (!api.isModded(player)) {
			player.sendMessage("You are not modded!");
			return;
		}
		
		api.createGui().addElement(GuiElements.CONTAINER_LINEAR, c -> {
			c.setOrientation(true);
			GuiBase<?> gui = c.getGui();
			
			gui.addElement(GuiElements.BUTTON, e -> c.makeChildLast(e)
					.setText("First"));
			
			gui.addElement(GuiElements.CONTAINER_GRID, grid -> {
				c.makeChildLast(grid);
				grid.setPaddingBottom(50);
				grid.setGridSize(3, 3);
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y++) {
						int finalX = x;
						int finalY = y;
						gui.addElement(GuiElements.RECTANGLE, e -> grid.makeChild(e, finalX, finalY)
								.setColor((finalX + finalY) % 2 == 0 ? IodineColor.BLACK : IodineColor.DARK_RED));
					}
				}
			});
			
			gui.addElement(GuiElements.BUTTON, e -> c.makeChildLast(e)
					.setText("Last"));
		})
				.addElement(GuiElements.BUTTON, 5, 30, e -> e.setText("P-1").setDrawPriority(-1).setWidth(0))
				.addElement(GuiElements.BUTTON, 10, 25, e -> e.setText("P+1").setDrawPriority(1).setWidth(0))
				.onClosed((gui, p) -> p.sendMessage("You closed the GUI"))
				.openFor(player);
	}
}
