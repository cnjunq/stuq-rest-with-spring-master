package io.junq.examples.common.metric.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.junq.examples.common.metric.service.IActuatorMetricService;

@Controller
public class MetricController {

    @Autowired
    private IActuatorMetricService actuatorMetricService;
	
    @RequestMapping(value = "/metric-graph-data", method = RequestMethod.GET)
    @ResponseBody
	public Object[][] getActuatorMetricData() {
        return actuatorMetricService.getGraphData();	
	}
	
}
