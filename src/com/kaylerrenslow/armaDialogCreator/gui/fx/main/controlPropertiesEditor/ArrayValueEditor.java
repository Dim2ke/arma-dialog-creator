package com.kaylerrenslow.armaDialogCreator.gui.fx.main.controlPropertiesEditor;

import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.ArmaStringChecker;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.InputField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 Created by Kayler on 07/13/2016.
 */
public class ArrayValueEditor implements ValueEditor<SVStringArray> {

	protected ArrayList<InputField<ArmaStringChecker, String>> editors = new ArrayList<>();

	protected final Button btnDecreaseSize = new Button("-");
	protected final Button btnIncreaseSize = new Button("+");
	private final double gap = 5;
	protected final FlowPane editorsPane = new FlowPane(gap, gap);
	private final HBox masterPane;
	private final InputField<ArmaStringChecker, String> overrideField = new InputField<>(new ArmaStringChecker());

	public ArrayValueEditor(int numInitialFields) {
		masterPane = new HBox(5, editorsPane);
		editorsPane.minWidth(0d);
		editorsPane.prefWidth(0d);
		editorsPane.setPrefWrapLength(300d + 5 * numInitialFields);
		masterPane.minWidth(0d);

		masterPane.getChildren().addAll(btnDecreaseSize, btnIncreaseSize);
		btnDecreaseSize.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (editors.size() > 1) {
					InputField removed = editors.remove(editors.size() - 1);
					editorsPane.getChildren().remove(removed);
				}
			}
		});
		btnIncreaseSize.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				InputField in = getTextField();
				editors.add(in);
				editorsPane.getChildren().add(in);
				editorsPane.autosize();
			}
		});
		InputField in;
		for (int i = 0; i < numInitialFields; i++) {
			in = getTextField();
			editors.add(in);
			editorsPane.getChildren().add(in);
		}
		editorsPane.autosize();
	}

	private InputField<ArmaStringChecker, String> getTextField() {
		InputField<ArmaStringChecker, String> tf = new InputField<>(new ArmaStringChecker());
		tf.setPrefWidth(100d);
		return tf;
	}

	@Override
	public SVStringArray getValue() {
		String[] values = new String[editors.size()];
		int i = 0;
		for (InputField tf : editors) {
			values[i++] = new String(tf.getText());
		}
		return new SVStringArray(values);
	}

	@Override
	public void setValue(SVStringArray val) {
		int i = 0;
		for (String s : val.getAsStringArray()) {
			editors.get(i++).setText(s);
		}
	}

	@Override
	public @NotNull Node getRootNode() {
		return masterPane;
	}

	@Override
	public void setToOverride(boolean override) {
		masterPane.getChildren().clear();
		if (override) {
			masterPane.getChildren().add(overrideField);
		} else {
			masterPane.getChildren().add(editorsPane);
		}
	}

	@Override
	public InputField<ArmaStringChecker, String> getOverrideTextField() {
		return overrideField;
	}
}
