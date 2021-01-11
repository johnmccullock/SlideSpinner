# SlideSpinner

You're not finished prepping this one.  The images need to be dealt with.

A composite java swing component, combining a JTextfield, spinner buttons, and a drop-down slider component.  Its purpose is simply for choosing from a range of numbers.

This design is based on the one found in Corel PaintShop Pro X5.  I liked it so much I decided to make my own.

The SlideSpinnerBuilder class makes it easy to instantiate a SlideSpinner.

Note that there are four different data models created for the SlideSpinner: SlideSpinnerIntegerModel, SlideSpinnerFloatModel, SlideSpinnerExpIntegerModel and SlideSpinnerExpFloatModel.  The integer and float models are as simple as their names imply; just supply and range of numbers for their respective data types.  The exponential models ("Exp") are a little more complicated, as their resulting numbers are exponential instead of linear.  To construct an exponential model, you need to supply the "a" and "b" values for the standard exponential formula. 

SlideSpinnerSlider.java and SlideSpinnerSliderFrame.java are implemented from a javax.swing.JDialog.  The JDialog constructor needs a java.awt.Window object passed to it, make sure to use a reference to the JFrame or JDialog hosting the SlideSpinner instance.  Troubleshooting tip: if you click the SlideSpinner dropdown button and nothing happens, the Window object you passed to it is the wrong one.

