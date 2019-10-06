/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;

/**
 *
 * @author pawel
 */
public class ConvertHexBin extends javax.swing.JButton implements Serializable {
	
	/**
	 * 
	 */
	
	
	private String hex ;
	private String bin ;
        private String title;
        private String etykieta;
	
	public ConvertHexBin() {
		super();
		
	}

	 public static char[] convertHexBin(String hex) 
	{
		String value = "";
		Object tekst = "Podano niewłaściwą wartość. Popraw wpis w polu HEX.";
		try {
			
		int Hex = Integer.parseInt(hex, 16);
		value = Integer.toBinaryString(Hex);
		
		} catch (Exception e) {
			
			javax.swing.JOptionPane.showMessageDialog(null,  tekst,  "zły wpis", 0);
			
		}	
		char[] charArray = value.toCharArray();
		
		System.out.println(charArray);
		return charArray;
	}

	 public static String convertBinHex(String bin) 
	{
		String value = "";
		Object tekst = "Podano niewłaściwą wartość. Popraw wpis w polu BIN.";
		try {
		
		int Bin = Integer.parseInt(bin, 2);
		value = Integer.toHexString(Bin).toUpperCase();
		
		} catch (Exception e) {
			
			javax.swing.JOptionPane.showMessageDialog(null,  tekst,  "zły wpis", 0);
		}	
				
		return value;
	}

	public String getHex() {
		return hex;
	}


	public void setHex(String hex) {
		this.hex = hex;
	}
        
        public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
        
        public String getEtykieta() {
		return etykieta;
	}


	public void setEtykieta(String etykieta) {
		this.etykieta = etykieta;
	}


	public String getBin() {
		return bin;
	}


	public void setBin(String bin) {
		this.bin = bin;
	}

       
	
	
}
