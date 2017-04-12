package ru.ncedu.java.tasks;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector {
	double[] elements;
	
	public void set(double... elements) {
		this.elements = elements;
	}
	
	public double[] get() {
		return this.elements;
	}
	
	public ArrayVector clone() {
		ArrayVector vectorClone = new ArrayVectorImpl();
		vectorClone.set(this.elements.clone());
		return vectorClone;
	}
	
	public int getSize() {
		return this.elements.length;
	}
	
	public void set(int index, double value) {
		if (index >= 0) {
			if (index < this.elements.length) {
				this.elements[index] = value;
			}
			else {
				double[] tempArray = new double[index + 1];
				for (int i = 0; i < this.elements.length; i++) {
					tempArray[i] = this.elements[i];
				}
				tempArray[index] = value;
				this.elements = tempArray;
			}
		}
	}
	
	public double get(int index)
		throws ArrayIndexOutOfBoundsException {
		if (index < 0 || index >= this.elements.length) {
			throw new ArrayIndexOutOfBoundsException();
		}	
		return this.elements[index];
	}
	
	public double getMax() {
		double max = this.elements[0];
		for (int i = 1; i < this.elements.length; i++) {
			if (this.elements[i] > max) {
				max = this.elements[i];
			}
		}
		return max;
	}
	
	public double getMin() {
		double min = this.elements[0];
		for (int i = 1; i < this.elements.length; i++) {
			if (this.elements[i] < min) {
				min = this.elements[i];
			}
		}
		return min;
	}
	
	public void sortAscending() {
		Arrays.sort(this.elements);
	}
	
	public void mult(double factor) {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] *= factor;
		}
	}
	
	public ArrayVector sum(ArrayVector anotherVector) {
		int length = Math.min(getSize(), anotherVector.getSize());
		for (int i = 0; i < length; i++) {
			this.elements[i] += anotherVector.get(i);
		}
		return this;
	}
	
	public double scalarMult(ArrayVector anotherVector) {
		int length = Math.min(getSize(), anotherVector.getSize());
		double result = 0;
		for (int i = 0; i < length; i++) {
			result += this.elements[i] * anotherVector.get(i);
		}
		return result;
	}
	
	public double getNorm() {
		return Math.sqrt(scalarMult(this));
	}
}
