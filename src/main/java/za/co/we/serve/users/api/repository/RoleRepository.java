package za.co.we.serve.users.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.we.serve.users.api.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{

}
