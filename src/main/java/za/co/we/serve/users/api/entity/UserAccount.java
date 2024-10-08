package za.co.we.serve.users.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "UserAccounts")
public class UserAccount {

	/*
	 * Attributes
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(min = 2, max = 20)
	private String name;
	@NotBlank
	@Size(min = 2, max = 30)
	private String surname;
	@NotBlank
	@Email
	private String email;
	//Customized validator
	@NotBlank
	private String password;
	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;

	public UserAccount() {
		// TODO Auto-generated constructor stub
	}

	public UserAccount(Long id, @NotBlank @Size(min = 2, max = 20) String name,
			@NotBlank @Size(min = 2, max = 30) String surname, @NotBlank @Email String email, @NotBlank String password,
			Role role) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", role=" + role + "]";
	}
	
	

}
