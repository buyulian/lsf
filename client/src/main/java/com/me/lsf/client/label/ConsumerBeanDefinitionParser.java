package com.me.lsf.client.label;

import com.me.lsf.client.consumer.ConsumerBeanFactory;
import com.me.lsf.client.consumer.Consumerbean;
import com.me.lsf.client.provider.ProviderBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author buyulian
 * @date 2020/4/28
 */
public class ConsumerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return ConsumerBeanFactory.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String aClass = element.getAttribute("interface");
        String alias = element.getAttribute("alias");
        String registry = element.getAttribute("registry");

        builder.setFactoryMethod("create");

        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Consumerbean.class);
        definitionBuilder.addPropertyValue("alias", alias);
        definitionBuilder.addPropertyValue("interfaceName", aClass);
        definitionBuilder.addPropertyReference("registryBean", registry);
        AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();

        builder.addConstructorArgValue(beanDefinition);

    }

}