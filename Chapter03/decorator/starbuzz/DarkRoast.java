package decorator.starbuzz;

import java.io.FilterInputStream;
import java.util.zip.ZipInputStream;

public class DarkRoast extends Beverage {
	public DarkRoast() {
		description = "Dark Roast Coffee";
	}

	public double cost() {
		return .99;
	}

}