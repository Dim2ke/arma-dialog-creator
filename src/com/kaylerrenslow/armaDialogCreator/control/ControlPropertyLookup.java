package com.kaylerrenslow.armaDialogCreator.control;

import com.kaylerrenslow.armaDialogCreator.arma.util.AColor;
import com.kaylerrenslow.armaDialogCreator.arma.util.AFont;
import com.kaylerrenslow.armaDialogCreator.arma.util.AHexColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 Created by Kayler on 05/22/2016.
 */
public enum ControlPropertyLookup {
	IDC(0, "idc", ControlProperty.PropertyType.INT, "Control id, or -1 if doesn't matter."),
	X(1, "x", ControlProperty.PropertyType.FLOAT, "X position."),
	Y(2, "y", ControlProperty.PropertyType.FLOAT, "Y position."),
	W(3, "w", ControlProperty.PropertyType.FLOAT, "Width of control."),
	H(4, "h", ControlProperty.PropertyType.FLOAT, "Height of control."),
	TYPE(5, "type", ControlProperty.PropertyType.INT, "Type of the control."),
	STYLE(6, "style", ControlProperty.PropertyType.INT, "Style of the control."),
	ACCESS(7, "access", ControlProperty.PropertyType.INT, "Read and write setting.", new ControlPropertyOption("Read and Write", "0", "Default case where properties can still be added or overridden."), new ControlPropertyOption("Read and Create", "1", "Only allows creating new properties."), new ControlPropertyOption("Read Only", "2", "Does not allow to do anything in deriving classes."), new ControlPropertyOption("Read Only Verified", "3", "Does not allow to do anything either in deriving classes, and a CRC check will be performed.")),

	/*Common*/
	/** moving: boolean. Set whether control can be dragged */
	MOVING(8, "moving", ControlProperty.PropertyType.BOOLEAN, "Whether or not this control can be dragged."),
	/** sizeEx: float. Set font size */
	SIZE_EX(9, "sizeEx", ControlProperty.PropertyType.FLOAT, "Font size of text."),
	FONT(10, "font", ControlProperty.PropertyType.FONT, "Font for text."),
	COLOR_TEXT(11, "colorText", ControlProperty.PropertyType.COLOR, "Color of text."),
	COLOR_BACKGROUND(12, "colorBackground", ControlProperty.PropertyType.COLOR, "Background color of control."),
	TEXT(13, "text", ControlProperty.PropertyType.STRING, "Text to show."),
	SHADOW(14, "shadow", ControlProperty.PropertyType.INT, "Shadow for control.", new ControlPropertyOption("No", "0", "No shadow."), new ControlPropertyOption("Yes", "1", "Drop shadow with soft edges."), new ControlPropertyOption("Stroke", "2", "Stroke")), //does absolutely nothing inside the Attributes class for structured text
	TOOLTIP(15, "tooltip", ControlProperty.PropertyType.STRING, "Text to display when mouse hovers over this control."),
	TOOLTIP_COLOR_SHADE(16, "tooltipColorShade", ControlProperty.PropertyType.COLOR, "Tooltip background color."),
	TOOLTIP_COLOR_TEXT(17, "tooltipColorText", ControlProperty.PropertyType.COLOR, "Tooltip text color."),
	TOOLTIP_COLOR_BOX(18, "tooltipColorBox", ControlProperty.PropertyType.COLOR, "Tooltip border color."),
	ALIGN(19, "align", ControlProperty.PropertyType.STRING, "Horizontal align of text.", new ControlPropertyOption("Left", "left", "Left align."), new ControlPropertyOption("Center", "center", "Center align."), new ControlPropertyOption("Right", "right", "Right align.")),
	VALIGN(20, "valign", ControlProperty.PropertyType.STRING, "Vertical align of text.", new ControlPropertyOption("Top", "top", "Top align."), new ControlPropertyOption("Middle", "middle", "Middle align."), new ControlPropertyOption("Bottom", "bottom", "Bottom align.")),
	COLOR_HEX(21, "color", ControlProperty.PropertyType.HEX_COLOR_STRING, "Text color."),
	SHADOW_COLOR(22, "shadowColor", ControlProperty.PropertyType.HEX_COLOR_STRING, "Shadow color."), //default shadow color
	BLINKING_PERIOD(23, "blinkingPeriod", ControlProperty.PropertyType.FLOAT, "Makes the text start transparent, go to full opacity and back to full transparent in the amount of seconds specified."),
	SOUND_CLICK(54, "soundClick", ControlProperty.PropertyType.SOUND, "Sound to play when mouse button is released."),
	SOUND_ENTER(55, "soundEnter", ControlProperty.PropertyType.SOUND, "Sound to play when mouse cursor is moved over the control."),
	SOUND_ESCAPE(56, "soundEscape", ControlProperty.PropertyType.SOUND, "Sound to play when the control was clicked via the mouse, and then released outside the control area."),
	SOUND_PUSH(57, "soundPush", ControlProperty.PropertyType.SOUND, "Sound to play when mouse is clicked on control."),

	/*Static*/
	STATIC_AUTO_PLAY(24, "autoPlay", ControlProperty.PropertyType.BOOLEAN, "Autoplay the video (video only)."),
	STATIC_KEY(25, "key", ControlProperty.PropertyType.STRING, "From the wiki:\"a possibly quite useless xbox value\"."),
	STATIC_LOOPS(26, "loops", ControlProperty.PropertyType.INT, "Number of times the video loops."),
	STATIC_LINE_SPACING(27, "lineSpacing", ControlProperty.PropertyType.FLOAT, "Line spacing of text and is required if style is MULTI (16)"),
	STATIC_FIXED_WIDTH(28, "fixedWidth", ControlProperty.PropertyType.BOOLEAN, null),

	/*Structured Text*/
	STRUCT_TEXT_SIZE(29, "size", ControlProperty.PropertyType.FLOAT, "Size of text. If 1, size will be value of parent class."),

	/*HTML*/
	HTML_CYCLE_LINKS(30, "cyclelinks", ControlProperty.PropertyType.BOOLEAN, null),
	HTML_FILE_NAME(31, "filename", ControlProperty.PropertyType.FILE_NAME, "HTML file to load into control at startup."),
	HTML_COLOR_BOLD(32, "colorBold", ControlProperty.PropertyType.COLOR, "Text color of inside <b></b> (bold text)."),
	HTML_COLOR_LINK(33, "colorLink", ControlProperty.PropertyType.COLOR, "Text color of inside <a href='#section'></a> (link text)."),
	HTML_COLOR_LINK_ACTIVE(34, "colorLinkActive", ControlProperty.PropertyType.COLOR, "Text color of an active link."),
	HTML_COLOR_PICTURE(35, "colorPicture", ControlProperty.PropertyType.COLOR, "Color of transparent part of image."),
	HTML_COLOR_PICTURE_BORDER(36, "colorPictureBorder", ControlProperty.PropertyType.COLOR, "Color of border around image."),
	HTML_COLOR_PICTURE_LINK(37, "colorPictureLink", ControlProperty.PropertyType.COLOR, "Color of transparent part of image that is inside link."),
	HTML_COLOR_PICTURE_SELECTED(38, "colorPictureSelected", ControlProperty.PropertyType.COLOR, "Color of transparent part of image that is within an active link."),
	HTML_PREV_PAGE(39, "prevPage", ControlProperty.PropertyType.IMAGE, "File name of image which is used for left arrow."),
	HTML_NEXT_PAGE(40, "nextPage", ControlProperty.PropertyType.IMAGE, "File name of image which is used for right arrow."),

	/*Button*/
	BTN_ACTION(41, "action", ControlProperty.PropertyType.SQF, "Script command(s) to execute when button is pressed. Variable 'this' contains unit that pressed button."),
	BTN_BORDER_SIZE(42, "borderSize", ControlProperty.PropertyType.FLOAT, "If > 0 then a background (in the color defined in 'colorBorder') is drawn behind the button. It extends to the left by the distance defined here, its height is slightly less than that of the button, and it is vertically centered. The width extends to the right, to where the drop shadow starts. Stays static when button is pressed."),
	BTN_COLOR_BACKGROUND_ACTIVE(43, "colorBackgroundActive", ControlProperty.PropertyType.COLOR, "Button's background color if 'active' (mouse pointer is over it)."),
	BTN_COLOR_BACKGROUND_DISABLED(44, "colorBackgroundDisabled", ControlProperty.PropertyType.COLOR, "Button's background color if disabled."),
	BTN_COLOR_BORDER(45, "colorBorder", ControlProperty.PropertyType.COLOR, "Color of left border that is defined in 'borderSize'."),
	BTN_COLOR_DISABLED(46, "colorDisabled", ControlProperty.PropertyType.COLOR, "Text color if button is disabled."),
	BTN_COLOR_FOCUSED(47, "colorFocused", ControlProperty.PropertyType.COLOR, "Alternating background color. While the control has focus (but without the mouse pointer being over it) the background will cycle between 'colorFocused' and 'colorBackground'. If both are the same, then the color will be steady."),
	BTN_COLOR_SHADOW(48, "colorShadow", ControlProperty.PropertyType.COLOR, "Color of drop shadow behind button. This color is not visible when button is disabled."),
	BTN_DEFAULT(49, "default", ControlProperty.PropertyType.BOOLEAN, "Whether or not the button will have focus upon loading the dialog."),
	BTN_OFFSET_PRESSED_X(50, "offsetPressedX", ControlProperty.PropertyType.FLOAT, "The button's text & background will move by this distance when pressed (ff a shadow is defined, it will stay in place)."),
	BTN_OFFSET_PRESSED_Y(51, "offsetPressedY", ControlProperty.PropertyType.FLOAT, "The button's text & background will move by this distance when pressed (ff a shadow is defined, it will stay in place)."),
	BTN_OFFSET_X(52, "offsetX", ControlProperty.PropertyType.FLOAT, "Horizontal and vertical offset of drop shadow. if 0, then shadow will be placed directly behind button."),
	BTN_OFFSET_Y(53, "offsetY", ControlProperty.PropertyType.FLOAT, "Horizontal and vertical offset of drop shadow. if 0, then shadow will be placed directly behind button."),
	// a note on toolTip: can be tooltip
	/* ..Shortcut Button */
	BTN_ANIM_TEXTURE_NORMAL(58, "animTextureNormal", ControlProperty.PropertyType.TEXTURE, null),
	BTN_ANIM_TEXTURE_DISABLED(59, "animTextureDisabled", ControlProperty.PropertyType.TEXTURE, null),
	BTN_ANIM_TEXTURE_OVER(60, "animTextureOver", ControlProperty.PropertyType.TEXTURE, null),
	BTN_ANIM_TEXTURE_FOCUSED(61, "animTextureFocused", ControlProperty.PropertyType.TEXTURE, null),
	BTN_ANIM_TEXTURE_PRESSED(62, "animTexturePressed", ControlProperty.PropertyType.TEXTURE, null),
	BTN_ANIM_TEXTURE_DEFAULT(63, "animTextureDefault", ControlProperty.PropertyType.TEXTURE, null),
	BTN_TEXTURE_NO_SHORTCUT(64, "textureNoShortcut", ControlProperty.PropertyType.TEXTURE, null),
	BTN_COLOR2(65, "color2", ControlProperty.PropertyType.COLOR, null),
	BTN_COLOR_BACKGROUND2(66, "colorBackground2", ControlProperty.PropertyType.COLOR, null),
	BTN_PERIOD(67, "period", ControlProperty.PropertyType.FLOAT, null),
	BTN_PERIOD_FOCUS(68, "periodFocus", ControlProperty.PropertyType.FLOAT, null),
	BTN_PERIOD_OVER(69, "periodOver", ControlProperty.PropertyType.FLOAT, null),

	/*Active Text*/
	AT_ACTION(70, "action", ControlProperty.PropertyType.SQF, "Script command(s) to execute when text is clicked."),
	AT_CAN_DRAG(71, "canDrag", ControlProperty.PropertyType.BOOLEAN, null),
	AT_COLOR(72, "color", ControlProperty.PropertyType.COLOR, "Text color and underline color."),
	AT_COLOR_ACTIVE(73, "colorActive", ControlProperty.PropertyType.COLOR, "Text and underline color when mouse is over the active text."),
	AT_COLOR_SHADE(74, "colorShade", ControlProperty.PropertyType.COLOR, null),
	AT_COLOR_FOCUSED(75, "colorFocused", ControlProperty.PropertyType.COLOR, null),
	AT_COLOR_DISABLED(76, "colorDisabled", ControlProperty.PropertyType.COLOR, null),
	AT_COLOR_BACKGROUND2(77, "colorBackground2", ControlProperty.PropertyType.COLOR, null),
	AT_DEFAULT(78, "default", ControlProperty.PropertyType.BOOLEAN, "Whether or not the active text will have focus upon loading the dialog."),
	AT_PICTURE_WIDTH(79, "pictureWidth", ControlProperty.PropertyType.FLOAT, null),
	AT_PICTURE_HEIGHT(80, "pictureHeight", ControlProperty.PropertyType.FLOAT, null),
	AT_SIDE_DISABLED(81, "sideDisabled", ControlProperty.PropertyType.COLOR, null),
	AT_PICTURE(82, "picture", ControlProperty.PropertyType.TEXTURE, null),
	AT_SIDE_TOGGLE(83, "sideToggle", ControlProperty.PropertyType.COLOR, null),
	AT_TEXT_HEIGHT(84, "textHeight", ControlProperty.PropertyType.FLOAT, null),

	/*Edit*/
	EDIT_AUTO_COMPLETE(85, "autocomplete", ControlProperty.PropertyType.STRING, "Auto-completion option.", new ControlPropertyOption("None", "", "No auto-completion."), new ControlPropertyOption("Script", "scripting", "Auto-completion set for scripting."), new ControlPropertyOption("General", "general", "Auto-completion on normal words.")),
	EDIT_HTML_CONTROL(86, "htmlControl", ControlProperty.PropertyType.BOOLEAN, "If used together with style=ST_MULTI, allows multi-line editable text fields."),
	EDIT_LINE_SPACING(87, "lineSpacing", ControlProperty.PropertyType.FLOAT, "Line spacing and this is required if style is MULTI (16)"),
	EDIT_COLOR_SELECTION(88, "colorSelection", ControlProperty.PropertyType.COLOR, null),
	EDIT_SIZE(89, "size", ControlProperty.PropertyType.FLOAT, "From the wiki: \"possibly a typo, perhaps irrelevant xbox property\"."),

	/*Sliders*/
	SLIDE_ARROW_EMPTY(90, "arrowEmpty", ControlProperty.PropertyType.TEXTURE, null),
	SLIDE_ARROW_FULL(91, "arrowFull", ControlProperty.PropertyType.TEXTURE, null),
	SLIDE_BORDER(92, "border", ControlProperty.PropertyType.TEXTURE, null),
	SLIDE_ACTIVE(93, "colorActive", ControlProperty.PropertyType.COLOR, "Color of the arrows."),
	SLIDE_DISABLED(94, "colorDisabled", ControlProperty.PropertyType.COLOR, null),
	SLIDE_THUMB(95, "thumb", ControlProperty.PropertyType.TEXTURE, null),
	SLIDE_VSPACING(96, "vspacing", ControlProperty.PropertyType.FLOAT, null),

	/*Combo*/
	COMBO_ARROW_EMPTY(97, "arrowEmpty", ControlProperty.PropertyType.TEXTURE, null),
	COMBO_ARROW_FULL(98, "arrowFull", ControlProperty.PropertyType.TEXTURE, null),
	COMBO_COLOR(99, "color", ControlProperty.PropertyType.COLOR, "Color of the control surrounding lines."),
	COMBO_ACTIVE(100, "colorActive", ControlProperty.PropertyType.COLOR, null),
	COMBO_DISABLED(101, "colorDisabled", ControlProperty.PropertyType.COLOR, null),
	COMBO_COLOR_SCROLL_BAR(102, "colorScrollbar", ControlProperty.PropertyType.COLOR, null),
	COMBO_COLOR_SELECT(103, "colorSelect", ControlProperty.PropertyType.COLOR, "Color of selected text."),
	COMBO_COLOR_SELECT_BACKGROUND(104, "colorSelectBackground", ControlProperty.PropertyType.COLOR, "Background color of selected text."),
	COMBO_FROM(105, "from", ControlProperty.PropertyType.FLOAT, null),
	COMBO_TO(106, "to", ControlProperty.PropertyType.FLOAT, null),
	COMBO_MAX_HISTORY_DELAY(107, "maxHistoryDelay", ControlProperty.PropertyType.FLOAT, null),
	COMBO_ROW_HEIGHT(108, "rowHeight", ControlProperty.PropertyType.FLOAT, "Height of a single row in the elapsed box."),
	COMBO_SOUND_SELECT(109, "soundSelect", ControlProperty.PropertyType.SOUND, null),
	COMBO_SOUND_COLLAPSE(110, "soundCollapse", ControlProperty.PropertyType.SOUND, null),

	/*Listboxes*/
	LB_ACTIVE(111, "active", ControlProperty.PropertyType.BOOLEAN, null),
	LB_AUTO_SCROLL(112, "autoScroll", ControlProperty.PropertyType.INT, null),
	LB_ARROW_EMPTY(113, "arrowEmpty", ControlProperty.PropertyType.TEXTURE, null),
	LB_ARROW_FULL(114, "arrowFull", ControlProperty.PropertyType.TEXTURE, null),
	LB_BORDER(115, "border", ControlProperty.PropertyType.TEXTURE, null),
	LB_CAN_DRAG(116, "canDrag", ControlProperty.PropertyType.BOOLEAN, null),
	LB_COLLISION_COLOR(117, "collisionColor", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_SCROLL_BAR(118, "colorScrollbar", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR(119, "color", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_PLAYER_ITEM(120, "colorPlayerItem", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_ACTIVE(121, "colorActive", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_DISABLED(122, "colorDisabled", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_SELECT(123, "colorSelect", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_SELECT2(124, "colorSelect2", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_SELECT_BACKGROUND(125, "colorSelectBackground", ControlProperty.PropertyType.COLOR, null),
	LB_COLOR_SELECT_BACKGROUND2(126, "colorSelectBackground2", ControlProperty.PropertyType.COLOR, null),
	LB_COLUMNS(127, "columns", ControlProperty.PropertyType.ARRAY, "Float array and defines the left starting position of each column. The values are offsets ratios (not spacing ratios). Tip: Use {-0.01} in first column to fix unwanted offset, if desired."),
	LB_DISABLED(128, "disabled", ControlProperty.PropertyType.BOOLEAN, null),
	LB_ENABLED(129, "enabled", ControlProperty.PropertyType.BOOLEAN, null),
	LB_DISABLED_CTRL_COLOR(130, "disabledCtrlColor", ControlProperty.PropertyType.COLOR, null),
	LB_DISABLED_KEY_COLOR(131, "disabledKeyColor", ControlProperty.PropertyType.COLOR, null),
	LB_DRAW_SIDE_ARROWS(132, "drawSideArrows", ControlProperty.PropertyType.BOOLEAN, "Each row can be linked to 2 arrow buttons which are shown on the left and right of the row."),
	LB_IDC_LEFT(133, "idcLeft", ControlProperty.PropertyType.INT, "The IDC of the control to be used for the left button."),
	LB_IDC_RIGHT(134, "idcRight", ControlProperty.PropertyType.INT, "The IDC of the control to be used for the right button."),
	LB_MAIN_COLUMNW(135, "mainCollumW", ControlProperty.PropertyType.FLOAT, null),
	LB_SECOND_COLUMNW(136, "secndCollumW", ControlProperty.PropertyType.FLOAT, null),
	LB_LINE_SPACING(138, "lineSpacing", ControlProperty.PropertyType.FLOAT, null),
	LB_MAX_HISTORY_DELAY(139, "maxHistoryDelay", ControlProperty.PropertyType.FLOAT, null),
	LB_ROW_HEIGHT(140, "rowHeight", ControlProperty.PropertyType.FLOAT, "The height of a single row in the elapsed box."),
	LB_ROWS(141, "rows", ControlProperty.PropertyType.INT, null),

	/*event handlers*/
	EVENT_ON_LOAD(1000, "onLoad", ControlProperty.PropertyType.EVENT, strArr("Display and all controls are created, but no action on any is taken.", "Returns the display.", priority(1), "Display")),
	EVENT_ON_UNLOAD(1001, "onUnload", ControlProperty.PropertyType.EVENT, strArr("Display is closed, but no controls are destroyed yet.", "Returns the display and exit code.", priority(1), "Display")),
	EVENT_ON_CHILD_DESTROYED(1002, "onChildDestroyed", ControlProperty.PropertyType.EVENT, strArr("Child display is closed.", "Returns the display, which child display was closed and exit code.", priority(1), "Display")),
	EVENT_ON_MOUSE_ENTER(1003, "onMouseEnter", ControlProperty.PropertyType.EVENT, strArr("The mouse pointer enters the control area.", "Returns control.", priority(1), "Control")),
	EVENT_ON_MOUSE_EXIT(1004, "onMouseExit", ControlProperty.PropertyType.EVENT, strArr("The mouse pointer exits the control area.", "Returns control.", priority(1), "Control")),
	EVENT_ON_SET_FOCUS(1005, "onSetFocus", ControlProperty.PropertyType.EVENT, strArr("Input focus is on control. It now begins to accept keyboard input.", "Returns control.", priority(2), "Control")),
	EVENT_ON_KILL_FOCUS(1006, "onKillFocus", ControlProperty.PropertyType.EVENT, strArr("Input focus is no longer on control. It no longer accepts keyboard input.", "Returns control.", priority(2), "Control")),
	EVENT_ON_TIMER(1007, "onTimer", ControlProperty.PropertyType.EVENT, strArr("After amount of time given by setTimer function.", "Returns control.", priority(3), "Control")),
	EVENT_ON_KEY_DOWN(1008, "onKeyDown", ControlProperty.PropertyType.EVENT, strArr("Pressing any keyboard key. Fired before the onKeyUp event.", "Returns the control, the keyboard code and the state of Shift, Ctrl and Alt.", priority(2), "Display, Control")),
	EVENT_ON_KEY_UP(1009, "onKeyUp", ControlProperty.PropertyType.EVENT, strArr("Releasing any keyboard key. Fired after the onKeyDown event.", "Returns the control, the keyboard code and the state of Shift, Ctrl and Alt.", priority(2), "Display, Control")),
	EVENT_ON_CHAR(1010, "onChar", ControlProperty.PropertyType.EVENT, strArr("When some readable characters is recognised.", "Returns the control and the char code.", priority(2), "Control")),
	EVENT_ON_IME_CHAR(1011, "onIMEChar", ControlProperty.PropertyType.EVENT, strArr("When IME character is recognized (used in Korean and other eastern languages).", "Returns the control and the char code.", priority(2), "Control")),
	EVENT_ON_IME_COMPOSITION(1012, "onIMEComposition", ControlProperty.PropertyType.EVENT, strArr("When partial IME character is recognized (used in Korean and other eastern languages).", "Returns the control and the char code.", priority(2), "Control")),
	EVENT_ON_JOYSTICK_BUTTON(1013, "onJoystickButton", ControlProperty.PropertyType.EVENT, strArr("Pressing and releasing any joystick button.", "Not in Arma 2 or Arma 3 Returns the control and the the pressed button.", priority(3), "Control")),
	EVENT_ON_MOUSE_BUTTON_DOWN(1014, "onMouseButtonDown", ControlProperty.PropertyType.EVENT, strArr("Pressing a mouse button. Followed by the onMouseButtonUp event.", "Returns the control, the pressed button, the x and y coordinates and the state of Shift, Ctrl and Alt.", priority(2), "Control")),
	EVENT_ON_MOUSE_BUTTON_UP(1015, "onMouseButtonUp", ControlProperty.PropertyType.EVENT, strArr("Releasing a mouse button. Follows the onMouseButtonDown event.", "Returns the control, the pressed button, the x and y coordinates and the state of Shift, Ctrl and Alt.", priority(2), "Control")),
	EVENT_ON_MOUSE_BUTTON_CLICK(1016, "onMouseButtonClick", ControlProperty.PropertyType.EVENT, strArr("Pressing and releasing a mouse button.", "Returns the control, the pressed button, the x and y coordinates and the state of Shift, Ctrl and Alt.", priority(2), "ListBox, ComboBox, TextBox, Button, ActiveText")),
	EVENT_ON_MOUSE_BUTTON_DBL_CLICK(1017, "onMouseButtonDblClick", ControlProperty.PropertyType.EVENT, strArr("Pressing and releasing a mouse button twice within very short time.", "Returns the control, the pressed button, the x and y coordinates and the state of Shift, Ctrl and Alt.", priority(2), "Control")),
	EVENT_ON_MOUSE_MOVING(1018, "onMouseMoving", ControlProperty.PropertyType.EVENT, strArr("Fires continuously while moving the mouse with a certain interval.", "Returns the control, the x and y coordinates relative to control and mouseOver.", priority(2), "Control")),
	EVENT_ON_MOUSE_HOLDING(1019, "onMouseHolding", ControlProperty.PropertyType.EVENT, strArr("Fires continuously while mouse is not moving with a certain interval.", "Returns the display, the some kind of x and y delta position.", priority(2), "Display")),
	EVENT_ON_MOUSE_ZCHANGED(1020, "onMouseZChanged", ControlProperty.PropertyType.EVENT, strArr("Fires when mouse wheel position is changed. Does not fire on disabled control. Checked with CT_EDIT type in v1.50.", "Returns the control, the x and y coordinates and mouseOver. If used with a display, the mouseOver parameter is excluded.", priority(2), "Control")),
	EVENT_ON_CAN_DESTROY(1021, "onCanDestroy", ControlProperty.PropertyType.EVENT, strArr("Ask this control if dialog can be closed (used for validation of contained entry).", "Returns the control and the change of the scrollbar.", priority(3), "Control only")),
	EVENT_ON_DESTROY(1022, "onDestroy", ControlProperty.PropertyType.EVENT, strArr("Destroying control", "Returns the control and exit code.", priority(3), "Control")),
	EVENT_ON_BUTTON_CLICK(1023, "onButtonClick", ControlProperty.PropertyType.EVENT, strArr("The attached button action is performed. When returned value is true, button's display remains opened.", "Returns the control and exit code.", priority(1), "Control")),
	EVENT_ON_BUTTON_DBL_CLICK(1024, "onButtonDblClick", ControlProperty.PropertyType.EVENT, strArr("?", "Returns control.", priority(-1), "Button")),
	EVENT_ON_BUTTON_DOWN(1025, "onButtonDown", ControlProperty.PropertyType.EVENT, strArr("The left mouse button is pressed over the button area or a key on the keyboard is pressed.", "Arma 3", priority(1), "Button")),
	EVENT_ON_BUTTON_UP(1026, "onButtonUp", ControlProperty.PropertyType.EVENT, strArr("The left mouse buttons is released outside the button area and the attached button action is not performed.", "Returns control.", priority(1), "Button")),
	EVENT_ON_LB_SEL_CHANGED(1027, "onLBSelChanged", ControlProperty.PropertyType.EVENT, strArr("The selection in a listbox is changed. The left mouse button has been released and the new selection is fully made.", "Returns control.", priority(2), "Button")),
	EVENT_ON_LB_LIST_SEL_CHANGED(1028, "onLBListSelChanged", ControlProperty.PropertyType.EVENT, strArr("Selection in XCombo box changed (but value is not stored yet).", "Returns the control and the selected element index.", priority(2), "Listbox")),
	EVENT_ON_LB_DBL_CLICK(1029, "onLBDblClick", ControlProperty.PropertyType.EVENT, strArr("Double click on some row in listbox.", "Returns the control and the selected element index.", priority(2), "Listbox")),
	EVENT_ON_LB_DRAG(1030, "onLBDrag", ControlProperty.PropertyType.EVENT, strArr("Drag & drop operation started.", "Returns the control and the selected element index.", priority(2), "Listbox")),
	EVENT_ON_LB_DRAGGING(1031, "onLBDragging", ControlProperty.PropertyType.EVENT, strArr("Drag & drop operation is in progress.", "Returns the control and the selected element index.", priority(2), "Listbox")),
	EVENT_ON_LB_DROP(1032, "onLBDrop", ControlProperty.PropertyType.EVENT, strArr("Drag & drop operation finished.", "Returns the control and the x and y coordinates.", priority(2), "Listbox")),
	EVENT_ON_TREE_SEL_CHANGED(1033, "onTreeSelChanged", ControlProperty.PropertyType.EVENT, strArr("Changing the selection in a tree.", "Returns the control and the x and y coordinates.", priority(2), "Listbox, Combobox, Textbox, ActiveText, Button")),
	EVENT_ON_TREE_LBUTTON_DOWN(1034, "onTreeLButtonDown", ControlProperty.PropertyType.EVENT, strArr("Pressing and releasing left mouse button on a tree.", "Returns the control and the new selection path.", priority(2), "Tree")),
	EVENT_ON_TREE_DBL_CLICK(1035, "onTreeDblClick", ControlProperty.PropertyType.EVENT, strArr("Pressing and releasing twice on a tree entry.", "Returns the control.", priority(2), "Tree")),
	EVENT_ON_TREE_EXPANDED(1036, "onTreeExpanded", ControlProperty.PropertyType.EVENT, strArr("The tree folder structure has been expanded.", "Returns the control and the current selection path.", priority(3), "Tree")),
	EVENT_ON_TREE_COLLAPSED(1037, "onTreeCollapsed", ControlProperty.PropertyType.EVENT, strArr("The tree folder structure has been collapsed.", "Returns the control.", priority(3), "Tree")),
	EVENT_ON_TREE_MOUSE_MOVE(1038, "onTreeMouseMove", ControlProperty.PropertyType.EVENT, strArr("Fires continuously while moving the mouse with a certain interval.", "Returns the control.", priority(2), "Tree")),
	EVENT_ON_TREE_MOUSE_HOLD(1039, "onTreeMouseHold", ControlProperty.PropertyType.EVENT, strArr("Fires continuously while mouse is not moving with a certain interval.", "Returns the control.", priority(2), "Tree")),
	EVENT_ON_TREE_MOUSE_EXIT(1040, "onTreeMouseExit", ControlProperty.PropertyType.EVENT, strArr("The mouse pointer exits the tree control area", "Returns the control.", priority(2), "Tree")),
	EVENT_ON_TOOL_BOX_SEL_CHANGED(1041, "onToolBoxSelChanged", ControlProperty.PropertyType.EVENT, strArr("Changed the selection in a toolbox.", "Returns the control.", priority(2), "Tree")),
	EVENT_ON_CHECKED(1042, "onChecked", ControlProperty.PropertyType.EVENT, strArr("?", "Returns the control and the selected element index.", priority(-1), "Toolbox")),
	EVENT_ON_CHECKED_CHANGED(1043, "onCheckedChanged", ControlProperty.PropertyType.EVENT, strArr("Checked state of CheckBox changed.", "Arma 3", priority(-1), "Checkbox")),
	EVENT_ON_CHECK_BOXES_SEL_CHANGED(1044, "onCheckBoxesSelChanged", ControlProperty.PropertyType.EVENT, strArr("Changed the selection in a checkbox.", "Arma 3. Returns control and the checked state.", priority(2), "Checkbox")),
	EVENT_ON_HTML_LINK(1045, "onHTMLLink", ControlProperty.PropertyType.EVENT, strArr("Pressing and releasing a HTML link.", "Returns the control, the selected element index and the current state.", priority(2), "Checkbox")),
	EVENT_ON_SLIDER_POS_CHANGED(1046, "onSliderPosChanged", ControlProperty.PropertyType.EVENT, strArr("Changing the position of a slider.", "Returns the control and href.", priority(2), "HTML")),
	EVENT_ON_OBJECT_MOVED(1047, "onObjectMoved", ControlProperty.PropertyType.EVENT, strArr("Moving an object.", "Returns the control and the change.", priority(2), "Slider")),
	EVENT_ON_MENU_SELECTED(1048, "onMenuSelected", ControlProperty.PropertyType.EVENT, strArr("Some item in context menu (used now only in new mission editor) was selected.", "Returns the control and the offset on the x, y and z axes.", priority(2), "Object")),
	EVENT_ON_DRAW(1049, "onDraw", ControlProperty.PropertyType.EVENT, strArr("Fires when the map is drawn (can occur more than once per second).", "Returns the control and the command id.", priority(-1), "Context menu")),
	EVENT_ON_VIDEO_STOPPED(1050, "onVideoStopped", ControlProperty.PropertyType.EVENT, strArr("1.56 Activated every time video ends (when looped, handler is executed after every finished loop).", "Returns the map control.", priority(2), "Map")),


	/*HitZone and TextPos classes*/
	/*TOP(0, "top", PropertyType.FLOAT, null),
	RIGHT(0, "right", PropertyType.FLOAT, null),
	BOTTOM(0, "bottom", PropertyType.FLOAT, null),
	LEFT(0, "left", PropertyType.FLOAT, null),

	/*ShortcutPos class*/
	/*CLASS_SHORTCUT_POS__W(0, "w", PropertyType.FLOAT, null),
	CLASS_SHORTCUT_POS__H(0, "h", PropertyType.FLOAT, null)*/;


	public static final ControlPropertyLookup[] EMPTY = new ControlPropertyLookup[0];
	/** All values that the property can be, or null if user defined. */
	public final @Nullable ControlPropertyOption[] options;
	public final String propertyName;
	public final ControlProperty.PropertyType propertyType;
	public final String[] about;
	/**
	 A unique id for the lookup item to guarantee a match by despite order change or property name change, or some other change.
	 <br>When the loopup item is written, the propertyId must <b>NEVER</b> change.
	 */
	public final int propertyId;

	ControlPropertyLookup(int propertyId, @NotNull String propertyName, ControlProperty.@NotNull PropertyType propertyType, @NotNull String[] about, @Nullable ControlPropertyOption... options) {
		if (PropertiesLookupDataVerifier.usedIds.contains(propertyId)) {
			int canUse;
			for (int i = 0; true; i++) {
				if (!PropertiesLookupDataVerifier.usedIds.contains(i)) {
					canUse = i;
					break;
				}
			}
			throw new IllegalStateException("id '" + propertyId + "' is already taken for property enum name:" + name() + ". Here is an unused id for your convenience: " + canUse);
		}
		if (propertyId == -1) {
			throw new IllegalStateException("-1 propertyId is reserved for user-defined properties");
		}
		PropertiesLookupDataVerifier.usedIds.add(propertyId);
		this.propertyId = propertyId;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.about = about;
		this.options = options;
	}

	ControlPropertyLookup(int propertyId, @NotNull String propertyName, ControlProperty.@NotNull PropertyType propertyType, @Nullable String about) {
		this(propertyId, propertyName, propertyType, about, (ControlPropertyOption[]) null);
	}

	ControlPropertyLookup(int propertyId, @NotNull String propertyName, ControlProperty.@NotNull PropertyType propertyType, @Nullable String about, @Nullable ControlPropertyOption... options) {
		this(propertyId, propertyName, propertyType, about == null ? strArr("No documentation.") : strArr(about), options);
	}

	@Override
	public String toString() {
		return propertyName;
	}

	public ControlProperty getEventProperty(String defaultEventValue) {
		return new ControlProperty(this, propertyName, propertyType, defaultEventValue);
	}

	public ControlProperty getIntProperty(int defaultValue) {
		return new ControlProperty(this, propertyName, defaultValue);
	}

	public ControlProperty getFloatProperty(double defaultValue) {
		return new ControlProperty(this, propertyName, defaultValue);
	}

	public ControlProperty getBooleanProperty(boolean defaultValue) {
		return new ControlProperty(this, propertyName, defaultValue);
	}

	public ControlProperty getStringProperty(String defaultValue) {
		return new ControlProperty(this, propertyName, ControlProperty.PropertyType.STRING, defaultValue);
	}

	public ControlProperty getArrayProperty(String[] defaultValue) {
		return new ControlProperty(this, propertyName, ControlProperty.PropertyType.ARRAY, defaultValue);
	}

	public ControlProperty getColorProperty(AColor defaultValue) {
		return new ControlProperty(this, propertyName, defaultValue);
	}

	public ControlProperty getFontProperty(AFont defaultValue) {
		return new ControlProperty(this, propertyName, defaultValue);
	}

	public ControlProperty getHexColorProperty(AHexColor defaultValue) {
		return new ControlProperty(this, propertyName, defaultValue);
	}

	public ControlProperty getProperty(Object defaultValue) {
		if (defaultValue instanceof String[]) {
			throw new IllegalArgumentException("Use getProperty(String[] defaultValues) instead");
		}
		if (defaultValue instanceof ControlPropertyOption[]) {
			throw new IllegalArgumentException("Use getProperty(Option[] defaultValues) instead");
		}
		return new ControlProperty(this, propertyName, propertyType, defaultValue);
	}

	public ControlProperty getProperty(String[] defaultValues) {
		return new ControlProperty(this, propertyName, propertyType, defaultValues);
	}

	public ControlProperty getPropertyFromOption(int optionNum) {
		if(options == null || optionNum < 0 || optionNum > options.length){
			throw new IllegalStateException("options and optionNum are bad. options=" + (options != null ? Arrays.toString(options) : "null") + " optionNum=" + optionNum);
		}
		return new ControlProperty(this, propertyName, propertyType, options[optionNum].value);
	}

	public ControlProperty getPropertyWithNoData() {
		return new ControlProperty(this, propertyName, propertyType);
	}

	/** Get all lookup enums where their property type is equal to find */
	public static ControlPropertyLookup[] getAllOfTypeControlProperties(ControlProperty.PropertyType find) {
		ArrayList<ControlPropertyLookup> props = new ArrayList<>(values().length);
		for (ControlPropertyLookup controlProperty : values()) {
			if (controlProperty.propertyType == find) {
				props.add(controlProperty);
			}
		}
		return props.toArray(new ControlPropertyLookup[props.size()]);
	}

	private static String[] strArr(String... vals) {
		return vals;
	}

	private static String priority(int i) {
		if (i < 0) {
			return "unknown";
		}
		return i + "";
	}

	private static class PropertiesLookupDataVerifier {
		static ArrayList<Integer> usedIds = new ArrayList<>();
	}

}