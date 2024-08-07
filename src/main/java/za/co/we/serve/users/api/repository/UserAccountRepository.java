package za.co.we.serve.users.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.we.serve.users.api.entity.UserAccount;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	public Optional<UserAccount> findByEmail(String email);
	
	public boolean existsByEmail(String email);
}
