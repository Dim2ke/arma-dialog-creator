/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.main;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 Created by Kayler on 10/16/2016.
 */
public class LocaleDescriptor {
	private Locale locale;

	public LocaleDescriptor(Locale locale) {
		this.locale = locale;
	}

	@NotNull
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		String displayCountry = locale.getDisplayCountry(locale);
		return locale.getDisplayLanguage(locale) + (displayCountry.length() == 0 ? "" : " (" + displayCountry + ")");
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof LocaleDescriptor) {
			LocaleDescriptor that = (LocaleDescriptor) o;
			return this.locale.equals(that.locale);
		}
		return false;
	}
}
