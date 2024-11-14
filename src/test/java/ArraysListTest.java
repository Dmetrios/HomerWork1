import dz1.Employee;
import dz1.MyComparator;
import dz1.util.ArraysList;
import dz1.util.MyCollections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ArraysListTest {

    @Test
    public void testAdd(){
        ArraysList<Integer> integers = new ArraysList<>(3);

        int number = 10;
        integers.add(10);
        integers.add(5);
        integers.add(3);
        integers.add(1, 10);
        Assertions.assertEquals(number, integers.get(0));
        Assertions.assertEquals(number, integers.get(1));

    }

    @Test
    public void testGet(){
        ArraysList<String> strings = new ArraysList<String>(List.of("Hello", "World"));

        String str = strings.get(1);
        Assertions.assertEquals("World", str);
        Assertions.assertEquals("World", strings.getLast());
    }

    @Test
    public void testRemoved(){
        ArraysList<String> strings = new ArraysList<String>(List.of("Hello", "World"));

        Assertions.assertTrue(strings.remove("World"));
        strings.remove(0);
        Assertions.assertNull(strings.get(0));
    }

    @Test
    public void testClear(){
        ArraysList<String> strings = new ArraysList<String>(List.of("Hello", "World"));

        Assertions.assertTrue(strings.remove("World"));
        strings.clear();
        Assertions.assertEquals(0, strings.size());
    }

    @Test
    public void testSort(){
        ArraysList<Double> list = new ArraysList<>(100);
        for(int i = 0; i<100; i++) {
            list.add(Math.random());
        }
        MyCollections.sort(list);
        Assertions.assertTrue(list.isSorted());
    }

    @Test
    public void testSortWithComparator(){
        ArraysList<Employee> employees = new ArraysList<Employee>(List.of(
        new Employee("U", 39),
        new Employee("Z", 20),
        new Employee("A", 10),
        new Employee("B", 10),
        new Employee("D", 10),
        new Employee("A", 10),
        new Employee("Y", 10),
        new Employee("Q", 10),
        new Employee("I", 10),
        new Employee("O", 10)));
        MyCollections.sort(employees, new MyComparator());
        Assertions.assertTrue(employees.isSorted(new MyComparator()));
    }
}
