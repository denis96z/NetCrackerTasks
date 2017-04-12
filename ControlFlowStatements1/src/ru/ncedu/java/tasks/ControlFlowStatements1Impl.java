package ru.ncedu.java.tasks;

public class ControlFlowStatements1Impl
	implements ControlFlowStatements1 {

	@Override
	public float getFunctionValue(float x) {
		float f = 0;
		
		if (x > 0) {
			f = 2 * (float)Math.sin(x);
		}
		else {
			f = 6 - x;
		}
		
		return f;
	}

	@Override
	public String decodeWeekday(int weekday) {
		String strWeekday = null;
		
		switch (weekday) {
			case 1:
				strWeekday = "Monday";
				break;
				
			case 2:
				strWeekday = "Tuesday";
				break;
			
			case 3:
				strWeekday = "Wednesday";
				break;
				
			case 4:
				strWeekday = "Thursday";
				break;
				
			case 5:
				strWeekday = "Friday";
				break;
				
			case 6:
				strWeekday = "Saturday";
				break;
				
			case 7:
				strWeekday = "Sunday";
				break;
				
			default:
				strWeekday = "Error";
				break;
		}
		
		return strWeekday;
	}

	@Override
	public int[][] initArray() {
		final int m = 8, n = 5;		
		int[][] array = new int[m][n];
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				array[i][j] = i * j;
			}
		}
		
		return array;
	}

	@Override
	public int getMinValue(int[][] array) {
		int min = array[0][0];
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] < min) {
					min = array[i][j];
				}					
			}
		}
			
		return min;
	}

	@Override
	public BankDeposit calculateBankDeposit(double P) {
		BankDeposit deposit = new BankDeposit();
		double pf = 1 + P / 100;
		
		for (deposit.years = 0, deposit.amount = 1000;
			deposit.amount <= 5000; deposit.years++) {
			deposit.amount *= pf;
		}
		
		return deposit;
	}
	
	public static void main(String[] args) {
		ControlFlowStatements1 cfs = new ControlFlowStatements1Impl();
		
		BankDeposit d = cfs.calculateBankDeposit(12.5);
		System.out.println(d);
	}
}
