package com.lesso.newlp.pm.repository;

import com.lesso.newlp.material.entity.MaterialEntity;
import com.lesso.newlp.pm.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * UserDTO: Sean
 * Date: 11/14/13
 * Time: 10:20 PM
 */
@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")
public interface ClientRepository extends PagingAndSortingRepository<ClientEntity,Long> {
    @Query("select client from ClientEntity  client left join client.members  clientMem  where clientMem.memberId = :memberId")
    Page<ClientEntity> findByMemberId(@Param("memberId") String memberId,Pageable pageable);

    @Query("select client from ClientEntity  client  left join client.members  clientMem  where (client.clientNum like %:searchTerm% or client.clientName like %:searchTerm% ) and clientMem.memberId = :memberId")
    Page<ClientEntity> findByNameOrNumLike(@Param("searchTerm") String searchTerm,@Param("memberId") String memberId, Pageable pageable);
}
