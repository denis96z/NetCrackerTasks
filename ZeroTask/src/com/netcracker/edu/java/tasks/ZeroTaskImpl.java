package com.netcracker.edu.java.tasks;

public class ZeroTaskImpl implements ZeroTask {
	private int number = 0;
	
	@Override
	public void setIntegerValue(int value) {
		number = value;
	}
	
	@Override
	public double getDoubleValue() {
		return number;
	}
	
	public static void main(String[] args)
	{
		ZeroTaskImpl z = new ZeroTaskImpl();
		z.setIntegerValue(3);
		System.out.print(z.getDoubleValue());
	}
}
