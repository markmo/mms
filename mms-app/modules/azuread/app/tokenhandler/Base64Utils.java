package org.sampleapp.tokenhandler;

import java.io.UnsupportedEncodingException;

import org.sampleapp.exceptions.SampleAppException;
import org.sampleapp.services.AppParameter;

import com.sun.org.apache.xml.internal.security.utils.Base64;


/**
 * Helper methods for encoding and decoding strings to base64.
 * @author t-mislam
 *
 */
public class Base64Utils {
	/**
	 * Url encoding: + becomes -
	 */
	private static final char base64Character62 = '+';
	
	/**
	 * Url encoding: / becomes _
	 */
	private static final char base64Character63 = '/';
	
	/**
	 * Url encoding: + becomes -
	 */
	private static final char base64UrlCharacter62 = '-';
	
	/**
	 * Url encoding: / becomes _
	 */
	private static final char base64UrlCharacter63 = '_';
	
	
	
	/**
	 * The method that converts an input String to an equivalent
	 * url safe base64 encoded String.
	 * @param arg The Input String.
	 * @return A url safe base64 encoded string.
	 * @throws SampleAppException If the String can not be encoded by the encoding specified.
	 */
	public static String encode(String arg) throws SampleAppException{
		try {
			return Base64Utils.encode(arg.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			throw new SampleAppException(AppParameter.ErrorGeneratingToken, AppParameter.ErrorGeneratingTokenMessage, e);
		}
	}



	/**
	 * Encodes the byte array.
	 * @param Input byte array.
	 * @return Encoded String in base64.
	 */
	public static String encode(byte[] arg) {
		String text = new String(Base64.encode(arg));
		text=text.split("=")[0];
		text.replace(base64Character62, base64UrlCharacter62);
		text.replace(base64Character63, base64UrlCharacter63);	
		return text;
	}
	
}
