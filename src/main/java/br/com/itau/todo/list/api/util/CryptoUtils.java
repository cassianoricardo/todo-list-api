package br.com.itau.todo.list.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoUtils {

    public static String encrypt(String password){
      return new BCryptPasswordEncoder().encode(password);
    }

    public static void main(String[] args) {
        System.out.println("password encrypt: "+ encrypt("123"));
    }
}
