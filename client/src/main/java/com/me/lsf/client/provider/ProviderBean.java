package com.me.lsf.client.provider;

import com.me.lsf.client.registry.RegistryBean;

public class ProviderBean {

    private String interfaceName;

    private Object implObject;

    private String alias;

    private Boolean register = true;

    private RegistryBean registryBean;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Object getImplObject() {
        return implObject;
    }

    public void setImplObject(Object implObject) {
        this.implObject = implObject;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public String getRegisterKey() {
        return interfaceName + "#" + alias;
    }

    public RegistryBean getRegistryBean() {
        return registryBean;
    }

    public void setRegistryBean(RegistryBean registryBean) {
        this.registryBean = registryBean;
    }
}
