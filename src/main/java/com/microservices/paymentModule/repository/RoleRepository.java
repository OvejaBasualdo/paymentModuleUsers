package com.microservices.paymentModule.repository;

import com.microservices.paymentModule.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role,Long> {
}
