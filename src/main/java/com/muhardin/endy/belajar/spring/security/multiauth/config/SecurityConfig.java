package com.muhardin.endy.belajar.spring.security.multiauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity @Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SQL_LOGIN
            = "select u.username, up.password, u.active " +
            "from s_users_passwords up " +
            "inner join s_users u on u.id = up.id_user " +
            "where u.username = ?";

    private static final String SQL_PERMISSION =
            "select u.username, p.value as authority " +
                    "from s_users u " +
                    "inner join s_roles r on r.id = u.id_role " +
                    "inner join s_roles_permissions rp on rp.id_role = r.id " +
                    "inner join s_permissions p on rp.id_permission = p.id " +
                    "where u.username = ?";

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(this.dataSource);
        manager.setUsersByUsernameQuery(SQL_LOGIN);
        manager.setAuthoritiesByUsernameQuery(SQL_PERMISSION);
        return manager;
    }
}
