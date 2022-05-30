package com.dw.ngms.cis.service.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 22/03/21, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationCountDto {

    private Long totalRegisteredUsers;

    private Long totalActiveUsers;

    private Long totalLockedUsers;

    private Long totalActiveParticipants;


}
