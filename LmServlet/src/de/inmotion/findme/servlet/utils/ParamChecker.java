package de.inmotion.findme.servlet.utils;

/**
 * Hilfsklasse, die Operationen zum Validerung der Paramenter anbietet.
 * @author Tobias Wochinger
 *
 */
public final class ParamChecker {
	
	private ParamChecker() {
		//STATIC, should not be created
	}
	
	/**
	 * Checkt den Parameter auf null.
	 * @param param Zu ueberpruefender Parameter
	 * @return <code>true</code> falls null, sonst <code>false</code>
	 */
	public static boolean isParamNull(final Object param) {
		return param == null;
	}
}
