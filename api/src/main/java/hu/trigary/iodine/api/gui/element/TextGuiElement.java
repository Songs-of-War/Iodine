package hu.trigary.iodine.api.gui.element;

import hu.trigary.iodine.api.gui.element.base.GuiElement;
import hu.trigary.iodine.api.gui.element.base.GuiHeightSettable;
import hu.trigary.iodine.api.gui.element.base.GuiTextable;
import hu.trigary.iodine.api.gui.element.base.GuiWidthSettable;

/**
 * A GUI element that displays text.
 */
public interface TextGuiElement extends GuiElement<TextGuiElement>, GuiWidthSettable<TextGuiElement>,
		GuiHeightSettable<TextGuiElement>, GuiTextable<TextGuiElement> {
}