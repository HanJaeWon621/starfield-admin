package kr.co.starfield.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {	
	private String Key = null;
	private String IV = null;
	
	public void setKey(String Key) {
		this.Key = Key;
	}
	
	public String getKey() {
		return Key;
	}
	
	public void setIV(String IV) {
		this.IV = IV;
	}
	
	public String getIV() {
		return IV;
	}
	
	public byte [] IC_AES_Encrypt(byte [] data) throws Exception  {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(Key.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		int padSize = 16 - (data.length % 16), i;
		byte [] padding = new byte [data.length + padSize];
		for(i = 0; i < padding.length; i++) {
			if(i < data.length) {
				padding[i] = data[i];
			} else {
				padding[i] = (byte)padSize;
			}
		}
		byte [] encrypted = cipher.doFinal(padding);
		return encrypted;
		
	}
	public byte [] IC_AES_Decrypt(byte [] enc) throws Exception  {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(Key.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		int padSize, i;
		byte [] decrypted = cipher.doFinal(enc);
		
		padSize = decrypted[decrypted.length - 1];
		byte [] ret = new byte [decrypted.length - padSize];
		for(i = 0; i < ret.length; i++) {
			ret[i] = decrypted[i];
		}
		return ret;
	}
	public String IC_AES_Base64_Encrypt(String data) throws Exception  {
		byte [] enc = IC_AES_Encrypt(data.getBytes());
		char [] encoded = Base64Coder.encode(enc);
		return new String(encoded);
	}
	public String IC_AES_Base64_Decrypt(String enc) throws Exception  {
		byte [] decoded = Base64Coder.decode(enc);
		byte [] data = IC_AES_Decrypt(decoded);
		return new String(data, "euc-kr");
	}
    
	public String exeCrypt(String key, String value, String type) throws Exception {
		Key = key;
		IV  = key;
		
		if(type.equals("E")) {
			String Encrypt = IC_AES_Base64_Encrypt(value);
			//System.out.println("Encrypt : ["+Encrypt+"]");
			return Encrypt;
		}
		else {
			String Decrypt = IC_AES_Base64_Decrypt(value);
			//System.out.println("Decrypt : ["+Decrypt+"]");
			return Decrypt;
		}
	}
}