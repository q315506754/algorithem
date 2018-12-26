package com.jiangli.springMVCflow.flowhandler;

import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("hotels/booking")
public class BookingFlowHandler extends AbstractFlowHandler {
	public String handleExecutionOutcome(FlowExecutionOutcome outcome,
                                         HttpServletRequest request, HttpServletResponse response) {

		if (outcome.getId().equals("bookingConfirmed")) {
			return "/booking/show?bookingId=" + outcome.getOutput().get("bookingId");
		} else {
			return "/hotels/index";
		}
	}
}
