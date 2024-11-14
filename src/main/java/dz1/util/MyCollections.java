package dz1.util;

import java.util.Comparator;
import java.util.List;

/**
 * Утилитный класс для сортировки коллекций.
 * @author Dmitry Niyazov
 * @version 1.0*/

public final class MyCollections {

    private MyCollections(){
    }

    public  static <E> void sort(List<E> list){
        list.sort(null);
    }

    public static <E> void sort(List<E> list, Comparator<? super E> c){
        list.sort(c);
    }
}
