package ru.ncedu.java.tasks;

import org.w3c.dom.*;
import java.util.*;
import javax.xml.xpath.*;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XPathCallerImpl implements XPathCaller {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();   
            Document doc = builder.parse("C:\\Users\\Денис\\Downloads\\netcracker-learning-master\\XPathCaller\\emp.xml");
            XPathCaller xpc = new XPathCallerImpl();
            Element[] empl = xpc.getCoworkers(doc, "7369", null);
            for (Element e : empl) {
                System.out.println(e.getElementsByTagName("ename").item(0).getTextContent());
            }
        }
        catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }
    
    private Element[] performQuery(Document src, String docType, String query) {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile(query);
            NodeList nodes = (NodeList)expr.evaluate(src, XPathConstants.NODESET);
            Element[] result = new Element[nodes.getLength()];
            for (int i = 0; i < nodes.getLength(); i++) {
                result[i] = (Element)nodes.item(i);
            }
            return result;
        }
        catch (Exception exception) {
            return null;
        }
    }
    
    @Override
    public Element[] getEmployees(Document src, String deptno, String docType) {
        return performQuery(src, docType, "//employee[@deptno=" + deptno + "]");
    }

    @Override
    public String getHighestPayed(Document src, String docType) {
        Element[] employees = performQuery(src, docType, "//employee");
        if (employees.length >= 1) {
            int maxIndex = 0;
            double maxSalary = Double.parseDouble(employees[0].getElementsByTagName("sal").item(0).getTextContent());
            for (int i = 1; i < employees.length; i++) {
                double salary = Double.parseDouble(employees[i].getElementsByTagName("sal").item(0).getTextContent());
                if (salary > maxSalary) {
                    maxSalary = salary;
                    maxIndex = i;
                }
            }
            return employees[maxIndex].getElementsByTagName("ename").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String getHighestPayed(Document src, String deptno, String docType) {
        Element[] employees = getEmployees(src, deptno, docType);
        if (employees.length >= 1) {
            int maxIndex = 0;
            double maxSalary = Double.parseDouble(employees[0].getElementsByTagName("sal").item(0).getTextContent());
            for (int i = 1; i < employees.length; i++) {
                double salary = Double.parseDouble(employees[i].getElementsByTagName("sal").item(0).getTextContent());
                if (salary > maxSalary) {
                    maxSalary = salary;
                    maxIndex = i;
                }
            }
            return employees[maxIndex].getElementsByTagName("ename").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public Element[] getTopManagement(Document src, String docType) {
        return performQuery(src, docType, "//employee[not (@mgr)]");
    }

    @Override
    public Element[] getOrdinaryEmployees(Document src, String docType) {
        /*Element[] employees = performQuery(src, docType, "//employee");
        
        ArrayList<Element> ordinary = new ArrayList<Element>();
        for (int i = 0; i < employees.length; i++) {
            boolean flag = true;         
            for (int j = 0; j < employees.length; j++) {
                if (i == j) {
                    continue;
                }
                if (employees[j].getAttribute("mgr").equals(employees[i].getAttribute("empno"))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ordinary.add(employees[i]);
            }
        }
        
        Element[] ordinaryEmployees = new Element[ordinary.size()];
        return ordinary.toArray(ordinaryEmployees);*/
        String pattern = docType.equals("emp")
                ? "/content/emp/employee[not(@empno = (/content/emp/employee/@mgr))]"
                : "//employee[not(./employee)]";
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = null;
        try {
            XPathExpression expression = xPath.compile(pattern);
            nodeList = (NodeList) expression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }      
        Element[] elements = new Element[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            elements[i] = (Element) nodeList.item(i);
        }
        return elements;
    }

    @Override
    public Element[] getCoworkers(Document src, String empno, String docType) {
        /*Element[] empnoEmployees = performQuery(src, docType, "//employee[@empno=" + empno + "]");
        if (empnoEmployees == null || empnoEmployees.length == 0) {
            return new Element[0];
        }
        
        Element employee = empnoEmployees[0];
        String managerEmpno = employee.getAttribute("mgr");
        
        return performQuery(src, docType, "//employee[@empno!=" + empno + "][@mgr=" + managerEmpno + "]");*/
        String pattern = docType.equals("emp")
                ? "/content/emp/employee[" +
                "not(@empno='" + empno + "') " +
                "and @mgr = (/content/emp/employee[@empno='" + empno + "']/@mgr)" +
                "]"
                : "//employee[@empno='" + empno + "']" +
                "/../" +
                "employee[not(@empno='" + empno + "')]";
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = null;
        try {
            XPathExpression expression = xPath.compile(pattern);
            nodeList = (NodeList) expression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }      
        Element[] elements = new Element[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            elements[i] = (Element) nodeList.item(i);
        }
        return elements;
    } 
}
