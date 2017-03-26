package com.zk.op;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCacheManager {

	private static final Map<String, Zk> clientCache = new ConcurrentHashMap<>();

	public static Zk getZk(String cxnString) {
		Zk zk = clientCache.get(cxnString);
		if (zk == null)
			zk = new Zk(cxnString);
		clientCache.put(cxnString, zk);
		return zk;
	}

}
