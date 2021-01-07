package main.gui.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

/**
 * Version 1.0.1 adds the setEnabled() function.
 * @version 1.0.1 2018-04-25
 */
@SuppressWarnings("serial")
public class SlideSpinner extends JComponent
{
	private static final Border DEFAULT_BORDER = new JTextField().getBorder();
	private static final int DEFAULT_TEXT_ALIGN = SwingConstants.CENTER;
	private static final int DEFAULT_SMALL_SLIDER_HEIGHT = 3;
	private static final boolean DEFAULT_UP_BUTTON_FOCUSABLE = false;
	private static final boolean DEFAULT_DOWN_BUTTON_FOCUSABLE = false;
	private static final boolean DEFAULT_DROP_BUTTON_FOCUSABLE = false;
	private static final Color DEFAULT_TEXTFIELD_ERROR_STATE_COLOR = new Color(255, 64, 64, 255);
	private static final Color DEFAULT_TEXTFIELD_NORMAL_BACKGROUND = UIManager.getColor("TextField.background");
	
	private JTextField mTextField = null;
	private JButton mUpButton = null;
	private JButton mDownButton = null;
	private JButton mDropButton = null;
	private SlideSpinnerSlider mSmallSlider = null;
	private SlideSpinnerSliderFrame mLargeSlider = null;
	private SlideSpinnerModel mModel = null;
	private Color mErrorBackground = DEFAULT_TEXTFIELD_ERROR_STATE_COLOR;
	private Color mNormalBackground = DEFAULT_TEXTFIELD_NORMAL_BACKGROUND;
	private Vector<SlideSpinnerChangeListener> mChangeListeners = new Vector<SlideSpinnerChangeListener>();
	
	/**
	 * Includes all features.
	 * @param owner Window.  Very important if this component is used on a modal dialog !!!  SlideSpinnerSliderFrame will not
	 * function correctly on a modal dialog unless it's part of the dialog's modal ownership.
	 * @param upArrow
	 * @param downArrow
	 * @param dropArrow
	 * @param model
	 * @param document
	 */
	public SlideSpinner(Window owner, ImageIcon upArrow, ImageIcon downArrow, ImageIcon dropArrow, SlideSpinnerModel model, PlainDocument document)
	{
		this.mModel = model;
		this.initializeMain(owner, upArrow, downArrow, dropArrow, document);
		return;
	}
	
	/**
	 * No slider components.  This creates a spinner only.
	 * @param upArrow
	 * @param downArrow
	 * @param model
	 * @param document
	 */
	public SlideSpinner(ImageIcon upArrow, ImageIcon downArrow, SlideSpinnerModel model, PlainDocument document)
	{
		this.mModel = model;
		this.initializeMain(upArrow, downArrow, document);
		return;
	}
	
	private void initializeMain(Window owner, ImageIcon upArrow, ImageIcon downArrow, ImageIcon dropArrow, PlainDocument document)
	{
		this.setLayout(new GridBagLayout());
		this.setBorder(DEFAULT_BORDER);
		
		this.mTextField = this.createTextField(document);
		this.mUpButton = this.createUpButton(upArrow);
		this.mDownButton = this.createDownButton(downArrow);
		this.mDropButton = this.createDropButton(dropArrow);
		this.mSmallSlider = this.createSmallSlider();
		this.mLargeSlider = this.createLargeSlider(owner);
		
		this.mUpButton.addActionListener(this.createIncreaseButtonListener());
		this.mDownButton.addActionListener(this.createDecreaseButtonListener());
		
		this.add(this.mTextField, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.mUpButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.mDownButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.mDropButton, new GridBagConstraints(2, 0, 1, 2, 0.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.mSmallSlider, new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		
		this.addFocusListener(this.createFocusListener());
		return;
	}
	
	private void initializeMain(ImageIcon upArrow, ImageIcon downArrow, PlainDocument document)
	{
		this.setLayout(new GridBagLayout());
		this.setBorder(DEFAULT_BORDER);
		
		this.mTextField = this.createTextField(document);
		this.mUpButton = this.createUpButton(upArrow);
		this.mDownButton = this.createDownButton(downArrow);
		
		this.mUpButton.addActionListener(this.createIncreaseButtonListener());
		this.mDownButton.addActionListener(this.createDecreaseButtonListener());
		
		this.add(this.mTextField, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.mUpButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.mDownButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		return;
	}
	
	private JTextField createTextField(PlainDocument document)
	{
		JTextField field = new JTextField();
		field.setBorder(BorderFactory.createEmptyBorder(2, 2, 1, 2));
		field.setMargin(new Insets(2, 2, 2, 2));
		field.setHorizontalAlignment(DEFAULT_TEXT_ALIGN);
		field.setDocument(document);
		field.addMouseWheelListener(this.createMouseWheelListener());
		field.addKeyListener(this.createKeyListener());
		field.getDocument().addDocumentListener(this.createDocumentListener());
		return field;
	}
	
	private JButton createUpButton(ImageIcon upArrow)
	{
		JButton button = new JButton();
		button.setMargin(new Insets(1, 0, 1, 0));
		button.setIcon(upArrow);
		int width = upArrow.getIconWidth() + button.getBorder().getBorderInsets(button).left + button.getBorder().getBorderInsets(button).right;
		button.setMinimumSize(new Dimension(width, button.getMinimumSize().height));
		button.setMaximumSize(new Dimension(width, button.getMaximumSize().height));
		button.setPreferredSize(new Dimension(width, button.getPreferredSize().height));
		button.setFocusable(DEFAULT_UP_BUTTON_FOCUSABLE);
		return button;
	}
	
	private JButton createDownButton(ImageIcon downArrow)
	{
		JButton button = new JButton();
		button.setMargin(new Insets(1, 0, 1, 0));
		button.setIcon(downArrow);
		int width = downArrow.getIconWidth() + button.getBorder().getBorderInsets(button).left + button.getBorder().getBorderInsets(button).right;
		button.setMinimumSize(new Dimension(width, button.getMinimumSize().height));
		button.setMaximumSize(new Dimension(width, button.getMinimumSize().height));
		button.setPreferredSize(new Dimension(width, button.getMinimumSize().height));
		button.setFocusable(DEFAULT_DOWN_BUTTON_FOCUSABLE);
		return button;
	}
	
	private JButton createDropButton(ImageIcon dropArrow)
	{
		JButton button = new JButton();
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setIcon(dropArrow);
		int width = UIManager.getInt("ScrollBar.width");
		if(width < button.getBorder().getBorderInsets(button).left + dropArrow.getIconWidth() + button.getBorder().getBorderInsets(button).right){
			width = button.getBorder().getBorderInsets(button).left + dropArrow.getIconWidth() + button.getBorder().getBorderInsets(button).right;
		}
		button.setMinimumSize(new Dimension(width, button.getMinimumSize().height));
		button.setMaximumSize(new Dimension(width, button.getMaximumSize().height));
		button.setPreferredSize(new Dimension(width, button.getPreferredSize().height));
		button.addMouseListener(this.createDropButtonListener());
		button.setFocusable(DEFAULT_DROP_BUTTON_FOCUSABLE);
		return button;
	}
	
	private SlideSpinnerSlider createSmallSlider()
	{
		SlideSpinnerSlider slider = new SlideSpinnerSlider(this.createSmallSliderObserver());
		slider.setMinimumSize(new Dimension(slider.getMinimumSize().width, DEFAULT_SMALL_SLIDER_HEIGHT));
		slider.setMaximumSize(new Dimension(slider.getMaximumSize().width, DEFAULT_SMALL_SLIDER_HEIGHT));
		slider.setPreferredSize(new Dimension(slider.getPreferredSize().width, DEFAULT_SMALL_SLIDER_HEIGHT));
		slider.installListeners();
		return slider;
	}
	
	private SlideSpinnerSliderFrame createLargeSlider(Window owner)
	{
		SlideSpinnerSliderFrame slider = new SlideSpinnerSliderFrame(owner, this.createLargeSliderObserver());
		slider.installListeners();
		return slider;
	}
	
	public JTextField getTextField()
	{
		return this.mTextField;
	}
	
	public void setModel(SlideSpinnerModel model)
	{
		this.mModel = model;
		return;
	}
	
	public SlideSpinnerModel getModel()
	{
		return this.mModel;
	}
	
	public void setValue(String value)
	{
		if(value == null || value.isEmpty()){
			return;
		}
		this.mModel.setValue(value);
		this.updateAll();
		return;
	}
	
	public String getValue()
	{
		return String.valueOf(this.mModel.getValue());
	}
	
	public JButton getIncreaseButton()
	{
		return this.mUpButton;
	}
	
	public JButton getDecreaseButton()
	{
		return this.mDownButton;
	}
	
	public JButton getDropButton()
	{
		return this.mDropButton;
	}
	
	private void positionChanged(double proportion)
	{
		this.mModel.setValue(proportion);
		this.updateTextField();
		this.notifyChangeListeners();
		//System.out.println(String.valueOf(this.mModel.getValue()));
		return;
	}
	
	public boolean getValueIsAdjusting()
	{
		if(this.mSmallSlider == null){
			return false;
		}
		if(this.mLargeSlider == null){
			return false;
		}
		return this.mSmallSlider.getValueIsAdjusting() || this.mLargeSlider.getValueIsAdjusting();
	}
	
	private void updateModelValue()
	{
		this.mModel.setValue(this.mTextField.getText());
		this.updateSliders();
		return;
	}
	
	private void updateTextField()
	{
		this.mTextField.setText(String.valueOf(this.mModel.getValue()));
		return;
	}
	
	private void updateAll()
	{
		this.updateTextField();
		this.updateSliders();
	}
	
	private void updateSliders()
	{
		if(this.mSmallSlider == null || this.mLargeSlider == null){
			return;
		}
		this.mSmallSlider.setPosition(this.mModel.getProportion());
		this.mLargeSlider.setPosition(this.mModel.getProportion());
		return;
	}
	
	private boolean checkIfValueExists()
	{
		boolean result = true;
		if(this.mTextField.getText() == null || this.mTextField.getText().isEmpty()){
			this.mTextField.setBackground(this.mErrorBackground);
			this.updateSliders();
			result = false;
		}else{
			this.mTextField.setBackground(this.mNormalBackground);
			result = true;
		}
		return result;
	}
	
	private boolean checkRange()
	{
		boolean inRange = true;
		try{
			int results = this.mModel.isWithinRange(this.mTextField.getText());
			if(results < 0){
				this.mTextField.setBackground(this.mErrorBackground);
				this.updateSliders();
				inRange = false;
			}else if(results > 0){
				this.mTextField.setBackground(this.mErrorBackground);
				this.updateSliders();
				inRange = false;
			}
		}catch(Exception ex){
			this.mTextField.setBackground(this.mErrorBackground);
			this.updateSliders();
			inRange = false;
		}
		return inRange;
	}
	
	@Override
	public void setEnabled(boolean value)
	{
		super.setEnabled(value);
		this.mTextField.setEnabled(value);
		this.mDownButton.setEnabled(value);
		this.mUpButton.setEnabled(value);
		if(this.mDropButton != null){
			this.mDropButton.setEnabled(value);
		}
		if(this.mSmallSlider != null){
			this.mSmallSlider.setEnabled(value);
		}
	}
	
	public void addChangeListener(SlideSpinnerChangeListener listener)
	{
		this.mChangeListeners.add(listener);
		return;
	}
	
	public void removeChangeListener(SlideSpinnerChangeListener listener)
	{
		this.mChangeListeners.remove(listener);
		return;
	}
	
	public void notifyChangeListeners()
	{
		for(SlideSpinnerChangeListener listener : this.mChangeListeners)
		{
			listener.stateChanged();
		}
		return;
	}
	
	private SlideSpinnerObserver createSmallSliderObserver()
	{
		return new SlideSpinnerObserver()
		{
			@Override
			public void positionChanged(double proportion)
			{
				SlideSpinner.this.positionChanged(proportion);
				mLargeSlider.setPosition(mModel.getProportion());
				//mLargeSlider.setPosition(proportion);
				return;
			}
			
			@Override
			public void increase()
			{
				mModel.getNextValue();
				updateAll();
			}
			
			@Override
			public void decrease()
			{
				mModel.getPreviousValue();
				updateAll();
			}
			
			@Override
			public Object getMin()
			{
				return mModel.getMin();
			}
			
			@Override
			public Object getMax()
			{
				return mModel.getMax();
			}
			
			public void updateModelValue() { return; }
			public void updateSlidersOnly() { return; }
		};
	}
	
	private SlideSpinnerObserver createLargeSliderObserver()
	{
		return new SlideSpinnerObserver()
		{
			@Override
			public void positionChanged(double proportion)
			{
				SlideSpinner.this.positionChanged(proportion);
				mSmallSlider.setPosition(mModel.getProportion());
				return;
			}
			
			@Override
			public void increase()
			{
				mModel.getNextValue();
				updateAll();
			}
			
			@Override
			public void decrease()
			{
				mModel.getPreviousValue();
				updateAll();
			}
			
			@Override
			public Object getMin()
			{
				return mModel.getMin();
			}
			
			@Override
			public Object getMax()
			{
				return mModel.getMax();
			}
			
			public void updateModelValue() { return; }
			public void updateSlidersOnly() { return; }
		};
	}
	
	private ActionListener createIncreaseButtonListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(mModel == null){
					return;
				}
				mModel.getNextValue();
				updateAll();
				return;
			}
		};
	}
	
	private ActionListener createDecreaseButtonListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(mModel == null){
					return;
				}
				mModel.getPreviousValue();
				updateAll();
				return;
			}
		};
	}
	
	private DocumentListener createDocumentListener()
	{
		return new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				if(!checkIfValueExists()){
					return;
				}
				if(!checkRange()){
					return;
				}
				updateModelValue();
				return;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				if(!checkIfValueExists()){
					return;
				}
				if(!checkRange()){
					return;
				}
				updateModelValue();
				return;
			}
			
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				if(!checkIfValueExists()){
					return;
				}
				if(!checkRange()){
					return;
				}
				updateModelValue();
				return;
			}
		};
	}
	
	private FocusListener createFocusListener()
	{
		return new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				mLargeSlider.setVisible(false);
				return;
			}
		};
	}
	
	private MouseListener createDropButtonListener()
	{
		return new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(!SlideSpinner.this.isEnabled()){
					return;
				}
				int x = mDropButton.getLocationOnScreen().x - (int)Math.round((mLargeSlider.getWidth() - mDropButton.getWidth()) / 2.0);
				int y = mDropButton.getLocationOnScreen().y + mDropButton.getHeight();
				mLargeSlider.showPanel(x, y);
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
					mModel.getNextValue();
					updateAll(); // Only use updateAll() for up and down keys.  Otherwise, it interferes with document listener.
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					mModel.getPreviousValue();
					updateAll(); // Only use updateAll() for up and down keys.  Otherwise, it interferes with document listener.
				}
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
					mModel.getNextValue();
				}else if(e.getWheelRotation() > 0){
					mModel.getPreviousValue();
				}
				updateAll();
				return;
			}
		};
	}
}
