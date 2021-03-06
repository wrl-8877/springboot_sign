package com.example.demo.open.config;


import com.example.demo.open.ApiProperties;
import com.example.demo.open.ProductProvider;
import com.example.demo.open.filter.ShaFilter;
import com.example.demo.open.hmac.SHASign;
import com.example.demo.open.hmac.SignBuilder;
import com.example.demo.open.util.SpringActiveUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;



import java.util.List;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
@ConditionalOnProperty(prefix = "api.hmac", value = "enabled", havingValue = "true")
@Slf4j
@Import(SpringActiveUtils.class)
public class ShaAutoConfiguration {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private SignBuilder signBuilder;

    @Autowired
    private SpringActiveUtils springActiveUtils;

    @Autowired
    private ProductProvider productProvider;

    @Bean
    public FilterRegistrationBean<ShaFilter> hmacFilter() {
        log.info("init hmac filter");
        ShaFilter shaFilter = new ShaFilter(signBuilder, apiProperties, springActiveUtils, productProvider);
        FilterRegistrationBean<ShaFilter> registration = new FilterRegistrationBean<>();
        List<String> urlPatterns = apiProperties.getHmac().getUrlPatterns();
        registration.addUrlPatterns(urlPatterns.toArray(new String[0]));
        registration.setFilter(shaFilter);
        registration.setName("shaFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(SignBuilder.class)
    public SignBuilder hmacSha256sign() {
        log.info("init System shaSign sign");
        return new SHASign();
    }
}

