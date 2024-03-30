package com.project.admin.controller;

import com.project.library.model.Category;
import com.project.library.service.CategoryService;
import org.hibernate.result.UpdateCountOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService cateService;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String categories(Model mod){
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if(authen == null || authen instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        mod.addAttribute("title", "Manage Category");
        List<Category> categories = cateService.findALl();
        mod.addAttribute("categories", categories);
        mod.addAttribute("size", categories.size());
        mod.addAttribute("categoryNew", new Category());
        return "categories";
    }


    @RequestMapping(value = "/save-category", method = RequestMethod.POST)
    public String save(@ModelAttribute("categoryNew") Category category ,Model mod, RedirectAttributes redAtbute){
        try {
            cateService.save(category);
            mod.addAttribute("categoryNew", category);
            redAtbute.addFlashAttribute("success", "Successfully!");
        }catch (DataIntegrityViolationException exception1){
            exception1.printStackTrace();
            redAtbute.addFlashAttribute("error", "Duplicate name ");
        }catch(Exception exception2){
            exception2.printStackTrace();
            mod.addAttribute("categoryNew", category);
            redAtbute.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Category> findById( Long id){
        return cateService.findById(id);
    }

    @RequestMapping(value = "/update-category", method = RequestMethod.GET)
    public String update(Category category, RedirectAttributes redAtbute){
        try {
            cateService.update(category);
            redAtbute.addFlashAttribute("success", "Successfully");
        }catch (DataIntegrityViolationException exception1){
            exception1.printStackTrace();
            redAtbute.addFlashAttribute("error", "Duplicate name");
        }catch (Exception exception2){
            exception2.printStackTrace();
            redAtbute.addFlashAttribute("error", "Error from server");
        }
        return "redirect:/categories";
    }


    @RequestMapping(value = "/delete-category", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id,RedirectAttributes redAtbute){
        try {
            cateService.deleteById(id);
            redAtbute.addFlashAttribute("success", "Successfully");
        }catch (DataIntegrityViolationException exception1){
            exception1.printStackTrace();
            redAtbute.addFlashAttribute("error", "Duplicate name");
        } catch (Exception exception2){
            exception2.printStackTrace();
            redAtbute.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes redAtbute){
        try {
            cateService.enableById(id);
            redAtbute.addFlashAttribute("success", "Successfully");
        }catch (DataIntegrityViolationException exception1){
            exception1.printStackTrace();
            redAtbute.addFlashAttribute("error", "Duplicate name");
        }catch (Exception exception2){
            exception2.printStackTrace();
            redAtbute.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }







}
