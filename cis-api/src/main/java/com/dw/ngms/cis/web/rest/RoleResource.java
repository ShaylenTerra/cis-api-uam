package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.RoleService;
import com.dw.ngms.cis.service.SiteRoleService;
import com.dw.ngms.cis.service.dto.sitemap.SiteMapDto;
import com.dw.ngms.cis.service.dto.sitemap.SiteMapRoleDto;
import com.dw.ngms.cis.service.dto.user.UserRoleDto;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.DeleteResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 23/11/20, Mon
 * <p>
 * this class represent ExternalRole and support CRUD operation on it
 **/
@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/roles")
public class RoleResource {

    private final RoleService roleService;

    private final SiteRoleService siteRoleService;

    /**
     * @return Collection<SiteMapRoleDto>
     */
    @GetMapping("/siteRoles")
    public ResponseEntity<Collection<SiteMapRoleDto>> getAllSiteRolesWithAllPrivileges() {
        return ResponseEntity.ok()
                .body(siteRoleService.getAllSiteRoles());
    }

    /**
     * @param pageable {@link Pageable}
     * @return Collection<SiteMapDto>
     */
    @GetMapping("/siteMap")
    @ApiPageable
    public ResponseEntity<Collection<SiteMapDto>>
    getAllSiteMap(@ApiIgnore @SortDefault(sort = "description", direction = Sort.Direction.ASC)
                  @PageableDefault(size = Integer.MAX_VALUE) final Pageable pageable) {
        Page<SiteMapDto> allSiteMap = siteRoleService.getAllSiteMap(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allSiteMap);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allSiteMap.getContent());
    }

    /**
     * @param roleId roleId
     * @return {@link SiteMapRoleDto}
     */
    @GetMapping("/siteMap/{roleId}")
    public ResponseEntity<SiteMapRoleDto> siteMapByRoleId(@PathVariable @NotNull final Long roleId) {
        return ResponseEntity.ok()
                .body(siteRoleService.getSiteRoleBasedOnRoleId(roleId));
    }

    /**
     * @param siteMapRoleDtos Collection<SiteMapRoleDto>
     */
    @PostMapping("/updateSiteMapRole")
    public void updateSiteRoleMap(@RequestBody @Valid Collection<SiteMapRoleDto> siteMapRoleDtos) {
        siteRoleService.updateSiteRoleMap(siteMapRoleDtos);
    }

    /**
     * Get all the role of the selected user based on user id.
     *
     * @param userId userId
     * @return Collection<UserRoleDto>
     */
    @GetMapping("/userRoles/{userId}")
    public ResponseEntity<Collection<UserRoleDto>> getUserRoles(@PathVariable @NotNull final Long userId) {
        return ResponseEntity.ok().body(roleService.getRoles(userId));
    }

    /**
     * @param userRoleDto {@link UserRoleDto}
     * @return {@link UserRoleDto}
     */
    @PostMapping("/saveUserRole")
    public ResponseEntity<UserRoleDto> saveUserRole(@RequestBody @NotNull final UserRoleDto userRoleDto) {
        return ResponseEntity.ok().body(roleService.saveUserRole(userRoleDto));
    }

    @DeleteMapping("/userRoles/{userRoleId}")
    public ResponseEntity<DeleteResponse> deleteRoleId(@PathVariable final Long userRoleId) {
        Boolean b = roleService.deleteRoleByUserRoleId(userRoleId);
        final DeleteResponse build = DeleteResponse.builder().isDeleted(b).build();
        return ResponseEntity.ok().body(build);
    }
}
