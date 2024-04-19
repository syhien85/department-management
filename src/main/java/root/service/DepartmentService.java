package root.service;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.DepartmentDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.entity.Department;
import root.repository.DepartmentRepo;

import java.util.List;
import java.util.stream.Collectors;

public interface DepartmentService {
    void create(DepartmentDTO departmentDTO);

    void update(DepartmentDTO departmentDTO);

    void delete(int id);

    DepartmentDTO getById(int id);

    List<DepartmentDTO> getAll();

    PageDTO<List<DepartmentDTO>> searchName(SearchDTO searchDTO);
}

@Service
class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepo departmentRepo;

    @Transactional
    public void create(DepartmentDTO departmentDTO) {
        Department department = new ModelMapper().map(departmentDTO, Department.class);

        departmentRepo.save(department);
    }

    @Transactional
    public void update(DepartmentDTO departmentDTO) {
        // if .orElseThrow(NoResultException::new), ko can check null nua
        Department currentDepartment = departmentRepo.findById(departmentDTO.getId())
            .orElseThrow(NoResultException::new);

        currentDepartment.setName(departmentDTO.getName());

        departmentRepo.save(currentDepartment);
    }

    @Transactional
    public void delete(int id) {
        departmentRepo.deleteById(id);
    }

    public DepartmentDTO getById(int id) {
        // if .orElseThrow(NoResultException::new), ko can check null nua
        Department department = departmentRepo.findById(id).orElseThrow(NoResultException::new);

        return convert(department);
    }

    public List<DepartmentDTO> getAll() {
        List<Department> departmentList = departmentRepo.findAll();

        return departmentList.stream().map(this::convert).collect(Collectors.toList());
    }

    public PageDTO<List<DepartmentDTO>> searchName(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("name").ascending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) { // StringUtils.hasText(sortedField)
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        // mac dinh cho currentPage va size
        if (searchDTO.getKeyword() == null) {
            searchDTO.setKeyword("");
        }
        if (searchDTO.getCurrentPage() == null) {
            searchDTO.setCurrentPage(0);
        }
        if (searchDTO.getSize() == null) {
            searchDTO.setSize(10);
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(),
            searchDTO.getSize(),
            sortBy
        );

        Page<Department> page = departmentRepo.searchName(
            "%" + searchDTO.getKeyword() + "%",
            pageRequest
        );

        return PageDTO.<List<DepartmentDTO>>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private DepartmentDTO convert(Department department) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(department, DepartmentDTO.class);
    }
}
