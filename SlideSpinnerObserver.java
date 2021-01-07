package main.gui.custom;

public interface SlideSpinnerObserver
{
	abstract void positionChanged(double proportion);
	abstract void increase();
	abstract void decrease();
	abstract void updateModelValue();
	abstract void updateSlidersOnly();
	abstract Object getMin();
	abstract Object getMax();
}
