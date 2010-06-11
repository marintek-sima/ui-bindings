package com.rcpcompany.uibindings.extests.valueBindings;

import static com.rcpcompany.uibindings.extests.BaseTestUtils.createTestView;
import static com.rcpcompany.uibindings.extests.BaseTestUtils.postKeyStroke;
import static com.rcpcompany.uibindings.extests.BaseTestUtils.postMouse;
import static com.rcpcompany.uibindings.extests.BaseTestUtils.sleep;
import static com.rcpcompany.uibindings.extests.BaseTestUtils.yield;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.TextCommitStrategy;
import com.rcpcompany.uibindings.extests.views.TestView;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopItem;
import com.rcpcompany.uibindings.utils.IFormCreator;

/**
 * Tests that when the model is changed, then the widget is changed as well.
 * 
 * @author Tonny Madsen, The RCP Company
 */
@RunWith(Parameterized.class)
public class ValueBindingFocusOutTest {
	private static final int DELAY = 200;
	private final TextCommitStrategy myStrategy;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

		{ TextCommitStrategy.ON_MODIFY },

		{ TextCommitStrategy.ON_MODIFY_DELAY },

		{ TextCommitStrategy.ON_FOCUS_OUT }

		});
	}

	public ValueBindingFocusOutTest(TextCommitStrategy strategy) {
		myStrategy = strategy;
	}

	private TestView myView;

	private ShopItem myItem;

	private IFormCreator myForm;

	private IValueBinding myNameBinding;
	private IValueBinding myPriceBinding;

	@Before
	public void setup() {
		IManager.Factory.getManager().setTextCommitStrategy(myStrategy);
		IManager.Factory.getManager().setTextCommitStrategyDelay(DELAY);
		IManager.Factory.getManager().setEditCellSingleClick(false);

		createModel();
		createView();
	}

	private void createModel() {
		myItem = ShopFactory.eINSTANCE.createShopItem();
		myItem.setName("abc");
		myItem.setPrice(1f);
	}

	private void createView() {
		myView = createTestView(this);
		myForm = myView.createFormCreator(myItem);

		myNameBinding = myForm.addField("name");
		myPriceBinding = myForm.addField("price");
		myForm.finish();
		myForm.getTop().layout();
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	@Test
	public void testValue() {
		yield();
		final Control c = myPriceBinding.getControl();
		assertTrue(c instanceof Text);
		final Text text = (Text) c;
		yield();

		assertEquals("1.00", text.getText());

		postMouse(c);
		yield();

		assertTrue(c.isFocusControl());

		postKeyStroke(c, "CTRL+A");
		postKeyStroke(c, "2");
		yield();

		switch (myStrategy) {
		case ON_MODIFY:
			assertEquals(2.0f, myItem.getPrice(), 0.001);
			break;
		case ON_MODIFY_DELAY:
			assertEquals(1.0f, myItem.getPrice(), 0.001);
			sleep(2 * DELAY);
			assertEquals(2.0f, myItem.getPrice(), 0.001);
			break;
		case ON_FOCUS_OUT:
			assertEquals(1.0f, myItem.getPrice(), 0.001);
			break;
		}
		yield();
		assertEquals("2", text.getText());

		assertTrue(myNameBinding.getControl().setFocus());
		yield();

		assertEquals("2.00", text.getText());
	}

	/**
	 * @param binding
	 * 
	 */
	private void dump(IValueBinding binding) {
		final Control c = binding.getControl();
		final Rectangle bounds = c.getBounds();
		System.out.println(binding.getLabel() + ": " + c.hashCode() + " - " + bounds + " - "
				+ c.getDisplay().map(c, null, bounds));
		for (Control d = c; !(d instanceof Shell); d = d.getParent()) {
			System.out.println("  " + d + ": " + d.getBounds() + " - " + c.getDisplay().map(c, d, bounds));

		}
	}
}