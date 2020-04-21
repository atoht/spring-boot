package com.example.demo.Authentication;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("demoAuthenticationSuccessHandler")
public class DemoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String header = request.getHeader(AUTHORIZATION);
		if (header == null) {
			throw new UnapprovedClientAuthenticationException("AUTHORIZATION不存在");
		}

		header = header.trim();
		if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
			throw new UnapprovedClientAuthenticationException("AUTHORIZATION不是basic");
		}

		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, StandardCharsets.UTF_8);

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		String[] tokens = {token.substring(0, delim), token.substring(delim + 1)};
		String clientId = tokens[0];
		String clientSecret = tokens[1];
		
		ClientDetails loadClientByClientId = clientDetailsService.loadClientByClientId(clientId);
		if(null == loadClientByClientId) {
			throw new UnapprovedClientAuthenticationException("client不存在");
		}else if(!passwordEncoder.matches(clientSecret, loadClientByClientId.getClientSecret())) {
			throw new UnapprovedClientAuthenticationException("clientSecret不一致");
		}
		
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_SORTED_MAP,clientId,loadClientByClientId.getScope(),"custom");
		
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(loadClientByClientId);
		
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		
		OAuth2AccessToken createAccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(createAccessToken));
	}

}
