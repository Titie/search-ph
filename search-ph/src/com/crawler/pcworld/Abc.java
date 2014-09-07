package com.crawler.pcworld;


public class Abc {
	
	
	public static void main(String[] args) {
		String response = "Jets_NYJ_New York Jets_000003139_00-0030639_NOELIASID_NOFOOTBALLNAME_Jeremy_Reeves_J.Reeves_41_DB_P, ";
		String inputted = "j.r.";
		System.out.println(checkContainsData(response, inputted));

	}
	
	/**
	 * This method will check data in the response contains inputted data.
	 * @param response
	 * @param inputted
	 * @return
	 */
	public static boolean checkContainsData(String response, String inputted) {
		String[] inputtedArrays = inputted.split("\\.");
		for (String search : inputtedArrays) {
			System.out.println("inputted: " + search);
			if (response.toUpperCase().contains(search.trim().toUpperCase())) return true;
		}
		return false;
	}
	
	
	private Boolean runNegative;

	/**
	 * @return the runNegative
	 */
	public Boolean getRunNegative() {
		return runNegative;
	}

	/**
	 * @param runNegative the runNegative to set
	 */
	public void setRunNegative(Boolean runNegative) {
		this.runNegative = runNegative;
	}
	
	

}
