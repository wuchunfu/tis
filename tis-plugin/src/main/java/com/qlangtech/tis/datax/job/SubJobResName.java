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

package com.qlangtech.tis.datax.job;

/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2024-03-11 12:37
 **/
public abstract class SubJobResName<T> extends JobResName<T> {

    private final SubJobExec<T> jobExec;

    protected SubJobResName(String name, SubJobExec<T> jobExec) {
        super(name);
        this.jobExec = jobExec;
    }

    @Override
    protected void execute(SSERunnable sse, T t) throws JobOrchestrateException {
        jobExec.accept(t);
    }
}
