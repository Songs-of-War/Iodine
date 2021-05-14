package hu.trigary.iodine.forge.gui.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import hu.trigary.iodine.client.gui.IodineRoot;
import hu.trigary.iodine.client.gui.element.TextFieldGuiElement;
import hu.trigary.iodine.forge.gui.IodineGuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of {@link TextFieldGuiElement}.
 */
public class TextFieldGuiElementImpl extends TextFieldGuiElement {
	private TextFieldWidget widget;
	
	/**
	 * Creates a new instance.
	 *
	 * @param root the instance which will contain this element
	 * @param id the internal ID of this element
	 */
	public TextFieldGuiElementImpl(@NotNull IodineRoot root, int id) {
		super(root, id);
	}
	
	
	
	@Override
	protected void updateImpl(int positionX, int positionY, int width, int height) {
		widget = new TextFieldWidget(Minecraft.getInstance().font, positionX, positionY, width, height, new StringTextComponent(text));
		widget.setValue(text);
		widget.setMaxLength(maxLength);
		widget.setFilter(regex == null ? s -> true : s -> regex.matcher(s).matches());
	}
	
	@Override
	protected void drawImpl(int positionX, int positionY, int width, int height, int mouseX, int mouseY, float partialTicks) {
		widget.render(new MatrixStack(),mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawTooltipImpl(int positionX, int positionY, int width, int height, int mouseX, int mouseY) {
		if (widget.isHovered()) {
			IodineGuiUtils.renderTooltip(mouseX, mouseY, tooltip);
		}
	}
	
	
	
	@Override
	public void setFocused(boolean focused) {
		widget.setFocus(focused);
	}
	
	@Override
	public boolean onMousePressed(double mouseX, double mouseY) {
		return editable && widget.mouseClicked(mouseX, mouseY, 0);
	}
	
	@Override
	public void onKeyPressed(int key, int scanCode, int modifiers) {
		if (editable) {
			widget.keyPressed(key, scanCode, modifiers);
			onChanged(widget.getValue());
		}
	}
	
	@Override
	public void onCharTyped(char codePoint, int modifiers) {
		if (editable) {
			widget.charTyped(codePoint, modifiers);
			onChanged(widget.getValue());
		}
	}
}
