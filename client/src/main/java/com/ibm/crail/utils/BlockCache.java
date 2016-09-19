/*
 * Crail: A Multi-tiered Distributed Direct Access File System
 *
 * Author: Patrick Stuedi <stu@zurich.ibm.com>
 *
 * Copyright (C) 2016, IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ibm.crail.utils;

import java.util.concurrent.ConcurrentHashMap;
import com.ibm.crail.namenode.protocol.BlockInfo;

public class BlockCache {
	private ConcurrentHashMap<Long, FileBlockCache> blockCache;
	
	public BlockCache(){
		this.blockCache = new ConcurrentHashMap<Long, FileBlockCache>();  
	}
	
	public FileBlockCache getFileBlockCache(long fd){
		FileBlockCache fileBlockCache = blockCache.get(fd);
		if (fileBlockCache == null){
			fileBlockCache = new FileBlockCache(fd);
			FileBlockCache oldFileBlockCache = blockCache.putIfAbsent(fd, fileBlockCache);
			if (oldFileBlockCache != null){
				fileBlockCache = oldFileBlockCache;
			}
		}
		return fileBlockCache;
	}
	
	public void remove(long fd) {
		blockCache.remove(fd);
	}	
	
	public static class FileBlockCache {
		private long fd;
		private ConcurrentHashMap<String, BlockInfo> fileBlockCache;
		
		public FileBlockCache(long fd){
			this.fd = fd;
			this.fileBlockCache = new ConcurrentHashMap<String, BlockInfo>();
		}

		public void put(String key, BlockInfo block){
			this.fileBlockCache.put(key, block);
		}
		
		public BlockInfo get(String key){
			return this.fileBlockCache.get(key);
		}

		public boolean containsKey(String key) {
			return this.fileBlockCache.containsKey(key);
		}

		public long getFd() {
			return fd;
		}
	}
}