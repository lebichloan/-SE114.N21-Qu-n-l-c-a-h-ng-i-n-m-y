package com.example.se114n21.Model.Data;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private String prefix;
    private String suffix;
    private AtomicInteger lastId;
    private String StringFormat;

    public IdGenerator()
    {
        this.lastId = new AtomicInteger();
        this.prefix = "";
        this.suffix = "";
    }
    public void init(String prefix, String suffix, int lastId, String stringFormat)
    {
        this.prefix = prefix;
        this.suffix = suffix;
        this.lastId.set(lastId);
        this.StringFormat = stringFormat;
    }
    public String generate()
    {
        return this.prefix + String.format(this.StringFormat, this.lastId.incrementAndGet()) + this.suffix;
    }
}
