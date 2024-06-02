package com.xmeter.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import com.jayway.jsonpath.JsonPath;

public class jsonPathFromVar extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_jsonPathFromVar"; //function name
    static {
        desc.add("Target variable");
        desc.add("JsonPath expression");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String targetVarName = ((CompoundVariable) values[0]).execute().trim();
        String jsonPathExpression = ((CompoundVariable) values[1]).execute().trim();
        String result = "null";
        if (!targetVarName.isEmpty() && !jsonPathExpression.isEmpty()) {
            String targetVar = getVariables().get(targetVarName);
            result = JsonPath.parse(targetVar).read(jsonPathExpression).toString();
        }
        String inputVar = ((CompoundVariable) values[2]).execute().trim();
        if (!inputVar.isEmpty()) {
            getVariables().put(inputVar, result);
        }
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