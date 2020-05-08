package com.mz.lojavirtual.controllers.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerUtil {
	
	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Converte uma lista de numeros em string para uma lista de Inteiros
	 * @param stsNumbers - lista numerica em string - (ex: "1,2,3") 
	 * @return Uma lista do tipo Integer equivalente a lista em string informada
	 */
	public static List<Integer> decodeIntList(String stsNumbers) {
		return Arrays.asList(stsNumbers.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
