package main.gui.custom;

import java.util.Vector;

/**
 *
 * y = a * b^x
 *
 * x = log(y / a) / log(b);
 *
 * @author John McCullock
 */
public class SlideSpinnerExpIntegerModel implements SlideSpinnerModel
{
	private double mA = 1.3269;
	private double mB = 1.102057;
	private int mValue = 0;
	private int mMin = 1;
	private int mMax = 100;
	private int mMinLock = 1;
	private int mMaxLock = 100;
	private int mStep = 1;
	private Vector<SlideSpinnerModelListener> mListeners = new Vector<SlideSpinnerModelListener>();
	
	public SlideSpinnerExpIntegerModel() { return; }
	
	public SlideSpinnerExpIntegerModel(double a, double b, int value, int min, int max, int step)
	{
		this.mA = a;
		this.mB = b;
		this.mValue = value;
		this.mMin = min;
		this.mMax = max;
		this.mMaxLock = this.mMax;
		this.mMinLock = this.mMin;
		this.mStep = step;
		return;
	}
	
	public void setValue(double proportion)
	{
		double x = proportion * 100.0;
		this.mValue = (int)Math.round(mA * (Math.pow(mB, x)));
		this.mValue = this.mValue < this.mMinLock ? this.mMinLock : this.mValue > this.mMaxLock ? this.mMaxLock : this.mValue;
		this.notifyModelListeners();
		return;
	}
	
	public void setValue(String value)
	{
		if(value == null || value.isEmpty()){
			return;
		}
		this.mValue = Integer.valueOf(value);
		this.notifyModelListeners();
		return;
	}
	
	public Object getValue()
	{
		return this.mValue;
	}
	
	public double getProportion()
	{
		return (Math.log(this.mValue / this.mA) / Math.log(this.mB)) / 100.0;
	}
	
	public Object getNextValue()
	{
		this.mValue = this.mValue + this.mStep > this.mMaxLock ? this.mMaxLock : this.mValue + this.mStep;
		this.notifyModelListeners();
		return this.mValue;
	}
	
	public Object getPreviousValue()
	{
		this.mValue = this.mValue - this.mStep < this.mMinLock ? this.mMinLock : this.mValue - this.mStep;
		this.notifyModelListeners();
		return this.mValue;
	}
	
	public int isWithinRange(String value) throws Exception
	{
		int results = 0;
		int test = Integer.parseInt(value);
		if(test < this.mMin){
			results = -1;
		}
		if(test > this.mMax){
			results = 1;
		}
		return results;
	}
	
	public Object getMin()
	{
		return this.mMin;
	}
	
	public Object getMax()
	{
		return this.mMax;
	}
	
	public Object getStep()
	{
		return this.mStep;
	}
	
	public void setMinLock(Object minLock)
	{
		this.mMinLock = (int)minLock;
		return;
	}
	
	public Object getMinLock()
	{
		return this.mMinLock;
	}
	
	public void setMaxLock(Object maxLock)
	{
		this.mMaxLock = (int)maxLock;
		return;
	}
	
	public Object getMaxLock()
	{
		return this.mMaxLock;
	}
	
	public void addModelListener(SlideSpinnerModelListener listener)
	{
		this.mListeners.add(listener);
		return;
	}
	
	public void notifyModelListeners()
	{
		for(SlideSpinnerModelListener obs : this.mListeners)
		{
			obs.valueChanged();
		}
		return;
	}
	
	public void removeModelListener(SlideSpinnerModelListener listener)
	{
		for(int i = 0; i < this.mListeners.size(); i++)
		{
			if(this.mListeners.get(i).equals(listener)){
				this.mListeners.remove(i);
				break;
			}
		}
		return;
	}
}
