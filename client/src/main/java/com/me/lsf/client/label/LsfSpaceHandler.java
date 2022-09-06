package com.me.lsf.client.label;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author buyulian
 * @date 2020/4/28
 */
public class LsfSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParser());
        registerBeanDefinitionParser("provider", new ProviderBeanDefinitionParser());
        registerBeanDefinitionParser("consumer", new ConsumerBeanDefinitionParser());
    }
}
