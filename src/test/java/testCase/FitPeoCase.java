package testCase;

import org.testng.annotations.Test;

import factory.Browser;
import pageObject.FitPeoPage;

public class FitPeoCase extends Browser {

	FitPeoPage fitpeopage;

	@Test(priority = 1)
	public void testRevenueCalcLink() {
		fitpeopage = new FitPeoPage(driver);
		fitpeopage.clickRevenueCalculator();
	}

	@Test(priority = 2)
	public void slideRavenueCalc() {
		fitpeopage.scrollToMedicarePatients();
	}

	@Test(priority = 3)
	public void testAdjustSlider() {
		fitpeopage.adjustSlider();
	}

	@Test(priority = 4)
	public void testUpdatedPatients() {
		fitpeopage.updateSliderValue();
	}

	@Test(priority = 5)
	public void testCheckbox() {
		fitpeopage.checkboxPerform();
	}

	@Test(priority = 6)
	public void testTotalReimbursement() {
		fitpeopage.verifyTotalReimbursement();
	}
}
