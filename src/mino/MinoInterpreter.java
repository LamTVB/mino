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

package mino;

import java.io.*;

import mino.exception.*;
import mino.language_mino.*;
import mino.structure.ClassTable;
import mino.walker.*;

public class MinoInterpreter {

    public static void main(
            String[] args) {

        Reader in = null;

        if (args.length == 0) {
            // read from standard input
            in = new InputStreamReader(System.in);
        }
        else if (args.length == 1) {
            // read from given file
            System.out.println("TEST");
            try {
                in = new FileReader(args[0]);
            }
            catch (FileNotFoundException e) {
                System.err.println("INPUT ERROR: file not found '" + args[0]
                        + "'.");
                System.exit(1);
            }
        }
        else {
            System.err.println("COMMAND-LINE ERROR: too many arguments.");
            System.exit(1);
        }

        Node syntaxTree = null;

        try {
            // parse
            syntaxTree = new Parser(in).parse();
        }
        catch (IOException e) {
            String inputName;
            if (args.length == 0) {
                inputName = "standard input";
            }
            else {
                inputName = "file '" + args[0] + "'";
            }
            System.err.println("INPUT ERROR: " + e.getMessage()
                    + " while reading " + inputName + ".");
            System.exit(1);
        }
        catch (ParserException e) {
            System.err.println("SYNTAX ERROR: " + e.getMessage() + ".");
            System.exit(1);
        }
        catch (LexerException e) {
            System.err.println("LEXICAL ERROR: " + e.getMessage() + ".");
            System.exit(1);
        }

        InterpreterEngine interpreterEngine = new InterpreterEngine();
        ClassTable classTable = new ClassTable();

        try {
            //Fill classTable
            ClassFinder.find(syntaxTree, classTable);

            //Fill subtypes of each class
            SubTypesFinder.print(syntaxTree, classTable);

            //Collect methods and attributes to each class
            ClassDefinitionFinder.find(syntaxTree, classTable);

            SemanticAnalysis.verify(syntaxTree, classTable);

            //Fill virtual tables of each class and print it
            VirtualTablePrinter.print(syntaxTree, classTable);

            // interpret
            interpreterEngine.visit(syntaxTree, classTable);
        }
        catch (InterpreterException e) {
            System.out.flush();
            System.err.println("INTERPRETER ERROR: " + e.getMessage() + ".");
            interpreterEngine.printStackTrace();
            System.exit(1);
        }catch(SemanticException e){
            System.out.flush();
            System.err.println("SEMANTIC ERROR: " + e.getMessage() + ".");
            System.exit(1);
        }

        // finish normally
        System.exit(0);
    }

}
