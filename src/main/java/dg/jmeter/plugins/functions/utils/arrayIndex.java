package dg.jmeter.plugins.functions.utils;

public class arrayIndex {
    String[] arr;
    int index = -1;
    public arrayIndex(String[] arr) {
        this.arr = arr;
    }

    public String getNext() {
        try {
            this.index++;
            return this.arr[this.index];
        } catch (IndexOutOfBoundsException ignore) {
            this.index = -1;
            return null;
        }
    }

    public synchronized String getNextSync() {
        return this.getNext();
    }
}
