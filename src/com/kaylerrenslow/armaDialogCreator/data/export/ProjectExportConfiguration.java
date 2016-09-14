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

import com.kaylerrenslow.armaDialogCreator.data.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 Created by Kayler on 09/13/2016.
 */
public class ProjectExportConfiguration {

	private final String headerFileName;
	private final String exportClassName;
	private final boolean exportMacrosToNewFile;
	private final File exportLocation;
	private final Project project;
	private final boolean placeAdcNotice;

	public ProjectExportConfiguration(@NotNull String headerFileName, @NotNull String exportClassName, boolean exportMacrosToNewFile, @NotNull File exportLocation, @NotNull Project project, boolean
			placeAdcNotice) {
		this.headerFileName = headerFileName;
		this.exportClassName = exportClassName;
		this.exportMacrosToNewFile = exportMacrosToNewFile;
		this.exportLocation = exportLocation;
		this.project = project;
		this.placeAdcNotice = placeAdcNotice;
	}

	@NotNull
	public String getHeaderFileName() {
		return headerFileName;
	}

	public boolean shouldPlaceAdcNotice() {
		return placeAdcNotice;
	}

	@NotNull
	public File getExportLocation() {
		return exportLocation;
	}

	@NotNull
	public String getExportClassName() {
		return exportClassName;
	}

	public boolean exportMacrosToNewFile() {
		return exportMacrosToNewFile;
	}

	@NotNull
	public Project getProject() {
		return project;
	}
}
