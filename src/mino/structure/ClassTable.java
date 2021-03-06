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

public class ClassTable {

    private final Map<String, ClassInfo> nameToClassInfoMap = new LinkedHashMap<String, ClassInfo>();

    public ClassInfo add(
            NClassdef definition) {

        Token nameToken = definition.get_ClassName();
        String name = nameToken.getText();

        if (this.nameToClassInfoMap.containsKey(name)) {
            throw new InterpreterException("duplicate definition of class "
                    + name, nameToken);
        }

        ClassInfo classInfo;
        if (name.equals("Boolean")) {
            classInfo = new BooleanClassInfo(this, definition);
        }
        else if (name.equals("Integer")) {
            classInfo = new IntegerClassInfo(this, definition);
        }
        else if (name.equals("String")) {
            classInfo = new StringClassInfo(this, definition);
        }else if(name.equals("Float")){
            classInfo = new FloatClassInfo(this, definition);
        }
        else {
            classInfo = new ClassInfo(this, definition);
        }

        this.nameToClassInfoMap.put(name, classInfo);
        return classInfo;
    }

    public ClassInfo getObjectClassInfoOrNull() {

        return this.nameToClassInfoMap.get("Object");
    }

    public ClassInfo get(
            NClassName classNameToken) {

        String name = classNameToken.getText();
        if (!this.nameToClassInfoMap.containsKey(name)) {
            throw new InterpreterException("class " + name
                    + " has not yet been defined", classNameToken);
        }

        return this.nameToClassInfoMap.get(name);
    }

    public ClassInfo getBooleanClassInfoOrNull() {

        return this.nameToClassInfoMap.get("Boolean");
    }

    public ClassInfo getIntegerClassInfoOrNull() {

        return this.nameToClassInfoMap.get("Integer");
    }

    public ClassInfo getStringClassInfoOrNull() {

        return this.nameToClassInfoMap.get("String");
    }

    public ClassInfo getFloatClassInfoOrNull(){
        return this.nameToClassInfoMap.get("Float");
    }

    public void printVirtualTables(){

        for(Map.Entry<String, ClassInfo> classInfo : nameToClassInfoMap.entrySet()){
            LinkedHashMap<String, MethodInfo> virtualTables = classInfo.getValue().getMethodTable().getVirtualTable();

            if(virtualTables != null){
                System.out.println("============== Virtual Table for " + classInfo.getKey() + " ==============");
                int i = 0;
                for(Map.Entry<String, MethodInfo> method : virtualTables.entrySet()){
                    ++i;
                    System.out.println(i + " - " + method.getValue().getClassInfo().getName() + "." + method.getKey());
                }
            }
        }
    }

    public void printSubTypesTables(){

        for(Map.Entry<String, ClassInfo> classInfo : nameToClassInfoMap.entrySet()){
            LinkedList<ClassInfo> subTypes = classInfo.getValue().getSubTypes();

            if(subTypes != null){
                System.out.println("============== SubTypes Table for " + classInfo.getKey() + " ==============");
                int i = 0;
                for(ClassInfo subType : subTypes){
                    ++i;
                    System.out.println(i + " - " + subType.getName());
                }
            }
        }
    }
}
