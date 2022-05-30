package com.dw.ngms.cis.service.dto.sitemap;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteMapDto {

    private String name;

    private Long id;

    private String userType;

    private String icon;

    private String route;
}
