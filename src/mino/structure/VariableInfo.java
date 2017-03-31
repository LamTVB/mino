package mino.structure;

import mino.language_mino.NClassName;
import mino.language_mino.Token;

/**
 * Created by Lam on 06/03/2017.
 */
public class VariableInfo {

    private String name;

    private NClassName className;

    private Token location;

    public VariableInfo(
            String name,
            NClassName className,
            Token location){

        this.name = name;
        this.className = className;
        this.location = location;
    }

    public String getName() {

        return name;
    }

    public NClassName getClassName() {

        return className;
    }

    public Token getLocation() {

        return location;
    }
}
