package com.kaylerrenslow.armaDialogCreator.data.xml;

import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaControl;
import com.kaylerrenslow.armaDialogCreator.data.Project;
import com.kaylerrenslow.armaDialogCreator.data.tree.TreeNode;
import com.kaylerrenslow.armaDialogCreator.data.tree.TreeStructure;
import com.kaylerrenslow.armaDialogCreator.util.DataContext;
import org.w3c.dom.Document;

/**
 A wrapper for a loader. Since the loader is the one that loads the xml document, this will wrap the XmlLoader. This class is meant to be extended so that each save version of projects has its own
 implementation.
 @author Kayler
 @since 08/07/2016. */
public abstract class ProjectVersionLoader {
	
	protected final DataContext dataContext;
	protected Project project;
	protected Document document;
	public final ProjectXmlLoader loader;
	protected final TreeStructure<ArmaControl> treeStructureMain = new TreeStructure.Simple<>(new TreeNode.Simple<>(null, "", false));
	protected final TreeStructure<ArmaControl> treeStructureBg = new TreeStructure.Simple<>(new TreeNode.Simple<>(null, "", false));
	
	
	protected ProjectVersionLoader(ProjectXmlLoader loader) throws XmlParseException {
		this.document = loader.document;
		this.loader = loader;
		this.dataContext = loader.dataContext;
	}
	
	public void addError(ParseError error) {
		loader.addError(error);
	}

	public void readDocument() throws XmlParseException {
		
	}
}
