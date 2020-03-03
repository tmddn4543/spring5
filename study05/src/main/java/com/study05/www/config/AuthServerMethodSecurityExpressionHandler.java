package com.study05.www.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

public class AuthServerMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler{

	@Override
	public StandardEvaluationContext createEvaluationContextInternal(Authentication auth, MethodInvocation mi) {
		AuthServerSecurityExpressionRoot root = new AuthServerSecurityExpressionRoot(auth);
		root.setTrustResolver(getTrustResolver());
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setRoleHierarchy(getRoleHierarchy());
		StandardEvaluationContext sec = super.createEvaluationContextInternal(auth, mi);
		sec.setRootObject(root);
		sec.setVariable("auth", new AuthServerMethodSecurityExpression(auth));
		return sec;
	}
}
