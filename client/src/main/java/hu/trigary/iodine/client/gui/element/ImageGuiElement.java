package hu.trigary.iodine.client.gui.element;

import hu.trigary.iodine.backend.BufferUtils;
import hu.trigary.iodine.client.gui.IodineRoot;
import hu.trigary.iodine.client.gui.element.base.GuiElement;
import hu.trigary.iodine.client.IntPair;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

/**
 * The implementation of {@link hu.trigary.iodine.backend.GuiElementType#IMAGE}.
 */
public abstract class ImageGuiElement extends GuiElement {
	private static final int MAX_IMAGE_LENGTH = 1 << 20;
	protected int width;
	protected int height;
	protected String tooltip;
	protected int resizeMode;
	protected byte[] image = new byte[0];
	
	/**
	 * Creates a new instance.
	 *
	 * @param root the instance which will contain this element
	 * @param id the internal ID of this element
	 */
	protected ImageGuiElement(@NotNull IodineRoot root, int id) {
		super(root, id);
	}
	
	
	
	@Override
	protected final void deserializeImpl(@NotNull ByteBuffer buffer) {
		width = buffer.getShort();
		height = buffer.getShort();
		tooltip = BufferUtils.deserializeString(buffer);
		resizeMode = buffer.get();
		int length = buffer.getInt();
		if (length > MAX_IMAGE_LENGTH) {
			throw new AssertionError("Image mustn't be bigger than 1 MB");
		}
		image = new byte[length];
		buffer.get(image);
	}
	
	@NotNull
	@Override
	protected final IntPair calculateSizeImpl(int screenWidth, int screenHeight) {
		return new IntPair(width, height);
	}
	
	/**
	 * Should be called when the user clicked this element.
	 * Calls {@link #sendChangePacket(int, Consumer)} internally after doing sanity checks.
	 */
	protected final void onChanged() {
		sendChangePacket(0, b -> {});
	}
}
