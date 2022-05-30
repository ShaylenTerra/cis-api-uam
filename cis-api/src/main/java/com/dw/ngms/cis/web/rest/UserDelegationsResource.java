package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.user.UserDelegationsDto;
import com.dw.ngms.cis.service.user.UserDelegationService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 19/12/20, Sat
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/user/delegations")
public class UserDelegationsResource {

    private final UserDelegationService userDelegationService;

    private final UserService userService;


    /**
     * method to fetch all internal Users
     * need to change in future after fixing user domain
     * it will be based on provinceId and internal User
     *
     * @param pageable {@link Pageable}
     * @return Collection<UserDto>
     */
    @GetMapping("/allIntenalUser")
    @ApiPageable
    public ResponseEntity<Collection<UserDto>> getAllInternalUsers(final Pageable pageable) {
        Page<UserDto> allUserByType = userService.getAllUserByType(UserType.INTERNAL, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allUserByType);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allUserByType.getContent());
    }

    /**
     * @param userId   userId of logged in User
     * @param pageable {@link Pageable}
     * @return Page<UserDelegationsDto>
     */
    @GetMapping("/list")
    @ApiPageable
    public ResponseEntity<Page<UserDelegationsDto>> getAllUserDelegations(@RequestParam @NotNull final Long userId,
                                                                          @ApiIgnore final Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(userDelegationService.getAllUserDelegations(userId, pageable));
    }

    /**
     * @param statusItemId status to be changed whether 108/109
     * @param id     delegationId primary key of user_delegations table
     * @return UpdateResponse whether userDelegations updated or not
     */
    @GetMapping("/update")
    public ResponseEntity<UpdateResponse> updateUserDelegations(@RequestParam @NotNull final Long statusItemId,
                                                                @RequestParam @NotNull final Long id) {
        Boolean aBoolean = userDelegationService.updateUserDelegation(statusItemId, id);
        UpdateResponse updateResponse = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok()
                .body(updateResponse);
    }

    /**
     * save user delegations
     *
     * @param userDelegationsDto {@link UserDelegationsDto}
     * @return {@link UserDelegationsDto}
     */
    @PostMapping("/add")
    public ResponseEntity<UserDelegationsDto> addUserDelegations(@RequestBody @Valid final UserDelegationsDto userDelegationsDto) {

        return ResponseEntity.ok()
                .body(userDelegationService.saveUserDelegation(userDelegationsDto));
    }
}

