package singlesignonimpl.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import singlesignonimpl.project.entity.Product;
import singlesignonimpl.project.repository.ProductRepository;
import singlesignonimpl.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import singlesignonimpl.project.service.ReCaptchaValidationService;

@Controller
public class AppController {
	@Autowired
	private ProductService service;
	@Autowired
	private ReCaptchaValidationService validator;

	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping("/list")
	public String viewHomePage(Model model) {
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);
		
		return "products";
	}
	
	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product,
	                          @RequestParam(name="g-recaptcha-response")
			                  String captcha, Model model)
	{
		if (validator.validateCaptcha(captcha))
		{
			service.save(product);
			model.addAttribute("product", new Product());
			model.addAttribute("message", "Employee added!!");
			}
		else{
				model.addAttribute("message", "Please Verify Captcha");
			}
			return "redirect:/list";
		}


//	) {
//		service.save(product);
//
//		return "redirect:/list";
//	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product");
		
		Product product = service.get(id);
		mav.addObject("product", product);
		
		return mav;
	}	
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		service.delete(id);
		
		return "redirect:/list";
	}
}
