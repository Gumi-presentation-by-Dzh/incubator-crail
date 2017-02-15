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

package com.ibm.crail.core;

import java.util.concurrent.atomic.AtomicLong;

import com.ibm.crail.CrailStatistics;

public class CoreIOStatistics implements CrailStatistics.StatisticsProvider {
	private String mode;
	private AtomicLong totalOps;
	private AtomicLong localOps;
	private AtomicLong remoteOps;
	private AtomicLong localDirOps;
	private AtomicLong remoteDirOps;
	private AtomicLong cachedOps;
	private AtomicLong nonblockingOps;
	private AtomicLong blockingOps;
	private AtomicLong prefetchedOps;
	private AtomicLong prefetchedBlockingOps;
	private AtomicLong prefetchedNonblockingOps;
	private AtomicLong opLen;
	private AtomicLong capacity;
	private AtomicLong totalStreams;
	private AtomicLong totalSeeks;
	
	public CoreIOStatistics(String mode){
		this.mode = mode;
		this.totalOps = new AtomicLong(0);
		this.localOps = new AtomicLong(0);
		this.remoteOps = new AtomicLong(0);
		this.localDirOps = new AtomicLong(0);
		this.remoteDirOps = new AtomicLong(0);
		this.cachedOps = new AtomicLong(0);
		this.nonblockingOps = new AtomicLong(0);
		this.blockingOps = new AtomicLong(0);
		this.prefetchedOps = new AtomicLong(0);
		this.prefetchedBlockingOps = new AtomicLong(0);
		this.prefetchedNonblockingOps = new AtomicLong(0);
		this.opLen = new AtomicLong(0);
		this.capacity = new AtomicLong(0);
		this.totalStreams = new AtomicLong(0);
		this.totalSeeks = new AtomicLong(0);
		reset();
	}
	
	@Override
	public String providerName() {
		return "IOStatistics, " + mode;
	}

	@Override
	public String printStatistics() {
		return ", total " + getTotalOps() + ", localOps " + getLocalOps() + ", remoteOps " + getRemoteOps() + ", localDirOps " + getLocalDirOps() + ", remoteDirOps " + getRemoteDirOps() + 
		", cached " + getCachedOps() + ", nonBlocking " + getNonblockingOps() + ", blocking " + getBlockingOps() +
		", prefetched " + getPrefetchedOps() + ", prefetchedNonBlocking " + getPrefetchedNonblockingOps() + ", prefetchedBlocking " + getPrefetchedBlockingOps() +
		", capacity " + getCapacity() + ", totalStreams " + getTotalStreams() + ", avgCapacity " + getAvgCapacity() +
		", avgOpLen " + getAvgOpLen();
	}	
	
	public void reset(){
		this.totalOps.set(0);
		this.localOps.set(0);
		this.remoteOps.set(0);
		this.localDirOps.set(0);
		this.remoteDirOps.set(0);
		this.cachedOps.set(0);
		this.nonblockingOps.set(0);
		this.blockingOps.set(0);
		this.prefetchedOps.set(0);
		this.prefetchedBlockingOps.set(0);
		this.prefetchedNonblockingOps.set(0);
		this.opLen.set(0);
		this.capacity.set(0);
		this.totalStreams.set(0);
		this.totalSeeks.set(0);
	}
	
	public long getTotalOps() {
		return totalOps.get();
	}
	
	void incTotalOps(long opLen) {
		this.totalOps.incrementAndGet();
		this.opLen.addAndGet(opLen);
	}
	
	public long getLocalOps(){
		return localOps.get();
	}
	
	public void incLocalOps() {
		localOps.incrementAndGet();
	}	
	
	public long getRemoteOps(){
		return remoteOps.get();
	}
	
	public void incRemoteOps() {
		remoteOps.incrementAndGet();
	}	
	
	public long getLocalDirOps(){
		return localDirOps.get();
	}
	
	public void incLocalDirOps() {
		localDirOps.incrementAndGet();
	}	
	
	public long getRemoteDirOps(){
		return remoteDirOps.get();
	}
	
	public void incRemoteDirOps() {
		remoteDirOps.incrementAndGet();
	}	
	
	public long getCachedOps() {
		return cachedOps.get();
	}
	
	void incCachedOps() {
		this.cachedOps.incrementAndGet();
	}

	public long getNonblockingOps() {
		return nonblockingOps.get();
	}
	
	void incNonblockingOps() {
		this.nonblockingOps.incrementAndGet();
	}	
	
	public long getBlockingOps() {
		return blockingOps.get();
	}
	
	void incBlockingOps() {
		this.blockingOps.incrementAndGet();
	}
	
	public long getPrefetchedOps() {
		return prefetchedOps.get();
	}
	
	void incPrefetchedOps() {
		this.prefetchedOps.incrementAndGet();
	}	
	
	public long getPrefetchedBlockingOps() {
		return prefetchedBlockingOps.get();
	}
	
	void incPrefetchedBlockingOps() {
		this.prefetchedBlockingOps.incrementAndGet();
	}	
	
	public long getPrefetchedNonblockingOps() {
		return prefetchedNonblockingOps.get();
	}
	
	void incPrefetchedNonblockingOps() {
		this.prefetchedNonblockingOps.incrementAndGet();
	}	
	
	public long getOpLen() {
		return opLen.get();
	}
	
	public long getAvgOpLen(){
		long _avg = 0;
		long _totalOps = totalOps.get();
		long _opLen = opLen.get();
		if (_totalOps > 0){
			_avg = _opLen / _totalOps;
		}
		return _avg;
	}
	
	public void setCapacity(long capacity){
		this.capacity.set(capacity);
	}
	
	public long getCapacity(){
		return capacity.get();
	}
	
	public long getAvgCapacity(){
		long _avg = 0;
		long _capacity = capacity.get();
		long _totalStreams = totalStreams.get();
		if (_totalStreams > 0){
			_avg = _capacity / _totalStreams;
		}
		return _avg;
	}
	
	public long getTotalStreams(){
		return totalStreams.get();
	}
	
	public long getTotalSeeks() {
		return totalSeeks.get();
	}
	
	void incTotalSeekds() {
		this.totalSeeks.incrementAndGet();
	}	

	void add(CoreIOStatistics coreStatistics) {
		totalOps.addAndGet(coreStatistics.getTotalOps());
		localOps.addAndGet(coreStatistics.getLocalOps());
		remoteOps.addAndGet(coreStatistics.getRemoteOps());
		localDirOps.addAndGet(coreStatistics.getLocalDirOps());
		remoteDirOps.addAndGet(coreStatistics.getRemoteDirOps());
		cachedOps.addAndGet(coreStatistics.getCachedOps());
		nonblockingOps.addAndGet(coreStatistics.getNonblockingOps());
		blockingOps.addAndGet(coreStatistics.getBlockingOps());
		prefetchedOps.addAndGet(coreStatistics.getPrefetchedOps());
		prefetchedNonblockingOps.addAndGet(coreStatistics.getPrefetchedNonblockingOps());
		prefetchedBlockingOps.addAndGet(coreStatistics.getPrefetchedBlockingOps());
		opLen.addAndGet(coreStatistics.getOpLen());
		capacity.addAndGet(coreStatistics.getCapacity());
		totalSeeks.addAndGet(coreStatistics.getTotalSeeks());
		totalStreams.incrementAndGet();
	}
}
