package com.ashu.blogapp.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //Authorization-------------------------------------------------------------------------------------------------

        //getting header 1st out of request token (Authorization = Bearer "fggfuyfkjkhff")
        // trying to fetch "fggfuyfkjkhff"
        String requestHeader = request.getHeader("Authorization");

        //Bearer 2352345235sdfrsfgsdfsdf
        logger.info(" Header :  {}", requestHeader);        // !**


        String token = null;  // token will be fetched from "requestHeader"
        String username = null; // and userName will be fetched out of 'token' variable


        // started checking token Nd username valid or not ? -----------------------------------------------------------

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            //looking good
            token = requestHeader.substring(7);
            // using substring "7" as we want this "fggfuyfkjkhff" out of <Bearer "fggfuyfkjkhff">, So we are exculding 'Bearer_'

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            logger.info("Invalid Header Value !! ");
            System.out.println("JWT token doesn't starts with Bearer");
        }


        // Now trying to compare userName found in above method with existing User Data present in DB
        // if both data matches then we are good to give User green-Signal to access Data
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);


            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // setting "authentication" as everything is good to go
                // so using "setAuthentication()" this from "SecurityContextHolder" in-built method to set authentication details
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            else {
                logger.info("Validation fails !!");
                System.out.println("Invalid JWT token !!");
            }

        }
        else {
            System.out.println("UserName is NULL or context is not Null !!");
        }


        // forwarding request to next step as everything good to go
        filterChain.doFilter(request, response);

    }

}
