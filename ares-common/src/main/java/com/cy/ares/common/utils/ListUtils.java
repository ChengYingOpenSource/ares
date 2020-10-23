package com.cy.ares.common.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;



public class ListUtils {

	public static <K, T> ListMultimap<K, T> toMultimap(List<T> t1, Function<T, K> function) {

		ListMultimap<K, T> map = ArrayListMultimap.create();
		if (t1 == null) {
			return map;
		}

		for (T t : t1) {
			K k = function.apply(t);
			map.put(k, t);
		}

		return map;

	}


	/**
	 * list 中有一个为主键key 
	 * @param list
	 * @param function
	 * @return
	 */
	public static <K,V> Map<K, V> listToMap(List<V> list,Function<V, K> function){
		Map<K, V> map = new HashMap<K, V>();
		if(list == null) return map;
		for(V value:list){
			K k = function.apply(value);
			map.put(k, value);
		}
		return map;
	}
	
	/**
	 * list 元素转为另一个元素的list
	 */
	public static <F,R> List<R> listConvert(List<F> list,Function<F, R> function){
		List<R> listN = new ArrayList<R>(); 
		if(list == null) return listN;
		for(F value:list){
			R r = function.apply(value);
			if(r != null)
				listN.add(r);
		}
		return listN;
	}
	
	/**
	 * list 元素过滤另一个元素的list
	 */
	public static <F> List<F> filter(List<F> list,Function<F, Boolean> function){
		List<F> listN = new ArrayList<F>(); 
		if(list == null) return listN;
		for(F value:list){
			Boolean r = function.apply(value);
			if(r)
			  listN.add(value);
		}
		return listN;
	}
	
	
	
	/**
	 * list 元素转为另一个元素的list并且去重,适合list数据量较小
	 */
	public static <F,R> List<R> listConvertAndUnique(List<F> list,Function<F, R> function){
		List<R> listN = new ArrayList<R>(); 
		if(list==null) return listN;
		for(F value:list){
			R r = function.apply(value);
			if(r!=null && !listN.contains(r))
				listN.add(r);
		}
		return listN;
	}
	
	/**
	 * list 元素转为以seg分割的字符串
	 */
	public static String split(List list,String seg){
		StringBuilder sb = new StringBuilder();
		for(Object value:list){
			sb.append(value.toString()+seg);
		}
		String t = sb.toString();
		if(t.endsWith(seg)){
			int end = t.length()-seg.length();
			t = t.substring(0, end);
		}
		return t;
	}
	
	
	public static <V> Map<Integer, V> listToMapByHashCode(List<V> list){
		Map<Integer, V> map = new HashMap<Integer, V>();
		for(V value:list){
			map.put(Integer.valueOf(value.hashCode()), value);
		}
		return map;
	}
	
	
	public static <V> List<V> pageList(List<V> list,int pageIndex,int pageNum){
		int size = list.size();
		int fromIndex = pageIndex*pageNum;
		if(fromIndex > size){
			fromIndex = size;
		}
		int toIndex = fromIndex+pageNum;
		if(toIndex > size){
			toIndex = size;
			return copy(list,fromIndex, toIndex);
		}
		
		return copy(list,fromIndex, toIndex);
	}
	
	
	public static <V> List<V> copy(List<V> list,int fromIndex,int toIndex){
	    List<V> listN = new ArrayList<V>(); 
	    if(list == null || list.isEmpty()){
	        return listN;
	    }
	    for(int i = fromIndex;i<toIndex;i++){
	        listN.add(list.get(i));
	    }
	    return listN;
	    
	}
	
	public static <V> List<V> pageToEnd(List<V> list,int pageIndex,int pageNum){
	    
	    int size = list.size();
        int fromIndex = pageIndex*pageNum;
        if(fromIndex > size){
            fromIndex = size;
        }
        int toIndex = size;
        return copy(list,fromIndex, toIndex);
	    
	}
	
	public static <E> boolean isEmpty(List<E> list){
		
		return list == null || list.isEmpty();
	}
	
	public static <T> List<T> toList(T ... a){
		
		if(a == null) return null;
		List<T> l = new ArrayList<>();
		for(T e:a){
			l.add(e);
		}
		
		return l;
	}
	
	public static <T> List<T> toList(Set<T> setT){
        
        if(setT == null) return null;
        List<T> l = new ArrayList<>();
        for(T e:setT){
            l.add(e);
        }
        
        return l;
    }
	
	public static <T> boolean eq(List<T> t1,List<T> t2){
	    
	    if(t1 == null && t2 == null){
	        return true;
	    }
	    if(t1 == null || t2 == null){
            return false;
        }
	    
	    int size1 = t1.size();
	    int size2 = t2.size();
	    if(size1 != size2)
	        return false;
	    
	    for(int i = 0;i<size1;i++){
	        T tt1 = t1.get(i);
	        T tt2 = t2.get(i);
	        if(!tt1.equals(tt2)){
	            return false;
	        }
	    }
	    return true;
	    
	}
	
	

	
}
