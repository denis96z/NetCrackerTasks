package ru.ncedu.java.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

public class DateCollectionsImpl implements DateCollections {
	int dateStyle = DateFormat.MEDIUM;
	
	Map<String, Element> mainMap = null;
	
	@Override
	public void setDateStyle(int dateStyle) {
		this.dateStyle = dateStyle;
	}

	@Override
	public Calendar toCalendar(String dateString) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(dateStyle,Locale.US);
		Date date = format.parse(dateString);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}

	@Override
	public String toString(Calendar date) {
		DateFormat dateFormat = DateFormat.getDateInstance(dateStyle);
		return dateFormat.format(date.getTime());
	}

	@Override
	public void initMainMap(int elementsNumber, Calendar firstDate) {
		Comparator<String> comparator = new Comparator<String>() {
		    @Override
		    public int compare(String left, String right) {
				try {
					return toCalendar(left).compareTo(toCalendar(right));
				} catch (ParseException e) {
					return 0;
				}
		    }
		};
		
		mainMap = new TreeMap<String, Element>(comparator);
		
		Random random = new Random();
		for (int i = 0; i < elementsNumber; i++) {
			Calendar birthDate = new GregorianCalendar(firstDate.get(Calendar.YEAR),
					firstDate.get(Calendar.MONTH), firstDate.get(Calendar.DATE) + i * 110);
			int lifetime = random.nextInt(2000);
			mainMap.put(toString(birthDate), new Element(birthDate, lifetime));
		}
	}

	@Override
	public void setMainMap(Map<String, Element> map) {
		mainMap = map;
	}

	@Override
	public Map<String, Element> getMainMap() {
		return mainMap;
	}

	@Override
	public SortedMap<String, Element> getSortedSubMap() {
		Comparator<String> comparator = new Comparator<String>() {
		    @Override
		    public int compare(String left, String right) {
				try {
					return toCalendar(left).compareTo(toCalendar(right));
				} catch (ParseException e) {
					return 0;
				}
		    }
		};
		
		SortedMap<String, Element> futMap = new TreeMap<String, Element>(comparator);
		
		Calendar today = Calendar.getInstance();
		for (Entry<String, Element> entry : mainMap.entrySet()) {
			if (entry.getValue().getBirthDate().compareTo(today) > 0) {
				futMap.put(entry.getKey(), entry.getValue());
			}
	    }
		
		return futMap;
	}

	@Override
	public List<Element> getMainList() {
		return new ArrayList<Element>(mainMap.values());
	}

	@Override
	public void sortList(List<Element> list) {
		Comparator<Element> comparator = new Comparator<Element>() {
		    @Override
		    public int compare(Element left, Element right) {
		        return left.getDeathDate().compareTo(right.getDeathDate());
		    }
		};
		Collections.sort(list, comparator);
	}

	@Override
	public void removeFromList(List<Element> list) {
		for (ListIterator<Element> iterator =
				list.listIterator(); iterator.hasNext(); ) {
		   int month = iterator.next().getBirthDate().get(Calendar.MONTH);
		   if(month == Calendar.DECEMBER || month == Calendar.JANUARY || month == Calendar.FEBRUARY) {
		      iterator.remove();
		   }
		}
	}
	
	public static void main(String[] args) {
		DateCollections dc = new DateCollectionsImpl();
		Calendar firstDate = new GregorianCalendar(2014, 7, 4);
		dc.initMainMap(20, firstDate);
		
		dc.setDateStyle(DateFormat.SHORT);
		for (Element e : dc.getMainList()) {
			System.out.println(dc.toString(e.getBirthDate()));
		}
		
		try {
			dc.toCalendar("10.01.2017");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
