package root.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.dto.SearchTicketDTO;
import root.dto.TicketDTO;
import root.entity.Department;
import root.entity.Ticket;
import root.repository.DepartmentRepo;
import root.repository.TicketRepo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Transactional
    public void create(TicketDTO ticketDTO) {
        Ticket ticket = new ModelMapper().map(ticketDTO, Ticket.class);

        ticketRepo.save(ticket);
    }

    @Transactional
    public void update(TicketDTO ticketDTO) {
        Department currentDepartment = departmentRepo.findById(
            ticketDTO.getDepartment().getId()
        ).orElse(null);

        if (currentDepartment != null) {
            currentDepartment.setId(ticketDTO.getDepartment().getId());
        }

        Ticket currentTicket = ticketRepo.findById(ticketDTO.getId()).orElse(null);
        if (currentTicket != null) {
            currentTicket.setClientName(ticketDTO.getClientName());
            currentTicket.setClientPhone(ticketDTO.getClientPhone());
            currentTicket.setContent(ticketDTO.getContent());
            currentTicket.setProcessDate(ticketDTO.getProcessDate());

            currentTicket.setStatus(ticketDTO.isStatus());

            currentTicket.setDepartment(currentDepartment);

            ticketRepo.save(currentTicket);
        }
    }

    @Transactional
    public void updateStatus(TicketDTO ticketDTO) {
        Ticket currentTicket = ticketRepo.findById(ticketDTO.getId()).orElse(null);
        if (currentTicket != null) {
            currentTicket.setStatus(ticketDTO.isStatus());

            ticketRepo.save(currentTicket);
        }
    }

    @Transactional
    public void delete(int id) {
        ticketRepo.deleteById(id);
    }

    public TicketDTO getById(int id) {
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket != null) {
            return convert(ticket);
        } else {
            return null;
        }
    }

    public List<TicketDTO> getAll() {
        List<Ticket> ticketList = ticketRepo.findAll();

        return ticketList.stream().map(this::convert).collect(Collectors.toList());
    }

    public PageDTO<List<TicketDTO>> searchByName(SearchTicketDTO searchDTO) {
        Sort sortBy = Sort.by("id").ascending();
        //sort nhieu tieu chi
//         Sort sortBy = Sort.by("name")
//             .and(Sort.by("age").descending())
//             .ascending();
        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) { // StringUtils.hasText(sortedField)
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }
        if (searchDTO.getKeyword() == null) searchDTO.setKeyword("");
        if (searchDTO.getCurrentPage() == null) searchDTO.setCurrentPage(0);
        if (searchDTO.getSize() == null) searchDTO.setSize(10);

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(),
            searchDTO.getSize(),
            sortBy
        );

        // If ko nhap gi ca
        Page<Ticket> page = ticketRepo.findAll(pageRequest);

        String key = "%" + searchDTO.getKeyword() + "%";

        Integer c1 = searchDTO.getDepartmentId();
        Date c2 = searchDTO.getStart();
        Date c3 = searchDTO.getEnd();

        if (c1 == null && c2 == null && c3 == null) {
            page = ticketRepo.searchByDefault(key, pageRequest);
        }

        if (c1 != null && c2 == null && c3 == null) {
            page = ticketRepo.searchByDepartmentId(key, c1, pageRequest);
        }
        if (c1 == null && c2 != null && c3 == null) {
            page = ticketRepo.searchByStart(key, c2, pageRequest);
        }
        if (c1 == null && c2 == null && c3 != null) {
            page = ticketRepo.searchByEnd(key, c3, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null) {
            page = ticketRepo.searchByDepartmentIdAndStart(key, c1, c2, pageRequest);
        }
        if (c1 != null && c2 == null && c3 != null) {
            page = ticketRepo.searchByDepartmentIdAndEnd(key, c1, c3, pageRequest);
        }
        if (c1 == null && c2 != null && c3 != null) {
            page = ticketRepo.searchByStartAndEnd(key, c2, c3, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null) {
            page = ticketRepo.searchByDepartmentIdAndStartAndEnd(key, c1, c2, c3, pageRequest);
        }

        // page = ticketRepo.searchByClientName("%" + searchDTO.getKeyword() + "%", pageRequest);

        return PageDTO.<List<TicketDTO>>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private TicketDTO convert(Ticket ticket) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(ticket, TicketDTO.class);
    }
}
