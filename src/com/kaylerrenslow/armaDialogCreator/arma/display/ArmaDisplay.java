/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.arma.display;

import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaControl;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.Display;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.Resolution;
import com.kaylerrenslow.armaDialogCreator.util.ReadOnlyList;
import com.kaylerrenslow.armaDialogCreator.util.UpdateListener;
import com.kaylerrenslow.armaDialogCreator.util.UpdateListenerGroup;

import java.util.ArrayList;
import java.util.List;

/**
 @author Kayler
 Interface that specifies something that is displayable in preview and in Arma 3 (title, dialog, display)
 Created on 06/14/2016. */
public class ArmaDisplay implements Display<ArmaControl>{

	private int idd;
	private boolean movingEnable, enableSimulation;
	private final List<ArmaControl> controls = new ArrayList<>();
	private final ReadOnlyList<ArmaControl> controlReadOnlyList = new ReadOnlyList<>(controls);
	private final List<ArmaControl> bgControls = new ArrayList<>();
	private final ReadOnlyList<ArmaControl> bgControlReadOnlyList = new ReadOnlyList<>(bgControls);
		
	private UpdateListenerGroup<Object> updateGroup = new UpdateListenerGroup<>();
	private final UpdateListener<Object> controlListener = new UpdateListener<Object>(){
		@Override
		public void update(Object data) {
			updateGroup.update(data);
		}
	};
		
	public ArmaDisplay(int idd) {
		this.idd = idd;
	}

	public int getIdd() {
		return idd;
	}

	public void setIdd(int idd) {
		this.idd = idd;
	}

	/** Return true if the display/dialog is allowed to move. If it isn't, return false. */
	public boolean movingEnabled() {
		return movingEnable;
	}

	public void setMovingEnable(boolean movingEnable) {
		this.movingEnable = movingEnable;
	}

	/** Return true if the display/dialog has user interaction. If no interaction is allowed, return false. */
	public boolean simulationEnabled() {
		return enableSimulation;
	}

	public void setEnableSimulation(boolean enableSimulation) {
		this.enableSimulation = enableSimulation;
	}
		
	public UpdateListenerGroup<Object> getUpdateListenerGroup() {
		return updateGroup;
	}
	
	@Override
	public ReadOnlyList<ArmaControl> getBackgroundControls() {
		return bgControlReadOnlyList;
	}
	
	@Override
	public void addBackgroundControl(ArmaControl control) {
		bgControls.add(control);
		control.getUpdateGroup().addListener(controlListener);
	}
	
	@Override
	public void addBackgroundControl(int index, ArmaControl toAdd) {
		bgControls.add(index, toAdd);
		toAdd.getUpdateGroup().addListener(controlListener);
	}
	
	@Override
	public int indexOfBackgroundControl(ArmaControl control) {
		return bgControls.indexOf(control);
	}
	
	@Override
	public boolean removeBackgroundControl(ArmaControl control) {
		if (bgControls.remove(control)) {
			control.getUpdateGroup().removeUpdateListener(controlListener);
			return true;
		}
		return false;
	}
	
	@Override
	public void resolutionUpdate(Resolution newResolution) {
		for(ArmaControl control : bgControls){
			control.resolutionUpdate(newResolution);
		}
		for(ArmaControl control : controls){
			control.resolutionUpdate(newResolution);
		}
	}
	
	/** Get all controls. If simulation isn't enabled, return the controls regardless. */
	public ReadOnlyList<ArmaControl> getControls() {
		return controlReadOnlyList;
	}
	
	@Override
	public void addControl(ArmaControl control) {
		controls.add(control);
		control.getUpdateGroup().addListener(controlListener);
	}
	
	@Override
	public void addControl(int index, ArmaControl toAdd) {
		controls.add(index, toAdd);
		toAdd.getUpdateGroup().addListener(controlListener);
	}
	
	@Override
	public int indexOf(ArmaControl control) {
		return controls.indexOf(control);
	}
	
	@Override
	public boolean removeControl(ArmaControl control) {
		if (controls.remove(control)) {
			control.getUpdateGroup().removeUpdateListener(controlListener);
			return true;
		}
		return false;
	}
	
	
}
