package tn.abouzidi.voters;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EvenNumberedVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public int vote(Authentication arg0, Object arg1, Collection<ConfigAttribute> arg2) {
		boolean evenMinute = LocalDateTime.now().getMinute() % 2 == 0;
		log.info(" RESULT = "+( evenMinute ? "ACCESS_DENIED" : "ACCESS_ABSTAIN"));
		return evenMinute ? ACCESS_DENIED : ACCESS_ABSTAIN;
	}

}
