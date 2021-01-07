package main.gui.custom;

/**
 * An interface class that defines the methods signatures for the SlideSpinner models.
 * 
 * Version 1.1 includes minimum lock and maximum lock functionality. The locking mechanism keeps the value from going lower than
 * the minimum lock, or going higher than the maximum lock.  One could just change the primary min or max, but the visual
 * representations are based on the min and max, which might confuse the user.
 * 
 * @author John McCullock
 * @version 1.1 2020-07-28
 */
public interface SlideSpinnerModel
{
	abstract void setValue(double proportion);
	abstract void setValue(String value);
	abstract Object getValue();
	abstract double getProportion();
	abstract Object getNextValue();
	abstract Object getPreviousValue();
	abstract Object getMin();
	abstract Object getMax();
	abstract Object getStep();
	abstract void setMinLock(Object minLock);
	abstract Object getMinLock();
	abstract void setMaxLock(Object maxLock);
	abstract Object getMaxLock();
	abstract int isWithinRange(String value) throws Exception;
	abstract void addModelListener(SlideSpinnerModelListener listener);
	abstract void notifyModelListeners();
	abstract void removeModelListener(SlideSpinnerModelListener listener);
}
