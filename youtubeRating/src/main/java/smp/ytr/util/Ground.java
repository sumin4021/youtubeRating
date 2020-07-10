package smp.ytr.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Ground {

	public static void main(String[] args) {
		String plain_str = "Tmfrltnals2";
		System.out.println("plain_string: " + plain_str);

		String sha256hex_str = DigestUtils.sha256Hex(plain_str);
		// SHA-256 digest as a hex(4 bits per char) string ==> CHAR(64)
		System.out.println("sha256hex_string: " + sha256hex_str);

		assert sha256hex_str.length() == 64 : "CHAR(64)로 변환되어야 합니다.";
		

	}
}
