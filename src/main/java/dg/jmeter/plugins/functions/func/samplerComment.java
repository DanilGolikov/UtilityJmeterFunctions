package dg.jmeter.plugins.functions.func;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;



public class samplerComment extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_samplerComment"; //function name
    static {
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler currentSampler) {
        String result = currentSampler.getComment();
        String inputVar = ((CompoundVariable) values[0]).execute().trim();
        if (!inputVar.isEmpty())
            getVariables().put(inputVar, result);
        return  result;
    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters){
        values = parameters.toArray();
    }
}