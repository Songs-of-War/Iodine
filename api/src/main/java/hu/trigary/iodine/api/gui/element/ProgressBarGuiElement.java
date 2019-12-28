package hu.trigary.iodine.api.gui.element;

import hu.trigary.iodine.api.gui.element.base.*;

/**
 * A GUI element that is a progress bar, with text on top of it.
 * Only the width or the height is customizable (not both) depending on the orientation.
 * The non-configurable dimension's getter returns 0.
 */
public interface ProgressBarGuiElement extends GuiElement<ProgressBarGuiElement>, GuiTextable<ProgressBarGuiElement>,
		GuiWidthSettable<ProgressBarGuiElement>, GuiHeightSettable<ProgressBarGuiElement>,
		GuiProgressable<ProgressBarGuiElement>, GuiOrientable<ProgressBarGuiElement> {
}