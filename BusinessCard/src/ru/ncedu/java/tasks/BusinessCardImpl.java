package ru.ncedu.java.tasks;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;

public class BusinessCardImpl implements BusinessCard {
    private String employee = null;
    private String department = null;
    private Calendar birthDate = null;
    private String gender = null;
    private int salary = 0;
    private String phoneNumber = null;
    
    @Override
    public BusinessCard getBusinessCard(Scanner scanner) {
        BusinessCardImpl card = new BusinessCardImpl();
        
        String[] items = scanner.nextLine().split(";");
        if (items.length != 7) {
            throw new NoSuchElementException();
        }
        
        if (items[0].matches("^[A-Z,a-z]+([-][A-Z,a-z]+)*$") &&
                items[1].matches("^[A-Z,a-z]+([-][A-Z,a-z]+)*$")) {
            card.employee = items[0] + " " + items[1];
        }
        else {
            throw new InputMismatchException();
        }
        
        if (items[2].matches("^[A-Z,a-z]+$")) {
            card.department = items[2];
        }
        else {
            throw new InputMismatchException();
        }
        
        if (items[3].matches("^[0-9]{2}[-][0-9]{2}[-][0-9]{4}$")) {
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            card.birthDate = GregorianCalendar.getInstance();
            try {
                card.birthDate.setTime(format.parse(items[3]));
            }
            catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        else {
            throw new InputMismatchException();
        }
        
        if (items[4].matches("^(M|F)$")) {
            card.gender = items[4];
        }
        else {
            throw new InputMismatchException();
        }
        
        if (items[5].matches("^[0-9]{3,6}$")) {
            card.salary = Integer.parseInt(items[5]);
            if (card.salary > 100000) {
                throw new InputMismatchException();
            }
        }
        else {
            throw new InputMismatchException();
        }
        
        if (items[6].matches("^[0-9]{10}$")) {
            card.phoneNumber = "+7 " + items[6].substring(0, 3) +
                    "-" + items[6].substring(3,6) + "-" +
                    items[6].substring(6, 8) + "-" + items[6].substring(8);
        }
        else {
            throw new InputMismatchException();
        }
        
        return card;
    }

    @Override
    public String getEmployee() {
        return employee;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public int getAge() {
        Calendar today = GregorianCalendar.getInstance();
        
        Date bd = birthDate.getTime();
        
        int years = today.get(GregorianCalendar.YEAR) -
                birthDate.get(GregorianCalendar.YEAR);
        
        int month = today.get(GregorianCalendar.MONTH);
        int birthMonth = birthDate.get(GregorianCalendar.MONTH); 
        if (month < birthMonth ) {
            years --;
        } else  if (month == birthMonth
                && today.get(GregorianCalendar.DAY_OF_MONTH)
                < birthDate.get(GregorianCalendar.DAY_OF_MONTH)) {
            years --;
        }
        
        return years;
    }

    @Override
    public String getGender() {
        return gender.equals("M") ? "Male" : "Female";
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public static void main(String[] args) {
        BusinessCardImpl card = new BusinessCardImpl();
        
        Scanner s = new Scanner("Alexander;Kharichkin;DSI;07-09-1988;M;1000;9032606540");
        BusinessCard newCard = card.getBusinessCard(s);
        
        System.out.println(newCard.getEmployee());
        System.out.println(newCard.getDepartment());
        System.out.println(newCard.getSalary());
        System.out.println(newCard.getAge());
        System.out.println(newCard.getGender());
        System.out.println(newCard.getPhoneNumber());
    }
}
