package edu.mit.cci.roma.web;

import edu.mit.cci.roma.server.CompositeScenario;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "compositescenarios", formBackingObject = CompositeScenario.class)
@RequestMapping("/compositescenarios")
@Controller
public class CompositeScenarioController {
}