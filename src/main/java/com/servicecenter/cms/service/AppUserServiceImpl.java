package com.servicecenter.cms.service;

import com.servicecenter.cms.model.ApplicationUser;
import com.servicecenter.cms.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.servicecenter.cms.security.UserRole.TECHNICIAN;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository repository;
    private PasswordEncoder passwordEncoder;

//    private List<ApplicationUser> list;
//
//    @PostConstruct
//    public void hello() {
//        this.list = new ArrayList<>(Arrays.asList(
//                new ApplicationUser("admin", passwordEncoder.encode("admin123"), ADMIN.getGrantedAuthorities(), null),
//                new ApplicationUser("callcenter", passwordEncoder.encode("system123"), CALLCENTER.getGrantedAuthorities(), null)
//        ));
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return list.stream().filter(l->l.getUsername().equals(username))
//                .findFirst().orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public void addNewUser(String username, String password, String technicianId) {
        ApplicationUser user = new ApplicationUser(username, passwordEncoder.encode(password), TECHNICIAN.getGrantedAuthorities(), technicianId);
        repository.insert(user);
//        list.add(user);
    }

    @Override
    public void deleteUser(String technicianId) {
        repository.deleteByTechnicianId(technicianId);
//        ApplicationUser user = list.stream().filter(l -> l.getTechnicianId().equals(technicianId)).findFirst().orElseThrow();
//        list.remove(user);
    }
}
