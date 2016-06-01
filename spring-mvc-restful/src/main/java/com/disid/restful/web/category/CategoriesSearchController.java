package com.disid.restful.web.category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.disid.restful.service.api.CategoryService;

@Controller
@RequestMapping("/categories/search")
public class CategoriesSearchController {

    public CategoryService categoryService;

    @Autowired
    public CategoriesSearchController(CategoryService categoryService) {
	this.categoryService = categoryService;
    }

}
