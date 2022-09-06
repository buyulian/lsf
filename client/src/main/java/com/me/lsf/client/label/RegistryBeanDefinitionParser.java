package com.me.lsf.client.label;

import com.me.lsf.client.registry.RegistryBean;
import com.me.lsf.common.model.LsfConnection;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author buyulian
 * @date 2020/4/28
 */
public class RegistryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return RegistryBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String host = element.getAttribute("host");
        String port = element.getAttribute("port");

        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LsfConnection.class);
        definitionBuilder.addPropertyValue("host", host);
        definitionBuilder.addPropertyValue("port", port);
        AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();

        builder.addPropertyValue("lsfConnection", beanDefinition);

    }

}