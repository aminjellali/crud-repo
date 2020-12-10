package main;

public class CalculatorWithDiscount implements CalclInt {
	
	private Double discount;

	@Override
	public Double calculatePriceByUnitPriceAndQuantity(int quantity, Double unitPrice) {
		return quantity * unitPrice - discount;
	}

}
