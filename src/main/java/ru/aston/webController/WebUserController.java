package ru.aston.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aston.DAO.UserDao;
import ru.aston.model.User;

import javax.validation.Valid;

@Controller
@RequestMapping("/web")
public class WebUserController {
    private final UserDao userDao;

    @Autowired
    public WebUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", userDao.index());
        return "/web/index";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDao.show(id));
        return "web/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "web/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "web/new";

        userDao.save(user);
        return "redirect:/web";
    }

    @GetMapping("/{id}/edit")
    public String editPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDao.show(id));
        return "web/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "web/edit";

        userDao.update(id, user);
        return "redirect:/web";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userDao.delete(id);
        return "redirect:/web";
    }
}
