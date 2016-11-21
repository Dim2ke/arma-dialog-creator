/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main.controlPropertiesEditor;

import com.kaylerrenslow.armaDialogCreator.control.ControlClass;
import com.kaylerrenslow.armaDialogCreator.control.ControlProperty;
import com.kaylerrenslow.armaDialogCreator.control.Macro;
import com.kaylerrenslow.armaDialogCreator.control.PropertyType;
import com.kaylerrenslow.armaDialogCreator.control.sv.SVString;
import com.kaylerrenslow.armaDialogCreator.control.sv.SerializableValue;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.InputField;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.StringChecker;
import com.kaylerrenslow.armaDialogCreator.util.ValueListener;
import com.kaylerrenslow.armaDialogCreator.util.ValueObserver;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

/**
 @author Kayler
 @since 11/20/2016 */
interface ControlPropertyValueEditor extends ControlPropertyEditor {

	enum EditMode {
		/** Using default editor */
		DEFAULT,
		/**
		 Uses a raw input field in which the user can enter anything they want. When this scenario happens, must be careful with casting after this is turned off.
		 It is recommended to just clear the user's entered data to prevent exceptions from occurring.

		 @deprecated This is broken. Maybe fix it later.
		 */
		@Deprecated
		CUSTOM_DATA,
		/** The control property's value is set to a macro */
		MACRO
	}

	/**
	 Updating the edit mode. When this is invoked, the proper new editor should be used.
	 <ul>
	 <li>{@link EditMode#DEFAULT} = default editor</li>
	 <li>{@link EditMode#CUSTOM_DATA} = use an editor that allows for literally any input (use a {@link TextField} for input)</li>
	 <li>{@link EditMode#MACRO} = setting the ControlProperty's value equal to a {@link Macro}</li>
	 </ul>
	 */
	void setToMode(EditMode mode);

	@NotNull Node getRootNode();

	/** Get the Class type that */
	@NotNull Class<? extends SerializableValue> getMacroClass();

	/**
	 Return true if the {@link #getRootNode()}'s width should fill the parent's width.
	 False if the width should be whatever it is initially. By default, will return false.
	 */
	default boolean displayFullWidth() {
		return false;
	}

	/** DO NOT USE THIS FOR ARRAY INPUT */
	static InputField<StringChecker, String> modifyRawInput(InputField<StringChecker, String> rawInput, ControlClass control, ControlProperty controlProperty) {
		if (controlProperty.isPropertyType(PropertyType.ARRAY)) {
			throw new IllegalArgumentException("don't use this method for ARRAY property type");
		}
		rawInput.getValueObserver().addListener(new ValueListener<String>() {
			@Override
			public void valueUpdated(@NotNull ValueObserver<String> observer, String oldValue, String newValue) {
				controlProperty.setValue(new SVString(newValue));
			}
		});
		return rawInput;
	}

	/** Clear all listeners */
	void clearListeners();

	/** Initialize all listeners */
	void initListeners();

	/** Set the editor to the {@link #getControlProperty()} value. */
	void refresh();
}
