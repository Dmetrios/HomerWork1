package dz1.util;

import java.util.*;

/**
* Реализация интерфейса List с динамическим изменением размера массива.
 * @author Dmitry Niyazov
 * @version 1.0
 * @since 22.0
 */
public class ArraysList<E> implements List<E> {

    /**Объем массива по умолчанию.*/
    private final static int DEFAULT_V = 10;

    /**Экземпляр пустого массива.*/
    private final static Object[] DEFAULT_ARRAY_DATA = {};

/**
    Экземпляр массива, который будет хранить элементы ArraysList, пустой ArraysList будет расширен до DEFAULT_V, при добавлении первого элемента.
*/
    private Object[] arrayData;

    /**Поле, хранящее количество элементов в ArraysList.*/
    private int size;


/**
    Конструктор пустого листа, который в момент добавления 1-го элемента будет расширен до DEFAULT_V.
*/
    public ArraysList() {
        this.arrayData = DEFAULT_ARRAY_DATA;
    }

/**
    * Конструктор листа с заданным объемом.
    * @param initial_V заданный объем листа
    * @throws IllegalArgumentException если заданный объем отрицательный
*/
    public ArraysList(int initial_V){
        if(initial_V > 0){
            this.arrayData = new Object[initial_V];
        }
        else if (initial_V == 0){
            this.arrayData = DEFAULT_ARRAY_DATA;
        }
        else throw new IllegalArgumentException("Illegal Capacity: "+
                    initial_V);
    }
    /**
     * Конструктор листа с заданным коллекцией.
     * @param collection заданная коллекция, элементы которой необходимо встроить в лист
     * @throws NullPointerException если заданная коллекция пуста
     */
    public ArraysList(Collection<? extends E> collection){
        Object[] a = collection.toArray();
        if ((this.size = a.length) != 0){
            if(collection.getClass() == ArraysList.class){
                this.arrayData = a;
            }
            else {
                arrayData = Arrays.copyOf(a, this.size, Object[].class);
            }
        }
        else {
            arrayData = DEFAULT_ARRAY_DATA;
        }
    }
    /**
     * Метод увеличения объема массива, который гарантирует, что ArraysList вместит в себя
     * необходимое минимальное количество элементов.
     * @param min_V минимальный объем
     */
    private Object[] grow(int min_V){
        int old_V = this.arrayData.length;
        if(old_V > 0 || this.arrayData != DEFAULT_ARRAY_DATA){
            int new_V = old_V + (old_V >> 1);
            return this.arrayData = Arrays.copyOf(this.arrayData, new_V);
        }
        else {
            return arrayData = new Object[Math.max(DEFAULT_V, min_V)];
        }
    }

    private Object[] grow(){
        return grow(this.size + 1);
    }
    /**
     * Вспомогательный метод при добавлении элемента в список.
     */
    private void add(E e, Object[] arrayData, int size){
        if(size == arrayData.length)
            arrayData = grow();
        arrayData[size] = e;
        this.size = size + 1;
    }
    /**
     * Метод добавление элемента в конец списка.
     * @param e элемент, который необходимо добавить
     */
    public boolean add(E e){
        add(e, this.arrayData, size);
        return true;
    }

    /**
     * Метод добавления элемента в определенный индекс.
     * @param index индекс, куда необходим добавить элемент
     * @param element элемент, который необходимо добавить
     * @throws IndexOutOfBoundsException если индекс находится за пределами массива
     */
    public void add(int index, E element) {
        rangeCheck(index);
        if(index == size) {
            add(element);
        }
        else if(index < size){
            this.arrayData[index] = element;
        }
    }

    /**
     * Возвращает количество элементов в списке.
     * @return  количество элементов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Удаляет первое вхождение заданного элемента.
     * @param o элемент, который необходимо удалить
     */
    public boolean remove(Object o) {
        int i = 0;
        found: {
            if (o == null){
                for(; i < this.size; i++)
                    if(this.arrayData[i] == null)
                        break found;
            }
            else {
                for (; i<this.size;i++){
                    if (o.equals(this.arrayData[i]))
                        break found;
                }
            }
            return false;
        }
        remove(i);
        return true;
    }

    /**
     * Возвращает элемент по индексу.
     * @param index индекс элемента, который необходимо получить
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) this.arrayData[index];
    }

    /**
     * Возвращает последний элемент списка.
     * @throws NoSuchElementException если таковой элемент отсутствует
     * @return возвращает последний элемент списка
     */
    @SuppressWarnings("unchecked")
    public E getLast(){
        if(size-1 < 0){
           throw new NoSuchElementException();
        }
        else {
            return (E) this.arrayData[size-1];
        }
    }
    /**
     * Удаляет элемент по заданному индексу.
     * @param index индекс элемента, который необходимо удалить
     * @throws IndexOutOfBoundsException если индекс находится за пределами массива
     */
    public E remove(int index) {
        rangeCheck(index);
        E removeValue = (E) this.arrayData[index];
        this.arrayData[index] = null;
        size-=1;
        return removeValue;
    }
    /**
     * Возвращает {@code true} если список пуст.
     * @return {@code true} если список пуст
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c){
        int referenceIndex = size/2;
        MyArrays.quickSort((E[])arrayData, referenceIndex, 0, size-1, c);
    }
    /**
     * Очищает исходный список до изначального состояния.
     */
    public void clear() {
        this.arrayData = DEFAULT_ARRAY_DATA;
        size = 0;
    }
    /**
     * Возвращает {@code true} если список отсортирован.
     * @return {@code true} если список отсортирован
     */
    @SuppressWarnings("unchecked")
    public boolean isSorted(){
        int count = 0;
        for(int i = 0; i < size-1; i++){
            if(((Comparable) arrayData[i]).compareTo(arrayData[i+1]) <= 0){
                count++;
            }
        }

        return count == size - 1;
    }
    /**
     * Возвращает {@code true} если список отсортирован.
     * @param c компаратор для определения порядка в массиве, значение null указывает на то,
     * что следует использовать естественный порядок расположение элементов
     * @return {@code true} если список отсортирован
     */
    @SuppressWarnings("unchecked")
    public boolean isSorted(Comparator<E> c){
        int count = 0;
        for(int i = 0; i < size-1; i++){
            if(c.compare((E)arrayData[i], (E)arrayData[i+1]) <= 0){
                count++;
            }
        }

        return count == size - 1;
    }

    private void rangeCheck(int index){
        if(index > size  || index < 0){
            throw new ArrayIndexOutOfBoundsException("Индекс за пределами массива: "  + index + " of " + size);
        }
    }


    public boolean addAll(Collection c) {
        return false;
    }
    public boolean contains(Object o) {
        return false;
    }
    public Iterator<E> iterator() {
        return null;
    }
    public Object[] toArray() {
        return new Object[0];
    }
    public <T> T[] toArray(T[] a) {
        return null;
    }
    public boolean containsAll(Collection<?> c) {
        return false;
    }
    public boolean removeAll(Collection<?> c) {
        return false;
    }
    public boolean retainAll(Collection<?> c) {
        return false;
    }
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }
    public E set(int index, E element) {
        return null;
    }
    public int indexOf(Object o) {
        return 0;
    }
    public int lastIndexOf(Object o) {
        return 0;
    }
    public ListIterator<E> listIterator() {
        return null;
    }
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }
}
