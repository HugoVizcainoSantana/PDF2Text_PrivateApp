package ocrGUI.util;

public class IntRange {

    public IntRange setBegin(int begin) {
        this.begin = begin;
        return this;
    }

    public IntRange setEnd(int end) {
        this.end = end;
        return this;
    }

    private int begin,end;

    private IntRange(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int[] toPrimitiveArray(){
        int[] array = new int[end - begin];
        for (int i = 0; i < array.length; i++) {
            array[i]=i+begin;
        }
        return array;
    }

    public Integer[] toIntegerArray(){
        Integer[] array = new Integer[end - begin];
        for (int i = 0; i < array.length; i++) {
            array[i]=i+begin;
        }
        return array;
    }

    public static IntRange from(int begin){
        return new IntRange(begin,0);
    }

    public IntRange to(int end){
        setEnd(end);
        return this;
    }

}
