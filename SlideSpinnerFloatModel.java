package main.gui.custom;

import java.util.Vector;

public class SlideSpinnerFloatModel implements SlideSpinnerModel
{
	private double mValue = 0.0;
	private double mMin = 0.0;
	private double mMax = 100.0;
	private double mMinLock = 0.0;
	private double mMaxLock = 100.0;
	private double mStep = 1.0;
	private Vector<SlideSpinnerModelListener> mListeners = new Vector<SlideSpinnerModelListener>();
	
	public SlideSpinnerFloatModel() { return; }
	
	public SlideSpinnerFloatModel(double value, double min, double max, double step)
	{
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
		this.mValue = (proportion * (this.mMax - this.mMin)) + this.mMin;
		this.mValue = this.mValue < this.mMinLock ? this.mMinLock : this.mValue > this.mMaxLock ? this.mMaxLock : this.mValue;
		this.mValue = Math.round(this.mValue * 1000.0) / 1000.0;
		this.notifyModelListeners();
		//System.out.println(this.mValue);
		return;
	}
	
	public void setValue(String value)
	{
		if(value == null || value.isEmpty()){
			return;
		}
		this.mValue = Double.valueOf(value);
		this.notifyModelListeners();
		//System.out.println(this.mValue);
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
		double test = Double.parseDouble(value);
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
		this.mMinLock = (double)minLock;
		return;
	}
	
	public Object getMinLock()
	{
		return this.mMinLock;
	}
	
	public void setMaxLock(Object maxLock)
	{
		this.mMaxLock = (double)maxLock;
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
