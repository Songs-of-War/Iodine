package hu.trigary.iodine.bukkit.api;

import hu.trigary.iodine.api.IodineApi;
import hu.trigary.iodine.api.PlayerState;
import hu.trigary.iodine.bukkit.IodinePlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class IodineApiImpl extends IodineApi {
	private final IodinePlugin plugin;
	
	public IodineApiImpl(@NotNull IodinePlugin plugin) {
		this.plugin = plugin;
	}
	
	
	
	@NotNull
	@Contract(pure = true)
	@Override
	public PlayerState getState(@NotNull Player player) {
		return plugin.getPlayer().getState(player);
	}
}
