package hu.trigary.iodine.api.gui.element;

import hu.trigary.iodine.api.gui.element.base.GuiEditable;
import hu.trigary.iodine.api.gui.element.base.GuiElement;
import hu.trigary.iodine.api.gui.element.base.GuiTooltipable;
import hu.trigary.iodine.api.player.IodinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A GUI element that is a radio button.
 * Radio buttons are linked together by their common group-ID, which is '0' by default.
 */
public interface RadioButtonGuiElement extends GuiElement<RadioButtonGuiElement>,
		GuiEditable<RadioButtonGuiElement>, GuiTooltipable<RadioButtonGuiElement> {
	/**
	 * Gets whether this element is currently in its checked state.
	 *
	 * @return whether this element is checked
	 */
	@Contract(pure = true)
	boolean isChecked();
	
	/**
	 * Gets this element's group-ID.
	 *
	 * @return the group-ID of this element
	 */
	@Contract(pure = true)
	int getGroupId();
	
	
	
	/**
	 * Makes this element the checked one in its group.
	 *
	 * @return the current instance (for chaining)
	 */
	@NotNull
	RadioButtonGuiElement setChecked();
	
	/**
	 * Sets this element's group-ID to the specified ID.
	 * This element will become unchecked except if it is
	 * the only element with the specified ID.
	 *
	 * @param groupId the new group-ID of this element
	 * @return the current instance (for chaining)
	 */
	@NotNull
	RadioButtonGuiElement setGroupId(int groupId);
	
	
	
	/**
	 * Sets the action that should be executed when
	 * this GUI element is unchecked by a player.
	 * This callback fires before {@link #onChecked(CheckedAction)}.
	 * The callback is atomically executed GUI updating wise.
	 *
	 * @param action the action to atomically execute
	 * @return the current instance (for chaining)
	 */
	@NotNull
	RadioButtonGuiElement onUnchecked(@Nullable UncheckedAction action);
	
	/**
	 * Sets the action that should be executed when
	 * this GUI element is checked by a player.
	 * This callback fires after {@link #onUnchecked(UncheckedAction)}.
	 * The callback is atomically executed GUI updating wise.
	 *
	 * @param action the action to atomically execute
	 * @return the current instance (for chaining)
	 */
	@NotNull
	RadioButtonGuiElement onChecked(@Nullable CheckedAction action);
	
	
	
	/**
	 * The handler of the unchecked action.
	 */
	@FunctionalInterface
	interface UncheckedAction {
		/**
		 * Handles the unchecked action.
		 *
		 * @param checked the element that became checked
		 * @param unchecked the element that became unchecked
		 * @param player the player who caused the change
		 */
		void accept(@NotNull RadioButtonGuiElement checked,
				@NotNull RadioButtonGuiElement unchecked, @NotNull IodinePlayer player);
	}
	
	/**
	 * The handler of the checked action.
	 */
	@FunctionalInterface
	interface CheckedAction {
		/**
		 * Handles the checked action.
		 *
		 * @param checked the element that became checked
		 * @param unchecked the element that became unchecked
		 * @param player the player who caused the change
		 */
		void accept(@NotNull RadioButtonGuiElement checked,
				@NotNull RadioButtonGuiElement unchecked, @NotNull IodinePlayer player);
	}
}
