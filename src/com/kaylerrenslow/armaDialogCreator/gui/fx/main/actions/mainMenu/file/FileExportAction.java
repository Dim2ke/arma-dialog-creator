/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main.actions.mainMenu.file;

import com.kaylerrenslow.armaDialogCreator.control.DisplayProperty;
import com.kaylerrenslow.armaDialogCreator.data.ApplicationDataManager;
import com.kaylerrenslow.armaDialogCreator.data.Project;
import com.kaylerrenslow.armaDialogCreator.data.io.export.ProjectExportConfiguration;
import com.kaylerrenslow.armaDialogCreator.data.io.export.ProjectExporter;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.FileChooserPane;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.IdentifierChecker;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.InputField;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.controlPropertiesEditor.ValueEditor;
import com.kaylerrenslow.armaDialogCreator.gui.fx.popup.StageDialog;
import com.kaylerrenslow.armaDialogCreator.main.ArmaDialogCreator;
import com.kaylerrenslow.armaDialogCreator.main.ExceptionHandler;
import com.kaylerrenslow.armaDialogCreator.main.HelpUrls;
import com.kaylerrenslow.armaDialogCreator.main.lang.Lang;
import com.kaylerrenslow.armaDialogCreator.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 Created by Kayler on 05/20/2016.
 */
public class FileExportAction implements EventHandler<ActionEvent> {
	@Override
	public void handle(ActionEvent event) {
		ExportProjectConfigurationDialog dialog = new ExportProjectConfigurationDialog(ApplicationDataManager.getInstance().getCurrentProject());
		dialog.show();
		if (dialog.getConfiguration() == null) {
			return;
		}
		try {
			ProjectExporter.export(dialog.getConfiguration());
		} catch (IOException e) {
			ExceptionHandler.error(e);
		}
	}

	private static class ExportProjectConfigurationDialog extends StageDialog<VBox> {

		private enum DisplayType {
			DIALOG(Lang.Popups.ExportProject.DisplayProperties.DIALOG), TITLE(Lang.Popups.ExportProject.DisplayProperties.TITLE);

			private final String displayName;

			DisplayType(String displayName) {
				this.displayName = displayName;
			}

			public static final DisplayType DEFAULT = DIALOG;

		}

		private boolean cancel = false;
		private DisplayType selectedDisplayType = DisplayType.DEFAULT;

		private final Insets padding10 = new Insets(10);
		private final ProjectExportConfiguration configuration;

		/*display properties things*/
		/** observer for the exporting display's class name */
		private ValueObserver<String> classNameObserver;
		private static final String DEFAULT_CLASS_NAME = "MyDialog";


		/*export parameter things*/
		/** the observer for the directory of where to export the project/display */
		private ValueObserver<File> exportDirectoryObserver;
		/** observer to detect when the macros are being exported to their own file, or being placed in the display's header file */
		private final ValueObserver<Boolean> exportMacrosToFileObserver = new ValueObserver<>(false);

		/*export preview things*/
		/** group that should be notified when the export preview should be updated */
		private final UpdateListenerGroup<Object> updatePreviewGroup = new UpdateListenerGroup<>();

		public ExportProjectConfigurationDialog(@NotNull Project project) {
			super(ArmaDialogCreator.getPrimaryStage(), new VBox(10), Lang.Popups.ExportProject.DIALOG_TITLE, true, true, true);
			btnOk.setText(Lang.Popups.ExportProject.OK_BUTTON_EXPORT);
			configuration = new ProjectExportConfiguration("", project.getProjectSaveDirectory(), project, false, exportMacrosToFileObserver.getValue());
			setStageSize(720, 480);
			myRootElement.setPadding(new Insets(10d));

			final Label lblTitle = new Label(Lang.Popups.ExportProject.TITLE_LABEL);
			lblTitle.setFont(Font.font(17));
			myRootElement.getChildren().add(lblTitle);
			myRootElement.getChildren().add(new Separator(Orientation.HORIZONTAL));
			final Tab tabDisplayProperties = new Tab(Lang.Popups.ExportProject.DISPLAY_PROPERTIES);
			tabDisplayProperties.setClosable(false);
			final Tab tabExportParameters = new Tab(Lang.Popups.ExportProject.EXPORT_PARAMETERS);
			tabExportParameters.setClosable(false);
			final Tab tabExportPreview = new Tab(Lang.Popups.ExportProject.EXPORT_PREVIEW);
			tabExportPreview.setClosable(false);

			final TabPane tabPane = new TabPane(tabDisplayProperties, tabExportParameters, tabExportPreview);
			VBox.setVgrow(tabPane, Priority.ALWAYS);

			myRootElement.getChildren().add(tabPane);

			initTabDisplayProperties(tabDisplayProperties);
			initTabExportParameters(tabExportParameters);
			initTabExportPreview(tabExportPreview);

			classNameObserver.updateValue(DEFAULT_CLASS_NAME);
		}

		/*
		*
		* TAB INIT: Display Properties
		*
		*/
		private void initTabDisplayProperties(Tab tabDisplayProperties) {
			final VBox tabRoot = new VBox(10);
			tabRoot.setPadding(padding10);
			tabDisplayProperties.setContent(tabRoot);

			/*class name*/
			final Label lblClassName = new Label("");
			final InputField<IdentifierChecker, String> inputFieldClassName = new InputField<>(new IdentifierChecker());
			classNameObserver = inputFieldClassName.getValueObserver();
			classNameObserver.addValueListener(new ValueListener<String>() {
				@Override
				public void valueUpdated(@NotNull ValueObserver<String> observer, String oldValue, String newValue) {
					configuration.setExportClassName(newValue);
					updateExportPreview();
				}
			});
			HBox.setHgrow(inputFieldClassName, Priority.ALWAYS);
			final HBox hboxClassName = new HBox(5, lblClassName, inputFieldClassName);
			tabRoot.getChildren().add(hboxClassName);

			/*display type*/
			final Label lblDisplayType = new Label(Lang.Popups.ExportProject.DisplayProperties.DISPLAY_TYPE);
			final ToggleGroup toggleGroup = new ToggleGroup();
			final FlowPane flowPaneDisplayType = new FlowPane(Orientation.HORIZONTAL, 5, 10);
			final HBox hboxDisplayType = new HBox(5, lblDisplayType, flowPaneDisplayType);
			toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					selectedDisplayType = (DisplayType) newValue.getUserData();
					lblClassName.setText(String.format(Lang.Popups.ExportProject.DisplayProperties.CLASS_NAME_F, selectedDisplayType.displayName));
				}
			});
			for (DisplayType type : DisplayType.values()) {
				final RadioButton radioButton = new RadioButton(type.displayName);
				radioButton.setUserData(type);
				radioButton.setToggleGroup(toggleGroup);
				if (type == selectedDisplayType) {
					radioButton.setSelected(true);
				}
				flowPaneDisplayType.getChildren().add(radioButton);
			}
			tabRoot.getChildren().add(hboxDisplayType);

			/*display properties*/
			final VBox vboxDisplayProperties = new VBox(5);
			for (DisplayProperty displayProperty : configuration.getProject().getEditingDisplay().getDisplayProperties()) {
				final ValueEditor editor = ValueEditor.getEditor(displayProperty.getPropertyType(), ArmaDialogCreator.getApplicationData().getGlobalExpressionEnvironment());
				//todo add value listener
				if(editor.displayFullWidth()){
					HBox.setHgrow(editor.getRootNode(), Priority.ALWAYS);
				}

				vboxDisplayProperties.getChildren().add(new HBox(5, new Label(displayProperty.getName() + ":")));
			}
		}

		/*
		*
		* TAB INIT: Export Parameters
		*
		*/
		private void initTabExportParameters(Tab tabExportParameters) {
			final VBox tabRoot = new VBox(20);
			tabRoot.setPadding(padding10);
			tabExportParameters.setContent(tabRoot);

			/*set export directory*/
			final Label lblExportDirectory = new Label(Lang.Popups.ExportProject.ExportParameters.EXPORT_DIRECTORY);
			final FileChooserPane chooserPane = new FileChooserPane(ArmaDialogCreator.getPrimaryStage(), FileChooserPane.ChooserType.DIRECTORY,
					Lang.Popups.ExportProject.ExportParameters.LOCATE_EXPORT_DIRECTORY, configuration.getExportLocation());
			Tooltip.install(chooserPane, new Tooltip(Lang.Popups.ExportProject.ExportParameters.EXPORT_DIRECTORY_TOOLTIP));
			exportDirectoryObserver = chooserPane.getChosenFileObserver();
			chooserPane.setChosenFile(configuration.getExportLocation());
			exportDirectoryObserver.addValueListener(new ValueListener<File>() {
				@Override
				public void valueUpdated(@NotNull ValueObserver<File> observer, File oldValue, File newValue) {
					configuration.setExportLocation(newValue);
				}
			});
			tabRoot.getChildren().add(new VBox(5, lblExportDirectory, chooserPane));


			/*export macros to own file*/
			final CheckBox checkBoxExportMacrosToFile = new CheckBox(Lang.Popups.ExportProject.ExportParameters.EXPORT_MACROS_TO_FILE);
			checkBoxExportMacrosToFile.setTooltip(new Tooltip(Lang.Popups.ExportProject.ExportParameters.EXPORT_MACROS_TO_FILE_TOOLTIP));
			checkBoxExportMacrosToFile.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean selected) {
					configuration.setExportMacrosToFile(selected);
					exportMacrosToFileObserver.updateValue(selected);
					updateExportPreview();
				}
			});
			tabRoot.getChildren().add(checkBoxExportMacrosToFile);
		}

		/*
		*
		* TAB INIT: Export Preview
		*
		*/
		private void initTabExportPreview(Tab tabExportPreview) {
			final StackPane tabRoot = new StackPane();
			tabExportPreview.setContent(tabRoot);

			final SplitPane splitPane = new SplitPane();
			splitPane.setOrientation(Orientation.HORIZONTAL);
			tabRoot.getChildren().add(splitPane);

			final VBox vboxDisplayPreview = new VBox(5);
			final Label lblDisplayExportPreview = new Label("");
			final TextArea textAreaDisplay = new TextArea();
			textAreaDisplay.setEditable(false);
			vboxDisplayPreview.getChildren().add(lblDisplayExportPreview);
			vboxDisplayPreview.getChildren().add(textAreaDisplay);
			VBox.setVgrow(textAreaDisplay, Priority.ALWAYS);

			final VBox vboxMacrosPreview = new VBox(5);
			final Label lblMacrosExportPreview = new Label("");
			final TextArea textAreaMacros = new TextArea();
			textAreaMacros.setEditable(false);

			vboxMacrosPreview.getChildren().add(lblMacrosExportPreview);
			vboxMacrosPreview.getChildren().add(textAreaMacros);
			VBox.setVgrow(textAreaMacros, Priority.ALWAYS);

			classNameObserver.addValueListener(new ValueListener<String>() {
				@Override
				public void valueUpdated(@NotNull ValueObserver<String> observer, String oldValue, String newValue) {
					lblDisplayExportPreview.setText(ProjectExporter.getDisplayFileName(configuration));
					lblMacrosExportPreview.setText(ProjectExporter.getMacrosFileName(configuration));
				}
			});

			exportMacrosToFileObserver.addValueListener(new ValueListener<Boolean>() {
				@Override
				public void valueUpdated(@NotNull ValueObserver<Boolean> observer, Boolean oldValue, Boolean export) {
					if (export) {
						splitPane.getItems().add(vboxMacrosPreview);
					} else {
						splitPane.getItems().remove(vboxMacrosPreview);
					}
				}
			});

			updatePreviewGroup.addListener(new UpdateListener<Object>() {
				@Override
				public void update(Object data) {
					final ByteArrayOutputStream outDisplay = new ByteArrayOutputStream();
					final ByteArrayOutputStream outMacros = new ByteArrayOutputStream();
					try {
						ProjectExporter.export(configuration, outDisplay, outMacros);
					} catch (Exception e) {
						textAreaDisplay.setText("Could not export: " + e.getMessage());
						ExceptionHandler.error(e);
					}
					textAreaDisplay.setText(new String(outDisplay.toByteArray()));
					textAreaMacros.setText(new String(outMacros.toByteArray()));
					try {
						outDisplay.close();
						outMacros.close();
					} catch (Exception e) {
						ExceptionHandler.error(e);
					}
				}
			});

			splitPane.getItems().add(vboxDisplayPreview);
			if (configuration.shouldExportMacrosToFile()) {
				splitPane.getItems().add(vboxMacrosPreview);
			}
		}

		private void updateExportPreview() {
			updatePreviewGroup.update("");
		}

		@Override
		protected void cancel() {
			super.cancel();
			this.cancel = true;
		}

		@Override
		protected void help() {
			BrowserUtil.browse(HelpUrls.EXPORT);
		}

		@Nullable
		public ProjectExportConfiguration getConfiguration() {
			return cancel ? null : configuration;
		}
	}
}