package com.paula.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private int userId;

	@NotBlank(message = "*El campo usuario no puede estar vacio.")
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	@Email(message = "*El formato del correo electrónico no es válido.")
	private String email;

    private String password;

	@Column(name = "username")
	@Pattern(regexp = "\\w+", message = "*El nombre de usuario debe ser una sola palabra.")
	private String username;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<FavouriteBooks> favorites = new HashSet<>();

	public User(String email) {
		this.email = email;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password, String username, Role role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}

	public User(int userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public Integer getId() {
		return userId;
	}
}
