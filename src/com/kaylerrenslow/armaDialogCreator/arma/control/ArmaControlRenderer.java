package com.kaylerrenslow.armaDialogCreator.arma.control;

import com.kaylerrenslow.armaDialogCreator.arma.util.AColor;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.Region;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.ViewportComponent;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.ui.TextCanvasComponent;
import com.kaylerrenslow.armaDialogCreator.util.ValueListener;
import com.kaylerrenslow.armaDialogCreator.util.ValueObserver;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

/**
 @author Kayler
 Base class for JavaFX canvas rendering of arma controls
 Created on 05/20/2016. */
public class ArmaControlRenderer extends TextCanvasComponent implements ViewportComponent {
	private static final Font FONT = Font.font(20d);
	protected ArmaControl myControl;
	private ValueObserver<AColor> backgroundColorObserver;
	private boolean disablePaintFromCanvas;

	private ValueObserver<Boolean> enabledObserver = new ValueObserver<>(isEnabled());

	public ArmaControlRenderer() {
		super(0, 0, 0, 0);
		setFont(FONT);
		backgroundColorObserver = new ValueObserver<>(new AColor(backgroundColor));
		backgroundColorObserver.addValueListener(new ValueListener<AColor>(){
			@Override
			public void valueUpdated(@NotNull ValueObserver<AColor> observer, AColor oldValue, AColor newValue) {
				System.out.println("ArmaControlRenderer.valueUpdated newValue.toString()=" + newValue.toString());
			}
		});
	}

	final void setMyControl(ArmaControl myControl) {
		this.myControl = myControl;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		enabledObserver.updateValue(enabled);
	}

	public ValueObserver<Boolean> getEnabledObserver() {
		return enabledObserver;
	}

	public ArmaControl getMyControl() {
		return myControl;
	}

	public ValueObserver<AColor> getBackgroundColorObserver() {
		return backgroundColorObserver;
	}

	@Override
	public void translate(int dx, int dy) {
		super.translate(dx, dy);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void scale(int dxl, int dxr, int dyt, int dyb) {
		super.scale(dxl, dxr, dyt, dyb);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void setPosition(Region r) {
		super.setPosition(r);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void setPosition(int x1, int y1, int x2, int y2) {
		super.setPosition(x1, y1, x2, y2);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void setPositionWH(int x1, int y1, int width, int height) {
		super.setPositionWH(x1, y1, width, height);
		myControl.calcPositionFromRenderer();
	}

	/** Set the position without telling the control */
	public void setPositionWHSilent(int x1, int y1, int width, int height) {
		super.setPositionWH(x1, y1, width, height);
	}

	@Override
	public void setX1(int x1) {
		super.setX1(x1);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void setY1(int y1) {
		super.setY1(y1);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void setX2(int x2) {
		super.setX2(x2);
		myControl.calcPositionFromRenderer();
	}

	@Override
	public void setY2(int y2) {
		super.setY2(y2);
		myControl.calcPositionFromRenderer();
	}


	public void setX1Silent(int x1) {
		super.setX1(x1);
	}


	public void setY1Silent(int y1) {
		super.setY1(y1);
	}


	public void setX2Silent(int x2) {
		super.setX2(x2);
	}


	public void setY2Silent(int y2) {
		super.setY2(y2);
	}

	@Override
	public void setGhost(boolean ghost) {
		super.setGhost(ghost);
		myControl.getUpdateGroup().update(null);
	}

	@Override
	public void setPercentX(double percentX) {
		myControl.defineX(percentX);
		myControl.getUpdateGroup().update(null);
	}

	@Override
	public void setPercentY(double percentY) {
		myControl.defineY(percentY);
		myControl.getUpdateGroup().update(null);
	}

	@Override
	public void setPercentW(double percentW) {
		myControl.defineW(percentW);
		myControl.getUpdateGroup().update(null);
	}

	@Override
	public void setPercentH(double percentH) {
		myControl.defineH(percentH);
		myControl.getUpdateGroup().update(null);
	}

	@Override
	public double getPercentX() {
		return myControl.x;
	}

	@Override
	public double getPercentY() {
		return myControl.y;
	}

	@Override
	public double getPercentW() {
		return myControl.width;
	}

	@Override
	public double getPercentH() {
		return myControl.height;
	}

	@Override
	public int calcScreenX(double percentX) {
		return myControl.calcScreenX(percentX);
	}

	@Override
	public int calcScreenY(double percentY) {
		return myControl.calcScreenY(percentY);
	}

	@Override
	public int calcScreenWidth(double percentW) {
		return myControl.calcScreenWidth(percentW);
	}

	@Override
	public int calcScreenHeight(double percentH) {
		return myControl.calcScreenHeight(percentH);
	}

	@Override
	public void paint(GraphicsContext gc) {
		if (disablePaintFromCanvas) {
			return;
		}
		super.paint(gc);
	}

	/** Forces the paint on the given graphics context. @see ArmaControlRenderer#disablePaintFromCanvas(boolean) for more information as to why this method is needed. */
	public void forcePaint(GraphicsContext gc) {
		boolean old = disablePaintFromCanvas;
		disablePaintFromCanvas = false;
		paint(gc);
		disablePaintFromCanvas = old;
	}

	/** Since the control group's individual components are added to the canvas, we can't allow the default implementation of paint do anything. This item should be painted when the group is painted */
	public void disablePaintFromCanvas(boolean disable) {
		this.disablePaintFromCanvas = disable;
	}
}
