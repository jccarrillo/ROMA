package edu.mit.cci.roma.web;

import edu.mit.cci.roma.excel.ExcelSimulation;
import edu.mit.cci.roma.excel.ExcelVariable;
import edu.mit.cci.roma.impl.DefaultVariable;
import edu.mit.cci.roma.server.DefaultServerVariable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;


@RequestMapping("/excelvariables")
@Controller
public class ExcelVariableController {

     @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid ExcelVariable excelVariable, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("excelVariable", excelVariable);
            return "excelvariables/create";
        }
        excelVariable.persist();
        return "redirect:/excelvariables/" + encodeUrlPathSegment(excelVariable.getId().toString(), request);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("excelVariable", new ExcelVariable());
        return "excelvariables/create";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("excelvariable", ExcelVariable.findExcelVariable(id));
        model.addAttribute("itemId", id);
        return "excelvariables/show";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("excelvariables", ExcelVariable.findExcelVariableEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ExcelVariable.countExcelVariables() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("excelvariables", ExcelVariable.findAllExcelVariables());
        }
        return "excelvariables/list";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid ExcelVariable excelVariable, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("excelVariable", excelVariable);
            return "excelvariables/update";
        }
        excelVariable.merge();
        return "redirect:/excelvariables/" + encodeUrlPathSegment(excelVariable.getId().toString(), request);
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("excelVariable", ExcelVariable.findExcelVariable(id));
        return "excelvariables/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        ExcelVariable.findExcelVariable(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/excelvariables?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }

    @ModelAttribute("excelsimulations")
    public Collection<ExcelSimulation> populateExcelSimulations() {
        return ExcelSimulation.findAllExcelSimulations();
    }

    @ModelAttribute("defaultvariables")
    public Collection<DefaultServerVariable> populateDefaultVariables() {
        return DefaultServerVariable.findAllDefaultVariables();
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
