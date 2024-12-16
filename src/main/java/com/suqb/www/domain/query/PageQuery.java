package com.suqb.www.domain.query;

import java.io.Serializable;

public class PageQuery implements Serializable
{
    private static final long serialVersionUID = 1L;
    private long current = 1L;
    private long size = 10L;

    public PageQuery()
    {
    }

    public long getCurrent()
    {
        return this.current;
    }

    public long getSize()
    {
        return this.size;
    }

    public void setCurrent(long current)
    {
        this.current = current;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }
        else if (!(o instanceof PageQuery))
        {
            return false;
        }
        else
        {
            PageQuery other = (PageQuery) o;
            if (!other.canEqual(this))
            {
                return false;
            }
            else if (this.getCurrent() != other.getCurrent())
            {
                return false;
            }
            else
            {
                return this.getSize() == other.getSize();
            }
        }
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof PageQuery;
    }

}
