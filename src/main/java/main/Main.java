package main;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Scanner;

import exceptions.ClassHasNoIdFieldException;

public class Main {

	public static void main(String[] args)
			throws IllegalArgumentException, IllegalAccessException, ClassHasNoIdFieldException, IOException {
		CrudService cs = new CrudService();
		Person pers = new Person("Amin", 256);
		//
		Scanner scannerInstance = new Scanner(System.in);
		System.out.println("Enter unit price [Double]");
		Double unitPrice = scannerInstance.nextDouble();
		System.out.println("Enter quantity [quantity]");
		int quantity = scannerInstance.nextInt();
		System.out.println(MessageFormat.format("Total price is: {0}", Calculator.calculatePriceByUnitPriceAndQuantity(quantity, unitPrice)));

	}

}
