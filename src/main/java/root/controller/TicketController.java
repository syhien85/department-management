package root.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.service.DepartmentService;
import root.service.TicketService;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/new")
    public String create(Model model) {
        /*PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
        model.addAttribute("departmentList", pageDTO.getData());*/

        List<DepartmentDTO> getAllDepartment = departmentService.getAll();
        model.addAttribute("departmentList", getAllDepartment);

        model.addAttribute("ticket", new TicketDTO());
        return "ticket/ticket-new.html";
    }

    @PostMapping("/new")
    public String create(
        Model model,
        @ModelAttribute("ticket") @Valid TicketDTO ticketDTO,
        BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            /*PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
            model.addAttribute("departmentList", pageDTO.getData());*/

            List<DepartmentDTO> getAllDepartment = departmentService.getAll();
            model.addAttribute("departmentList", getAllDepartment);

            return "ticket/ticket-new.html";
        }

        ticketService.create(ticketDTO);

        return "redirect:/ticket/list";
    }

    @GetMapping("/edit")
    public String update(@RequestParam("id") int id, Model model) {
        TicketDTO ticketDTO = ticketService.getById(id);

        /*PageDTO<List<DepartmentDTO>> pageDTO = departmentService.searchName(new SearchDTO());
        model.addAttribute("departmentList", pageDTO.getData());*/

        List<DepartmentDTO> getAllDepartment = departmentService.getAll();
        model.addAttribute("departmentList", getAllDepartment);

        model.addAttribute("ticket", ticketDTO);

        return "ticket/ticket-edit.html";
    }

    @PostMapping("/edit")
    public String update(
        @ModelAttribute("ticket") @Valid TicketDTO ticketDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "ticket/ticket-edit.html";
        }
        ticketService.update(ticketDTO);

        return "redirect:/ticket/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        ticketService.delete(id);

        return "redirect:/ticket/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        /*List<TicketDTO> ticketDTOs = ticketService.getAll();

        model.addAttribute("ticketList", ticketDTOs);
        model.addAttribute("searchDTO", new SearchTicketDTO());

        PageDTO<List<DepartmentDTO>> pageDTODepartment = departmentService.searchName(new SearchDTO());
        model.addAttribute("departmentList", pageDTODepartment.getData());

        return "ticket/ticket-list.html";*/

        return "redirect:/ticket/search";
    }

    @GetMapping("/search")
    public String search(
        Model model,
        @ModelAttribute("searchDTO") @Valid SearchTicketDTO searchDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "ticket/ticket-list.html"; // if co loi, ket thuc ham, return views
        }

        PageDTO<List<TicketDTO>> pageDTO = ticketService.searchByName(searchDTO);

        model.addAttribute("ticketList", pageDTO.getData());
        model.addAttribute("totalPage", pageDTO.getTotalPage());
        model.addAttribute("totalElements", pageDTO.getTotalElements());
        model.addAttribute("searchDTO", searchDTO);

        PageDTO<List<DepartmentDTO>> pageDTODepartment = departmentService.searchName(new SearchDTO());
        model.addAttribute("departmentList", pageDTODepartment.getData());

        return "ticket/ticket-list.html";
    }
}
