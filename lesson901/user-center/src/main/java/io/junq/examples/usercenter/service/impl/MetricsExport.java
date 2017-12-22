package io.junq.examples.usercenter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class MetricsExport {
	
	private Logger LOGGER = LoggerFactory.getLogger(MetricsExport.class);
	
	@Autowired
	private MetricRepository metricRepo;
	
	@Scheduled(fixedDelay = 1000 * 30) // 30s
	public void exportData() {
		metricRepo.findAll().forEach(this::log);
	}
	
	private final void log(final Metric<?> m) {
		LOGGER.info("Reporting metric {} = {}", m.getName(), m.getValue());
		metricRepo.reset(m.getName());
	}
	
}
