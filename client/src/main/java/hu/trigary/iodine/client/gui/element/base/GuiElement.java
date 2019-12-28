package hu.trigary.iodine.client.gui.element.base;

import hu.trigary.iodine.backend.PacketType;
import hu.trigary.iodine.client.util.IntPair;
import hu.trigary.iodine.client.gui.container.base.GuiBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public abstract class GuiElement {
	private final short[] padding = new short[4];
	private final GuiBase gui;
	private final int id;
	private byte drawPriority;
	private int width;
	private int height;
	private int positionX;
	private int positionY;
	
	protected GuiElement(@NotNull GuiBase gui, int id) {
		this.gui = gui;
		this.id = id;
	}
	
	
	
	@NotNull
	@Contract(pure = true)
	public final GuiBase getGui() {
		return gui;
	}
	
	@Contract(pure = true)
	public final int getId() {
		return id;
	}
	
	@Contract(pure = true)
	public final byte getDrawPriority() {
		return drawPriority;
	}
	
	@Contract(pure = true)
	public final int getWidth() {
		return width;
	}
	
	@Contract(pure = true)
	public final int getHeight() {
		return height;
	}
	
	
	
	public final void deserialize(@NotNull ByteBuffer buffer) {
		padding[0] = buffer.getShort();
		padding[0] = buffer.getShort();
		padding[0] = buffer.getShort();
		padding[0] = buffer.getShort();
		drawPriority = buffer.get();
		deserializeImpl(buffer);
	}
	
	protected abstract void deserializeImpl(@NotNull ByteBuffer buffer);
	
	public void initialize() {}
	
	
	
	public final void calculateSize(int screenWidth, int screenHeight) { //in case percentage based stuff is added
		IntPair size = calculateSizeImpl(screenWidth, screenHeight);
		width = size.getX() + padding[2] + padding[3];
		height = size.getY() + padding[0] + padding[1];
	}
	
	@NotNull
	protected abstract IntPair calculateSizeImpl(int screenWidth, int screenHeight);
	
	public final void setPosition(int x, int y) {
		positionX = x + padding[2];
		positionY = y + padding[0];
		setChildrenPositions(positionX, positionY);
	}
	
	protected void setChildrenPositions(int offsetX, int offsetY) {}
	
	public final void update() {
		updateImpl(width, height, positionX, positionY);
	}
	
	protected abstract void updateImpl(int width, int height, int positionX, int positionY);
	
	
	
	public final void draw(int mouseX, int mouseY, float partialTicks) {
		drawImpl(width, height, positionX, positionY, mouseX, mouseY, partialTicks);
	}
	
	protected abstract void drawImpl(int width, int height, int positionX,
			int positionY, int mouseX, int mouseY, float partialTicks);
	
	protected final void sendChangePacket(int dataLength, @NotNull Consumer<ByteBuffer> dataProvider) {
		getGui().getMod().getNetwork().send(PacketType.CLIENT_GUI_CHANGE, dataLength + 8, buffer -> {
			buffer.putInt(gui.getId());
			buffer.putInt(id);
			dataProvider.accept(buffer);
		});
	}
	
	
	
	public void onKeyTyped(char typedChar, int keyCode) {}
	
	public boolean onMousePressed(int mouseX, int mouseY) {
		return false;
	}
	
	public void onMouseReleased(int mouseX, int mouseY) {}
}