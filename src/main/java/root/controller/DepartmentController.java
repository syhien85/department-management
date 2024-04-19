package root.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import root.dto.DepartmentDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.service.DepartmentService;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("department", new DepartmentDTO());

        return "department/department-new.html";
    }

    @PostMapping("/new")
    public String create(
        @ModelAttribute("department") @Valid DepartmentDTO departmentDTO,
        BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "department/department-new.html";
        }

        departmentService.create(departmentDTO);

        return "redirect:/department/list";
    }

    @GetMapping("/edit")
    public String update(@RequestParam("id") int id, Model model) {
        DepartmentDTO departmentDTO = departmentService.getById(id);
        model.addAttribute("department", departmentDTO);

        return "department/department-edit.html";
    }

    @PostMapping("/edit")
    public String update(
        @ModelAttribute("department") @Valid DepartmentDTO departmentDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "department/department-edit.html";
        }

        departmentService.update(departmentDTO);

        return "redirect:/department/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        departmentService.delete(id);

        return "redirect:/department/list";
    }

    @GetMapping("/list")
    public String list(Model model) {

        /*SearchDTO searchDTO = new SearchDTO();
        searchDTO.setKeyword("");

        PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(searchDTO);

        model.addAttribute("departmentList", pageDTO.getData());
        model.addAttribute("totalPage", pageDTO.getTotalPage());
        model.addAttribute("totalElements", pageDTO.getTotalElements());
        model.addAttribute("searchDTO", searchDTO);

        return "department/department-list.html";*/

        return "redirect:/department/search";
    }

    @GetMapping("/search")
    public String search(
        Model model,
        @ModelAttribute("searchDTO") @Valid SearchDTO searchDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("searchDTO", new SearchDTO());
            return "department/department-list.html";
        }

        PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(searchDTO);

        model.addAttribute("departmentList", pageDTO.getData());
        model.addAttribute("totalPage", pageDTO.getTotalPage());
        model.addAttribute("totalElements", pageDTO.getTotalElements());
        model.addAttribute("searchDTO", searchDTO);

        return "department/department-list.html";
    }
}
