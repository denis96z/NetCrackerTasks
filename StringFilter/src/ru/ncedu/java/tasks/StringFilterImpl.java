package ru.ncedu.java.tasks;

import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class StringFilterImpl implements StringFilter {
	Set<String> strings = new LinkedHashSet<String>();
	
	@Override
	public void add(String s) {
		strings.add(s != null ? s.toLowerCase() : null);
	}
	
	@Override
	public boolean remove(String s) {
		return strings.remove(s);
	}
	
	@Override
	public void removeAll() {
		strings.clear();
	}
	
	@Override
	public Collection<String> getCollection() {
		return strings;
	}
	
	private abstract class Filter {
		protected String filterString = null;
		
		public Filter(String filter) {
			filterString = filter != null ? filter.toLowerCase() : null;
		}
		
		@SuppressWarnings("unused")
		public final void setFilterString(String filter) {
			filterString = filter;
		}
		
		public abstract boolean stringMatchesCondition(String s);
	}
	
	private class ContainsFilter extends Filter {
		public ContainsFilter(String filter) {
			super(filter);
		}
		
		@Override
		public boolean stringMatchesCondition(String s) {
			if (filterString == null || filterString.isEmpty()) {
				return true;
			}
			if (s == null || s.isEmpty()) {
				return false;
			}
			
			return s.contains(filterString);
		}
	}
	
	private class StartsWithFilter extends Filter {
		public StartsWithFilter(String filter) {
			super(filter);
		}
		
		@Override
		public boolean stringMatchesCondition(String s) {
			if (filterString == null || filterString.isEmpty()) {
				return true;
			}
			if (s == null || filterString.length() > s.length()) {
				return false;
			}
			
			return s.substring(0, filterString.length()).equals(filterString);
		}
	}
	
	private class NumberStringFilter extends Filter {
		public NumberStringFilter(String filter) {
			super(filter);
		}
		
		@Override
		public boolean stringMatchesCondition(String s) {
			if (filterString == null || filterString.isEmpty()) {
				return true;
			}	
			if (s == null || s.length() != filterString.length()) {
				return false;
			}
			
			for (int i = 0; i < s.length(); i++) {
				if (filterString.charAt(i) == '#') {
					if (s.charAt(i) < '0' || s.charAt(i) > '9') {
						return false;
					}
				}
				else {
					if (s.charAt(i) != filterString.charAt(i)) {
						return false;
					}
				}
			}
			
			return true;
		}
	}
	
	private class PatternStringFilter extends Filter {
		public PatternStringFilter(String filter) {
			super(filter);
		}
		
		@Override
		public boolean stringMatchesCondition(String s) {
			if (filterString == null || filterString.isEmpty()) {
				return true;
			}	
			if (s == null) {
				return false;
			}
			
			int firstStarIndex = filterString.indexOf('*');
			int lastStarIndex = filterString.lastIndexOf('*');
			
			if (firstStarIndex < 0) {
				return s.equals(filterString);
			}
			
			String firstPart = null, middlePart = null, lastPart = null;
			if (firstStarIndex == lastStarIndex) {
				if (firstStarIndex == 0) {
					firstPart = "";
					lastPart = filterString.substring(firstStarIndex + 1, filterString.length());
				}
				else if (firstStarIndex < filterString.length() - 1) {
					firstPart = filterString.substring(0, firstStarIndex);
					lastPart = filterString.substring(firstStarIndex + 1, filterString.length());
				}
				else {
					firstPart = filterString.substring(0, firstStarIndex);
					lastPart = "";
				}
				middlePart = "";
			}
			else {
				if (firstStarIndex == 0) {
					firstPart = "";
				}
				else {
					firstPart = filterString.substring(0, firstStarIndex);
				}
				
				middlePart = filterString.substring(firstStarIndex + 1, lastStarIndex);
				
				if (lastStarIndex < filterString.length() - 1) {
					lastPart = filterString.substring(lastStarIndex + 1, filterString.length());
				}
				else {
					lastPart = "";
				}
			}
			
			if (s.length() < firstPart.length() + middlePart.length() + lastPart.length()) {
				return false;
			}
			
			if (firstPart.length() > 0 && !s.substring(0, firstPart.length()).equals(firstPart)) {
				return false;
			}
			
			if (lastPart.length() > 0 && !s.substring(s.length() -
					lastPart.length(), s.length()).equals(lastPart)) {
				return false;
			}
			
			if (middlePart.length() > 0 && !s.substring(firstPart.length(),
					s.length() - lastPart.length()).contains(middlePart)) {
				return false;
			}
			
			return true;
		}
	}
	
	private Iterator<String> getIterator(Filter f) {
		ArrayList<String> sList = new ArrayList<String>();
		
		for (String s : strings) {
			if (f.stringMatchesCondition(s)) {
				sList.add(s);
			}
		}
		
		return sList.iterator();
	}
	
	@Override
	public Iterator<String> getStringsContaining(String chars) {
		return getIterator(new ContainsFilter(chars));
	}

	@Override
	public Iterator<String> getStringsStartingWith(String begin) {
		return getIterator(new StartsWithFilter(begin));
	}

	@Override
	public Iterator<String> getStringsByNumberFormat(String format) {
		return getIterator(new NumberStringFilter(format));
	}

	@Override
	public Iterator<String> getStringsByPattern(String pattern) {
		return getIterator(new PatternStringFilter(pattern));
	}
	
	public static void main(String[] args) {
		StringFilter sf = new StringFilterImpl();
		sf.add("threatened");
		sf.add("that");
		sf.add("they");
		sf.add("them");
		sf.add("than");
		sf.add("22(14)");
		sf.add(null);
		sf.add(null);
		sf.add("overload");
		sf.add("OK");
		
		Iterator<String> iter = sf.getStringsStartingWith("This");
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}
