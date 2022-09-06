package com.me.lsf.client.provider;

public class ProviderBeanFactory {

    public static Object createBean(ProviderBean providerBean) {
        ProviderBeanCenter.registerProvider(providerBean);
        return null;
    }

}
