package main.gui.custom;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public abstract class UndoRedoAction extends AbstractAction
{
	protected String mCaption = null;
	protected UndoRedoAction mSibling = null;
	
	public UndoRedoAction(String caption)
	{
		super(caption);
		this.mCaption = caption;
		this.setEnabled(false);
		return;
	}
	
	public abstract void update();
	
	public void setSibling(UndoRedoAction sibling)
	{
		this.mSibling = sibling;
		return;
	}
	
}
