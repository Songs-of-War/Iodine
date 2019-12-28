package hu.trigary.iodine.bukkit.gui.element;

import hu.trigary.iodine.api.gui.element.DropdownGuiElement;
import hu.trigary.iodine.backend.GuiElementType;
import hu.trigary.iodine.bukkit.gui.container.base.GuiBaseImpl;
import hu.trigary.iodine.bukkit.gui.element.base.GuiElementImpl;
import hu.trigary.iodine.bukkit.network.ResizingByteBuffer;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * The implementation of {@link DropdownGuiElement}.
 */
public class DropdownGuiElementImpl extends GuiElementImpl<DropdownGuiElement> implements DropdownGuiElement {
	private final List<String> choices = new ArrayList<>(Collections.singletonList(""));
	private short width = 100;
	private boolean editable = true;
	private short selected;
	private ChosenAction chosenAction;
	
	/**
	 * Creates a new instance.
	 *
	 * @param gui the GUI which will contain this element
	 * @param internalId the internal ID of this element
	 * @param id the API-friendly ID of this element
	 */
	public DropdownGuiElementImpl(@NotNull GuiBaseImpl<?> gui, int internalId, @NotNull Object id) {
		super(gui, GuiElementType.DROPDOWN, internalId, id);
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
	public List<String> getChoices() {
		return Collections.unmodifiableList(choices);
	}
	
	@NotNull
	@Contract(pure = true)
	@Override
	public String getSelected() {
		return choices.get(selected);
	}
	
	
	
	@NotNull
	@Override
	public DropdownGuiElementImpl setWidth(int width) {
		this.width = (short) width;
		getGui().flagAndUpdate(this);
		return this;
	}
	
	@NotNull
	@Override
	public DropdownGuiElementImpl setEditable(boolean editable) {
		this.editable = editable;
		getGui().flagAndUpdate(this);
		return this;
	}
	
	@NotNull
	@Override
	public DropdownGuiElementImpl setChoices(@NotNull Collection<String> choices) {
		Validate.isTrue(!choices.isEmpty(), "The count of choices must be at least 1");
		this.choices.clear();
		this.choices.addAll(choices);
		selected = 0;
		getGui().flagAndUpdate(this);
		return this;
	}
	
	@NotNull
	@Override
	public DropdownGuiElementImpl setSelected(@NotNull String value) {
		int index = choices.indexOf(value);
		Validate.isTrue(index != -1, "The selected value must be among the choices");
		selected = (short) index;
		getGui().flagAndUpdate(this);
		return this;
	}
	
	@NotNull
	@Override
	public DropdownGuiElementImpl onChosen(@Nullable ChosenAction action) {
		chosenAction = action;
		return this;
	}
	
	
	
	@Override
	public void serializeImpl(@NotNull ResizingByteBuffer buffer) {
		buffer.putShort(width);
		buffer.putBool(editable);
		buffer.putShort((short) choices.size());
		for (String choice : choices) {
			buffer.putString(choice);
		}
		buffer.putShort(selected);
	}
	
	@Override
	public void handleChangePacket(@NotNull Player player, @NotNull ByteBuffer message) {
		if (!editable) {
			return;
		}
		
		short newSelected = message.getShort();
		if (selected == newSelected || newSelected < 0 || newSelected >= choices.size()) {
			return;
		}
		
		int oldSelected = selected;
		selected = newSelected;
		if (chosenAction == null) {
			getGui().flagAndUpdate(this);
		} else {
			getGui().flagAndAtomicUpdate(this, () -> chosenAction.accept(this,
					choices.get(oldSelected), choices.get(selected), player));
		}
	}
}