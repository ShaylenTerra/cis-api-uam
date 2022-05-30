package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.persistence.domains.sitemap.SiteMap;
import com.dw.ngms.cis.persistence.domains.sitemap.SiteMapRole;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.SiteMapRepository;
import com.dw.ngms.cis.persistence.repository.SiteMapRoleRepository;
import com.dw.ngms.cis.service.dto.sitemap.SiteMapDto;
import com.dw.ngms.cis.service.dto.sitemap.SiteMapRoleDto;
import com.dw.ngms.cis.service.mapper.sitemap.SiteMapMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SiteRoleService {

    private final SiteMapMapper siteMapMapper;

    private final SiteMapRepository siteMapRepository;

    private final SiteMapRoleRepository siteMapRoleRepository;

    private final RoleRepository roleRepository;

    /**
     *
     * @return Collection<SiteMapRoleDto>
     */
    public Collection<SiteMapRoleDto> getAllSiteRoles() {
        Collection<SiteMapRoleDto> srDtos = new ArrayList<>();
        List<Roles> srs = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "roleName"));
        for (Roles sr : srs) {
            List<SiteMap> smColl = new ArrayList<>();
            for (SiteMapRole smr : sr.getSiteMapRoles()) {
                smColl.add(smr.getSiteMap());
            }
            srDtos.add(new SiteMapRoleDto(sr.getRoleId(), sr.getRoleName(), siteMapMapper.collectionSiteMapToCollectionSiteMapDto(smColl)));
        }
        return srDtos;

    }

    /**
     *
     * @param roleId roleId
     * @return {@link SiteMapRoleDto}
     */
    public SiteMapRoleDto getSiteRoleBasedOnRoleId(final Long roleId) {
        Roles siteRole = roleRepository.findByRoleId(roleId);
        SiteMapRoleDto siteMapRoleDto = null;
        if( null != siteRole) {

            List<SiteMap> siteMaps = siteRole.getSiteMapRoles()
                    .stream()
                    .map(SiteMapRole::getSiteMap)
                    .collect(Collectors.toList());

            List<SiteMapDto> siteMapDtos = siteMapMapper.collectionSiteMapToCollectionSiteMapDto(siteMaps);
            siteMapRoleDto = new SiteMapRoleDto(siteRole.getRoleId(), siteRole.getRoleName(),siteMapDtos );

        }
        return siteMapRoleDto;
    }

    /**
     *
     * @param pageable {@link Pageable}
     * @return {@link SiteMapDto}
     */
    public Page<SiteMapDto> getAllSiteMap(final Pageable pageable) {
        return siteMapRepository.findAll(pageable)
                .map(siteMapMapper::siteMapToSiteMapDto);

    }

    /**
     *
     * @param siteMapRoleDtos Collection<SiteMapRoleDto> siteMapRoleDtos
     */
    @Transactional
    public void updateSiteRoleMap(final Collection<SiteMapRoleDto> siteMapRoleDtos) {

        siteMapRoleDtos.forEach(siteMapRoleDto -> {

            siteMapRoleRepository.deleteByRoleId(siteMapRoleDto.getId());

            List<SiteMapDto> privileges = siteMapRoleDto.getPrivileges();

            siteMapMapper.collectionSiteMapDtoToCollectionSiteMap(privileges)
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(siteMap -> {
                SiteMapRole siteMapRole = new SiteMapRole();
                siteMapRole.setRoleId(siteMapRoleDto.getId());
                siteMapRole.setIsAccessible(Boolean.TRUE);
                siteMapRole.setSiteMapId(siteMap.getId());
                siteMapRoleRepository.save(siteMapRole);
            });
        });
    }

}
