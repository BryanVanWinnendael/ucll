package ucll.be.ip.minor.groep1210;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthenticationBasicConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        /* Aanmaken users en rols:
        1. InMemoryAuthentication() - Dit is de methode die wordt gebruikt om een nieuwe gebruiker te maken.
        2. withUser("user") - Dit is de naam van de gebruiker.
        3. password(encoder.encode("t")) - Dit is het wachtwoord van de gebruiker.
        4. roles("USER") - Dit is de rol van de gebruiker.
        */
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("t"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("t"))
                .roles("ADMIN","USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    
                /* 
                    authorizeRequests() - de nieuwe instance van een authorizeRequests() wordt gebruikt om aanvragen te autoriseren
                    antMatchers("/...") - het path die je wilt instellen voor authorization
                    permitAll() - iedereen heeft toegang tot deze path
                    hasAnyRole("...") - iedereen met een van deze rollen heeft toegang tot deze path
                */
                .authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/overOns").permitAll()
                .antMatchers("/fotos").permitAll()
                .antMatchers("/muntcollectie-overview").hasAnyRole("USER","ADMIN")
                .antMatchers("/club-overview").hasAnyRole("USER","ADMIN")
                .antMatchers("/muntcollectie-search").hasAnyRole("USER","ADMIN")
                .antMatchers("/club-search").hasAnyRole("USER","ADMIN")
                .antMatchers("/muntcollectie-add").hasAnyRole("ADMIN")
                .antMatchers("/muntcollectie/update/*").hasAnyRole("ADMIN")
                .antMatchers("/muntcollectie-delete-confirm/*").hasAnyRole("ADMIN")
                .antMatchers("/club-add").hasAnyRole("ADMIN")
                .antMatchers("/club/update/*").hasAnyRole("ADMIN")
                .antMatchers("/club-delete-confirm/*").hasAnyRole("ADMIN")




                // ADDED FOR H2 ACCESS VIA BROWSER
                .antMatchers("/console/**").permitAll()
                .and().headers().frameOptions().disable()
                .and()
                // ADDED FOR CSFR (post add not working problem)
                // disable csrf (needed for rest api)
                // use th:action in add-patient.html to use Thymeleaf CSRF processor
                .csrf().disable()
                .httpBasic()
                .and()

                /* Login functie:
                1. We gebruiken formLogin() om op formulieren gebaseerde verificatie in te schakelen.
                2. We gebruiken loginPage("/login") om de inlogpagina in te stellen. (url)
                3. We gebruiken usernameParameter("u") en passwordParameter("p") om de gebruikersnaam en wachtwoordparameter in te stellen.
                    in de form van login:
                    <input name="u"/>
                    <input name="p"/> 
                4. We gebruiken permitAll() om alle iedereen toegang te geven. */
                .formLogin()
                    .loginPage("/login") 
                    .usernameParameter("u").passwordParameter("p")
                    .permitAll()
                    .and()
                
                /* Logout functie:
                1. .logout() is een methode die wordt gebruikt om de gebruiker uit te loggen.
                2. We gebruiken permitAll() om alle iedereen toegang te geven.
                */
                .logout()
                    .permitAll()
                    .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                /* Logout functie:
                1. We maken een nieuwe matcher voor afmeldingsverzoeken.
                2. We stellen de afmeldingsverzoekmatcher in op de afmeldingsverzoekovereenkomst. (url)
                3. We stellen de url voor uitloggensucces in. (urk) */
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");

    }
}