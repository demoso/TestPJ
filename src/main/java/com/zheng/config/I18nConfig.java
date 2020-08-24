package com.zheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
//	/**
//	 * 默认拦截器 其中lang表示切换语言的参数名
//	 *
//	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
//		localeInterceptor.setParamName("lang");
//		registry.addInterceptor(localeInterceptor).addPathPatterns("/**");
//	}
	/**
	 * 默认解析器 其中locale表示默认语言
	 */
	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return localeResolver;
	}
}