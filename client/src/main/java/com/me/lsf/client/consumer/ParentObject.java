package com.me.lsf.client.consumer;

public class ParentObject {

    private Class rpcClass;

    @Override
    public int hashCode() {
        return rpcClass.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return rpcClass.hashCode() == obj.hashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return rpcClass.getCanonicalName()+"$LsfProxy";
    }

    @Override
    protected void finalize() throws Throwable {

    }

    public void setInterfaceClass(Class rpcClass) {
        this.rpcClass = rpcClass;
    }
}
