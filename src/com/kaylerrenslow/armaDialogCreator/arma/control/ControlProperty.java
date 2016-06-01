package com.kaylerrenslow.armaDialogCreator.arma.control;

import com.kaylerrenslow.armaDialogCreator.arma.util.AColor;
import com.kaylerrenslow.armaDialogCreator.arma.util.AFont;
import com.kaylerrenslow.armaDialogCreator.arma.util.AHexColor;
import com.kaylerrenslow.armaDialogCreator.arma.util.ASound;
import com.kaylerrenslow.armaDialogCreator.util.ValueListener;
import com.kaylerrenslow.armaDialogCreator.util.ValueObserver;
import javafx.beans.value.ChangeListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 Created by Kayler on 05/22/2016.
 */
public class ControlProperty {
	public static final ControlProperty[] EMPTY = new ControlProperty[0];

	private final String name;
	private final PropertyType type;
	private final ControlPropertiesLookup propertyLookup;
	private ValueObserver<String[]> valuesObserver;

	public enum PropertyType {
		/** Is a integer value. Current implementation is a 32 bit integer (java int) */
		INT,
		/** Is a floating point value. The current implementation uses 32 bit floating point (java double) */
		FLOAT,
		/** Is a boolean (0 for false, 1 for true) */
		BOOLEAN,
		/** Is a String */
		STRING,
		/** Generic array property type */
		ARRAY,
		/** Color array string ({r,g,b,a} where r,g,b,a are from 0 to 1 inclusively) */
		COLOR,
		/** Is an array that is formatted to fit a sound and its params */
		SOUND,
		/** Is font name */
		FONT,
		/** Denotes a file name inside a String */
		FILE_NAME,
		/** Denotes an image path inside a String */
		IMAGE,
		/** Color is set to a hex string like #ffffff or #ffffffff */
		HEX_COLOR_STRING,
		/** example: #(argb,8,8,3)color(1,1,1,1) however there is more than one way to set texture */
		TEXTURE,
		/** Is an SQF code string, but this propertyType is an easy way to categorize all event handlers. */
		EVENT,
		/** SQF code String */
		SQF
	}

	/**
	 A control property is something like "idc" or "colorBackground". The current implementation puts all values inside a String array so that array serialization isn't needed.<br>
	 For types that aren't array, only 1 entry will be inside the array and that is the value.

	 @param propertyLookup unique lookup for the property.
	 @param name name of the property
	 @param type type of the property (integer, float, array, String)
	 @param values current values of the property. (if non-array, just create a String array of dimension 1 with value at index 0). If values are currently not set, use the constructor ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull PropertyType type, int numValues)
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull PropertyType type, @NotNull String[] values) {
		this.propertyLookup = propertyLookup;
		this.name = name;
		this.type = type;
		valuesObserver = new ValueObserver<>(values);
	}

	/**
	 This constructor is used for when the values of the property are not set but the number of values stored is determined. For more information on this class, see constructor ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull PropertyType type, @NotNull String[] values)

	 @param propertyLookup propertyLookup
	 @param name name of property
	 @param type type of property
	 @param numValues number of values that will be stored in the property (must be >=1).
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull PropertyType type, int numValues) {
		if (numValues <= 0) {
			throw new IllegalArgumentException("Number of values must be >= 1");
		}
		this.propertyLookup = propertyLookup;
		this.name = name;
		this.type = type;
		valuesObserver = new ValueObserver<>(new String[numValues]);
	}

	/**
	 Creates a control property of type Object (the value will be the .toString() value of the object)<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull PropertyType type, @NotNull Object value) {
		this(propertyLookup, name, type, new String[]{value.toString()});
	}

	/**
	 Creates a control property of type String<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull String value) {
		this(propertyLookup, name, PropertyType.STRING, new String[]{value});
	}

	/**
	 Creates a control property of type String<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, @NotNull AHexColor value) {
		this(propertyLookup, name, PropertyType.STRING, new String[]{value.getHexColor()});
	}

	/**
	 Creates a control property of type Int<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, int value) {
		this(propertyLookup, name, PropertyType.INT, new String[]{value + ""});
	}

	/**
	 Creates a control property of type Float<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, double value) {
		this(propertyLookup, name, PropertyType.FLOAT, new String[]{value + ""});
	}

	/**
	 Creates a control property of type Boolean<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, boolean value) {
		this(propertyLookup, name, PropertyType.BOOLEAN, new String[]{value + ""});
	}

	/**
	 Creates a control property of type Color<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, AColor value) {
		this(propertyLookup, name, PropertyType.COLOR, value.getAsStringArray());
	}

	/**
	 Creates a control property of type Sound<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, ASound value) {
		this(propertyLookup, name, PropertyType.SOUND, value.getAsStringArray());
	}

	/**
	 Creates a control property of type Font<br>
	 See constructor ControlProperty(ControlPropertiesLookup propertyLookup, String name, PropertyType type, String[] values) for more information
	 */
	public ControlProperty(ControlPropertiesLookup propertyLookup, @NotNull String name, AFont value) {
		this(propertyLookup, name, PropertyType.FONT, new String[]{value.name()});
	}

	public ControlPropertiesLookup getPropertyLookup() {
		return propertyLookup;
	}

	/** Get whether or not all values are set inside the property. */
	public boolean valuesSet() {
		for (String s : valuesObserver.getValue()) {
			if (s == null) {
				return false;
			}
		}
		return true;
	}

	@NotNull
	public String getName() {
		return name;
	}

	/** Return true if the given type is equal to this instance's property type, false otherwise. (This is effectively doing the same thing as getType() == PropertyType.something) */
	public boolean isType(PropertyType type) {
		return this.type == type;
	}

	@NotNull
	public PropertyType getType() {
		return type;
	}

	@NotNull
	public String[] getValues() {
		return valuesObserver.getValue();
	}

	public String getStringValue() {
		if (type != PropertyType.STRING) {
			throw new IllegalStateException("Incompatible type fetching. My property type=" + type);
		}
		return valuesObserver.getValue()[0];
	}

	public int getIntValue() {
		try {
			return Integer.valueOf(valuesObserver.getValue()[0]);
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Incompatible type fetching. My property type=" + type);
		}
	}

	public double getFloatValue() {
		try {
			return Double.valueOf(valuesObserver.getValue()[0]);
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Incompatible type fetching. My property type=" + type);
		}
	}

	public boolean getBooleanValue() {
		try {
			return Boolean.valueOf(valuesObserver.getValue()[0]);
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Incompatible type fetching. My property type=" + type);
		}
	}

	/** Get the observer that observers the values inside this property. Whenever the values get updated, the observer and it's listener will be told so. */
	public ValueObserver<String[]> getValuesObserver() {
		return valuesObserver;
	}

	/** Set all values */
	public void setValues(String[] values) {
		valuesObserver.updateValue(values);
	}

	/** Update values but only set the value at index valueInd */
	public void setValue(String v, int valueInd) {
		valuesObserver.getValue()[valueInd] = v;
		valuesObserver.updateValue(valuesObserver.getValue());
	}

	/** Set the first value to String (use this whenever the type has values length == 1 (e.g. STRING, INT, FONT but not ARRAY or SOUND)) */
	public void setValue(String v) {
		valuesObserver.getValue()[0] = v;
		valuesObserver.updateValue(valuesObserver.getValue());
	}

	/** Set the first value to int (use this if type==INT) */
	public void setValue(int v) {
		valuesObserver.getValue()[0] = v + "";
		valuesObserver.updateValue(valuesObserver.getValue());
	}

	/** Set the first value to double (use this if type==FLOAT) */
	public void setValue(double v) {
		valuesObserver.getValue()[0] = v + "";
		valuesObserver.updateValue(valuesObserver.getValue());
	}

	/** Set the first value to boolean (use this if type==BOOLEAN) */
	public void setValue(boolean v) {
		valuesObserver.getValue()[0] = v + "";
		valuesObserver.updateValue(valuesObserver.getValue());
	}

	/** Set the first value to a color (use this if type==COLOR or HEX_COLOR) */
	public void setValue(AColor color) {
		valuesObserver.updateValue(color.getAsStringArray());
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ControlProperty)) {
			return false;
		}
		ControlProperty other = (ControlProperty) o;
		return getName().equals(other.getName()) && type == other.type;
	}

}
