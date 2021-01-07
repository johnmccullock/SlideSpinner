package main.gui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * Version 1.0.1 adds the setEnabled() function.
 * @version 1.0.1 2018-04-25
 */
@SuppressWarnings("serial")
public class SlideSpinnerSlider extends JComponent
{
	private static final Color DEFAULT_BACKGROUND = UIManager.getColor("TextField.background");
	private static final Color DEFAULT_FOREGROUND = UIManager.getColor("TextField.selectionBackground");
	
	private SlideSpinnerObserver mObserver = null;
	private Color mBackground = DEFAULT_BACKGROUND;
	private Color mForeground = DEFAULT_FOREGROUND;
	private double mPosition = 0.0;
	private boolean mIsDragging = false;
	private MouseListener mMouseListener = null;
	private MouseMotionListener mMouseMotionListener = null;
	private MouseWheelListener mMouseWheelListener = null;
	
	public SlideSpinnerSlider(SlideSpinnerObserver observer)
	{
		this.mObserver = observer;
		this.initializeMain();
		return;
	}
	
	private void initializeMain()
	{
		this.setLayout(new BorderLayout());
		this.setOpaque(true);
		this.setBackground(this.mBackground);
		this.mMouseListener = this.createMouseListener();
		this.mMouseMotionListener = this.createMouseMotionListener();
		this.mMouseWheelListener = this.createMouseWheelListener();
		return;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(this.mBackground);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setPaint(this.mForeground);
		
		/*
		 * Seems I painted myself into a corner with how I convert mouse position into slider value.
		 * Now I can't use this, by itself, to paint the slider value:
		 * int width = (int)Math.ceil(this.getWidth() * this.mPosition);
		 */
		
		/*
		 * So instead, I have to translate the slider scale to where the minimum is >= 0.0, then compare the modified value
		 * to the modified scale.
		 * Finally, I can multiply the resulting ratio to the slide component's width.
		 */
		double max = Double.parseDouble(String.valueOf(this.mObserver.getMax()));
		double min = Double.parseDouble(String.valueOf(this.mObserver.getMin()));
		double ratio = ((this.mPosition  * max) + (0.0 - min)) / (max - min);
		int width = (int)Math.ceil(ratio * this.getWidth());
		
		if(width > 0){
			g2d.fillRect(0, 0, width, this.getHeight());
		}
		g2d.dispose();
		return;
	}
	
	public void setPosition(double position)
	{
		this.mPosition = position;
		//System.out.println(position);
		this.repaint();
		return;
	}
	
	public void installListeners()
	{
		this.addMouseListener(this.mMouseListener);
		this.addMouseMotionListener(this.mMouseMotionListener);
		this.addMouseWheelListener(this.mMouseWheelListener);
		return;
	}
	
	public void unInstallListeners()
	{
		this.removeMouseListener(this.mMouseListener);
		this.removeMouseMotionListener(this.mMouseMotionListener);
		this.removeMouseWheelListener(this.mMouseWheelListener);
		return;
	}
	
	public boolean getValueIsAdjusting()
	{
		return this.mIsDragging;
	}
	
	private MouseListener createMouseListener()
	{
		return new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(!SlideSpinnerSlider.this.isEnabled()){
					return;
				}
				if(SlideSpinnerSlider.this.getWidth() <= 0){
					return;
				}
				if(e.getX() < 0 || e.getY() < 0 || e.getX() > SlideSpinnerSlider.this.getWidth() || e.getY() > SlideSpinnerSlider.this.getHeight()){
					return;
				}
				mIsDragging = true;
				mPosition = e.getX() / (double)SlideSpinnerSlider.this.getWidth();
				//System.out.println(mPosition);
				mObserver.positionChanged(mPosition);
				SlideSpinnerSlider.this.repaint();
				return;
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if(!SlideSpinnerSlider.this.isEnabled()){
					return;
				}
				if(SlideSpinnerSlider.this.getWidth() <= 0){
					return;
				}
				if(e.getX() < 0 || e.getY() < 0 || e.getX() > SlideSpinnerSlider.this.getWidth() || e.getY() > SlideSpinnerSlider.this.getHeight()){
					return;
				}
				mIsDragging = false;
				mPosition = e.getX() / (double)SlideSpinnerSlider.this.getWidth();
				mObserver.positionChanged(mPosition);
				return;
			}
		};
	}
	
	private MouseMotionListener createMouseMotionListener()
	{
		return new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if(!SlideSpinnerSlider.this.isEnabled()){
					return;
				}
				if(!mIsDragging){
					return;
				}
				if(e.getX() < 0){
					mPosition = 0.0;
				}else if(e.getX() > SlideSpinnerSlider.this.getWidth()){
					mPosition = 1.0;
				}else{
					mPosition = e.getX() / (double)SlideSpinnerSlider.this.getWidth();
				}
				mObserver.positionChanged(mPosition);
				SlideSpinnerSlider.this.repaint();
				return;
			}
		};
	}
	
	private MouseWheelListener createMouseWheelListener()
	{
		return new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if(!SlideSpinnerSlider.this.isEnabled()){
					return;
				}
				if(e.getWheelRotation() < 0){
					mObserver.increase();
				}else if(e.getWheelRotation() > 0){
					mObserver.decrease();
				}
				return;
			}
		};
	}
	
}
