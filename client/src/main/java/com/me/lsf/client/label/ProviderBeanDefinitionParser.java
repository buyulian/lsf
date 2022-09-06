package com.me.lsf.client.label;

import com.me.lsf.client.provider.ProviderBean;
import com.me.lsf.client.provider.ProviderBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author buyulian
 * @date 2020/4/28
 */
public class ProviderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return ProviderBeanFactory.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String aClass = element.getAttribute("interface");
        String alias = element.getAttribute("alias");
        builder.setFactoryMethod("createBean");
        String ref = element.getAttribute("ref");
        String registry = element.getAttribute("registry");

        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ProviderBean.class);
        definitionBuilder.addPropertyValue("alias", alias);
        definitionBuilder.addPropertyValue("interfaceName", aClass);
        definitionBuilder.addPropertyReference("implObject", ref);
        definitionBuilder.addPropertyReference("registryBean", registry);
        AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();

        builder.addConstructorArgValue(beanDefinition);

    }

}