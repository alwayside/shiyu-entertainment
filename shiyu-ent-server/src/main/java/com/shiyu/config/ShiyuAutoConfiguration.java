package com.shiyu.config;

import com.shiyu.authority.AuthorityCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shiyu
 */
@Configuration
public class ShiyuAutoConfiguration {

    @Bean
    public AuthorityCenter AuthorityCenter() {
        return new AuthorityCenter();
    }
}
