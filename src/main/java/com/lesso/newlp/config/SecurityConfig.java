package com.lesso.newlp.config;

import com.lesso.newlp.config.ext.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Sean on 1/4/14.
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;


//    @Autowired
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser("user").password("password").roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(delegatingAuthenticationEntryPoint())
                .and()
                .authorizeRequests()

                /*dev*/
                .antMatchers("/app/**").permitAll()
                .antMatchers("/dist/**").permitAll()

                /*production*/

                .antMatchers("/").permitAll()
                .antMatchers("/api/v1/auth/authenticate").permitAll()
                .antMatchers("/api/v1/auth/logout").permitAll()
                .antMatchers("/**").authenticated()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/db/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_DBA')")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout").logoutSuccessUrl("/")
                .and().sessionManagement()
                .sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy())
        ;

//        http.addFilter(concurrentSessionFilter())
//                .addFilter(usernamePasswordAuthenticationFilter());
    }

    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter() {
        return new ConcurrentSessionFilter(sessionRegistry());
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
        authenticationFilter.setSessionAuthenticationStrategy(compositeSessionAuthenticationStrategy());
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy(){
        SessionRegistry sessionRegistry = sessionRegistry();

        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        SessionFixationProtectionStrategy fixationProtectionStrategy = new SessionFixationProtectionStrategy();
        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry);

        List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<SessionAuthenticationStrategy>();
        delegateStrategies.add(concurrentSessionControlAuthenticationStrategy);
        delegateStrategies.add(fixationProtectionStrategy);
        delegateStrategies.add(registerSessionAuthenticationStrategy);

        return new CompositeSessionAuthenticationStrategy(delegateStrategies);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
        DaoAuthenticationProvider daoAP = new DaoAuthenticationProvider();
        daoAP.setUserDetailsService(jdbcUserDetailsManager());
        providers.add(daoAP);
        return new ProviderManager(providers);
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        jdbcUserDetailsManager.setEnableGroups(true);
        jdbcUserDetailsManager.setEnableAuthorities(false);


        jdbcUserDetailsManager.setRolePrefix("ROLE_");
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT M.MEMBERID,M.PASSWORD,M.ENABLED FROM PM_MEMBER M WHERE M.MEMBERID = ?");
        jdbcUserDetailsManager.setGroupAuthoritiesByUsernameQuery("SELECT G.GROUPID ,G.NAME,G.GROUPID FROM PM_GROUP_MEMBER_REL GM LEFT JOIN PM_MEMBER M ON GM.member_memberId = M.MEMBERID LEFT JOIN PM_GROUP G ON G.GROUPID = GM.group_groupId WHERE M.MEMBERID = ?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public DelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint() {
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<RequestMatcher, AuthenticationEntryPoint>();
        ELRequestMatcher requestMatcher = new ELRequestMatcher("hasHeader('Accept','application/json')");
        entryPoints.put(requestMatcher, new RestAuthenticationEntryPoint());
        DelegatingAuthenticationEntryPoint authenticationEntryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
        authenticationEntryPoint.setDefaultEntryPoint(new Http403ForbiddenEntryPoint());
        return authenticationEntryPoint;
    }
}