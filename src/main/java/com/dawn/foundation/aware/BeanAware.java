package com.dawn.foundation.aware;

import org.springframework.beans.factory.Aware;

public interface BeanAware<T> extends Aware {

    boolean isMatched(T t);
}
