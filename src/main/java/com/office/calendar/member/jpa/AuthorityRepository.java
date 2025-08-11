package com.office.calendar.member.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {

    public boolean existsByAuthRoleName(String authRoleName);

}
