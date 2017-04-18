package com.wb.auth;

import com.wb.auth.service.security.MysqlUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by wangbo on 2017/4/5.
 */
@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
	
	@Configuration
	@EnableWebSecurity
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private MysqlUserDetailService userDetailsService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
					.authorizeRequests().anyRequest().authenticated()
					.and()
					.csrf().disable();
			// @formatter:on
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService)
					.passwordEncoder(new BCryptPasswordEncoder());
		}
		
		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
		
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private MysqlUserDetailService userDetailsService;
		
		@Autowired
		private Environment env;
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			
			// TODO persist clients details
			
			// @formatter:off
			clients.inMemory()
					.withClient("browser")
					.authorizedGrantTypes("refresh_token", "password")
					.scopes("ui")
					.and()
					.withClient("user")
					.secret("user_secret")
					.authorizedGrantTypes("client_credentials", "refresh_token")
					.scopes("server");
			
			
			// @formatter:on
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
					.tokenStore(tokenStore)
					.authenticationManager(authenticationManager)
					.userDetailsService(userDetailsService);
		}
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer
					.tokenKeyAccess("permitAll()")
					.checkTokenAccess("isAuthenticated()");
		}
	}
	
}
