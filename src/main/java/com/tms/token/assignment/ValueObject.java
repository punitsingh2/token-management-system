package com.tms.token.assignment;

public interface ValueObject<T> {

    boolean sameValueAs(T other);

}
