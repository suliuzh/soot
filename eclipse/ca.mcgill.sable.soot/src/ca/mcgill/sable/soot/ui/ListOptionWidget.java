package ca.mcgill.sable.soot.ui;

import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
//import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.*;

//import ca.mcgill.sable.soot.SootPlugin;


/**
 * @author jlhotak
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */
public class ListOptionWidget {//extends Composite {

	private Label label;
	private Text text;
	private String alias;
	
	/**
	 * Constructor for PathOptionClass.
	 * @param parent
	 * @param style
	 */
	//public PathOptionWidget(Composite parent, int style) {
	//	super(parent, style);
	//}
	
	/**
	 * Constructor for PathOptionClass.
	 * @param parent
	 * @param style
	 */
	public ListOptionWidget(Composite parent, int style, 
	  OptionData data) {
		//super(parent, style);
		
		setAlias(data.getRealAlias());
		
		Group path = new Group(parent, SWT.RIGHT);
		//path.pack();
		GridLayout gl = new GridLayout();
		gl.numColumns = 3;	
		//gl.marginHeight = 0;
			
		path.setLayout(gl);
		//parent.setLayout(gl);
		// this makes widget fill horizontal space
		GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
       	gridData2.horizontalSpan = 3;
       	//gridData2.verticalSpan = 1;
       	//gridData2.grabExcessHorizontalSpace = true;
       	//gridData2.grabExcessVerticalSpace = true;
       	path.setLayoutData(gridData2);
       
  
  		//IDialogSettings settings = SootPlugin.getDefault().getDialogSettings();
  		//String defaultVal = settings.get(getAlias());
  			
		setLabel(new Label(path, SWT.NONE));
		setLabelText(data.getText());
		setText(new Text(path,  SWT.MULTI | SWT.BORDER));
		//if (defaultVal != null) {
		//	getText().setText(defaultVal);
		//}
		if ((data.getInitText() == null) || (data.getInitText().length() == 0)){
			getText().setText(data.getInitText());
		}
		else {
			getText().setText(data.getInitText());
		}
		String listMessage = " Separate values on different lines.";
		getText().setToolTipText(data.getTooltip()+listMessage);		
		// this makes label fill available space		
		//GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        //gridData.horizontalSpan = 1;
        
		//getLabel().setLayoutData(gridData);

		// this makes textbox fill available space		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        //gridData.grabExcessHorizontalSpace = false;
		getText().setLayoutData(gridData);
		
		
	}
	
	
	

	public void setLabelText(String text) {
		getLabel().setText(text);
	}

	/**
	 * Returns the label.
	 * @return Label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * Returns the text.
	 * @return Text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * Sets the label.
	 * @param label The label to set
	 */
	public void setLabel(Label label) {
		this.label = label;
	}

	/**
	 * Sets the text.
	 * @param text The text to set
	 */
	public void setText(Text text) {
		this.text = text;
	}

	

	/**
	 * Returns the alias.
	 * @return String
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 * @param alias The alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

}
