package dz1.util;

import java.util.Arrays;
import java.util.Comparator;

/**
Утилитный класс, содержащий различные методы сортировок.
 @author Dmitry Niyazov
 @version 1.0
 */
public final class MyArrays {
/**
    Параметр при котором сортировка вставками будет приоритетнее сортировкам слиянием и быстрой сортировкой.
*/
    private static final int MINIMUM_ELEMENTS_SORTED_ARRAY = 3;

    /** Функция сортировки методом слиянием.
     * @param sortedArray - массив, подлежащий сортировке
     * @param leftIndex - индекс первого элемента
     * @param rightIndex - индекс последнего элемента
     * @param c - компаратор для определения порядка в массиве, значение null указывает на то,
          что следует использовать естественный порядок расположение элементов*/
    public static <E> void mergeSort(E[] sortedArray, int leftIndex, int rightIndex, Comparator<? super E> c) {
        if (c == null) {
            mergeSorted(sortedArray, leftIndex, rightIndex);
        } else {
            mergeSorted(sortedArray, leftIndex, rightIndex, c);
        }
    }
    /**
     SortedArray - это исходный массив, где leftIndex - 0-ой индекс исходного массива, а rightIndex - последний индекс.
     */
    @SuppressWarnings("unchecked")
    private static void mergeSorted(Object[] sortedArray, int leftIndex, int rightIndex) {
        int length = rightIndex - leftIndex + 1;

        // Сортировка вставками для массивов длиной до 3 элементов
        if (length < MINIMUM_ELEMENTS_SORTED_ARRAY) {
            for (int i = leftIndex; i <= rightIndex; i++) {
                for (int j = i; j > leftIndex && ((Comparable) sortedArray[j - 1]).compareTo(sortedArray[j]) >= 0; j--) {
                    swap(sortedArray, j, j - 1);
                }
            }
            return;
        }
        // Параметр, который указывает на середину массива
        int middleIndex = (leftIndex + rightIndex) >>> 1;
        mergeSorted(sortedArray, leftIndex, middleIndex);
        mergeSorted(sortedArray, middleIndex + 1, rightIndex);

        // Вспомогательный массив для записи в него сортировки
        Object[] supps = new Object[length];

        // Алгоритм сортировки
        for(int i = leftIndex, j=middleIndex+1, p=0; p<supps.length;){
            if(p==supps.length-1){
                if(j==rightIndex){
                    supps[p] = sortedArray[j];
                    break;
                }
                if(i==middleIndex){
                    supps[p] = sortedArray[i];
                    break;
                }
            }
            if (j<=rightIndex && ((Comparable) sortedArray[i]).compareTo(sortedArray[j]) >=0) {
                supps[p] = sortedArray[j];
                j++;
                p++;
            }
            else {
                if(i > middleIndex && p< supps.length){
                    supps[p] = sortedArray[j];
                    j++;
                    p++;
                    continue;
                }
                if(j> rightIndex && p< supps.length){
                    supps[p] = sortedArray[i];
                    i++;
                    p++;
                    continue;
                }
                supps[p] = sortedArray[i];
                p++;
                i++;
            }
        }
        // Копирования вспомогательного массива в основной
        System.arraycopy(supps,0, sortedArray, leftIndex, length);
    }
    // SortedArray - это исходный массив, где leftIndex - 0-ой индекс исходного массива, а rightIndex - последний индекс, с - компратор.
    @SuppressWarnings("unchecked")
    private static void mergeSorted(Object[] sortedArray, int leftIndex, int rightIndex, Comparator c) {
        int length = rightIndex - leftIndex + 1;
        // Сортировка вставками для массивов длиной до 3 элементов
        if (length < MINIMUM_ELEMENTS_SORTED_ARRAY) {
            for (int i = leftIndex; i <= rightIndex; i++) {
                for (int j = i; j > leftIndex && c.compare(sortedArray[j-1], sortedArray[j]) >= 0; j--) {
                    swap(sortedArray, j, j - 1);
                }
            }
            return;
        }
        // Параметр, который указывает на середину массива
        int middleIndex = (leftIndex + rightIndex) >>> 1;
        mergeSorted(sortedArray, leftIndex, middleIndex, c);
        mergeSorted(sortedArray, middleIndex + 1, rightIndex, c);

        // Вспомогательный массив для записи в него сортировки
        Object[] supps = new Object[length];

        // Алгоритм сортировки
        for(int i = leftIndex, j=middleIndex+1, p=0; p<supps.length;){
            if(p==supps.length-1){
                if(j==rightIndex){
                    supps[p] = sortedArray[j];
                    break;
                }
                if(i==middleIndex){
                    supps[p] = sortedArray[i];
                    break;
                }
            }
            if (j<=rightIndex && c.compare(sortedArray[i], sortedArray[j]) >=0) {
                supps[p] = sortedArray[j];
                j++;
                p++;
            }
            else {
                if(i > middleIndex && p< supps.length){
                    supps[p] = sortedArray[j];
                    j++;
                    p++;
                    continue;
                }
                if(j> rightIndex && p< supps.length){
                    supps[p] = sortedArray[i];
                    i++;
                    p++;
                    continue;
                }
                supps[p] = sortedArray[i];
                p++;
                i++;
            }
        }
        // Копирования вспомогательного массива в основной
        System.arraycopy(supps,0, sortedArray, leftIndex, length);
    }
    /**
    * Вспомогательный метод перестановки элементов в массиве.
    * @param x - исходный массив в котором происходят перестановки
    * @param a - индекс элемента, который нужно поменять
    * @param b - индекс элемента, на который нужно поменять*/
    private static void swap(Object[] x, int a, int b){
        Object t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    /** Реализация быстрой сортировки.
 * @param sortedArray - массив, подлежащий сортировке
 * @param referenceIndex - опорный индекс
 * @param leftIndex - индекс первого элемента
 * @param rightIndex - индекс последнего элемента
 * @param c - компаратор для определения порядка в массиве, значение null указывает на то,
      что следует использовать естественный порядок расположение элементов*/
    public static <E> void quickSort(E[] sortedArray, int referenceIndex, int leftIndex, int rightIndex, Comparator<? super E> c){
        if (c == null){
            quickSorted(sortedArray, referenceIndex, leftIndex, rightIndex);
        }
        else {
            quickSorted(sortedArray, referenceIndex, leftIndex, rightIndex, c);
        }
    }
/**
 SortedArray - это исходный массив, где leftIndex - 0-ой индекс исходного массива, а rightIndex - последний индекс.
*/
    @SuppressWarnings("unchecked")
    private static void quickSorted(Object[] sortedArray, int referenceIndex, int leftIndex, int rightIndex){
        int length = rightIndex - leftIndex + 1;
        // Массивы состоящие из 1 элемента являются по определению отсортированными
        if(sortedArray.length<2){
            return;
        }
        // Сортировка вставками для массивов длиной до 3 элементов
        if (length < MINIMUM_ELEMENTS_SORTED_ARRAY) {
            for (int i = leftIndex; i <= rightIndex; i++) {
                for (int j = i; j > leftIndex && ((Comparable) sortedArray[j - 1]).compareTo(sortedArray[j]) >= 0; j--) {
                    swap(sortedArray, j, j - 1);
                }
            }
            return;
        }
        // Алгоритм сортировки
        for(int middle = referenceIndex, count=0; leftIndex<=middle && rightIndex>=middle;){
                if (((Comparable) sortedArray[referenceIndex]).compareTo(sortedArray[leftIndex])<0 && referenceIndex > leftIndex) {
                    swap(sortedArray, referenceIndex - 1, leftIndex);
                    swap(sortedArray, referenceIndex - 1, referenceIndex);
                    referenceIndex--;
                    count++;
                } else {
                    leftIndex++;
                }
                if(((Comparable) sortedArray[referenceIndex]).compareTo(sortedArray[rightIndex])>0 && referenceIndex<rightIndex){
                    swap(sortedArray, referenceIndex+1, rightIndex);
                    swap(sortedArray, referenceIndex+1, referenceIndex);
                    referenceIndex++;
                    count++;
                }
                else {
                    rightIndex--;
                }

        }
        // Разделение исходного массива на два дочерних не включая опорный элемент
        Object[] leftSuppArray = Arrays.copyOfRange(sortedArray, 0, referenceIndex);
        int leftReferenceIndex = leftSuppArray.length/2;
        quickSorted(leftSuppArray, leftReferenceIndex,0, leftSuppArray.length-1);

        Object[] rightSuppArray = Arrays.copyOfRange(sortedArray,referenceIndex+1, sortedArray.length);
        int rightReferenceIndex = rightSuppArray.length/2;
        quickSorted(rightSuppArray, rightReferenceIndex, 0, rightSuppArray.length-1);

        // Вспомогательный массив для копирования результатов из дочерних в исходный.
        Object[] supp = Arrays.copyOf(leftSuppArray, sortedArray.length);
        supp[referenceIndex] = sortedArray[referenceIndex];

        System.arraycopy(rightSuppArray, 0, supp, referenceIndex+1, rightSuppArray.length);
        System.arraycopy(supp, 0, sortedArray, 0, sortedArray.length);
    }

    /**
     SortedArray - это исходный массив, где leftIndex - 0-ой индекс исходного массива, а rightIndex - последний индекс, с - компратор.
     */
    @SuppressWarnings("unchecked")
    private static void quickSorted(Object[] sortedArray, int referenceIndex, int leftIndex, int rightIndex, Comparator c){
        int length = rightIndex - leftIndex + 1;
        // Массивы состоящие из 1 элемента являются по определению отсортированными
        if(sortedArray.length<=1){
            return;
        }
        // Сортировка вставками для массивов длиной до 3 элементов
        if (length < MINIMUM_ELEMENTS_SORTED_ARRAY) {
            for (int i = leftIndex; i <= rightIndex; i++) {
                for (int j = i; j > leftIndex && c.compare(sortedArray[j-1], sortedArray[j]) >= 0; j--) {
                    swap(sortedArray, j, j - 1);
                }
            }
            return;
        }
        // Алгоритм сортировки
        for(int middle = referenceIndex, count=0; leftIndex<=middle && rightIndex>=middle;){
            if (c.compare(sortedArray[referenceIndex], sortedArray[leftIndex])<0 && referenceIndex > leftIndex) {
                swap(sortedArray, referenceIndex - 1, leftIndex);
                swap(sortedArray, referenceIndex - 1, referenceIndex);
                referenceIndex--;
                count++;
            } else {
                leftIndex++;
            }
            if((c.compare(sortedArray[referenceIndex], sortedArray[rightIndex]))>0 && referenceIndex<rightIndex){
                swap(sortedArray, referenceIndex+1, rightIndex);
                swap(sortedArray, referenceIndex+1, referenceIndex);
                referenceIndex++;
                count++;
            }
            else {
                rightIndex--;
            }

        }
        // Разделение исходного массива на два дочерних не включая опорный элемент
        Object[] leftSuppArray = Arrays.copyOfRange(sortedArray, 0, referenceIndex);
        int leftReferenceIndex = leftSuppArray.length/2;
        quickSorted(leftSuppArray, leftReferenceIndex,0, leftSuppArray.length-1, c);

        Object[] rightSuppArray = Arrays.copyOfRange(sortedArray,referenceIndex+1, sortedArray.length);
        int rightReferenceIndex = rightSuppArray.length/2;
        quickSorted(rightSuppArray, rightReferenceIndex, 0, rightSuppArray.length-1, c);

        // Вспомогательный массив для копирования результатов из дочерних в исходный.
        Object[] supp = Arrays.copyOf(leftSuppArray, sortedArray.length);
        supp[referenceIndex] = sortedArray[referenceIndex];

        System.arraycopy(rightSuppArray, 0, supp, referenceIndex+1, rightSuppArray.length);
        System.arraycopy(supp, 0, sortedArray, 0, sortedArray.length);
    }
}
