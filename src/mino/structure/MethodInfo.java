/* This file is part of Mino.
 *
 * See the NOTICE file distributed with this work for copyright information.
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
 */

package mino.structure;

import java.util.*;

import mino.exception.*;
import mino.language_mino.*;
import mino.walker.*;

public abstract class MethodInfo {

    private final MethodTable methodTable;

    private final LinkedList<VariableInfo> params = new LinkedList<>();

    private final LinkedList<String> paramNames = new LinkedList<String>();

    private final NClassName returnParam;

    MethodInfo(
            MethodTable methodTable,
            Map<NId, NClassName> params,
            NClassName returnParam) {

        this.methodTable = methodTable;
        this.returnParam = returnParam;

        Set<String> paramNameSet = new LinkedHashSet<String>();

        for (Map.Entry<NId, NClassName> id : params.entrySet()) {
            String name = id.getKey().getText();
            if (paramNameSet.contains(name)) {
                throw new InterpreterException("duplicate parameter " + name,
                        id.getKey());
            }
            paramNameSet.add(name);
            this.paramNames.add(name);
            this.params.add(new VariableInfo(name, id.getValue(), id.getKey()));
        }
    }

    public abstract String getName();

    public String getParamName(
            int i) {

        return this.paramNames.get(i);
    }

    public int getParamCount() {

        return this.paramNames.size();
    }

    public abstract void execute(
            InterpreterEngine interpreterEngine);

    public abstract void analyse(
            SemanticAnalysis semanticAnalysis);

    public ClassInfo getClassInfo() {

        return this.methodTable.getClassInfo();
    }

    public LinkedList<VariableInfo> getParams(){

        return this.params;
    }

    public NClassName getClassReturnParam(){

        return this.returnParam;
    }
}
