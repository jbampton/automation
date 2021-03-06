package org.riskfirst.automation;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class GithubAPI {

	
	@Value("${github.token}")
	String token;
	
	public Map<String, Object> invite(String username) {
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target("https://api.github.com/orgs/risk-first/memberships/"+username);
		Builder b = wt.request();
		b.header("authorization", "token "+token);
		Entity<String> in = Entity.text("");
		Response r = b.put(in);
		
		if (r.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			return r.readEntity(Map.class);
		} else {
			throw new RuntimeException("Problem calling api:"+ r.getStatusInfo());
		}	
	}
	
	/**
	 * This specifically invites to the risk-first team
	 */
	public Map<String, Object> teamInvite(String username) {
		Client c = ClientBuilder.newClient();
		WebTarget wt = c.target("https://api.github.com/teams/2784576/memberships/"+username);
		Builder b = wt.request();
		b.header("authorization", "token "+token);
		Entity<String> in = Entity.text("");
		Response r = b.put(in);
		
		if (r.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			return r.readEntity(Map.class);
		} else {
			throw new RuntimeException("Problem calling api:"+ r.getStatusInfo());
		}	
	}
}
