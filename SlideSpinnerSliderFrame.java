package main.gui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class SlideSpinnerSliderFrame extends JDialog
{
	private static final Dimension DEFAULT_SIZE = new Dimension(200, 20);
	private static final Color DEFAULT_BACKGROUND = UIManager.getColor("TextField.background");
	private static final Color DEFAULT_FOREGROUND = UIManager.getColor("TextField.selectionBackground");
	private static final Border DEFAULT_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1);
	
	private SlideSpinnerObserver mObserver = null;
	private JPanel mBasePanel = null;
	private Dimension mSize = DEFAULT_SIZE;
	private Color mBackground = DEFAULT_BACKGROUND;
	private Color mForeground = DEFAULT_FOREGROUND;
	private Border mBorder = DEFAULT_BORDER;
	private double mPosition = 0.0;
	private boolean mIsDragging = false;
	private KeyListener mKeyListener = null;
	private MouseListener mMouseListener = null;
	private MouseMotionListener mMouseMotionListener = null;
	private MouseWheelListener mMouseWheelListener = null;
	
	public SlideSpinnerSliderFrame(Window owner, SlideSpinnerObserver observer)
	{
		super(owner, Dialog.ModalityType.MODELESS);
		this.mObserver = observer;
		this.initializeMain();
		return;
	}
	
	public SlideSpinnerSliderFrame(SlideSpinnerObserver observer, Dimension size, Color background, Color foreground, Border border)
	{
		super();
		this.mObserver = observer;
		this.mSize = new Dimension(size.width, size.height);
		this.mBackground = background;
		this.mForeground = foreground;
		this.mBorder = border;
		this.initializeMain();
		return;
	}
	
	private void initializeMain()
	{
		this.mKeyListener = this.createKeyListener();
		this.mMouseListener = this.createMouseListener();
		this.mMouseMotionListener = this.createMouseMotionListener();
		this.mMouseWheelListener = this.createMouseWheelListener();
		this.mBasePanel = this.createBasePanel();
		
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.addFocusListener(this.createFocusListener());
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.setContentPane(this.mBasePanel);
		this.setMinimumSize(this.mSize);
		this.setMaximumSize(this.mSize);
		this.setPreferredSize(this.mSize);
		this.setAutoRequestFocus(true);
		this.setFocusable(true);
		this.setResizable(false);
		this.setVisible(false);
		this.pack();
		return;
	}
	
	private JPanel createBasePanel()
	{
		JPanel aPanel = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setPaint(mBackground);
				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setPaint(mForeground);
				
				/*
				 * Seems I painted myself into a corner with how I convert mouse position into into slider value.
				 * Now I can't use this, by itself, to paint the slider value:
				 * int width = (int)Math.ceil(this.getWidth() * this.mPosition);
				 */
				
				/*
				 * So instead, I have to translate the slider scale to where the minimum is >= 0.0, then compare the modified value
				 * to the modified scale.
				 * Finally, I can multiply the resulting ratio to the slide component's width.
				 */
				double max = Double.parseDouble(String.valueOf(mObserver.getMax()));
				double min = Double.parseDouble(String.valueOf(mObserver.getMin()));
				double ratio = ((mPosition  * max) + (0.0 - min)) / (max - min);
				int width = (int)Math.ceil(ratio * this.getWidth());
				
				if(width > 0){
					g2d.fillRect(0, 0, width, this.getHeight());
				}
				super.paintBorder(g2d);
				g2d.dispose();
				return;
			}
		};
		aPanel.setLayout(new BorderLayout());
		aPanel.setBorder(this.mBorder);
		return aPanel;
	}
	
	public void showPanel(int x, int y)
	{
		this.setLocation(x, y);
		this.setVisible(true);
		this.pack();
		return;
	}
	
	public void setPosition(double proportion)
	{
		this.mPosition = proportion;
		this.repaint();
		return;
	}
	
	public boolean getValueIsAdjusting()
	{
		return this.mIsDragging;
	}
	
	public void installListeners()
	{
		this.addKeyListener(this.mKeyListener);
		this.addMouseListener(this.mMouseListener);
		this.addMouseMotionListener(this.mMouseMotionListener);
		this.addMouseWheelListener(this.mMouseWheelListener);
		return;
	}
	
	public void unInstallListeners()
	{
		this.removeKeyListener(this.mKeyListener);
		this.removeMouseListener(this.mMouseListener);
		this.removeMouseMotionListener(this.mMouseMotionListener);
		this.removeMouseWheelListener(this.mMouseWheelListener);
		return;
	}
	
	private FocusListener createFocusListener()
	{
		return new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				SlideSpinnerSliderFrame.this.setVisible(false);
				return;
			}
		};
	}
	
	private KeyListener createKeyListener()
	{
		return new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_UP){
					mObserver.increase();
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					mObserver.decrease();
				}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					SlideSpinnerSliderFrame.this.setVisible(false);
				}
				return;
			}
		};
	}
	
	private MouseListener createMouseListener()
	{
		return new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(SlideSpinnerSliderFrame.this.getWidth() <= 0){
					return;
				}
				mPosition = e.getX() / (double)SlideSpinnerSliderFrame.this.getWidth();
				mIsDragging = true;
				mObserver.positionChanged(mPosition);
				SlideSpinnerSliderFrame.this.getContentPane().repaint();
				return;
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				mIsDragging = false;
				mPosition = e.getX() / (double)SlideSpinnerSliderFrame.this.getWidth();
				mObserver.positionChanged(mPosition);
				SlideSpinnerSliderFrame.this.setVisible(false);
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
				if(!mIsDragging){
					return;
				}
				if(e.getX() < 0){
					mPosition = 0.0;
				}else if(e.getX() > SlideSpinnerSliderFrame.this.getWidth()){
					mPosition = 1.0;
				}else{
					mPosition = e.getX() / (double)SlideSpinnerSliderFrame.this.getWidth();
				}
				mObserver.positionChanged(mPosition);
				SlideSpinnerSliderFrame.this.getContentPane().repaint();
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
