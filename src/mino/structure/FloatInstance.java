package mino.structure;

/**
 * Created by Lam on 09/02/2017.
 */
public class FloatInstance
        extends Instance{

    private final Float value;

    FloatInstance(
            ClassInfo classInfo,
            Float value) {

        super(classInfo);
        this.value = value;
    }

    public Float getValue(){
        return this.value;
    }


}
