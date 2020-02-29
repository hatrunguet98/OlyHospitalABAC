package UET.security.abac.spring;

import UET.model.User;
import UET.repository.UserRepository;
import UET.security.abac.policy.PolicyEnforcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Use this class in any of the Spring Beans to evaluate security policy.
 * @author <a href="mailto:mostafa.mahmoud.eltaher@gmail.com">Mostafa Eltaher</a>
 *
 */
@Component
public class ContextAwarePolicyEnforcement {
	@Autowired
	protected PolicyEnforcement policy;
	@Autowired
	private UserRepository userRepository;

	public void checkPermission(Object resource, String permission) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());

		Map<String, Object> environment = new HashMap<>();

		/*
		Object authDetails = auth.getDetails();
		if(authDetails != null) {
			if(authDetails instanceof WebAuthenticationDetails) {
				environment.put("remoteAddress", ((WebAuthenticationDetails) authDetails).getRemoteAddress());
			}
		}
		*/
		environment.put("time", new Date());

		if(!policy.check(user, resource, permission, environment))
			throw new AccessDeniedException("Access is denied");
	}
}
