package ru.ncedu.java.tasks;

public class ControlFlowStatements3Impl
	implements ControlFlowStatements3 {
	
	@Override
	public double getFunctionValue(double x)
	{
		double f = 0;
		
		if (x <= 0) {
			f = -x;
		}
		else if (0 < x && x < 2) {
			f = Math.pow(x, 2);
		}
		else {
			f = 4;
		}
		
		return f;
	}
	
	@Override
	public String decodeSeason(int monthNumber) {
		String season = null;
		
		switch (monthNumber) {
			case 1: case 2: case 12:
				season = "Winter";
				break;
				
			case 3: case 4: case 5:
				season = "Spring";
				break;
				
			case 6: case 7: case 8:
				season = "Summer";
				break;
				
			case 9: case 10: case 11:
				season = "Autumn";
				break;
				
			default:
				season = "Error";
		}
		
		return season;
	}
	
	@Override
	public long[][] initArray() {
		final int m = 8, n = 5;		
		long[][] array = new long[m][n];
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				array[i][j] = (long)Math.pow(Math.abs(i - j), 5);
			}
		}
		
		return array;
	}
	
	@Override
	public int getMaxProductIndex(long[][] array) {
		double maxComp = 0;
		int index = 0;
		
		for (int i = 0; i < array.length; i++) {
			long c = array[i][0];
			
			for (int j = 1; j < array[i].length; j++) {
				c *= array[i][j];
			}
			
			c = Math.abs(c);
			
			if (c > maxComp) {
				maxComp = c;
				index = i;
			}
		}
		
		return index;
	}
	
	@Override
	public float calculateLineSegment(float A, float B) {
		return A % B;
	}
	
	public static void main(String[] args) {
		ControlFlowStatements3 cfs = new ControlFlowStatements3Impl();
		
		System.out.println(cfs.getFunctionValue(-1));
		System.out.println(cfs.getFunctionValue(0));
		System.out.println(cfs.getFunctionValue(1.5));
		System.out.println(cfs.getFunctionValue(2));
		System.out.println(cfs.getFunctionValue(5));
		
		System.out.println(cfs.decodeSeason(1));
		System.out.println(cfs.decodeSeason(5));
		System.out.println(cfs.decodeSeason(7));
		System.out.println(cfs.decodeSeason(10));
		System.out.println(cfs.decodeSeason(13));
		
		long[][] array = cfs.initArray();
		
		System.out.println(cfs.getMaxProductIndex(array));
		
		System.out.println(cfs.calculateLineSegment(10, 3));
	}
}
