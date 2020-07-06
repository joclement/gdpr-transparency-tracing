package berlin.tu.peng.tracingproject.savescoreservice11.api;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/game/savescore").hasAuthority("SCOPE_resource.read")
                .pathMatchers(HttpMethod.POST, "/game/savescore").hasAnyAuthority("SCOPE_resource.read")
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.httpBasic().and().build(); //todo httBasic sinnvoll?
    }
}
