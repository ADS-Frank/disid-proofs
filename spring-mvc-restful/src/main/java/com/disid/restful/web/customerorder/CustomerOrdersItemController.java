package com.disid.restful.web.customerorder;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.service.api.CustomerOrderService;

@Controller
@RequestMapping("/customerorders/{customerorder}")
public class CustomerOrdersItemController {

    public CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrdersItemController(CustomerOrderService customerOrderService) {
	this.customerOrderService = customerOrderService;
    }

    @ModelAttribute
    public CustomerOrder getCustomer(@PathVariable("customerorder") Long id) {
	return customerOrderService.findOne(id);
    }

    // Update Customer

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity update(@Valid @RequestBody CustomerOrder customerOrder, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(result, HttpStatus.CONFLICT);
        }
	CustomerOrder savedCustomerOrder = customerOrderService.save(customerOrder);
	return new ResponseEntity(savedCustomerOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-form", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String editForm(@ModelAttribute CustomerOrder customerOrder, Model model) {
	return "customerorders/edit";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE)
    public String update(@Valid @ModelAttribute CustomerOrder customerOrder, BindingResult result,
	    RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
	    return "customerorders/edit";
        }
	CustomerOrder savedCustomerOrder = customerOrderService.save(customerOrder);
	redirectAttrs.addAttribute("id", savedCustomerOrder.getId());
	return "redirect:/customerorders/{id}";
    }

    // Delete Customer

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
    public String delete(@ModelAttribute CustomerOrder customerOrder, Model model) {
	customerOrderService.delete(customerOrder);
	return "redirect:/customerorders";
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@ModelAttribute CustomerOrder customerOrder) {
	customerOrderService.delete(customerOrder);
	return new ResponseEntity<>(HttpStatus.OK);
    }

    // Show Customer

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CustomerOrder> show(@ModelAttribute CustomerOrder customerOrder) {
	if (customerOrder == null) {
	    return new ResponseEntity<CustomerOrder>(HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<CustomerOrder>(customerOrder, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String show(@ModelAttribute CustomerOrder customerOrder, Model model) {
	return "customerorders/show";
    }
}
