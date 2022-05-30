package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/**
 * Created by nirmal on 2020/11/09.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByUserTypeItemId(final UserType userType, final Pageable pageable);

    @Query(value = "SELECT CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME ) from USERS U where U.USERID = :userId", nativeQuery = true)
    String getUserNameUsingUserId(final Long userId);

    @Query(value = "SELECT u.* FROM USERS u WHERE u.EMAIL = :EMAIL AND u.PASSWORD = :PASSWORD", nativeQuery = true)
    User getUserByEmailAndPassword(@Param("EMAIL") String EMAIL, @Param("PASSWORD") String PASSWORD);

    @Query(value = "SELECT u.* FROM USERS u WHERE u.EMAIL = :EMAIL AND u.USERID = :USERID", nativeQuery = true)
    User getUserByIdAndEmail(@Param("USERID") String USERID, @Param("EMAIL") String EMAIL);

    User findByEmailAndUserTypeItemId(String email, UserType userTypeItemId);

    @Deprecated
    User findByUserNameAndUserTypeItemId(String userName, UserType userTypeItemId);

    @Query(value = "SELECT u.* FROM USERS u WHERE u.USERID = :USERID", nativeQuery = true)
    User getUserById(@Param("USERID") Long USERID);

    User findUserByUserId(final Long userId);

    @Query(value = "SELECT u.* FROM USERS u ORDER BY u.USERID DESC NULLS LAST FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    User getLastUserId();

    @Query(value = "SELECT USERCREATIONDATE as userCreationDate,\n" +
            "       USERCREATIONDATE AS lastLoginDate, \n" +
            "       FULLNAME as fullName,\n" +
            "       USERTYPE as userType,\n" +
            "       ORGANIZATION_NAME as organisation,\n" +
            "       SECTOR_NAME as sector,\n" +
            "       ROLENAME as role,\n" +
            "       PROVINCE_NAME as province,\n" +
            "       EMAIL as userName,\n" +
            "       STATUS as status,\n" +
            "       ADDITIONAL_ROLENAME as additionalRole,\n" +
            "       SECTION_NAME as section from V_USERS\n" +
            "WHERE  (:userType is null or USERTYPE = :userType )\n" +
            "  AND  (:province is null or PROVINCE_NAME = :province) \n" +
            "  AND USERCREATIONDATE >= :fromDate" +
            "  AND USERCREATIONDATE <= :toDate",
            nativeQuery = true)
    Collection<Map<String, Object>> getUserSummaryDetails(final String province,
                                                          final String userType,
                                                          final LocalDate fromDate,
                                                          final LocalDate toDate);

    User findByEmailIgnoreCase(final String email);

    User findByUserNameIgnoreCase(final String userName);

    @Query(value = "SELECT U.* " +
            "FROM USERS U" +
            " INNER JOIN USER_PROFESSIONAL UF ON U.USERID=UF.USERID " +
            "WHERE UF.PPNNO = :ppnNo AND U.STATUSITEMID= :userStatus",
            nativeQuery = true)
    User getUserByPpnNoAndUserStatus(final String ppnNo, final Long userStatus);

    @Query(value = "SELECT u.* " +
            "FROM USER_ASSISTANTS ua " +
            "INNER JOIN USERS u ON u.userId = ua.assistantId "
            + " WHERE ua.userId = :userId", nativeQuery = true)
    Collection<User> findAssistantsByUserId(Long userId);

    User findUserByUserName(String userName);

    @Query(value = "SELECT U.* " +
            "FROM USERS U " +
            "INNER JOIN USER_ADDITIONAL_ROLE UAR on U.USERID = UAR.USERID " +
            "WHERE UAR.PROVINCEID = :provinceId AND UAR.ROLEID= :roleId AND U.STATUSITEMID=108", nativeQuery = true)
    Collection<User> findUserByProvinceIdAndRoleId(final Long provinceId, final Long roleId);

    @Query(value = "SELECT U.* " +
            "FROM USERS U " +
            "INNER JOIN USER_ADDITIONAL_ROLE UAR on U.USERID = UAR.USERID " +
            "WHERE U.EMAIL = :email AND UAR.ROLEID= :roleId AND U.STATUSITEMID=108", nativeQuery = true)
    User findUserByEmailAndRoleId(final String email, final Long roleId);

    @Query(value = "SELECT U.* FROM USERS U \n" +
            "INNER JOIN USER_ADDITIONAL_ROLE UAR on UAR.USERID = U.USERID\n" +
            "WHERE UAR.PROVINCEID = :provinceId AND\n" +
            "      U.USERTYPEITEMID = 3 AND\n" +
            "      U.STATUSITEMID = 108 AND\n" +
            "      UAR.ROLEID = :roleId", nativeQuery = true)
    Collection<User> getUserForDecision(Long roleId, Long provinceId);

    @Query(value = "select U.*\n" +
            "from USERS U\n" +
            "         inner join USER_ADDITIONAL_ROLE UAR on U.USERID = UAR.USERID\n" +
            "         inner join ROLES RL on RL.ROLEID = UAR.ROLEID\n" +
            "where RL.ROLENAME like '%Manager%'\n" +
            "  and UAR.SECTION_ITEMID = :sectionItemId\n" +
            "  and UAR.PROVINCEID = :provinceId", nativeQuery = true)
    Collection<User> searchUserBySectionAndProvinceId(Long sectionItemId, Long provinceId);

    @Query(value = "SELECT u.* FROM USERS u WHERE u.FIRSTNAME like  %:searchKey% " +
            " or u.SURNAME like %:searchKey% " +
            " or u.EMAIL like %:searchKey% ", nativeQuery = true)
    Collection<User> searchUsers(@Param("searchKey") String searchKey);

    @Query(value = "SELECT u.* " +
            "FROM USER_ASSISTANTS ua " +
            "INNER JOIN USERS u ON u.userId = ua.userId "
            + " WHERE ua.assistantId = :assistanceId AND ua.statusId = 106", nativeQuery = true)
    Collection<User> findProfessionalByAssistanceId(Long assistanceId);
}
