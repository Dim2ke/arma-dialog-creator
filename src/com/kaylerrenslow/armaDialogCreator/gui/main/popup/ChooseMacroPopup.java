package com.kaylerrenslow.armaDialogCreator.gui.main.popup;

import com.kaylerrenslow.armaDialogCreator.arma.stringtable.Language;
import com.kaylerrenslow.armaDialogCreator.arma.stringtable.StringTableKey;
import com.kaylerrenslow.armaDialogCreator.control.Macro;
import com.kaylerrenslow.armaDialogCreator.control.MacroType;
import com.kaylerrenslow.armaDialogCreator.control.sv.SVString;
import com.kaylerrenslow.armaDialogCreator.control.sv.SerializableValue;
import com.kaylerrenslow.armaDialogCreator.data.Project;
import com.kaylerrenslow.armaDialogCreator.gui.main.fxControls.ChooseItemPopup;
import com.kaylerrenslow.armaDialogCreator.gui.main.stringtable.StringTableLanguageTokenEditor;
import com.kaylerrenslow.armaDialogCreator.main.HelpUrls;
import com.kaylerrenslow.armaDialogCreator.main.Lang;
import com.kaylerrenslow.armaDialogCreator.util.BrowserUtil;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Used for displaying known macros of a given type and allowing the user to choose which macro they want.

 @author Kayler
 @since 08/09/2016. */
public class ChooseMacroPopup<V extends SerializableValue> extends ChooseItemPopup<Macro<V>> {

	private static final MacroItemCategory[] categories = new MacroItemCategory[MacroType.values().length + 1];

	static {
		categories[0] = new AllMacrosCategory();
		int i = 1;
		for (MacroType type : MacroType.values()) {
			if (type != MacroType.STRING_TABLE) {
				categories[i++] = new ImplicitMacroTypeCategory(type);
			} else {
				categories[i++] = new StringTableKeyMacroCategory();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <V extends SerializableValue> List<Macro<V>> getMacrosOfType(@NotNull Class<V> macroClassType) {
		List<Macro<V>> macros = new ArrayList<>();
		for (Macro macro : Project.getCurrentProject().getMacroRegistry().getMacros()) {
			if (macroClassType.isInstance(macro.getValue())) {
				macros.add(macro);
			}
		}
		if (macroClassType.isInstance(new SVString())) {
			if (Project.getCurrentProject().getStringTable() != null) {
				for (Macro macro : Project.getCurrentProject().getStringTable().getKeys()) {
					macros.add(macro);
				}
			}
		}
		return macros;
	}

	@SuppressWarnings("unchecked")
	public ChooseMacroPopup(@NotNull Class<V> macroClassType) {
		super(categories, getMacrosOfType(macroClassType),
				Lang.ApplicationBundle().getString("Popups.ChooseMacro.popup_title"),
				Lang.ApplicationBundle().getString("Popups.ChooseMacro.choose_macro_title")
		);
		myStage.sizeToScene();
	}

	@Override
	protected void help() {
		BrowserUtil.browse(HelpUrls.MACROS);
	}


	@Override
	public void show() {
		showAndWait();
	}

	private static class ImplicitMacroTypeCategory<V extends SerializableValue> extends BasicMacroItemCategory<V> {

		private final MacroType type;

		public ImplicitMacroTypeCategory(@NotNull MacroType type) {
			this.type = type;
		}

		@NotNull
		@Override
		public String categoryDisplayName() {
			return type.getDisplayText();
		}

		@Override
		public boolean itemInCategory(@NotNull Macro item) {
			return item.getMacroType() == type;
		}
	}

	private static class AllMacrosCategory<V extends SerializableValue> extends BasicMacroItemCategory<V> {

		@NotNull
		@Override
		public String categoryDisplayName() {
			return Lang.ApplicationBundle().getString("Popups.ChooseMacro.category_all_macros");
		}

		@Override
		public boolean itemInCategory(@NotNull Macro item) {
			return true;
		}
	}

	private static abstract class BasicMacroItemCategory<V extends SerializableValue> extends MacroItemCategory<V> {

		private final Node categoryNode;
		private final TextArea taComment = new TextArea();
		private final TextArea taValue = new TextArea();

		public BasicMacroItemCategory() {
			final double height = 100;
			VBox vb = new VBox(10);
			taComment.setPrefHeight(height);
			taComment.setEditable(false);
			Label lblComment = new Label(Lang.ApplicationBundle().getString("Macros.comment"), taComment);
			lblComment.setContentDisplay(ContentDisplay.BOTTOM);
			taValue.setEditable(false);
			taValue.setPrefHeight(height);
			Label lblValue = new Label(Lang.ApplicationBundle().getString("Macros.value"), taValue);
			lblValue.setContentDisplay(ContentDisplay.BOTTOM);
			vb.getChildren().addAll(lblValue, lblComment);
			categoryNode = vb;
		}

		@Nullable
		@Override
		public Node getMiscCategoryNode() {
			return categoryNode;
		}

		@Override
		public void newItemSelected(Macro<V> selected) {
			if (selected != null) {
				taComment.setText(selected.getComment());
				taValue.setText(selected.getValue().toString());
			}
		}
	}

	private static class StringTableKeyMacroCategory extends MacroItemCategory<SVString> {

		private final VBox vboxLanguages = new VBox(10);
		private final ScrollPane categoryNode = new ScrollPane(vboxLanguages);

		public StringTableKeyMacroCategory() {
			categoryNode.setFitToHeight(true);
			categoryNode.setFitToWidth(true);
			vboxLanguages.setFillWidth(true);
			categoryNode.setPadding(new Insets(5));
		}

		@NotNull
		@Override
		public String categoryDisplayName() {
			return MacroType.STRING_TABLE.getDisplayText();
		}

		@Override
		public boolean itemInCategory(@NotNull Macro<SVString> item) {
			return item.getMacroType() == MacroType.STRING_TABLE;
		}

		@Nullable
		@Override
		public Node getMiscCategoryNode() {
			return categoryNode;
		}

		@Override
		public void newItemSelected(@Nullable Macro<SVString> item) {
			vboxLanguages.getChildren().clear();
			if (item == null) {
				return;
			}
			StringTableKey key = (StringTableKey) item;

			if (key.getDefaultLanguageToken() != null) {
				vboxLanguages.getChildren().add(getVboxLanguage(key.getDefaultLanguage(), key.getDefaultLanguageToken()));
			}

			for (Map.Entry<Language, String> entry : key.getLanguageTokenMap().entrySet()) {
				if (entry.getKey() == key.getDefaultLanguage()) {
					continue;
				}
				VBox vboxLanguage = getVboxLanguage(entry.getKey(), entry.getValue());
				vboxLanguages.getChildren().add(vboxLanguage);
			}
		}

		@NotNull
		private VBox getVboxLanguage(Language language, String languageToken) {
			VBox vboxLanguage = new VBox(5);
			vboxLanguage.setFillWidth(true);
			vboxLanguage.getChildren().add(new Label(language.getName()));

			StringTableLanguageTokenEditor editor = new StringTableLanguageTokenEditor();
			editor.setWrapText(true);
			editor.setEditable(false);
			editor.replaceText(languageToken);
			vboxLanguage.getChildren().add(editor);
			return vboxLanguage;
		}
	}

	private static abstract class MacroItemCategory<V extends SerializableValue> implements ItemCategory<Macro<V>> {

		@NotNull
		@Override
		public String noItemsPlaceholderText() {
			return Lang.ApplicationBundle().getString("Popups.ChooseMacro.no_available_macros");
		}

		@NotNull
		@Override
		public String availableItemsDisplayText() {
			return Lang.ApplicationBundle().getString("Popups.ChooseMacro.available_macros");
		}

	}


}
