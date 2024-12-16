package com.suqb.www.domain.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class PageDTO<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final long total;
    private final List<T> records;

    public static <R> PageDTO<R> empty()
    {
        return new PageDTO(0L, Collections.emptyList());
    }

    public <R> PageDTO<R> clone(BiFunction<List<T>, Class<R>, List<R>> action, Class<R> clazz)
    {
        return new PageDTO(this.total, (List) action.apply(this.records, clazz));
    }

    public <R> PageDTO<R> clone(Function<Collection<T>, List<R>> mapper)
    {
        return new PageDTO(this.total, (List) mapper.apply(this.records));
    }

    public void forEach(Consumer<? super T> action)
    {
        ((List) Optional.ofNullable(this.records).orElse(Collections.emptyList())).forEach(action);
    }

    public long getTotal()
    {
        return this.total;
    }

    public List<T> getRecords()
    {
        return this.records;
    }

    public PageDTO(long total, List<T> records)
    {
        this.total = total;
        this.records = records;
    }
}
