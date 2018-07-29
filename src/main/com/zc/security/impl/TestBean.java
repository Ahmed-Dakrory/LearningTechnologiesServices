/**
 * 
 */
package main.com.zc.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import main.com.zc.shared.presentation.dto.BaseDTO;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * @author Omnya Alaa
 *
 */

public class TestBean {
public static void main(String[]args){
	Map<Integer, List<BaseDTO>> subs = new HashMap<Integer, List<BaseDTO>>();

	List<BaseDTO> list=new ArrayList<BaseDTO>();
	list.add(new BaseDTO(1, "1"));
	list.add(new BaseDTO(1, "2"));
	list.add(new BaseDTO(3, "3"));
	list.add(new BaseDTO(4, "4"));
	list.add(new BaseDTO(4, "5"));
	// iterate through your objects
	for(BaseDTO o : list){

	    // fetch the list for this object's id
	    List<BaseDTO> temp = subs.get(o.getId());

	    if(temp == null){
	        // if the list is null we haven't seen an
	        // object with this id before, so create 
	        // a new list
	        temp = new ArrayList<BaseDTO>();

	        // and add it to the map
	        subs.put(o.getId(), temp);
	    }

	    // whether we got the list from the map
	    // or made a new one we need to add our
	    // object.
	    temp.add(o);
	}
}
}


