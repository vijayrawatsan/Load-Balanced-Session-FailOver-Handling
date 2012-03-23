package in.xebia.vijay.controllers;

import in.xebia.vijay.api.SessionService;
import in.xebia.vijay.domain.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final String SEPARATOR = "|";
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Resource
	private SessionService sessionService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String home(Model model, User user,HttpSession session, @CookieValue("JSESSIONID") String jsessionId) {
		logger.info("In home!");
		sessionService.set(session.getId()+SEPARATOR+"user", user, session);
		User userFromSession = (User) sessionService.get(jsessionId+SEPARATOR+"user", session);
		model.addAttribute("userName", userFromSession.getUserName());
		return "home";
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(Model model, HttpSession session, @CookieValue("JSESSIONID") String jsessionId) {
		logger.info("In Check!");
		User userFromSession = (User) sessionService.get(jsessionId+SEPARATOR+"user", session);
		model.addAttribute("userName", userFromSession.getUserName());
		return "check";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		logger.info("In login!");
		return "login";
	}
	
}
