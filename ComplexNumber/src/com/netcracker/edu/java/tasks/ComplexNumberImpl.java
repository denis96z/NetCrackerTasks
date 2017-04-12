package com.netcracker.edu.java.tasks;

import java.util.Arrays;

public class ComplexNumberImpl implements ComplexNumber {
	private double re = 0;
	private double im = 0;
	
	public ComplexNumberImpl() { }
	
	public ComplexNumberImpl(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public ComplexNumberImpl(String value) {
		set(value);
	}
	
	public double getRe() {
		return re;
	}
	
	public double getIm() {
		return im;
	}
	
	public boolean isReal() {
		return im==0;
	}
	
	public void set(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public void set(String value) throws NumberFormatException {
		String re = "0";
		String im = "0";
		
		if (value.isEmpty()) {
			throw new NumberFormatException();
		}
		
		int liofPlus = value.lastIndexOf('+');
		int liofMinus = value.lastIndexOf('-');
		int signIndex = Math.max(liofPlus, liofMinus);
		
		if (value.indexOf('i') < 0) {
			if (signIndex > 0) {
				throw new NumberFormatException();
			}
			else {
				re = value;
			}
		}
		else {
			if (value.compareTo("i") == 0) {
				im = "1";
			}
			else if (signIndex > 0) {
				re = value.substring(0, signIndex);
				im = value.substring(signIndex, value.length() - 1);
			}
			else {
				im = value.substring(0, value.length() - 1);
			}
		}
		
		if (re.compareTo("+") == 0) {
			re = "1";
		}
		else if (re.compareTo("-") == 0) {
			re = "-1";
		}
		
		if (im.compareTo("+") == 0) {
			im = "1";
		}
		else if (im.compareTo("-") == 0) {
			im = "-1";
		}
		
		this.re = Double.parseDouble(re);
		this.im = Double.parseDouble(im);
	}
	
	public ComplexNumber copy() {
		return new ComplexNumberImpl(this.re, this.im);
	}
	
	public ComplexNumber clone() throws CloneNotSupportedException {
		return new ComplexNumberImpl(this.re, this.im);
	}
	
	public String toString() {
		String re = "";
		if (this.re != 0 || this.im == 0) {
			re = Double.toString(this.re);
		}
		
		String im = "";
		if (this.im != 0) {
			if (re.compareTo("") != 0)
			{
				im = this.im >= 0 ? "+" : "";
			}
			im += Double.toString(this.im) + "i";
		}
		
		return re + im;	
	}
	
	public boolean equals(Object other) {
		if (other instanceof ComplexNumber) {
			return ((ComplexNumber)other).getRe() == re &&
				((ComplexNumber)other).getIm() == im;
		}
		return false;
	}
	
	public int compareTo(ComplexNumber other) {
		double s1 = Math.pow(re, 2) + Math.pow(im, 2);
		double s2 = Math.pow(other.getRe(), 2) + Math.pow(other.getIm(), 2);
		
		if (s1 < s2) {
			return -1;
		}
		else if (s1 > s2) {
			return 1;
		}
		
		return 0;
	}
	
	public void sort(ComplexNumber[] array) {
		Arrays.sort(array);
	}
	
	public ComplexNumber negate() {
		re = -re;
		im = -im;
		return this;
	}
	
	public ComplexNumber add(ComplexNumber arg2) {
		re += arg2.getRe();
		im += arg2.getIm();
		return this;
	}
	
	public ComplexNumber multiply(ComplexNumber arg2) {
		double a = re;
		double b = im;
		double c = arg2.getRe();
		double d = arg2.getIm();
		
		re = a * c - b * d;
		im = b  *c + a * d;
		
		return this;
	}
	
	public static void main(String[] args) {
		ComplexNumber c = new ComplexNumberImpl();
		
		c.set("-5+2i");
		System.out.println(c.toString());
		
		c.set("1+i");
		System.out.println(c.toString());
		
		c.set("+4-i");
		System.out.println(c.toString());
		
		c.set("i");
		System.out.println(c.toString());
		
		c.set("-3i");
		System.out.println(c.toString());
		
		c.set("3");
		System.out.println(c.toString());
		
		c.set("1.0i");
		System.out.println(c.toString());
		
		c.set("-1.5+1.2i");
		System.out.println(c.toString());
	}
}
