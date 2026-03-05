package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.Cat;
import at.spengergasse.spring_thymeleaf.entities.CatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cat/")
public class CatController {

    private final CatRepository catRepository;

    public CatController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping("/list")
    public String cats(Model model) {
        model.addAttribute("cats", catRepository.findAll());
        return "catlist";
    }

    @GetMapping("/add")
    public String addCatForm(Model model) {
        model.addAttribute("cat", new Cat());
        return "add_cat";
    }

    @PostMapping("/add")
    public String saveCat(@ModelAttribute Cat cat) {
        catRepository.save(cat);
        return "redirect:/cat/list";
    }

    @GetMapping("/edit/{id}")
    public String editCatForm(@PathVariable int id, Model model) {
        Cat cat = catRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Katzen-ID: " + id));
        model.addAttribute("cat", cat);
        return "edit_cat";
    }

    @PostMapping("/edit/{id}")
    public String updateCat(@PathVariable int id, @ModelAttribute Cat cat) {
        cat.setId(id);
        catRepository.save(cat);
        return "redirect:/cat/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        catRepository.deleteById(id);
        return "redirect:/cat/list";
    }
}