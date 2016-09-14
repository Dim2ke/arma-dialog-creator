/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.data.export;

import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaControl;
import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaDisplay;
import com.kaylerrenslow.armaDialogCreator.control.ControlClass;
import com.kaylerrenslow.armaDialogCreator.control.ControlProperty;
import com.kaylerrenslow.armaDialogCreator.control.Macro;
import com.kaylerrenslow.armaDialogCreator.control.PropertyType;
import com.kaylerrenslow.armaDialogCreator.control.sv.SerializableValue;
import com.kaylerrenslow.armaDialogCreator.data.Project;
import com.kaylerrenslow.armaDialogCreator.main.lang.Lang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.List;

/**
 Created by Kayler on 09/13/2016.
 */
public class ProjectExporter {
	private final Project project;
	private final ProjectExportConfiguration configuration;

	private static final String CONTROLS_BACKGROUND = "ControlsBackground";
	private static final String OBJECTS = "Objects";
	private static final String CONTROLS = "Controls";

	public ProjectExporter(@NotNull ProjectExportConfiguration configuration) {
		this.configuration = configuration;
		this.project = configuration.getProject();
	}

	public void export() throws IOException {
		if (!configuration.getExportLocation().isDirectory()) {
			throw new IllegalArgumentException("exportLocation is not a directory");
		}
		final String exportDirectoryPath = configuration.getExportLocation().getPath() + "/adc export " + configuration.getHeaderFileName() + "/";
		final File exportDirectory = new File(exportDirectoryPath);

		exportDirectory.mkdir();

		final File exportDisplayFile = new File(exportDirectoryPath + configuration.getHeaderFileName());
		exportDisplayFile.createNewFile();
		final FileOutputStream fosDisplay = getFos(exportDisplayFile);

		FileOutputStream fosMacros = null;
		if (configuration.exportMacrosToNewFile()) {
			final File macrosExportFile = new File(getMacrosExportFilePath(configuration));
			macrosExportFile.createNewFile();
			fosMacros = getFos(macrosExportFile);
		}

		export(fosDisplay, fosMacros);
	}

	public void export(@NotNull OutputStream displayOutputStream, @Nullable OutputStream macrosOutputStream) throws IOException {
		if (macrosOutputStream == null) {
			exportMacros(displayOutputStream); //save the macros inside the display header file
		} else {
			exportMacros(macrosOutputStream);
			if (configuration.shouldPlaceAdcNotice()) {
				writelnComment(displayOutputStream, Lang.Misc.ADC_EXPORT_NOTICE);
				writeln(displayOutputStream, "");
			}
		}

		exportDisplay(displayOutputStream);
	}

	private void exportDisplay(@NotNull OutputStream os) throws IOException {
		if (project.getProjectDescription() != null && project.getProjectDescription().length() > 0) {
			writelnComment(os, project.getProjectDescription());
		}
		ArmaDisplay display = project.getEditingDisplay();

		String displayBody = "";
		String body = "";
		for (ArmaControl control : display.getBackgroundControls()) {
			body += getExportClassString(control, 2);
		}
		displayBody += getExportClassString(CONTROLS_BACKGROUND, null, 1, body);

		body = "";
		for (ArmaControl control : display.getControls()) {
			body += getExportClassString(control, 2);
		}
		displayBody += getExportClassString(CONTROLS, null, 1, body);

		writeln(os, getExportClassString(configuration.getExportClassName(), null, 1, displayBody));

		//todo write movingEnable and other properties

	}

	private void exportClass(@NotNull OutputStream os, @NotNull ControlClass controlClass, int tab) throws IOException {
		writeln(os, getExportClassString(controlClass, tab));
	}

	private String getExportClassString(@NotNull ControlClass controlClass, int tab) {
		String body = getExportControlPropertyString(tab + 1, controlClass.getAllDefinedProperties());

		for (ControlClass subclass : controlClass.getAllSubClasses()) {
			body += getExportClassString(subclass, tab + 1);
		}

		return getExportClassString(controlClass.getClassName(), controlClass.getExtendClass() != null ? controlClass.getExtendClass().getClassName() : "", tab, body);
	}

	private String getExportClassString(@NotNull String className, @Nullable String extendClass, int tab, String body) {
		final String classFormatString = "class %s%s\n%3$s{\n%3$s%4$s%3$s};";
		return String.format(classFormatString, extendClass != null ? (" : " + extendClass) : "", className, tab(tab), body);
	}

	private void exportMacros(@NotNull OutputStream os) throws IOException {
		if (configuration.shouldPlaceAdcNotice()) {
			writelnComment(os, Lang.Misc.ADC_EXPORT_NOTICE);
		}

		writelnComment(os, "All macros for display " + configuration.getExportClassName());

		for (Macro macro : project.getMacroRegistry().getMacros()) {
			if (macro.getComment().length() != 0) {
				writelnComment(os, macro.getComment());
			}
			writeln(os, "#define " + macro.getKey() + " " + getExportValueString(macro.getValue(), macro.getPropertyType()));
		}

		os.flush();
		os.close();
	}

	private static String getExportControlPropertyString(int tabNum, @NotNull List<ControlProperty> controlProperties) {
		String body = "";
		final String tab = tab(tabNum);
		final String itemFormatString = tab + "%s = %s;\n";
		final String itemArrayFormatString = tab + "%s[] = %s;\n";
		for (ControlProperty property : controlProperties) {
			if (property.getValue() == null/* && editor.isOptional()*/) { //can allow for partial implementation, so we don't need to check if it is optional
				continue;
			}
			if (property.getValue().getAsStringArray().length == 1) {
				body += String.format(itemFormatString, property.getName(), getExportValueString(property.getValue(), property.getPropertyType()));
			} else {
				body += String.format(itemArrayFormatString, property.getName(), getExportValueString(property.getValue(), property.getPropertyType()));
			}
		}
		return body;
	}

	private static String tab(int num) {
		String s = "";
		for (int t = 0; t < num; t++) {
			s += "\t";
		}
		return s;
	}

	public static String getExportValueString(@NotNull SerializableValue value, @NotNull PropertyType type) {
		String[] arr = value.getAsStringArray();
		if (arr.length == 1) {
			if (type.exportHasQuotes) {
				return "\"" + arr[0] + "\"";
			}
			return arr[0];
		}
		String ret = "{";
		String v;
		for (int i = 0; i < arr.length; i++) {
			v = arr[i];
			if (type.exportHasQuotes) {
				v = "\"" + v + "\"";
			}
			ret += v + (i != arr.length - 1 ? "," : "");
		}
		return ret + "}";
	}

	private static void writelnComment(OutputStream os, String s) throws IOException {
		if (s.contains("\n")) {
			writeln(os, "/*");
			writeln(os, s);
			writeln(os, "*/");
		} else {
			writeln(os, "//" + s);
		}
	}

	private static void writeln(OutputStream fos, String s) throws IOException {
		fos.write((s + "\n").getBytes());
	}

	private static FileOutputStream getFos(@NotNull File file) {
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static String getMacrosExportFilePath(ProjectExportConfiguration configuration) {
		return configuration.getExportLocation().getPath() + "/" + configuration.getExportClassName() + "_Macros.h";
	}
}
