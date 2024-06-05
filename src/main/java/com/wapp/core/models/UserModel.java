package com.wapp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {

	private String id;
	private String token;

	@NotBlank(message = "O username é obrigatório!")
	private String username;

	@NotBlank(message = "O nome é obrigatório!")
	private String name;

	@Email(message = "Informe um e-mail válido!")
	@NotBlank(message = "O e-mail é obrigatório!")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "A senha é obrigatória!")
	@Size(min = 8, max = 40, message = "A senha deve ter entre 8 e 40 caracteres!")
	@Pattern(regexp = ".*[^a-zA-Z0-9 ].*", message = "A senha deve conter ao menos um caractere especial!")
	private String password;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String identification;

}
