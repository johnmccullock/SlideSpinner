package main.gui;

import java.awt.Dimension;
import java.awt.Window;

import java.util.ResourceBundle;

import javax.swing.JTextField;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class SlideSpinnerBuilder
{
	public static SlideSpinner createIntegerField(Window owner, ResourceBundle guiHelperResource, IconTree icons, int value, int min, int max, int step, int width)
	{
		IntegerOnlyDocument document = new IntegerOnlyDocument(getDigits(max), min < 0 || max < 0);
		SlideSpinnerIntegerModel model = new SlideSpinnerIntegerModel(value, min, max, step);
		SlideSpinner field = new SlideSpinner(owner, icons.get(IconTree.Keys.SLIDESPINNER_UP), icons.get(IconTree.Keys.SLIDESPINNER_DOWN), icons.get(IconTree.Keys.SLIDESPINNER_DROP), model, document);
		field.setMinimumSize(new Dimension(width, field.getMinimumSize().height));
		field.setMaximumSize(new Dimension(width, field.getMaximumSize().height));
		field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		field.getTextField().addFocusListener(GUIHelper.createSelectAllTextFocusListener(field.getTextField()));
		field.getTextField().setComponentPopupMenu(GUIHelper.createEditorPopupMenu(field.getTextField(), true, guiHelperResource.getString("UNDO_CAPTION"), icons.get(IconTree.Keys.UNDO_SMALL), true, guiHelperResource.getString("REDO_CAPTION"), icons.get(IconTree.Keys.REDO_SMALL), true, guiHelperResource.getString("CUT_CAPTION"), icons.get(IconTree.Keys.CUT_SMALL), true, guiHelperResource.getString("COPY_CAPTION"), icons.get(IconTree.Keys.COPY_SMALL), true, guiHelperResource.getString("PASTE_CAPTION"), icons.get(IconTree.Keys.PASTE_SMALL), true, guiHelperResource.getString("SELECT_ALL_CAPTION"), null));
		return field;
	}
	
	/**
	 * Applies a new Document object and SlideSpinnerIntegerModel to an existing SlideSpinner object.
	 * @param field The SlideSpinner to be modified.
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 * @return The same SlideSpinner passed as a parameter.
	 */
	public static SlideSpinner createIntegerField(SlideSpinner field, int value, int min, int max, int step)
	{
		IntegerOnlyDocument document = new IntegerOnlyDocument(getDigits(max), min < 0 || max < 0);
		SlideSpinnerIntegerModel model = new SlideSpinnerIntegerModel(value, min, max, step);
		field.getTextField().setDocument(document);
		field.setModel(model);
		return field;
	}
	
	public static SlideSpinner createFloatEntryField(Window owner, ResourceBundle guiHelperResource, IconTree icons, double current, double min, double max, double step, int width)
	{
		FloatOnlyDocument document = new FloatOnlyDocument(getDigits(max), 3, min < 0.0 || max < 0.0);
		SlideSpinnerFloatModel model = new SlideSpinnerFloatModel(current, min, max, step);
		SlideSpinner field = new SlideSpinner(owner, icons.get(IconTree.Keys.SLIDESPINNER_UP), icons.get(IconTree.Keys.SLIDESPINNER_DOWN), icons.get(IconTree.Keys.SLIDESPINNER_DROP), model, document);
		field.setMinimumSize(new Dimension(width, field.getMinimumSize().height));
		field.setMaximumSize(new Dimension(width, field.getMaximumSize().height));
		field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		field.getTextField().addFocusListener(GUIHelper.createSelectAllTextFocusListener(field.getTextField()));
		field.getTextField().setComponentPopupMenu(GUIHelper.createEditorPopupMenu(field.getTextField(), true, guiHelperResource.getString("UNDO_CAPTION"), icons.get(IconTree.Keys.UNDO_SMALL), true, guiHelperResource.getString("REDO_CAPTION"), icons.get(IconTree.Keys.REDO_SMALL), true, guiHelperResource.getString("CUT_CAPTION"), icons.get(IconTree.Keys.CUT_SMALL), true, guiHelperResource.getString("COPY_CAPTION"), icons.get(IconTree.Keys.COPY_SMALL), true, guiHelperResource.getString("PASTE_CAPTION"), icons.get(IconTree.Keys.PASTE_SMALL), true, guiHelperResource.getString("SELECT_ALL_CAPTION"), null));
		return field;
	}
	
	public static SlideSpinner createFloatEntryField(Window owner, ResourceBundle guiHelperResource, IconTree icons, double current, double min, double max, double step, int width, boolean forceRound)
	{
		FloatOnlyDocument document = new FloatOnlyDocument(getDigits(max), 3, min < 0.0 || max < 0.0, true);
		SlideSpinnerFloatModel model = new SlideSpinnerFloatModel(current, min, max, step);
		SlideSpinner field = new SlideSpinner(owner, icons.get(IconTree.Keys.SLIDESPINNER_UP), icons.get(IconTree.Keys.SLIDESPINNER_DOWN), icons.get(IconTree.Keys.SLIDESPINNER_DROP), model, document);
		field.setMinimumSize(new Dimension(width, field.getMinimumSize().height));
		field.setMaximumSize(new Dimension(width, field.getMaximumSize().height));
		field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		field.getTextField().addFocusListener(GUIHelper.createSelectAllTextFocusListener(field.getTextField()));
		field.getTextField().setComponentPopupMenu(GUIHelper.createEditorPopupMenu(field.getTextField(), true, guiHelperResource.getString("UNDO_CAPTION"), icons.get(IconTree.Keys.UNDO_SMALL), true, guiHelperResource.getString("REDO_CAPTION"), icons.get(IconTree.Keys.REDO_SMALL), true, guiHelperResource.getString("CUT_CAPTION"), icons.get(IconTree.Keys.CUT_SMALL), true, guiHelperResource.getString("COPY_CAPTION"), icons.get(IconTree.Keys.COPY_SMALL), true, guiHelperResource.getString("PASTE_CAPTION"), icons.get(IconTree.Keys.PASTE_SMALL), true, guiHelperResource.getString("SELECT_ALL_CAPTION"), null));
		return field;
	}
	
	public static SlideSpinner createFloatEntryFieldSpinnerOnly(ResourceBundle guiHelperResource, IconTree icons, double current, double min, double max, double step, int width, boolean forceRound)
	{
		FloatOnlyDocument document = new FloatOnlyDocument(getDigits(max), 3, min < 0.0 || max < 0.0, true);
		SlideSpinnerFloatModel model = new SlideSpinnerFloatModel(current, min, max, step);
		SlideSpinner field = new SlideSpinner(icons.get(IconTree.Keys.SLIDESPINNER_UP), icons.get(IconTree.Keys.SLIDESPINNER_DOWN), model, document);
		field.setMinimumSize(new Dimension(width, field.getMinimumSize().height));
		field.setMaximumSize(new Dimension(width, field.getMaximumSize().height));
		field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		field.getTextField().addFocusListener(GUIHelper.createSelectAllTextFocusListener(field.getTextField()));
		field.getTextField().setComponentPopupMenu(GUIHelper.createEditorPopupMenu(field.getTextField(), true, guiHelperResource.getString("UNDO_CAPTION"), icons.get(IconTree.Keys.UNDO_SMALL), true, guiHelperResource.getString("REDO_CAPTION"), icons.get(IconTree.Keys.REDO_SMALL), true, guiHelperResource.getString("CUT_CAPTION"), icons.get(IconTree.Keys.CUT_SMALL), true, guiHelperResource.getString("COPY_CAPTION"), icons.get(IconTree.Keys.COPY_SMALL), true, guiHelperResource.getString("PASTE_CAPTION"), icons.get(IconTree.Keys.PASTE_SMALL), true, guiHelperResource.getString("SELECT_ALL_CAPTION"), null));
		UndoManager undoManager = new UndoManager();
		UndoRedoAction undoAction = GUIHelper.createUndoAction("Undo", undoManager);
		UndoRedoAction redoAction = GUIHelper.createRedoAction("Redo", undoManager);
		UndoableEditListener undoHandler = createUndoHandler(undoManager, undoAction, redoAction);
		field.getTextField().getDocument().addUndoableEditListener(undoHandler);
		return field;
	}
	
	/**
	 * Applies a new Document object and SlideSpinnerFloatModel to an existing SlideSpinner object.
	 * @param field The SlideSpinner to be modified.
	 * @param current
	 * @param min
	 * @param max
	 * @param step
	 * @return The same SlideSpinner passed as a parameter.
	 */
	public static SlideSpinner createFloatEntryField(SlideSpinner field, double current, double min, double max, double step)
	{
		FloatOnlyDocument document = new FloatOnlyDocument(getDigits(max), 3, min < 0.0 || max < 0.0);
		SlideSpinnerFloatModel model = new SlideSpinnerFloatModel(current, min, max, step);
		field.getTextField().setDocument(document);
		field.setModel(model);
		return field;
	}
	
	public static SlideSpinner createExpIntegerField(Window owner, ResourceBundle guiHelperResource, IconTree icons, double expAValue, double expBValue, int value, int min, int max, int step, int width)
	{
		SlideSpinnerExpIntegerModel model = new SlideSpinnerExpIntegerModel(expAValue, expBValue, value, min, max, step);
		IntegerOnlyDocument document = new IntegerOnlyDocument(getDigits(max), min < 0 || max < 0);
		SlideSpinner field = new SlideSpinner(owner, icons.get(IconTree.Keys.SLIDESPINNER_UP), icons.get(IconTree.Keys.SLIDESPINNER_DOWN), icons.get(IconTree.Keys.SLIDESPINNER_DROP), model, document);
		if(width > 0){
			field.setMinimumSize(new Dimension(width, field.getMinimumSize().height));
			field.setMaximumSize(new Dimension(width, field.getMaximumSize().height));
			field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		}
		field.getTextField().addFocusListener(GUIHelper.createSelectAllTextFocusListener(field.getTextField()));
		field.getTextField().setComponentPopupMenu(GUIHelper.createEditorPopupMenu(field.getTextField(), true, guiHelperResource.getString("UNDO_CAPTION"), icons.get(IconTree.Keys.UNDO_SMALL), true, guiHelperResource.getString("REDO_CAPTION"), icons.get(IconTree.Keys.REDO_SMALL), true, guiHelperResource.getString("CUT_CAPTION"), icons.get(IconTree.Keys.CUT_SMALL), true, guiHelperResource.getString("COPY_CAPTION"), icons.get(IconTree.Keys.COPY_SMALL), true, guiHelperResource.getString("PASTE_CAPTION"), icons.get(IconTree.Keys.PASTE_SMALL), true, guiHelperResource.getString("SELECT_ALL_CAPTION"), null));
		return field;
	}
	
	/**
	 * Applies a new Document object and SlideSpinnerExpIntegerModel to an existing SlideSpinner object.
	 * @param field The SlideSpinner to be modified.
	 * @param expAValue
	 * @param expBValue
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 * @return The same SlideSpinner passed as a parameter.
	 */
	public static SlideSpinner createExpIntegerField(SlideSpinner field, double expAValue, double expBValue, int value, int min, int max, int step)
	{
		SlideSpinnerExpIntegerModel model = new SlideSpinnerExpIntegerModel(expAValue, expBValue, value, min, max, step);
		IntegerOnlyDocument document = new IntegerOnlyDocument(getDigits(max), min < 0 || max < 0);
		field.getTextField().setDocument(document);
		field.setModel(model);
		return field;
	}
	
	public static SlideSpinner createExpFloatField(Window owner, ResourceBundle guiHelperResource, IconTree icons, double expAValue, double expBValue, double value, double min, double max, double step, int width)
	{
		SlideSpinnerExpFloatModel model = new SlideSpinnerExpFloatModel(expAValue, expBValue, value, min, max, step);
		FloatOnlyDocument document = new FloatOnlyDocument(getDigits(max), 3, min < 0.0 || max < 0.0);
		SlideSpinner field = new SlideSpinner(owner, icons.get(IconTree.Keys.SLIDESPINNER_UP), icons.get(IconTree.Keys.SLIDESPINNER_DOWN), icons.get(IconTree.Keys.SLIDESPINNER_DROP), model, document);
		if(width > 0){
			field.setMinimumSize(new Dimension(width, field.getMinimumSize().height));
			field.setMaximumSize(new Dimension(width, field.getMaximumSize().height));
			field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		}
		field.getTextField().addFocusListener(GUIHelper.createSelectAllTextFocusListener(field.getTextField()));
		field.getTextField().setComponentPopupMenu(GUIHelper.createEditorPopupMenu(field.getTextField(), true, guiHelperResource.getString("UNDO_CAPTION"), icons.get(IconTree.Keys.UNDO_SMALL), true, guiHelperResource.getString("REDO_CAPTION"), icons.get(IconTree.Keys.REDO_SMALL), true, guiHelperResource.getString("CUT_CAPTION"), icons.get(IconTree.Keys.CUT_SMALL), true, guiHelperResource.getString("COPY_CAPTION"), icons.get(IconTree.Keys.COPY_SMALL), true, guiHelperResource.getString("PASTE_CAPTION"), icons.get(IconTree.Keys.PASTE_SMALL), true, guiHelperResource.getString("SELECT_ALL_CAPTION"), null));
		return field;
	}
	
	/**
	 * Applies a new Document object and SlideSpinnerExpIntegerModel to an existing SlideSpinner object.
	 * @param field The SlideSpinner to be modified.
	 * @param expAValue
	 * @param expBValue
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 * @return The same SlideSpinner passed as a parameter.
	 */
	public static SlideSpinner createExpFloatField(SlideSpinner field, double expAValue, double expBValue, double value, double min, double max, double step)
	{
		SlideSpinnerExpFloatModel model = new SlideSpinnerExpFloatModel(expAValue, expBValue, value, min, max, step);
		FloatOnlyDocument document = new FloatOnlyDocument(getDigits(max), 3, min < 0.0 || max < 0.0);
		field.getTextField().setDocument(document);
		field.setModel(model);
		return field;
	}
	
	private static UndoableEditListener createUndoHandler(UndoManager undoManager, UndoRedoAction undoAction, UndoRedoAction redoAction)
	{
		return new UndoableEditListener()
		{
			public void undoableEditHappened(UndoableEditEvent e)
			{
				undoManager.addEdit(e.getEdit());
				undoAction.update();
				redoAction.update();
				return;
			}
		};
	}
	
	/**
	 * Gets the number digits from a numeric expression.
	 * Example: a value of 100 is composed of three digits.
	 * @param value a numeric expression to count the digits of.
	 * @return the number of digit in the numeric expression.
	 */
	private static int getDigits(double value)
	{
		return (int)Math.floor(Math.log10(value)) + 1;
	}
}
