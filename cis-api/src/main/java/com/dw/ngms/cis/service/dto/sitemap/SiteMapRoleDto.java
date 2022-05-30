package com.dw.ngms.cis.service.dto.sitemap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteMapRoleDto {
    private Long id;
    private String roleName;
    private List<SiteMapDto> privileges;
}
