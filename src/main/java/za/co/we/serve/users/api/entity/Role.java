package za.co.we.serve.users.api.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String role;
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Set<UserAccount> users;

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(Long id, String role, Set<UserAccount> users) {
		super();
		this.id = id;
		this.role = role;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonIgnore
	public Set<UserAccount> getUsers() {
		return users;
	}

	public void setUsers(Set<UserAccount> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + ", users=" + users + "]";
	}

}
