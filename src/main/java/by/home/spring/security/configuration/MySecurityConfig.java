package by.home.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;


/**
 * @EnableWebSecurity Аннотацией EnableWebSecurity помечается класс ответственный за Security Configuration.
 * Эта аннотация является конфигурацией.
 */
@EnableWebSecurity


/**
 * В этом классе, ответственным за Security Configuration прописываются usernames,
 * passwords и roles для работников которые пользуются приложением, для этого класс
 * должен extends WebSecurityConfigurerAdapted
 */
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;


    /**
     * что бы прописать юзернеймы, пароли и роли, нужно переопределить один метод:
     * protected void configure(AuthenticationManagerBuilder auth) throws Exception.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        /**
         *указываем что информация о пользователях находится в базе данных,
         * а информация о подключении к базе данных, содержит dataSource
         */
        auth.jdbcAuthentication().dataSource(dataSource);


        /** если информация о польователях нет в базе данных, то:
         * создается UserBuilder userBuilder = User.withDefaultPasswordEncoder();
         * данное решение годится для хранения паролей inMemory.
         */
        //User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        /**
         * с помощью userBuilder прописываем пользователей
         */

        //auth.inMemoryAuthentication()
        // .withUser(userBuilder.username("ilya").password("ilya").roles("EMPLOYEE"))
        // .withUser(userBuilder.username("nikita").password("nikita").roles("HR", "MANAGER"))
        // .withUser(userBuilder.username("darya").password("darya").roles("HR"));
    }


    /**
     * Что бы на кнопки могли нажимать и получать доступ только опредленные роли,
     * требуется переопределить метод:
     * protected void configure(HttpSecurity http) throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        /**
         * используя параметр метода http задаем авторизацию для определенных url.
         * для множества адресов, например, /hr_info/id/1, /hr_info/id/2, используется запись :
         * /hr_info/**, это означает что у сотрудника с ролью HR будет доступ на любую страничку
         * начинающуюся с /hr_info
         * методы formLogin() и permitAll() говорит о том что форма логина и пароля будет
         * запрашиваться у всех
         */
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
                .antMatchers("/hr_info").hasRole("HR")
                .antMatchers("/manager_info/**").hasRole("MANAGER")
                .and().formLogin().permitAll();
    }
}