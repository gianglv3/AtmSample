package com.ipos.restaurant.util;

import com.ipos.restaurant.model.DmAtm;

import java.util.Comparator;


public class SortHelper {

	
	public static Comparator<DmAtm> sorDistance = new Comparator<DmAtm>() {

		@Override
		public int compare(DmAtm lhs, DmAtm rhs) {
			
			double distance1 = lhs.getDistance();
			double distance2 = rhs.getDistance();
			  if (distance1 < distance2) return -1;
		        if (distance1 > distance2) return 1;
		        return 0;

		}
	};
	

}
