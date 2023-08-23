package com.example.HelloWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}
	@GetMapping
	public String pleaseInput(){
		return "Введите запрос в формате: /hello?login=Name&password=Password&confirmPassword=Password";
	}
	@GetMapping("/hello")
	public String hello(@RequestParam(name = "login") String login,
						@RequestParam(name = "password") String password,
						@RequestParam(name = "confirmPassword") String confirmPassword) {
		try {
			validateLogin(login);
			validatePassword(password, confirmPassword);
			return "Привет, " + login + "!";
		} catch (WrongLoginException | WrongPasswordException ex) {
			return ex.getMessage();
		}
	}

	public static void validateLogin(String login) throws WrongLoginException {
		if (!login.matches("[a-zA-Z0-9_]+") || login.length() > 20) {
			throw new WrongLoginException("Неверный логин: " + login);
		}
	}

	public static void validatePassword(String password, String confirmPassword) throws WrongPasswordException {
		if (!password.equals(confirmPassword)) {
			throw new WrongPasswordException("Пароль и пароль подтверждения не совпадают");
		}
		if (!password.matches("[a-zA-Z0-9_]+") || password.length() > 20) {
			throw new WrongPasswordException("Неверный формат пароля " + password);
		}
	}

	public static class WrongLoginException extends Exception {
		public WrongLoginException(String message) {
			super(message);
		}
	}

	public static class WrongPasswordException extends Exception {
		public WrongPasswordException(String message) {
			super(message);
		}
	}
}
