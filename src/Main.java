import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String path = "C:\\Users\\Nescara\\Desktop";
		int maxFileSize = 80;
	
		try {
			CSVFactory factory = new CSVFactory(path,
					"Marca__c", 
					"Dia__c",
					"MesAno__c",
					"Cliente__c",
					"Industria__c",
					"GMR_Obj_Mes__c",
					"GMR_Real__c",
					"GMR_Porc__c",
					"GMR_Obj_Acum__c",
					"GMR_Obj_Parcial__c",
					"GMR_Real_Acum__c",
					"GMR_Obj_Real_Porc__c",
					"GMR_Desvio__c",
					"CF_Obj_Mes__c",
					"CF_Obj_Parcial__c",
					"CF_Real__c",
					"CF_Porc__c",
					"CF_Obj_Acum__c",
					"CF_Real_Acum__c",
					"CF_Obj_Real_Porc__c",
					"CF_Desvio__c",
					"OL_Obj_Mes__c",
					"OL_Real__c",
					"OL_Porc__c",
					"OL_Obj_Acum__c",
					"OL_Obj_Parcial__c",
					"OL_Real_Acum__c",
					"OL_Obj_Real_Porc__c",
					"OL_Desvio__c",
					"OFL_Obj_Mes__c",
					"OFL_Real__c",
					"OFL_Porc__c",
					"OFL_Obj_Acum__c",
					"OFL_Obj_Parcial__c",
					"OFL_Real_Acum__c",
					"OFL_Obj_Real_Porc__c",
					"OFL_Desvio__c",
					"CAT_OTC__c",
					"CAT_Gen__c",
					"CAT_HB__c",
					"CAT_RX__c",
					"CAT_Dermo__c",
					"CAT_Nutricao__c",
					"CAT_Diabetes__c",
					"CAT_Conveniencia__c",
					"CAPT_TLV__c",
					"CAPT_PE__c"
			);
			
			for(int i = 0; i < 10000000; i++) {
				System.out.println("loading... " + i);
				
				factory.addRecord(
						"SC", 
						getRandom(100),
						"jun-19",
						"A" + getRandom(1000),
						"Chiesi",
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(10),
						getRandom(10),
						getRandom(10),
						getRandom(100),
						getRandom(1000),
						getRandom(1000),
						getRandom(1000),
						getRandom(1000),
						getRandom(100),
						getRandom(100),
						getRandom(10),
						getRandom(100),
						getRandom(10),
						getRandom(10),
						getRandom(1000),
						getRandom(100),
						getRandom(1000000),
						getRandom(100),
						getRandom(100),
						getRandom(10),
						getRandom(10),
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(10),
						getRandom(10),
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(100),
						getRandom(10),
						getRandom(10),
						getRandom(10),
						getRandom(10),
						getRandom(100),
						getRandom(1000),
						getRandom(100),
						getRandom(10),
						getRandom(10),
						getRandom(10)
					);
				
				if(factory.getFileSize() >= maxFileSize*1024*1024)
					factory.newFile();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getRandom(int num) {
		return String.valueOf((int)(Math.random() * num));
	}
}
