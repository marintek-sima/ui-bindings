package com.rcpcompany.uibindings.extests.uiAttributes;

import static com.rcpcompany.uibindings.extests.BaseTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Slider;
import org.junit.Test;

import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IUIAttribute;

/**
 * This test checks the properties of the default UI attributes for a {@link Slider} widget - those
 * with attribute = "".
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class UIAttributeSliderFactoryPropertiesTest extends BaseUIAttributeFactoryTest<Slider> {
	protected IUIAttribute attribute = null;

	@Test
	public void testUIAttribute() {
		final Slider widget = createWidget(Slider.class, SWT.NONE);

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				attribute = IManager.Factory.getManager().createUIAttribute(widget, "");
			}
		});

		assertNotNull(attribute);

		assertEquals("", attribute.getAttribute());
		assertEquals(widget, attribute.getWidget());

		testObservableValue(widget, "", attribute.getBackgroundValue(), Color.class, "background");
		testObservableValue(widget, "", attribute.getForegroundValue(), Color.class, "foreground");
		testObservableValue(widget, "", attribute.getFontValue(), Font.class, "font");
		testObservableValue(widget, "", attribute.getCursorValue(), Cursor.class, "cursor");
		testObservableValue(widget, "", attribute.getCurrentValue(), Integer.TYPE, "selection");
		testObservableValue(widget, "", attribute.getEnabledValue(), Boolean.TYPE, "enabled");
		testObservableValue(widget, "", attribute.getTooltipValue(), String.class, "toolTipText");
		testObservableValue(widget, "", attribute.getMinValue(), Integer.TYPE, "minimum");
		attribute.getMinValue().setValue(0);
		testObservableValue(widget, "", attribute.getMaxValue(), Integer.TYPE, "maximum");
		assertEquals(null, attribute.getFieldAssistAdapter());
		assertEquals(null, attribute.getPossibleValuesList());
		assertEquals(null, attribute.getStyleRangeList());
	}
}
