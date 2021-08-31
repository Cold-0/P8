package com.cold0.realestatemanager;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

	/**
	 * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 *
	 * @param dollars
	 * @return
	 */
	public static int convertDollarToEuro(int dollars) {
		return (int) Math.round(dollars * 0.8467188);
	}

	public static int convertEuroToDollars(int euros) {
		return (int) Math.round(euros * 1.18103);
	}

	/**
	 * Conversion de la date d'aujourd'hui en un format plus approprié
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 *
	 * @return
	 */
	public static String getTodayDateOld() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(new Date());
	}

	/**
	 * Conversion de la date d'aujourd'hui en un format plus approprié
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 *
	 * @return
	 */
	public static String getTodayDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(new Date());
	}

	/**
	 * Vérification de la connexion réseau
	 * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
	 *
	 * @return
	 */
	public boolean isInternetAvailable() {
		InetAddress address = null;
		try {
			address = InetAddress.getByName("www.google.com");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if (address == null)
			return false;

		return !address.toString().equals("");
	}
}