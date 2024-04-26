package com.paula.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Encriptation {
    public static String encriptPassword(String newPassword) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String newPasswordEncripted = passwordEncryptor.encryptPassword(newPassword);
		return newPasswordEncripted;
	}
	
	public static boolean validatePassword(String password, String correctPasswordEncripted) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		
		if (passwordEncryptor.checkPassword(password, correctPasswordEncripted)) {
			return true;
		} else {
			return false;
		}
	}
}
