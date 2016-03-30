package it.anggen.utils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.codahale.metrics.servlets.MetricsServlet;

import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.repository.log.LogEntryRepository;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;

public class AngenMetricServlet extends MetricsServlet {

	
	protected AutowireCapableBeanFactory ctx;
	
	@Autowired
	LogEntryService logEntryService;
	
	@Autowired
	SecurityService securityService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
			      config.getServletContext());

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logEntryService.addLogEntry("", LogType.INFO, OperationType.VIEW_METRICS, -1L, securityService.getLoggedUser(), null);
		super.doGet(req, resp);
	}

}
