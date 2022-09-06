package com.me.lsf.client.registry;

import com.me.lsf.common.model.LsfConnection;

/**
 * @author buyulian
 * @date 2020/4/29
 */
public class RegistryBean {
    private LsfConnection lsfConnection;

    public LsfConnection getLsfConnection() {
        return lsfConnection;
    }

    public void setLsfConnection(LsfConnection lsfConnection) {
        this.lsfConnection = lsfConnection;
    }
}
