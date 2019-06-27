
public class DocumentsGenerator {
	
	public static String geraCpf() {  
	    String iniciais = "";  
	    Integer numero;  
	    for (int i = 0; i < 9; i++) {  
	        numero = (int) (Math.random() * 10);
	        iniciais += numero.toString();  
	    }  
	    return "'" + calcDigVerifCpf(iniciais);
	}
	
	public static String geraCnpj() {
		String iniciais = "";  
	    Integer numero;  
	    for (int i = 0; i < 12; i++) {  
	        numero = (int) (Math.random() * 10);
	        iniciais += numero.toString();  
	    }  
	    return "'" + calcDigVerifCnpj(iniciais);
	}
	
	private static String calcDigVerifCnpj(String cnpj) {
		int digit = 0;
		for(int i = 0, j = 5; i < cnpj.length(); i++) {
            digit += Integer.parseInt(cnpj.substring(i, i+1)) * j;
            
            if(j == 2) 
                j = 10;
            
            j--;
        }
		
        int resto = digit % 11;
        
        if (resto >= 2)
            digit = 11 - resto;
        else
            digit = 0;
                
        cnpj += digit % 11;
        
        digit = 0;

        for(int i = 0, j = 6; i < cnpj.length(); i++) {
            digit += Integer.parseInt(cnpj.substring(i, i+1)) * j;
            
            if(j == 2)
                j = 10;
            
            j--;
        }

        resto = digit % 11;

        if (resto >= 2)
            digit = 11 - resto;
        else
            digit = 0;
        
        cnpj += digit;
        
		return cnpj;
	}

	private static String calcDigVerifCpf(String nums) {
		int sum = acmCpf(nums);
		
		int digit;
		
		digit = (sum*10) % 11;
		
		if(digit == 10)
			digit = 0;
		
		nums += digit;
		
		sum = acmCpf(nums);
		
		digit = (sum*10) % 11;
		
		if(digit == 10)
			digit = 0;
		
		return nums + digit;
	}
	
	private static int acmCpf(String str) {
		int acm = 0;
		
		for(int i = 0, j = str.length() + 1; i < str.length(); i++, j--) {
			acm += Integer.parseInt(String.valueOf(str.charAt(i))) * j;
		}
				
		return acm;
	}
}
