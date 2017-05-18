package com.renxl.realmall.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by renxl
 * On 2017/3/31 10:19.
 */

abstract class BaseCallback<T> {

    public Type mType;

    BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    // 默认的权限是包可以访问，子类跟非同一个包的不能访问，以此达到 http 包中的，HTTPCallback 可以访问，而实际的子类并不能直接使用 mMoke，只能通过 moke 方法来访问
    T mMoke;
    String cache;

    public abstract void ok(T response);

    public abstract void fail(String errorMessage);

    public abstract void tokenError();

    public abstract void mock(T mock);

    public abstract void setMock(T mock);
}
