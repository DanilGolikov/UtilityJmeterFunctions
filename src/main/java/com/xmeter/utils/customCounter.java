package com.xmeter.utils;

public class customCounter {
    private Long counter;
    private Long start = null;
    private Long end = null;

    public customCounter(Long start, Long end) {
        this.start = start;
        this.end = end;
        this.counter = start;
    }

    public synchronized long get() {
        return this.counter;
    }

    public synchronized long setAndGet(Long newStart, Long newEnd) {
        if (newStart != null && !newStart.equals(this.start))
            counter = newStart;
        if (newEnd != null && !newEnd.equals(this.end))
            this.end = newEnd;
        return this.counter;
    }

    public synchronized long getAndAdd(Long implement) {
        long oldValue = this.counter;
        this.counter += implement;
        if (this.end != null && this.counter > this.end)
            this.counter= this.start;
        return oldValue;
    }

    public synchronized long addAndGet(Long implement) {
        this.counter += implement;
        if (this.end != null && this.counter > this.end)
            this.counter = this.start;
        return this.counter;
    }
}
