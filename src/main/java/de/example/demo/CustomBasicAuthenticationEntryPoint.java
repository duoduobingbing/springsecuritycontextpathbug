
package de.example.demo;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {



    public CustomBasicAuthenticationEntryPoint() {
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        

        if (Objects.equals(request.getParameter("basic"), "1")) { //only use when basic = 1
            super.commence(request, response, authException); 
            return;
        }
        
        commenceDefault(request, response, authException);

    }
    
    public void commenceDefault(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized. " + authException.getMessage());
    }


}
