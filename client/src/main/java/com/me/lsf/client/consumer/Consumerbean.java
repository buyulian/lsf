package com.me.lsf.client.consumer;

import com.alibaba.fastjson.JSON;
import com.me.lsf.client.registry.RegistryBean;
import com.me.lsf.common.http.serialize.SerializeTypeEnum;
import com.me.lsf.client.common.CenterUtil;
import com.me.lsf.common.model.LsfConnection;
import com.me.lsf.common.model.RegisterInfoBean;
import com.me.lsf.common.model.RegisterTypeEnum;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

public class Consumerbean {

    private Class interfaceClass;

    private String interfaceName;

    private String alias;

    private Boolean register;

    private List<LsfConnection> aliveConnectionList;

    private List<LsfConnection> fixedConnectionList;

    private ParentObject parentObject;

    private String serializeType = SerializeTypeEnum.JSON_AUTO_TYPE.getCode();

    private RegistryBean registryBean;

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        try {
            interfaceClass = Class.forName(interfaceName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(interfaceName + "接口类定义未发现", e);
        }
        parentObject = new ParentObject();
        parentObject.setInterfaceClass(interfaceClass);
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

    public List<LsfConnection> getAliveConnectionList() {
        return aliveConnectionList;
    }

    public void setAliveConnectionList(List<LsfConnection> aliveConnectionList) {
        this.aliveConnectionList = aliveConnectionList;
    }

    public ParentObject getParentObject() {
        return parentObject;
    }

    public void setParentObject(ParentObject parentObject) {
        this.parentObject = parentObject;
    }

    public List<LsfConnection> getFixedConnectionList() {
        return fixedConnectionList;
    }

    public void setFixedConnectionList(List<LsfConnection> fixedConnectionList) {
        this.fixedConnectionList = fixedConnectionList;
    }

    public LsfConnection getConnection() {
        if (CollectionUtils.isEmpty(fixedConnectionList)) {

            if (!CollectionUtils.isEmpty(aliveConnectionList)) {
                return aliveConnectionList.get(0);
            }

            RegisterInfoBean registerInfoBean = new RegisterInfoBean();
            registerInfoBean.setNeedResult(true);
            String registerKey = getRegisterKey();
            registerInfoBean.setKey(registerKey);
            registerInfoBean.setType(RegisterTypeEnum.GET_PROVIDER.getCode());
            String content = CenterUtil.centerRegister(registryBean, registerInfoBean);
            LsfConnection lsfConnection = JSON.parseObject(content, LsfConnection.class);

            if (lsfConnection == null) {
                throw new RuntimeException("没有可用的 provider key " + registerKey);
            }

            aliveConnectionList = new LinkedList<>();
            aliveConnectionList.add(lsfConnection);
            return lsfConnection;
        } else {
            return fixedConnectionList.get(0);
        }
    }

    public String getRegisterKey() {
        return interfaceName + "#" + alias;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(String serializeType) {
        this.serializeType = serializeType;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public RegistryBean getRegistryBean() {
        return registryBean;
    }

    public void setRegistryBean(RegistryBean registryBean) {
        this.registryBean = registryBean;
    }
}
