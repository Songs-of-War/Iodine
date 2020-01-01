package hu.trigary.iodine.forge.gui.element;

import hu.trigary.iodine.client.gui.container.base.GuiBase;
import hu.trigary.iodine.client.gui.element.DiscreteSliderGuiElement;
import net.minecraft.client.gui.widget.AbstractSlider;
import org.jetbrains.annotations.NotNull;

public class DiscreteSliderGuiElementImpl extends DiscreteSliderGuiElement {
	private Slider widget;
	
	public DiscreteSliderGuiElementImpl(@NotNull GuiBase gui, int id) {
		super(gui, id);
	}
	
	
	
	@Override
	protected void updateImpl(int width, int height, int positionX, int positionY) {
		widget = new Slider(positionX, positionY, width, height, progress, maxProgress);
		widget.active = editable;
		widget.setMessage(text);
		//TODO verticalOrientation
	}
	
	@Override
	protected void drawImpl(int width, int height, int positionX, int positionY, int mouseX, int mouseY, float partialTicks) {
		widget.render(mouseX, mouseY, partialTicks);
	}
	
	
	
	@Override
	public boolean onMousePressed(double mouseX, double mouseY) {
		return widget.mouseClicked(mouseX, mouseY, 0);
	}
	
	@Override
	public void onMouseDragged(double mouseX, double mouseY, double deltaX, double deltaY) {
		widget.mouseClicked(mouseX, mouseY, 0);
	}
	
	@Override
	public void onMouseReleased(double mouseX, double mouseY) {
		widget.mouseReleased(mouseX, mouseY, 0);
		onChanged(widget.getProgress());
	}
	
	
	
	private static class Slider extends AbstractSlider {
		private final short maxProgress;
		
		protected Slider(int x, int y, int width, int height, short progress, short maxProgress) {
			super(x, y, width, height, (double) progress / maxProgress);
			this.maxProgress = maxProgress;
		}
		
		public short getProgress() {
			return (short) Math.round(value * maxProgress);
		}
		
		@Override
		protected void updateMessage() {}
		
		@Override
		protected void applyValue() {
			value = Math.floor(value * maxProgress + 0.5) / maxProgress;
		}
	}
}
