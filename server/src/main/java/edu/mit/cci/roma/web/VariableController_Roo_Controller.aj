// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.mit.cci.roma.web;

import edu.mit.cci.roma.api.DataType;
import edu.mit.cci.roma.impl.DefaultVariable;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Arrays;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect VariableController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String VariableController.create(@Valid DefaultVariable defaultVariable, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("defaultVariable", defaultVariable);
            return "variables/create";
        }
        defaultVariable.persist();
        return "redirect:/variables/" + encodeUrlPathSegment(defaultVariable.getId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String VariableController.createForm(Model model) {
        model.addAttribute("defaultVariable", new DefaultVariable());
        return "variables/create";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String VariableController.update(@Valid DefaultVariable defaultVariable, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("defaultVariable", defaultVariable);
            return "variables/update";
        }
        defaultVariable.merge();
        return "redirect:/variables/" + encodeUrlPathSegment(defaultVariable.getId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String VariableController.updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("defaultVariable", DefaultVariable.findDefaultVariable(id));
        return "variables/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String VariableController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        DefaultVariable.findDefaultVariable(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/variables?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @ModelAttribute("datatypes")
    public Collection<DataType> VariableController.populateDataTypes() {
        return Arrays.asList(DataType.class.getEnumConstants());
    }
    
    @ModelAttribute("defaultvariables")
    public Collection<DefaultVariable> VariableController.populateDefaultVariables() {
        return DefaultVariable.findAllDefaultVariables();
    }
    
    String VariableController.encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
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