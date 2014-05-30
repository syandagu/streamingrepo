/**
 * 
 */
package com.etrade.streaming.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * @author syandagu
 * 
 */
public class ETradeSortedProperties<T, E> extends Properties implements
		Comparator<T> {

	private static final long serialVersionUID = -294969808020736477L;

	public ETradeSortedProperties() {
		super();
	}

	public ETradeSortedProperties(Properties pProp) {
		super.putAll(pProp);
	}

	@Override
	public synchronized Enumeration keys() {
		Enumeration<T> propKeys = (Enumeration<T>) super.keys();
		PropertiesList<T> keyList = new PropertiesList<T>();
		for (; propKeys.hasMoreElements();) {
			keyList.add(propKeys.nextElement());
		}
		Collections.sort(keyList, this);
		return keyList.elements();
	}
	
	
	@Override
	public int compare(T key1, T key2) {
		
		if (key1 instanceof String) {
			return ((String) key1).compareTo(key2.toString());
		} else if (key1 instanceof Comparable) {
			return ((Comparable) key1).compareTo((Comparable) key2);
		} else if (key1 instanceof Comparator) {
			return ((Comparator) key1).compare(key1, key2);
		}
		return 0;
	}
	
	/**
	 * PropertiesList class is list implementation and it's subclass of 
	 * ArrayList. It has element method which will return Enumeration instance
	 * @author syandagu
	 * @param <E>
	 */
	private class PropertiesList<E> extends ArrayList<E>{

		/**
		 * 
		 */
		private static final long serialVersionUID = -294969808020736477L;
		
		public Enumeration<E> elements() {
	        return new Enumeration<E>() {
	            int count = 0,elementCount = size();;
	            public boolean hasMoreElements() {
	                return count < elementCount;
	            }

	            public E nextElement() {
	                synchronized (PropertiesList.this) {
	                    if (count < elementCount) {
	                        return elementData(count++);
	                    }
	                }
	                throw new NoSuchElementException("PropertiesList Enumeration");
	            }
	        };
	    }
		
		E elementData(int index) {
		        return (E) get(index);
		 }
				
	}

}
