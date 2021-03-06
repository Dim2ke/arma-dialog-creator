package com.kaylerrenslow.armaDialogCreator.data;

import com.kaylerrenslow.armaDialogCreator.control.ControlPropertyLookupConstant;
import com.kaylerrenslow.armaDialogCreator.control.DefaultValueProvider;
import com.kaylerrenslow.armaDialogCreator.control.sv.SerializableValue;
import com.kaylerrenslow.armaDialogCreator.data.xml.DefaultValueXmlLoader;
import com.kaylerrenslow.armaDialogCreator.data.xml.XmlParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 @author Kayler
 @since 11/22/2016 */
public class ProjectDefaultValueProvider implements DefaultValueProvider {
	private static final String PREFIX = "/com/kaylerrenslow/armaDialogCreator/defaultValues/";
	private static final String DEFAULT = PREFIX + "_FallbackDefaultPropertyValues.xml";

	private ApplicationData data;
	private final Map<String, SerializableValue> valuesMap = new HashMap<>();

	public ProjectDefaultValueProvider(@NotNull ApplicationData data) {
		this.data = data;
	}

	@Nullable
	@Override
	public SerializableValue getDefaultValue(@NotNull ControlPropertyLookupConstant lookup) {
		return valuesMap.get(lookup.getPropertyName());
	}

	@Override
	public void prefetchValues(@NotNull List<ControlPropertyLookupConstant> tofetch, @Nullable Context context) {
		try {
			String path;
			if (context != null) {
				path = PREFIX + buildPath(context);
			} else {
				path = DEFAULT;
			}

			InputStream is = getClass().getResourceAsStream(path);

			if (is == null) {
				return;
			}
			DefaultValueXmlLoader loader = new DefaultValueXmlLoader(new InputStreamReader(is), data);

			for (ControlPropertyLookupConstant lookup : tofetch) {
				valuesMap.put(lookup.getPropertyName(), loader.fetchValue(lookup));
			}
		} catch (XmlParseException ignore) {

		}
	}

	private String buildPath(@NotNull Context context) {
		StringBuilder b = new StringBuilder();

		LinkedList<Context> reverse = new LinkedList<>();
		//Have the oldest ancestor (parent) be at index 0 and the newest child context at the end.
		//This will make it so that if a ControlTypeContext is a parent of a ControlClassNameContext, the string will be
		//built as such: ControlType_ClassName
		{
			Context cur = context;
			do {
				reverse.addFirst(cur);
				cur = cur.getParentContext();
			} while (cur != null);
		}
		for (Context cur : reverse) {
			if (cur instanceof DefaultValueProvider.ControlTypeContext) {
				b.append(((ControlTypeContext) cur).getControlType().name());
			} else if (cur instanceof ControlClassNameContext) {
				b.append(((ControlClassNameContext) cur).getControlClassName());
			}
			if (cur != reverse.getLast()) {
				b.append("/");
			}
		}
		b.append(".xml");
		return b.toString();
	}

	@Override
	public void cleanup() {
		valuesMap.clear();
	}
}
