/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.tools.nodetool;

import static com.google.common.base.Preconditions.checkArgument;
import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.tools.NodeProbe;
import org.apache.cassandra.tools.NodeTool.NodeToolCmd;

@Command(name = "refresh", description = "Load newly placed SSTables to the system without restart")
public class Refresh extends NodeToolCmd
{
    @Arguments(usage = "<keyspace> <table>", description = "The keyspace and table name")
    private List<String> args = new ArrayList<>();

    @Option(title = "load-and-stream",
    name = {"-las", "--load-and-stream"},
    description = "Allows loading sstables that do not belong to this node, in which case they are automatically streamed to the owning nodes.")
    private boolean isLoadAndStream = false;

    @Option(title = "primary-replica-only",
    name = {"-pro", "--primary-replica-only"},
    description = "Load the sstables and stream to primary replica node that owns the data. Repair is needed after the load and stream process.")
    private boolean isPrimaryReplicaOnly= false;

    @Override
    public void execute(NodeProbe probe)
    {
        checkArgument(args.size() == 2, "refresh requires ks and cf args");
        probe.loadNewSSTables(args.get(0), args.get(1), isLoadAndStream, isPrimaryReplicaOnly);
    }
}
