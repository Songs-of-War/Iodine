package hu.trigary.iodine.bukkit.network.handler;

import hu.trigary.iodine.api.PlayerState;
import hu.trigary.iodine.bukkit.IodinePlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class PacketHandler {
	protected final IodinePlugin plugin;
	
	protected PacketHandler(@NotNull IodinePlugin plugin) {
		this.plugin = plugin;
	}
	
	
	
	@NotNull
	@Contract(pure = true)
	public PlayerState getTargetState() {
		return PlayerState.MODDED;
	}
	
	public abstract void handle(@NotNull Player player, @NotNull byte[] message);
}
