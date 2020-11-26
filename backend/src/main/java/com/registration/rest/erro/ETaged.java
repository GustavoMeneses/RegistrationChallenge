package com.registration.rest.erro;

import java.math.BigInteger;

/**
 * Objetos que tem um número (hash) indicando o estado dele.
 */
public interface ETaged {
	
	/**
	 * Um número grande o suficiente para mesmo assomado do menor e do maior {@link Integer} sempre vai ter a mesma quantidade de dígitos.
	 */
    BigInteger BASE = new BigInteger(Integer.toString(Integer.MIN_VALUE)).abs().multiply(new BigInteger("2")).add(new BigInteger("10").pow(10));
	
	/**
	 * Uma {@link String}, provavelmente apenas numérica, que indica o estado do objeto.
	 * @return Não pode ser null
	 */
	default String etag() {
		return BASE.add(new BigInteger(Integer.toString(this.hashCode()))).toString();
	}

}
