package hu.trigary.iodine.forge.gui.element;

import hu.trigary.iodine.client.gui.IodineRoot;
import hu.trigary.iodine.client.gui.element.DiscreteSliderGuiElement;
import hu.trigary.iodine.forge.gui.IodineGuiUtils;
import net.minecraft.client.gui.widget.AbstractSlider;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of {@link DiscreteSliderGuiElement}.
 */
public class DiscreteSliderGuiElementImpl extends DiscreteSliderGuiElement {
	private Slider widget;

	/**
	 * Creates a new instance.
	 *
	 * @param root the instance which will contain this element
	 * @param id the internal ID of this element
	 */
	public DiscreteSliderGuiElementImpl(@NotNull IodineRoot root, int id) {
		super(root, id);
	}
	
	
	
	@Override
	protected void updateImpl(int positionX, int positionY, int width, int height) {
		widget = new Slider(positionX, positionY, width, height, progress, maxProgress);
		widget.active = editable;
		widget.setMessage(text);
	}
	
	@Override
	protected void drawImpl(int positionX, int positionY, int width, int height, int mouseX, int mouseY, float partialTicks) {
		widget.render(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawTooltipImpl(int positionX, int positionY, int width, int height, int mouseX, int mouseY) {
		if (widget.isHovered()) {
			IodineGuiUtils.renderTooltip(mouseX, mouseY, tooltip);
		}
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
		
		Slider(int x, int y, int width, int height, short progress, short maxProgress) {
			super(x, y, width, height, (double) progress / maxProgress);
			this.maxProgress = maxProgress;
		}
		
		short getProgress() {
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
