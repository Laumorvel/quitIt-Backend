package com.example.demo.model;

import java.util.Comparator;

public class OrdenarPorNumero implements Comparator<User>{
	
	/**
	 * Comparador de los dias que lleva el usuario sin fumar, ordenandola de forma descendencte según los dias que llevan sin fumar
	 */
	@Override
	public int compare(User o1, User o2) {
		int resultado;

		if(o1.getDaysInARowWithoutSmoking()>o2.getDaysInARowWithoutSmoking()) {
			resultado=-1;
		}
		else if(o1.getDaysInARowWithoutSmoking()<o2.getDaysInARowWithoutSmoking()) {
			resultado=1;
		}
		else {
			resultado=0;
		}

		return resultado;
	}

}
