package hu.trigary.iodine.server.gui.element;

import hu.trigary.iodine.api.gui.element.ContinuousSliderGuiElement;
import hu.trigary.iodine.backend.GuiElementType;
import hu.trigary.iodine.backend.InputBuffer;
import hu.trigary.iodine.backend.OutputBuffer;
import hu.trigary.iodine.server.gui.IodineRootImpl;
import hu.trigary.iodine.server.gui.element.base.GuiElementImpl;
import hu.trigary.iodine.server.player.IodinePlayerBase;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of {@link ContinuousSliderGuiElement}.
 */
public class ContinuousSliderGuiElementImpl extends GuiElementImpl<ContinuousSliderGuiElement> implements ContinuousSliderGuiElement {
	private short width = 150;
	private boolean editable = true;
	private String tooltip = "";
	private String text = "";
	private float progress;
	private ProgressedAction progressedAction;
	
	/**
	 * Creates a new instance.
	 *
	 * @param root the instance which will contain this element
	 * @param internalId the internal ID of this element
	 * @param id the API-friendly ID of this element
	 */
	public ContinuousSliderGuiElementImpl(@NotNull IodineRootImpl<?> root, int internalId, @NotNull Object id) {
		super(root, GuiElementType.CONTINUOUS_SLIDER, internalId, id);
	}
	
	
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Contract(pure = true)
	@Override
	public boolean isEditable() {
		return editable;
	}
	
	@NotNull
	@Contract(pure = true)
	@Override
	public String getTooltip() {
		return tooltip;
	}
	
	@NotNull
	@Contract(pure = true)
	@Override
	public String getText() {
		return text;
	}
	
	@Contract(pure = true)
	@Override
	public float getProgress() {
		return progress;
	}
	
	
	
	@NotNull
	@Override
	public ContinuousSliderGuiElementImpl setWidth(int width) {
		if (this.width != width) {
			this.width = (short) width;
			getRoot().flagAndUpdate(this);
		}
		return this;
	}
	
	@NotNull
	@Override
	public ContinuousSliderGuiElementImpl setEditable(boolean editable) {
		if (this.editable != editable) {
			this.editable = editable;
			getRoot().flagAndUpdate(this);
		}
		return this;
	}
	
	@NotNull
	@Override
	public ContinuousSliderGuiElementImpl setTooltip(@NotNull String tooltip) {
		if (!this.tooltip.equals(tooltip)) {
			this.tooltip = tooltip;
			getRoot().flagAndUpdate(this);
		}
		return this;
	}
	
	@NotNull
	@Override
	public ContinuousSliderGuiElementImpl setText(@NotNull String text) {
		if (!this.text.equals(text)) {
			this.text = text;
			getRoot().flagAndUpdate(this);
		}
		return this;
	}
	
	@NotNull
	@Override
	public ContinuousSliderGuiElementImpl setProgress(float progress) {
		if (Float.compare(this.progress, progress) != 0) {
			Validate.isTrue(progress >= 0 && progress <= 1, "Progress must be at least 0 and at most 1");
			this.progress = progress;
			getRoot().flagAndUpdate(this);
		}
		return this;
	}
	
	@NotNull
	@Override
	public ContinuousSliderGuiElementImpl onProgressed(@Nullable ProgressedAction action) {
		progressedAction = action;
		return this;
	}
	
	
	
	@Override
	public void serializeImpl(@NotNull OutputBuffer buffer) {
		buffer.putShort(width);
		buffer.putBool(editable);
		buffer.putString(tooltip);
		buffer.putString(text);
		buffer.putFloat(progress);
	}
	
	@Override
	public void handleChangePacket(@NotNull IodinePlayerBase player, @NotNull InputBuffer buffer) {
		if (!editable) {
			return;
		}
		
		float newProgress = buffer.readFloat();
		if (Float.compare(progress, newProgress) == 0 || newProgress < 0 || newProgress > 1) {
			return;
		}
		
		float oldProgress = progress;
		progress = newProgress;
		if (progressedAction == null) {
			getRoot().flagAndUpdate(this);
		} else {
			getRoot().flagAndAtomicUpdate(this, () -> progressedAction.accept(this, oldProgress, progress, player));
		}
	}
}
