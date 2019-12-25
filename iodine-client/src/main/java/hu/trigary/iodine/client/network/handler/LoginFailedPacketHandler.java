package hu.trigary.iodine.client.network.handler;

import hu.trigary.iodine.client.IodineModBase;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.logging.Level;

public class LoginFailedPacketHandler extends PacketHandler {
	public LoginFailedPacketHandler(@NotNull IodineModBase mod) {
		super(mod);
	}
	
	@Override
	public void handle(@NotNull ByteBuffer buffer) {
		String text = "Server rejected login: ";
		byte value = buffer.get();
		if (value == 0) {
			text += "client sent invalid login packet";
		} else if (value == 1) {
			text += "outdated client";
		} else if (value == 2) {
			text += "outdated server";
		} else {
			text += "unknown reason, probably version mismatch";
		}
		mod.getLogger().log(value == 2 ? Level.INFO : Level.WARNING, text);
	}
}
