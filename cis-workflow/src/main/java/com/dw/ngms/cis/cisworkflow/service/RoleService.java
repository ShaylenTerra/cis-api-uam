package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.Roles;
import com.dw.ngms.cis.cisworkflow.persistence.repository.RoleRepository;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public ResponseEntity<?> getRoleList() {
        List<Roles> roleList = roleRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(WorkflowUtility.roleListToRoleMap(roleList));
    }
}
