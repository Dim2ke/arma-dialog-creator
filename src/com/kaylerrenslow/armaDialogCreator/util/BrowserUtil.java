/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.util;

import com.kaylerrenslow.armaDialogCreator.gui.fx.popup.StageDialog;
import com.kaylerrenslow.armaDialogCreator.main.ArmaDialogCreator;
import com.kaylerrenslow.armaDialogCreator.main.Lang;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.net.URI;

/**
 Created by Kayler on 05/26/2016.
 */
public class BrowserUtil {
	/** Attempts to open the browser at the specified url. If the operation succeeded, this method will return true. If the operation failed, will return false. */
	public static void browse(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			new StageDialog<>(ArmaDialogCreator.getPrimaryStage(), new TextArea(String.format(Lang.ApplicationBundle.getString("Misc.visit_link_in_browser_f"), url)), Lang.ApplicationBundle
					.getString("Popups.generic_popup_title"),
					false,
					true, false).show();
		}
	}
}
