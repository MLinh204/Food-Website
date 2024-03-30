package com.project.admin.controller;

import com.project.library.dto.ProductDto;
import com.project.library.model.Category;
import com.project.library.model.Product;
import com.project.library.service.CategoryService;
import com.project.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products(Model mod){
        List<ProductDto> products = productService.allProduct();
        mod.addAttribute("products", products);
        mod.addAttribute("size", products.size());
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if(authen == null || authen instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        return "products";
    }

    @RequestMapping(value = "/products/{pageNo}", method = RequestMethod.GET)
    public String allProducts(@PathVariable("pageNo") int pageNo, Model mod, Principal prin){
        if(prin == null){
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.getAllProducts(pageNo);
        mod.addAttribute("title", "Manage Products");
        mod.addAttribute("size", products.getSize());
        mod.addAttribute("products", products);
        mod.addAttribute("currentPage", pageNo);
        mod.addAttribute("totalPages", products.getTotalPages());
        return "products";
    }


    @RequestMapping(value = "/search-products/{pageNo}", method = RequestMethod.GET)
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword")String mainword,
                                Model mod
                                ){
        Page<ProductDto> products = productService.searchProducts(pageNo, mainword);
        mod.addAttribute("title", "Result Search Products");
        mod.addAttribute("size", products.getSize());
        mod.addAttribute("products", products);
        mod.addAttribute("currentPage", pageNo);
        mod.addAttribute("totalPages", products.getTotalPages());
        return "product-result";

    }

    @RequestMapping(value = "/add-product", method = RequestMethod.GET)
    public String addProductPage(Model mod){
        mod.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        mod.addAttribute("categories", categories);
        mod.addAttribute("productDto", new ProductDto());
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if(authen == null || authen instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        return "add-product";
    }

    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("productDto")ProductDto product,
                              @RequestParam("imageProduct") MultipartFile image,
                              RedirectAttributes redAtbute){
        try {
            productService.save(image,product);
            redAtbute.addFlashAttribute("success", "Successfully!");
        }catch (Exception exception){
            exception.printStackTrace();
            redAtbute.addFlashAttribute("error", "Failed, Please Try Again");
        }
        return "redirect:/products/0";
    }


    @RequestMapping(value = "/update-product/{id}", method = RequestMethod.GET)
    public String updateProductForm(@PathVariable("id")Long id,  Model mod){
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if(authen == null || authen instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivatedTrue();
        ProductDto productDto = productService.getById(id);
        mod.addAttribute("title", "Add Product");
        mod.addAttribute("categories", categories);
        mod.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto")ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile image,
                                RedirectAttributes redAtbute){
        try {

            productService.update(image, productDto);
            redAtbute.addFlashAttribute("success", "Successfully!");
        }catch (Exception exception){
            exception.printStackTrace();
            redAtbute.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/enable-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Long id, RedirectAttributes redAtbute){
        try {
            productService.enableById(id);
            redAtbute.addFlashAttribute("success", "Successfully!");
        }catch (Exception exception){
            exception.printStackTrace();
            redAtbute.addFlashAttribute("error", "Failed!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/delete-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Long id, RedirectAttributes redAtbute){
        try {
            productService.deleteById(id);
            redAtbute.addFlashAttribute("success", "Successfully!");
        }catch (Exception exception){
            exception.printStackTrace();
            redAtbute.addFlashAttribute("error", "Failed!");
        }
        return "redirect:/products/0";
    }
}
