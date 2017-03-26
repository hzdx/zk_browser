package com.zk.op;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.entity.ZkData;

public class Zk {
	private static final Logger LOGGER = LoggerFactory.getLogger(Zk.class);
	private ZkClient client;

	public boolean exists(String path) throws Exception {
		if (path == null || path.trim().equals("")) {
			throw new IllegalArgumentException("path can not be null or empty");
		}
		return getClient().exists(path);
	}

	public ZkData readData(String path) throws Exception {
		ZkData zkdata = new ZkData();
		Stat stat = new Stat();
		byte[] bytes = getClient().getData(getPath(path), stat);
		zkdata.setData(bytes);
		zkdata.setStat(stat);
		return zkdata;
	}

	public List<String> getChildren(String path) throws Exception {
		return getClient().getChildren(getPath(path));
	}

	public void create(String path, byte[] data) throws Exception {
		path = getPath(path);
		getClient().create(path, null);
		Stat stat = getClient().setData(path, data);
		LOGGER.info("create: node:{}, stat:{}:", path, stat);
	}

	public void edit(String path, byte[] data) throws Exception {
		path = getPath(path);
		Stat stat = getClient().setData(path, data);
		LOGGER.info("edit: node:{}, stat:{}:", path, stat);
	}

	public void delete(String path) throws Exception {
		path = getPath(path);
		boolean del = getClient().delete(path);
		LOGGER.info("delete: node:{}, boolean:{}:", path, del);
	}

	public void deleteRecursive(String path) throws Exception {
		path = getPath(path);
		List<String> childs = getChildren(path);
		if (childs != null) {
			for (String child : childs) {
				deleteRecursive(path + "/" + child);
			}
		}
		boolean deleteRecursive = getClient().delete(path);
		LOGGER.info("rmr: node:{}, success:{}:", path, deleteRecursive);
	}

	public Zk(String cxnString) {
		LOGGER.info("cxnString:{}", cxnString);
		this.client = new ZkClient(cxnString, 5000);

	}

	public ZkClient getClient() {
		return client;
	}

	public void setClient(ZkClient client) {
		this.client = client;
	}

	private String getPath(String path) {
		path = path == null ? "/" : path.trim();
		if (!StringUtils.startsWith(path, "/")) {
			path = "/" + path;
		}
		return path;
	}

}
