package net.etfbl.main.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import net.etfbl.main.dao.DangerCategoryDAO;
import net.etfbl.main.dao.DangerDAO;
import net.etfbl.main.dto.Danger;
import net.etfbl.main.dto.DangerCategory;

public class DangerBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	public List<Danger> getAll(){
		
		List<Danger> dangers=DangerDAO.getAll();
		Collections.reverse(dangers);
		return dangers;
	}
	
	public List<DangerCategory> getDangerCategories(){
		return DangerCategoryDAO.getAll();
	}

}
