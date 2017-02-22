package mino.structure;

import mino.language_mino.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Lam on 09/02/2017.
 */
public class FloatClassInfo
        extends ClassInfo{

    private final Map<Float, Instance> valueMap = new LinkedHashMap<Float, Instance>();

    FloatClassInfo(ClassTable classTable, NClassdef definition) {

        super(classTable, definition);
    }

    @Override
    public Instance newInstance() {

        throw new RuntimeException("invalid instance creation");
    }

    public Instance newFloat(Float value){
        Instance instance = this.valueMap.get(value);

        if (instance == null) {
            instance = new FloatInstance(this, value);
            this.valueMap.put(value, instance);
        }

        return instance;
    }
}
