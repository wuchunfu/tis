/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qlangtech.tis.plugin;

import com.qlangtech.tis.coredefine.module.action.TargetResName;
import com.qlangtech.tis.fullbuild.phasestatus.PhaseStatusCollection;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2024-03-04 09:05
 **/
public class PluginAndCfgSnapshotLocalCache {
    private final Map<String, PluginAndCfgsSnapshot> localCache;
    /**
     * 当前task对应的前一次执行的状态缓存，为这次全量构建，例如allrows 提供百分比支持
     */
    private final Map<Integer, PhaseStatusCollection> taskPreviousStatusCache = new WeakHashMap<>();

    public PluginAndCfgSnapshotLocalCache() {
        this.localCache = new ConcurrentHashMap<>();
    }

    public PhaseStatusCollection getPreviousStatus(Integer currentTaskId, Supplier<PhaseStatusCollection> statSupplier) {
        synchronized (taskPreviousStatusCache) {
            PhaseStatusCollection status = taskPreviousStatusCache.get(currentTaskId);
            if (status == null) {
                status = statSupplier.get();
                if (status != null) {
                    taskPreviousStatusCache.put(currentTaskId, status);
                }
            }
            return status;
        }
    }

    /**
     * 取得和app相关的资源pluginCfgs快照
     *
     * @param appName
     * @return
     */
    public void processLocalCache(TargetResName appName
            , Function<Optional<PluginAndCfgsSnapshot>, PluginAndCfgsSnapshot> snapshotProcessor) {
        PluginAndCfgsSnapshot snapshot = snapshotProcessor.apply(Optional.ofNullable(this.localCache.get(appName.getName())));
        this.localCache.put(appName.getName(), snapshot);
    }
}
