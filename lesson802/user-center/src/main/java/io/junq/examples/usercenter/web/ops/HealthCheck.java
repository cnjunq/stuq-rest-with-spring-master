package io.junq.examples.usercenter.web.ops;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

	@Override
	public Health health() {
		if (check()) {
			return Health.up().build();
		}
		return Health.outOfService().build();
	}

	private boolean check() {
		return false;
	}
	
}
