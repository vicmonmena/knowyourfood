package es.vicmonmena.knowyourfoodwriter.domain;

/**
 * 
 * @author vicmonmena
 *
 */
public class Food {

	private String name;
	private String brand;
	private String ingredients;
	private boolean lactosa;
	private boolean gluten;
	private boolean face;
	
	/**
	 * Alimento vacío
	 */
	public Food() {
	}

	/**
	 * Alimento con los datos pasados por parámetro.
	 * @param name
	 * @param brand
	 * @param ingredients
	 * @param lactosa
	 * @param gluten
	 * @param face
	 */
	public Food(String name, String brand, String ingredients, boolean lactosa,
			boolean gluten, boolean face) {
		super();
		this.name = name;
		this.brand = brand;
		this.ingredients = ingredients;
		this.lactosa = lactosa;
		this.gluten = gluten;
		this.face = face;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the ingredients
	 */
	public String getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return the lactosa
	 */
	public boolean isLactosa() {
		return lactosa;
	}

	/**
	 * @param lactosa the lactosa to set
	 */
	public void setLactosa(boolean lactosa) {
		this.lactosa = lactosa;
	}

	/**
	 * @return the gluten
	 */
	public boolean isGluten() {
		return gluten;
	}

	/**
	 * @param gluten the gluten to set
	 */
	public void setGluten(boolean gluten) {
		this.gluten = gluten;
	}

	/**
	 * @return the face
	 */
	public boolean isFace() {
		return face;
	}

	/**
	 * @param face the face to set
	 */
	public void setFace(boolean face) {
		this.face = face;
	}
}
