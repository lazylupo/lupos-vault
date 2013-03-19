package webclient;

import java.io.UnsupportedEncodingException;

/**
 * The class UTF8Encoder encodes any String into an UTF-8 String
 * @author Wolfgang Ostermeier
 *
 */
public class UTF8Encoder {
	
	/**
	 * The method encodes any String into an UTF-8 String
	 * @param utf8String the String to encode in UTF-8
	 * @return the UTF-8 encoded String
	 * @throws UnsupportedEncodingException
	 */
	public static final String utf8Convert(String utf8String) throws UnsupportedEncodingException {
		
	byte[] bytes = new byte[utf8String.length()];
	
		for (int i = 0; i < utf8String.length(); i++) {
		bytes[i] = (byte) utf8String.charAt(i);
		}
	return new String(bytes, "UTF-8");
	}

}
