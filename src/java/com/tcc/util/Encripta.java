/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author paulo
 */
public class Encripta {
    public static String criptografaSenha(String senha) throws NoSuchAlgorithmException {
        String s = "";
        if (senha != null) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            s = hash.toString(16);
            if (s.length() % 2 != 0) {
                s = "0" + s;
            }
        }
        return s;
    }
}
