package pageObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class FitPeoPage extends BasePage {

	public FitPeoPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//div[contains(text(), 'Revenue Calculator')]")
	WebElement RevenueCalc;

	@FindBy(xpath = "//h4[contains(text(), 'Medicare Eligible Patients')]")
	WebElement MedicarePatientsScroll;

	@FindBy(xpath = "//input[@type='range']")
	WebElement sliderHandle;

	@FindBy(xpath = "//fieldset[starts-with(@class, 'MuiOutlined')]")
	WebElement scroll;

	@FindBy(xpath = "//input[starts-with(@class, 'MuiInputBase')]")
	WebElement patientsValue;

	@FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div/p[1]")
	List<WebElement> cptCodes;

	@FindBy(xpath = "//input[@type='checkbox']")
	List<WebElement> checkbox;

	@FindBy(xpath = "//p[starts-with(text(),'Total Recurring')][1]")
	WebElement totalRecurringReimbursement;

	@FindBy(xpath = "//p[starts-with(text(),'Total Recurring')][1]/p")
	WebElement totalReimbursementPrice;

	List<String> cptlist = new ArrayList<>();

	public void clickRevenueCalculator() {
		RevenueCalc.click();
	}

	public void scrollToMedicarePatients() {
		explicitWait(MedicarePatientsScroll);
		scrollToElement(MedicarePatientsScroll);
	}

	public void adjustSlider() {
		Actions actions = new Actions(driver);
		actions.clickAndHold(sliderHandle).moveByOffset(94, 0).release().perform();

		// Get the updated value of the slider
		String updatedValue = sliderHandle.getAttribute("value");
		System.out.println("Updated Value: " + updatedValue);
	}

	public void updateSliderValue() {
		driver.navigate().refresh();
		patientsValue.clear();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '560';", patientsValue);

		String updatedValue = patientsValue.getAttribute("value");
		System.out.println("Updated Value: " + updatedValue);
	}

	public void checkboxPerform() {
		adjustSlider();

		for (WebElement ele : cptCodes) {
			String cpttitle = ele.getText();
			cptlist.add(cpttitle);
		}

		// List of CPT codes to check
		List<String> targetCPTs = Arrays.asList("CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474");

		// Scroll and click checkbox for each matching CPT code
		for (int i = 0; i < cptlist.size(); i++) {
			if (targetCPTs.contains(cptlist.get(i))) {
				scrollToElement(cptCodes.get(i));
				try {
					checkbox.get(i).click();
				} catch (Exception e) {
					System.out.println("Checkbox is not clickable.");
				}
			}
		}
	}

	public void verifyTotalReimbursement() {
		String totalReimbursementValue = totalReimbursementPrice.getText();
		String totalRecurringReimbursementHeading = totalRecurringReimbursement.getText();

		Assert.assertEquals(totalReimbursementValue, "$110970");

		System.out.println("Total Reimbursement Price: " + totalReimbursementValue);
		System.out.println(totalRecurringReimbursementHeading);
	}
}
