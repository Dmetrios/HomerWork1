package dz1;

import java.util.Comparator;

/** Класс компаратор демонстрационного класса*/

public class MyComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
