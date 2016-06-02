package com.disid.restful.web.customerorder;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.service.api.CustomerOrderService;
import com.disid.restful.service.api.OrderDetailService;

@Controller
@RequestMapping("/customerorders/{customerorder}/details")
public class CustomerOrdersItemDetailsController {

    public CustomerOrderService customerOrderService;

    public OrderDetailService orderDetailService;

    @Autowired
    public CustomerOrdersItemDetailsController(CustomerOrderService customerOrderService,
	    OrderDetailService orderDetailService) {
	this.orderDetailService = orderDetailService;
        this.customerOrderService = customerOrderService;
    }

    @ModelAttribute
    public CustomerOrder getCustomerOrder(@PathVariable("customerorder") Long id) {
	return customerOrderService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<OrderDetail> listOrderDetails(@PathVariable("customerorder") CustomerOrder customerOrder,
	    GlobalSearch search,
	    Pageable pageable) {
	Page<OrderDetail> orderDetails = orderDetailService.findAllByCustomerOrder(customerOrder, search, pageable);
	return orderDetails;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
    @ResponseBody
    public DatatablesData<OrderDetail> listOrderDetails(@PathVariable("customerorder") CustomerOrder customerOrder,
	    GlobalSearch search, Pageable pageable, @RequestParam("draw") Integer draw) {
	Page<OrderDetail> orderDetails = listOrderDetails(customerOrder, search, pageable);
	long allAvailableOrderDetails = orderDetailService.countByCustomerOrderId(customerOrder.getId());
	return new DatatablesData<OrderDetail>(orderDetails, allAvailableOrderDetails, draw);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerOrder setOrderDetails(@ModelAttribute CustomerOrder customerOrder,
	    @Valid @RequestBody Collection<OrderDetail> details) {
	return customerOrderService.setDetails(customerOrder, details);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerOrder addOrders(@ModelAttribute CustomerOrder customerOrder,
	    @Valid @RequestBody Collection<OrderDetail> details) {
	return customerOrderService.addDetails(customerOrder, details);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerOrder deleteOrders(@ModelAttribute CustomerOrder customerOrder,
	    @Valid @RequestBody Collection<OrderDetail> details) {
	return customerOrderService.deleteDetails(customerOrder, details);
    }

}
