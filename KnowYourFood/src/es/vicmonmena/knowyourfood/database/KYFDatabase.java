package es.vicmonmena.knowyourfood.database;

/**
 * 
 * @author vicmonmena
 *
 */
public interface KYFDatabase {

	/**
	 * Nombre de la base de datos.
	 */
	public static final String DATABASE_NAME = "knowyourfood.db";
	/**
	 * Versión de la base de datos.
	 */
	public static int DATABASE_VERSION = 1;
	/**
	 * Nombre de la tabla en la base de datos que almacena los Alimentos.
	 */
	public final String FOOD_TABLE_NAME = "tfood";
	/**
	 * Campo _id de la tabla Food de la base de datos.
	 */
	public final String _ID = "_id";
	/**
	 * Campo nombre de la tabla Food de la base de datos.
	 */
	public final String NAME = "sname";
	/**
	 * Campo marca de la tabla Food de la base de datos.
	 */
	public final String BRAND = "sbrand";
	/**
	 * Campo ingredientes de la tabla Food de la base de datos.
	 */
	public final String INGREDIENTS = "singredients";
	/**
	 * Campo LACTOSA de la tabla Food de la base de datos.
	 */
	public final String LACTOSA = "blactosa";
	/**
	 * Campo GLUTEN de la tabla Food de la base de datos.
	 */
	public final String GLUTEN = "bgluten";
	/**
	 * Campo FACE de la tabla Food de la base de datos.
	 */
	public final String FACE = "bface";
	/**
	 * Campos que obtenemos para mostrar en un listado de alimentos.
	 */
	public static final String[] PROJECTION = {_ID, NAME, BRAND};
}
