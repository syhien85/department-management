package root.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.dto.UserDTO;
import root.entity.Department;
import root.entity.User;
import root.repository.DepartmentRepo;
import root.repository.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Transactional
    public void create(UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);

        userRepo.save(user);
    }

    @Transactional
    public void update(UserDTO userDTO) {

        Department currentDepartment = departmentRepo.findById(userDTO.getDepartment().getId()).orElse(null);
        if (currentDepartment != null) {
            currentDepartment.setId(userDTO.getDepartment().getId());
        }

        User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
        if (currentUser != null) {
            currentUser.setName(userDTO.getName());
            currentUser.setAge(userDTO.getAge());
            currentUser.setHomeAddress(userDTO.getHomeAddress());
            currentUser.setBirthdate(userDTO.getBirthdate());

            currentUser.setAvatarUrl(userDTO.getAvatarUrl());

            currentUser.setDepartment(currentDepartment);

            userRepo.save(currentUser);
        }
    }

    @Transactional
    public void updatePassword(UserDTO userDTO) {
        User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
        if (currentUser != null) {
            currentUser.setPassword(userDTO.getPassword());

            userRepo.save(currentUser);
        }
    }

    @Transactional
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    public UserDTO getById(int id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            return convert(user);
        } else {
            return null;
        }
    }

    public List<UserDTO> getAll() {
        List<User> userList = userRepo.findAll();

        return userList.stream().map(this::convert).collect(Collectors.toList());
    }

    public PageDTO<List<UserDTO>> searchByName(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("name").ascending();
        //sort nhieu tieu chi
//         Sort sortBy = Sort.by("name")
//             .and(Sort.by("age").descending())
//             .ascending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) { // StringUtils.hasText(sortedField)
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        if (searchDTO.getKeyword() == null) {
            searchDTO.setKeyword("");
        }
        // mac dinh cho currentPage va size
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

        Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);

//        List<UserDTO> userDTOS = page.get().map(this::convert).toList();

        return PageDTO.<List<UserDTO>>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private UserDTO convert(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(user, UserDTO.class);
    }
}
