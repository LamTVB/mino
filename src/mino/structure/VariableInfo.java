package mino.structure;

import mino.language_mino.NClassName;
import mino.language_mino.Token;

/**
 * Created by Lam on 06/03/2017.
 */
public class VariableInfo {

    private String name;

    private NClassName explicitType;

    private Token location;

    public VariableInfo(
            String name,
            NClassName explicitType,
            Token location){

        this.name = name;
        this.explicitType = explicitType;
        this.location = location;
    }

    public String getName() {

        return name;
    }

    public NClassName getExplicitType() {

        return explicitType;
    }

    public Token getLocation() {

        return location;
    }
}
