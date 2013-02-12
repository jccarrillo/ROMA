package edu.mit.cci.roma.web;

import edu.mit.cci.roma.server.ScenarioList;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "scenariolists", formBackingObject = ScenarioList.class)
@RequestMapping("/scenariolists")
@Controller
public class ScenarioListController {
}