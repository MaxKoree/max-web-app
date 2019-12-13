package com.abnamro.trd.maxwebapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * This class configures where the JSP files can be found.
 */
// @EnableWebMvc
@Configuration
public class JspConfiguration extends WebMvcConfigurerAdapter {

  @Override
  public void configureViewResolvers(final ViewResolverRegistry registry) {
    final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/view/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    registry.viewResolver(resolver);
  }
}
