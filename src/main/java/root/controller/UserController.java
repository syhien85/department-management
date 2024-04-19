package root.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import root.dto.DepartmentDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.dto.UserDTO;
import root.service.DepartmentService;
import root.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/new")
    public String create(Model model) {

        PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
        model.addAttribute("departmentList", pageDTO.getData());

        model.addAttribute("user", new UserDTO());

        return "user/user-new.html";
    }

    @PostMapping("/new")
    public String create(
        Model model,
        @ModelAttribute("user") @Valid UserDTO userDTO,
        BindingResult bindingResult
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            // khi validation, can phai truyen lai department de chon
            PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
            model.addAttribute("departmentList", pageDTO.getData());

            return "user/user-new.html"; // if co loi, ket thuc ham, return views
        }

        if (!userDTO.getFile().isEmpty()) {
            // Ten file upload
            String filename = userDTO.getFile().getOriginalFilename();
            // Luu lai file vao o cung may chu
            File saveFile = new File("D://www/" + filename);
            userDTO.getFile().transferTo(saveFile);
            // Lay ten file luu vao database
            userDTO.setAvatarUrl(filename);
        }

        userService.create(userDTO);

        return "redirect:/user/list";
    }

    @GetMapping("/edit")
    public String update(@RequestParam("id") int id, Model model) {

        PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
        model.addAttribute("departmentList", pageDTO.getData());

        UserDTO userDTO = userService.getById(id);
        model.addAttribute("user", userDTO);

        return "user/user-edit.html";
    }

    @PostMapping("/edit")
    public String update(
        Model model,
        @ModelAttribute("user") @Valid UserDTO userDTO,
        BindingResult bindingResult
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            // khi validation, can phai truyen lai department de chon
            PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
            model.addAttribute("departmentList", pageDTO.getData());

            return "user/user-edit.html";
        }

        if (!userDTO.getFile().isEmpty()) {
            // Ten file upload
            String filename = userDTO.getFile().getOriginalFilename();
            // Luu lai file vao o cung may chu
            File saveFile = new File("D://www/" + filename);
            userDTO.getFile().transferTo(saveFile);
            // Lay ten file luu vao database
            userDTO.setAvatarUrl(filename);
        }

        userService.update(userDTO);

        return "redirect:/user/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {

        userService.delete(id);

        return "redirect:/user/list";
    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp)
        throws IOException {
        File file = new File("D://www/" + filename);
        Files.copy(file.toPath(), resp.getOutputStream());
    }

    @GetMapping("/list")
    public String list(Model model) {
        /*List<UserDTO> userDTOs = userService.getAll();

        model.addAttribute("userList", userDTOs);
        model.addAttribute("searchDTO", new SearchDTO());

        return "user/user-list.html";*/

         return "redirect:/user/search";
    }

    @GetMapping("/search")
    public String search(
        Model model,
        @ModelAttribute("searchDTO") @Valid SearchDTO searchDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "user/user-list.html"; // if co loi, ket thuc ham, return views
        }

        PageDTO<List<UserDTO>> pageDTO = userService.searchByName(searchDTO);

        model.addAttribute("userList", pageDTO.getData());
        model.addAttribute("totalPage", pageDTO.getTotalPage());
        model.addAttribute("totalElements", pageDTO.getTotalElements());
        model.addAttribute("searchDTO", searchDTO);

        return "user/user-list.html";
    }
}
