package com.finda.server.mydata.mgmts.domain.repository;

import com.finda.server.mydata.common.code.OrgType;
import com.finda.server.mydata.mgmts.domain.entity.OrgEntity;
import com.finda.server.mydata.mgmts.domain.entity.OrgMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MgmtOrgMapRepository extends JpaRepository<OrgMapEntity, String> {

    @Query("select o,u.industry from OrgEntity u join OrgMapEntity o on (u.orgCode = o.orgEntity.orgCode) where u.orgType IN (:ListType)" )
    List<OrgMapEntity> findByOrgType(List<OrgType> ListType);

    @Query("select o,u.industry from OrgEntity u join OrgMapEntity o on (u.orgCode = o.orgEntity.orgCode) where  u.industry = ?1  " )
    List<OrgMapEntity> findByOrgTypeAndIndustry(String industry);

    @Query("select u.industry from OrgEntity u where u.orgType IN (:ListType) AND u.industry is not null  GROUP BY u.industry" )
    List<String> findByOrgTypeGroup(List<OrgType> ListType);

    @Query(value =  "SELECT DISTINCT\n" +
            "       t1.* \n" +
            "  FROM mydata_bank_code_map t1 \n" +
            "        JOIN mydata_org_info moi ON (t1.ORG_CODE=moi.ORG_CODE AND moi.ORG_TYPE IN (01,02) ) \n" +
            "       JOIN pf_loanmanage_loanaccount t2 on (t2.bank_name =t1.kcb_bank_name1 \n" +
            "            OR t2.bank_name =t1.kcb_bank_name2 \n" +
            "            OR t2.bank_name =t1.kcb_bank_name3)\n" +
            "WHERE 1=1\n" +
            "  AND t2.user_id = :userId"
            ,nativeQuery = true)
    List<OrgMapEntity> findByKcbUserId(Long userId);


    ;
}
