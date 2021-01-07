package main.gui.custom;

import java.util.Vector;

public class SlideSpinnerIntegerModel implements SlideSpinnerModel
{
	private int mValue = 0;
	private int mMin = 0;
	private int mMax = 100;
	private int mStep = 1;
	private int mMinLock = 0;
	private int mMaxLock = 100;
	private Vector<SlideSpinnerModelListener> mListeners = new Vector<SlideSpinnerModelListener>();
	
	public SlideSpinnerIntegerModel() { return; }
	
	public SlideSpinnerIntegerModel(int value, int min, int max, int step)
	{
		this.mValue = value;
		this.mMin = min;
		this.mMax = max;
		this.mStep = step;
		this.mMaxLock = this.mMax;
		this.mMinLock = this.mMin;
		return;
	}
	
	public void setValue(double proportion)
	{
		this.mValue = (int)Math.ceil(proportion * (this.mMax - this.mMin)) + this.mMin;
		this.mValue = this.mValue < this.mMinLock ? this.mMinLock : this.mValue > this.mMaxLock ? this.mMaxLock : this.mValue;
		this.notifyModelListeners();
		return;
	}
	
	public void setValue(String value)
	{
		if(value == null || value.isEmpty()){
			return;
		}
		this.mValue = value.equalsIgnoreCase("-") ? 0 : Integer.valueOf(value);
		this.notifyModelListeners();
		return;
	}
	
	public Object getValue()
	{
		return this.mValue;
	}
	
	public double getProportion()
	{
		return this.mValue / (double)this.mMax;
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
